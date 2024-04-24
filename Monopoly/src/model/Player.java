
package model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class Player implements PlayerInterface{
		
	    private static final int START_BOX_INDEX_ON_BOARD = 0;
	    private static final int JAIL_EXIT_BOX_INDEX_ON_BOARD = 6;
	    private static final int JAIL_BOX_INDEX_ON_BOARD = 18;
	    private static final int TURNS_IN_JAIL = 3;
	    private static final int HOUSE_COST = 100;
	    private static final int MONEY_EVERY_LAP = 200;

	    //FIELDS
	    private String name;
	    private int balance;
	    private int positionIndex;
	    private Box positionBox;
	    private boolean inJail;
	    private int turnsInJail;
	    private Game game;
	    private Set<Box> properties = new HashSet<>();
	    
	    //CONSTRUCTORS
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
	    
	    //METHODS
	    // method that allows to buy a box
	    public void buyBox(Box box, int cost) {
	        if (this.balance >= cost && box.isSellable()) {
	            // The player becomes the new owner of the box
	            this.updateBalance(-cost);
	            box.setOwner(Optional.ofNullable(this));
	            this.properties.add(box);
	            box.markAsSellable(false);
	            System.out.println(this.name + " bought the property " + box.getName() 
				   + " at " + cost +"$" );
	        }
	    }
	    
	    // checks how many properties of the same color a player has
	    public int numberOfOwnedPropertiesOfType(BoxType type) {
	        return  (int)this.properties.stream()
	        					        .filter(box -> box.getType()== type)
	        					        .count();
	    }
	
	    // checks if the player has all the properties of the same color
	    public boolean ownsAllBoxesOfType(BoxType type) {
	    	return this.numberOfOwnedPropertiesOfType(type) == type.getNumberOfStreets();
	    }
	 
	    // method used for transactions
	    public void updateBalance(int amount) {
	        this.balance += amount;
	    }
	    
	    // method that allows the player to pay the rent
	    public void payRent(Box box) {
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
	    public void move(int displacement) {
	    	// if the displacement is 0 means that you got "double" three times in a row (illegal throw)
	    	if (displacement == 0)
	    		this.goToJail();
	    	// if you are not in jail
	    	else if (!inJail) {
	        	System.out.println(displacement);
	            int previousPosition = this.positionIndex;
	            // calculating the index of the new position using % for a circular array
	            int newPosition = (previousPosition + displacement) % game.getBoard().getBoxes().size();
	            this.positionIndex = newPosition;
	            
	            // actually finding the new position on the board using the calculated index
	            this.positionBox = game.getBoard().getBoxes().get(newPosition);
	            
	            // if you pass the start box you get +200$
	            if (displacement > 0 && previousPosition > this.positionIndex)
	                this.updateBalance(MONEY_EVERY_LAP);
	                  
	            // based on the box the player landed has to do something
	            this.manageBoxAction();
	            
	        // if you are in jail
	    	}else {
	            this.turnsInJail--;
	            if (this.turnsInJail == 0) {
	                this.inJail = false;
	                this.positionBox = game.getBoard().getBoxes().get(JAIL_EXIT_BOX_INDEX_ON_BOARD);
	                this.positionIndex = JAIL_EXIT_BOX_INDEX_ON_BOARD;
	            }
	        }
	    }
	    
	    // method that manages the action of the box
	    private void manageBoxAction() {
	    	// checking the type of box the player landed on
            if (positionBox instanceof ChanceBox) 
            	((ChanceBox) positionBox).executeAction(this);
            else if (positionBox instanceof UnexpectedBox)
            	((UnexpectedBox) positionBox).executeAction(this);
            // you go to jail if you land on the "go to jail" box (box 19)
            else if (this.positionBox.equals(this.game.getBoard().getBox(JAIL_BOX_INDEX_ON_BOARD))) 
                this.goToJail();
            // if the box belong to someone you have to pay the rent
            else if (positionBox.getOwner().isPresent() && !this.properties.contains(positionBox))
                payRent(this.positionBox);
	    }
	    
	    // method that send the player to prison
	    private void goToJail() {
	        this.inJail = true;
	        this.turnsInJail = TURNS_IN_JAIL;
	        this.positionBox = game.getBoard().getBoxes().get(JAIL_BOX_INDEX_ON_BOARD);
	    }
	    
	    // method that generates a random boolean emulating player's choice
	    private Boolean askPlayer() {
	    	return new Random().nextBoolean();
	    }
	    
	    // method that manages player's choice regarding the auction
	    private void managePlayerChoice(Box BoxUpForAuction, int cost) {
	    	System.out.println("is " + this.name + " going to buy the property "+ BoxUpForAuction.getName() 
	    	                   + " at " + cost + "$ ?");
	    	// if the player wants and he can buy the box
	    	if (this.askPlayer()) 
	    		this.buyBox(BoxUpForAuction, cost);
	    	else
	    		System.out.println(this.name + " did not buy the property.");
	    	
	    }
	    
	    // method that manages the auction
	    public void putUpForAuction(Box propertyToSell) {
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
	    			for (Player p: this.game.getPlayers()) {
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
	    public void buildHouse(Box box) {
	    	if (this.properties.contains(box) && box.getType() != BoxType.STATION && !box.isSpecial()) {
	    		if (this.ownsAllBoxesOfType(box.getType())) {
	    			if (this.balance >= HOUSE_COST) {
	    				box.buildHouse();
	    				this.updateBalance(-HOUSE_COST);
	    			} else {
	    				System.out.println("doesn't have enough money to buy the house");
	    			}
	    		} else {
	    			System.out.println("you dont have the complete series");
	    		}
	    	} else {
	    		System.out.println("you can't build a house in this property");
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

	    public Box getPosition() {
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
		
		public Set<Box> getProperties() {
			return new HashSet<Box>(this.properties);
		}
			
	}