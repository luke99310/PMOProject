package model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Player1 implements PlayerInterface{
		
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
	    public Player1(String name, Game game) {
	        this.name = name;
	        this.balance = 1500;
	        this.positionIndex = START_BOX_INDEX_ON_BOARD;
	        this.positionBox = game.getBoard().getBox(START_BOX_INDEX_ON_BOARD);
	        this.inJail = false;
	        this.turnsInJail = 0;
	        this.game = game;
	    }

	    
	    //METHODS
	    public void buyBox(Box box) {
	        if (this.balance >= box.getCost() && box.isSellable()) {
	            // The player becomes the new owner of the box
	            this.pay(box.getCost());
	            box.setOwner(Optional.ofNullable(this));
	            this.properties.add(box);
	            box.notForSale();
	        } else if (box instanceof ChanceBox) {
	            ((ChanceBox) box).executeAction(this);
	        } else if (box instanceof UnexpectedBox) {
	            ((UnexpectedBox) box).executeAction(this);
	        }
	    }
	    
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