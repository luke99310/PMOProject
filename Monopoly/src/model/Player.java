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
	    public void buyBox(Box box) {
	        if (this.balance >= box.getCost() && box.isSellable()) {
	            // The player becomes the new owner of the box
	            this.pay(box.getCost());
	            box.setOwner(Optional.ofNullable(this));
	            this.properties.add(box);
	            box.markAsSold();
	        } else if (box instanceof ChanceBox) {
	            ((ChanceBox) box).executeAction(this);
	        } else if (box instanceof UnexpectedBox) {
	            ((UnexpectedBox) box).executeAction(this);
	        }
	    }
	    
	    /*
	    public void purchaseBoxForSale(Box box) {
	        if (this.balance >= box.getCost() && box.isSellable() && !this.properties.contains(box)) {
	            // The player pays the cost of the box
	            this.pay(box.getCost());

	            // The old owner loses the ownership of the box
	            Player oldOwner = box.getOwner().get();
	            oldOwner.removeProperty(box);

	            // The player becomes the new owner of the box
	            box.setOwner(Optional.ofNullable(this));
	            this.properties.add(box);
	            box.notForSale();

	            // Remove the box from the list of boxes for sale in the game
	            this.game.removeBoxForSale(box);
	        }
	    }

	    
	    public void sellBox(Box box) {
	        if (box.getOwner().get() == this) {
	            box.sell();
	            this.game.addBoxForSale(box);
	        }
	    }
	    */
	    
	    public boolean ownsAllBoxesOfType(BoxType type) {
	        int count = 0;
	        for (Box box : this.properties) {
	            if (box.getType() == type) {
	                count++;
	            }
	        }
	        return count == type.getNumberOfStreets();
	    }
	    
	    public int numberOfOwnedPropertiesOfType(BoxType type) {
	        int count = 0;
	        for (Box box : this.properties) {
	            if (box.getType() == type) {
	                count++;
	            }
	        }
	        return count;
	    }

		public void collect(int rent) {
			this.balance += rent;
		}
		
		
	    public void pay(int amount) {
	        this.balance -= amount;
	    }
	    
	    
	    public void updateBalance(int amount) {
	        this.balance += amount;
	    }

	    
	    public void payRent(Box box) {
	        if(box.getOwner().isPresent() && !this.properties.contains(box) && box.getType().equals(BoxType.STATION)) {
	        	int numberOfProperties = box.getOwner().get().numberOfOwnedPropertiesOfType(BoxType.STATION);
	        	int rent = 0;
	        	while(numberOfProperties > 0) {
	        		rent += 25;
	        		numberOfProperties --;
	        	}
	        	this.pay(rent);
	            box.getOwner().get().collect(rent);
	        } else if (box.getOwner().isPresent() && !this.properties.contains(box)) {
	            int rent = box.getRent();
	            if (box.getOwner().get().ownsAllBoxesOfType(box.getType())) {
	                rent = box.fullColorRent();
	            }
	        	this.pay(rent);
	            box.getOwner().get().collect(rent);
	        }
	    }

	    
	    public void move(int displacement) {
	        if (!inJail) {
	        	System.out.println(displacement);
	            int previousPosition = this.positionIndex;
	            int newPosition = (previousPosition + displacement) % game.getBoard().getBoxes().size();
	            this.positionIndex = newPosition;
	            this.positionBox = game.getBoard().getBoxes().get(newPosition);
	            
	            if (displacement > 0 && previousPosition > newPosition) {
	                this.collect(200);
	            }

	            // you go to jail only if the box is the transit in position 10.
	            if (this.positionBox.getName().equals("Jail")) {
	                this.goToJail();
	            } else {
	                payRent(this.positionBox);
	            }
	            
	            this.buyBox(this.positionBox);
	        } else {
	            this.turnsInJail--;
	            if (this.turnsInJail == 0) {
	                this.inJail = false;
	                this.positionBox = game.getBoard().getBoxes().get(JAIL_EXIT_BOX_INDEX_ON_BOARD);
	            }
	        }
	    }
	    
	    
	    private void goToJail() {
	        this.inJail = true;
	        this.turnsInJail = TURNS_IN_JAIL;
	        this.positionBox = game.getBoard().getBoxes().get(JAIL_BOX_INDEX_ON_BOARD);
	    }
	    
	    // function that generates a random boolean emulating player's choice
	    private Boolean askPlayer() {
	    	Random rand = new Random();
	    	Boolean choice = rand.nextBoolean();
	    	return choice;
	    }
	    
	    // function that manages player's choice regarding the auction
	    public void managePlayerChoice(Box BoxUpForAuction, int price) {
	    	System.out.println("is " + this.name + " going to buy the property "+ BoxUpForAuction.getName() 
	    	                   + " at " + price + "$ ?");
	    	if (this.askPlayer()) {
	    		Player oldOwner = BoxUpForAuction.getOwner().get();
	    		oldOwner.updateBalance(price);
	    		BoxUpForAuction.setOwner(Optional.empty());
	    		System.out.println(this.name + " bought the property " + BoxUpForAuction.getName() 
	    						   + " at " + price +"$" );
	    		this.balance -= price;
	    		this.properties.add(BoxUpForAuction);
	    		BoxUpForAuction.markAsSold();
	    	}else{
	    		System.out.println(this.name + " did not buy the property.");
	    	}
	    }
	    
	    // function that manages the auction itself
	    public void putUpForAuction(Player seller, Box propertyToSell) {
	    	// if the seller is actually the owner of the property he can put it to auction
	    	if (seller.getProperties().contains(propertyToSell)) {
	    		System.out.println(seller.getName() + " puts the property " + propertyToSell +" up for auction ");
	    		propertyToSell.sell();
	    		int decrement = 0;
	    		// asking every player (except seller) 3 times if they want to buy the property 
	    		// and lowering the price very time
	    		for (int i = 0; i < 3; i++) {
	    			decrement +=(propertyToSell.getCost() / 10);
	    			for (Player p: this.game.getPlayers()) {
	    				if (propertyToSell.isSellable() == true && p != seller)
	    					p.managePlayerChoice(propertyToSell, propertyToSell.getCost() - decrement);
	    			}
	    		}
	    		// if none buys the property it is sold to the bank
	    		if (propertyToSell.isSellable()) {
	    			propertyToSell.setOwner(Optional.empty());
	    			seller.updateBalance(propertyToSell.getCost() - decrement);
	    			System.out.println(seller.getName() +  " sold the property " + propertyToSell.getName() +
	    					           " to the bank for " + (propertyToSell.getCost() - decrement) + "$");
	    		}
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
		
		private void removeProperty(Box box) {
		    this.properties.remove(box);
		}
		
		public void buildHouse(Box box) {
		    if (this.properties.contains(box) && box.getType() != BoxType.STATION && !box.isSpecial()) {
		        if (this.ownsAllBoxesOfType(box.getType())) {
		            if (this.balance >= HOUSE_COST) {
		                box.buildHouse();
		                this.pay(HOUSE_COST);
		            } else {
		                System.out.println("Non hai ssoldi per costruire la casa");
		            }
		        } else {
		            System.out.println("Non possiedi tutte le caselle dello stesso colore");
		        }
		    } else {
		        System.out.println("Non puoi costruire case in questa proprietà");
		    }
		}
	}