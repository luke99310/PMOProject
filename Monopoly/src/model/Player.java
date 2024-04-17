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
	            box.markAsSold();
	            System.out.println(this.name + " bought the property " + box.getName() 
				   + " at " + cost +"$" );
	        } else if (box instanceof ChanceBox) {
	            ((ChanceBox) box).executeAction(this);
	        } else if (box instanceof UnexpectedBox) {
	            ((UnexpectedBox) box).executeAction(this);
	        }
	    }
	    
	    // checks if the player has all the properties of the same color
	    public boolean ownsAllBoxesOfType(BoxType type) {
	        int count = 0;
	        for (Box box : this.properties) {
	            if (box.getType() == type) {
	                count++;
	            }
	        }
	        return count == type.getNumberOfStreets();
	    }
	    
	    // checks how many properties of the same color a player has
	    public int numberOfOwnedPropertiesOfType(BoxType type) {
	        int count = 0;
	        for (Box box : this.properties) {
	            if (box.getType() == type) {
	                count++;
	            }
	        }
	        return count;
	    }
	
	    // method used for transactions
	    public void updateBalance(int amount) {
	        this.balance += amount;
	    }
	    
	    // method that allows the player to pay the rent
	    public void payRent(Box box) {
	        if(box.getType().equals(BoxType.STATION)) {
	        	int numberOfProperties = box.getOwner().get().numberOfOwnedPropertiesOfType(BoxType.STATION);
	        	int rent = 0;
	        	while(numberOfProperties > 0) {
	        		rent += 25;
	        		numberOfProperties --;
	        	}
	        	this.updateBalance(-rent);
	            box.getOwner().get().updateBalance(rent);
	        } else if (box.getOwner().isPresent() && !this.properties.contains(box)) {
	            int rent = box.getRent();
	            if (box.getOwner().get().ownsAllBoxesOfType(box.getType())) {
	                rent = box.fullColorRent();
	            }
	        	this.updateBalance(-rent);
	            box.getOwner().get().updateBalance(rent);
	        }
	    }

	    // method that manages player movement
	    public void move(int displacement) {
	    	// if the displacement is 0 means that you got double three times in a row (illegal throw)
	    	if (displacement == 0)
	    		this.goToJail();
	    	// if you are not in jail
	    	else if (!inJail) {
	        	System.out.println(displacement);
	            int previousPosition = this.positionIndex;
	            int newPosition = (previousPosition + displacement) % game.getBoard().getBoxes().size();
	            this.positionIndex = newPosition;
	            this.positionBox = game.getBoard().getBoxes().get(newPosition);
	            
	            // if you pass the start box you get +200$
	            if (displacement > 0 && previousPosition > newPosition) {
	                this.updateBalance(MONEY_EVERY_LAP);
	            }
	            
	            // checking the type of box the player landed on
	            if (positionBox instanceof ChanceBox) 
	            	((ChanceBox) positionBox).executeAction(this);
	            else if (positionBox instanceof UnexpectedBox)
	            	((UnexpectedBox) positionBox).executeAction(this);
	            // you go to jail if you land on the "go to jail" box (position 19)
	            else if (this.positionBox.getName().equals("Jail")) 
	                this.goToJail();
	            // if the box belong to someone you have to pay the rent
	            else if (positionBox.getOwner().isPresent() && !this.properties.contains(positionBox))
	                payRent(this.positionBox);
	        // if you are in jail
	    	}else {
	            this.turnsInJail--;
	            if (this.turnsInJail == 0) {
	                this.inJail = false;
	                this.positionBox = game.getBoard().getBoxes().get(JAIL_EXIT_BOX_INDEX_ON_BOARD);
	            }
	        }
	    }
	    
	    // method that send the player to prison
	    private void goToJail() {
	        this.inJail = true;
	        this.turnsInJail = TURNS_IN_JAIL;
	        this.positionBox = game.getBoard().getBoxes().get(JAIL_BOX_INDEX_ON_BOARD);
	    }
	    
	    // method that generates a random boolean emulating player's choice
	    private Boolean askPlayer() {
	    	Random rand = new Random();
	    	Boolean choice = rand.nextBoolean();
	    	return choice;
	    }
	    
	    // method that manages player's choice regarding the auction
	    public void managePlayerChoice(Box BoxUpForAuction, int cost) {
	    	System.out.println("is " + this.name + " going to buy the property "+ BoxUpForAuction.getName() 
	    	                   + " at " + cost + "$ ?");
	    	// if the player wants and he can buy the box
	    	if (this.askPlayer()) 
	    		this.buyBox(BoxUpForAuction, cost);
	    	else
	    		System.out.println(this.name + " did not buy the property.");
	    	
	    }
	    
	    // method that manages the auction itself
	    public void putUpForAuction(Player seller, Box propertyToSell) {
	    	// if the seller is actually the owner of the property he can put it to auction
	    	if (seller.getProperties().contains(propertyToSell)) {
	    		System.out.println(seller.getName() + " puts the property " + propertyToSell +" up for auction ");
	    		propertyToSell.sell();
	    		int decrement = 0;
	    		// asking every player (except seller) 3 times if they want to buy the property 
	    		// and lowering the price very time
	    		// checking if the property is sellable for loop optimization
	    		for (int i = 0; i < 3 && propertyToSell.isSellable(); i++) {
	    			// reduction of the cost by 10% every loop
	    			decrement +=(propertyToSell.getCost() / 10);
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
	    		// either the bank or the other players bought the property 
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
	    				this.updateBalance(HOUSE_COST);
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
	    
		public boolean isInJail() {
			return this.inJail;
		}
		
		public Set<Box> getProperties() {
			return new HashSet<Box>(this.properties);
		}
			
	}