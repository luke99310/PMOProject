
package model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import enumeration.BoxType;

public class Player implements PlayerInterface{
	
    private static final int START_BOX_INDEX_ON_BOARD = 0;
    private static final int JAIL_INDEX_ON_BOARD = 6;
    private static final int GO_TO_JAIL_BOX_INDEX_ON_BOARD = 18;
    private static final int TURNS_IN_JAIL = 3;
    private static final int HOUSE_COST = 100;
    private static final int MONEY_EVERY_LAP = 200;

    // FIELDS
    private String name;
    private int balance;
    private int positionIndex;
    private BoxInterface positionBox;
    private boolean inJail;
    private int turnsInJail;
    private GameInterface game;
    private Set<BoxInterface> properties = new HashSet<>();
     
    // CONSTRUCTORS
    public Player(String name, Game game) {
        this.name = name;
        this.balance = 1500;
        this.positionIndex = START_BOX_INDEX_ON_BOARD;
        this.positionBox = game.getBoard().getBox(START_BOX_INDEX_ON_BOARD);
        this.inJail = false;
        this.turnsInJail = 0;
        this.game = game;
        game.addPlayer(this);
    }
    
    // METHODS
    // method that allows to buy a box
    public String buyBox(BoxInterface box, int cost) {
        if (this.balance >= cost && box.isSellable()) {
            // The player becomes the new owner of the box
            this.updateBalance(-cost);
            box.setOwner(Optional.ofNullable(this));
            this.properties.add(box);
            box.markAsSellable(false);
            return "bought: " + box.getName()+ " at " + cost +"$" ;
        }else
        	return "you cannot buy this property";
    }
    
    // checks how many properties of the same color a player has
    public int numberOfOwnedPropertiesOfType(BoxType type) {
        return (int)this.properties.stream()
        					       .filter(box -> box.getType()== type)
        					       .count();
    }

    // checks if the player has all the properties of the same color
    public boolean ownsAllBoxesOfType(BoxType type) {
    	return this.numberOfOwnedPropertiesOfType(type) == type.getNumberOfStreets(); 
    }
 
    // method used for transactions
    public void updateBalance(int amount) {
        // pays the amount	
    	this.balance += amount;
    	// balance reached a negative value, the player loses
        if (this.balance < 0)
        	game.getPlayers().remove(this);
    }
    
    // method that allows the player to pay the rent
    private void payRent(BoxInterface box) {
    	// if someone else owns the property you have to pay the rent
    	if (box.getOwner().isPresent() && !this.properties.contains(box)) {
            int rent = 0;
            if (box.getType().equals(BoxType.STATION)) {
                rent = 25 * box.getOwner().get().numberOfOwnedPropertiesOfType(BoxType.STATION);
            } else {
            	// if the owner has full set the rent is higher
            	rent = box.getOwner().get().ownsAllBoxesOfType(box.getType())?
            																  box.fullSet():
            																  box.getRent();
            }
            // transaction
        	this.updateBalance(-rent);
            box.getOwner().get().updateBalance(rent);
        }
    }
    
    // method that manages player movement
    public String move(int displacement) {
    	// if the displacement is -1 means that you got "double" three times in a row (illegal throw)
    	if (displacement == -1) {
    		this.goToJail();
    		return "You got double three times in a row, go to prison!!!";
    	}
    	// if you are not in jail
    	else if (!inJail && displacement != 0) {
        	System.out.println(displacement);
            int previousPosition = this.positionIndex;
            // calculating the index of the new position using % for a circular array
            int newPosition = (previousPosition + displacement) % this.game.getBoard().getBoxes().size();
            this.positionIndex = newPosition;
            
            // actually finding the new position on the board using the calculated index
            this.positionBox = this.game.getBoard().getBox(newPosition);
            
            // if you pass the start box you get +200$
            if (displacement > 0 && previousPosition > this.positionIndex)
                this.updateBalance(MONEY_EVERY_LAP);
                  
            // based on the box the player landed has to do something
            return this.manageBoxAction();
            
            
        // if you are in jail
    	}else {
            this.turnsInJail--;
            if (this.turnsInJail == 0)
                this.inJail = false;
            return "You passed 1 turn in jail";
        }
    }
    
    // method that manages the action of the box
    private String manageBoxAction() {
    	// checking the type of box the player landed on
        if (this.positionBox.getType().equals(BoxType.CHANCE) || this.positionBox.getType().equals(BoxType.UNEXPECTED))
        	return this.executeAction();
        // you go to jail if you land on the "go to jail" box (box 19)
        else if (this.positionBox.equals(this.game.getBoard().getBox(GO_TO_JAIL_BOX_INDEX_ON_BOARD))) { 
            this.goToJail();
            return "you go to prison!";
        }
        // if the box belong to someone you have to pay the rent
        else if (this.positionBox.getOwner().isPresent() && !this.properties.contains(positionBox)) {
            this.payRent(this.positionBox);
            return "You pay the rent!";
        }
		return "";
    }
    
    // method that executes the action in a card box
	private String executeAction() {
		// draws a card
		CardInterface card = this.game.drawCard(this.positionBox.getType());
		System.out.println(card.getDescription());
		switch (card.getAction()) {
		case BALANCE :
			this.updateBalance(card.getValue());
			break;
		case POSITION:
			this.move(card.getValue());
			break;
		case JAIL:
			this.move(card.getValue()); // sends them to prison
			break;
		default:
			System.out.println("Error, unrecognised action !!");
			break;
		}
		return card.getDescription();
	}
    
    // method that send the player to prison
    private void goToJail() {
        this.inJail = true;
        this.turnsInJail = TURNS_IN_JAIL;
        this.positionBox = game.getBoard().getBoxes().get(JAIL_INDEX_ON_BOARD);
        this.positionIndex = JAIL_INDEX_ON_BOARD;
    }
    
    // method that generates a random boolean emulating player's choice
    private Boolean askPlayer() {
    	return new Random().nextBoolean();
    }
    
    // method that manages player's choice regarding the auction
    public void managePlayerChoice(BoxInterface BoxUpForAuction, int cost) {
    	System.out.println("is " + this.name + " going to buy the property "+ BoxUpForAuction.getName() 
    	                   + " at " + cost + "$ ?");
    	// if the player wants and he can buy the box
    	if (this.askPlayer()) 
    		this.buyBox(BoxUpForAuction, cost);
    	else
    		System.out.println(this.name + " did not buy the property.");
    	
    }
    
    // method that manages the auction
    public void putUpForAuction(BoxInterface propertyToSell) {
    	// if the seller is actually the owner of the property he can put it to auction
    	if (this.getProperties().contains(propertyToSell)) {
    		System.out.println(this.getName() + " puts the property " + propertyToSell +" up for auction ");
    		
    		// variable for preventing the property being sold to the owner
    		Player seller = this;         	
    		// property is now sellable
    		propertyToSell.markAsSellable(true);	
    		int decrement = 0;      
    		// asking every player (except seller) 3 times if they want to buy the property 
    		// and lowering the price every time
    		// checking if the property is sellable for loop optimization (if it's sold the loop stops)
    		for (int i = 0; i < 3 && propertyToSell.isSellable(); i++) {
    			// reduction of the cost by 10% every loop
    			decrement += (propertyToSell.getCost() / 10);
    			for (PlayerInterface p: this.game.getPlayers()) {
    				if (propertyToSell.isSellable() && p != seller)
    					p.managePlayerChoice(propertyToSell, propertyToSell.getCost() - decrement);
    			}
    		}
    		// if none buys the property it is sold to the bank
    		if (propertyToSell.isSellable()) {
    			propertyToSell.setOwner(Optional.empty());
    			System.out.println(seller.getName() +  " sold the property " + propertyToSell.getName() +
    					           " to the bank for " + (propertyToSell.getCost() - decrement) + "$");
    		}
    		// either the bank or other players bought the property 
    		seller.updateBalance(propertyToSell.getCost() - decrement); 
    		seller.properties.remove(propertyToSell);
    	}
    }
    
    // method that allows the player to buy a house
    public String buildHouse(BoxInterface box) {
    	if (this.properties.contains(box) && box.getType() != BoxType.STATION && !box.isSpecial()) {
    		if (this.ownsAllBoxesOfType(box.getType())) {
    			if (this.balance >= HOUSE_COST) {
    				box.buildHouse();
    				this.updateBalance(-HOUSE_COST);
    				return "house created";
    			} else {
    				return "doesn't have enough money to buy the house";
    			}
    		} else {
    			return "you dont have the full set";
    		}
    	} else {
    		return "you can't build a house in this property";
    	}
    }
    
    // equals
    @Override
    public boolean equals(Object o) {
    	if (o == this)
    		return true;
    	if (!(o instanceof Player))
    		return false;
    	Player p = (Player)o;
    	return this.getName().equals(p.getName()) && this.getBalance() == p.getBalance() 
    		&& this.positionIndex == p.getPositionIndex() && this.positionBox.equals(p.getPosition())
    		&& this.inJail == p.isInJail() && this.turnsInJail == p.getTurnsInJail()
    		&& this.properties.equals(p.getProperties());
    } 
    
    // getters
	public String getName() {
		return this.name;
	}
	
	public int getBalance() {
		return this.balance;
	}

    public BoxInterface getPosition() {
        return this.positionBox;
    }
    
	public int getPositionIndex() {
		return this.positionIndex;
	}
	
	public int getTurnsInJail() {
		return this.turnsInJail;
	}
    
	public boolean isInJail() {
		return this.inJail;
	}
	
	public Set<BoxInterface> getProperties() {
		return new HashSet<BoxInterface>(this.properties);
	}	
	
	public String toString() {
        return this.name;
    }
}