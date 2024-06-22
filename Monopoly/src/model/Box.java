package model;

import java.util.Optional;

import model.Interfaces.BoxInterface;
import model.Interfaces.PlayerInterface;
import model.MonopolyTypes.BoxType;

public class Box implements BoxInterface{
    
	private static final int DOUBLE_RENT_FOR_COMPLETE_SERIES = 2;
    	
	// FIELDS
	private final String name;		// box name
	private final int cost;			// box cost
	private Optional<PlayerInterface> owner;	// box owner
	private final BoxType type;		// box type
	private final int rent;			// box rent
    private int builtHouses;		// amount of houses on the box
	private boolean sellable;		// is sellable
	private final boolean isSpecial;// is special (transit, chance, unexpected...)
        
    // CONSTRUCTOR
    public Box(final String name, final int cost, final int rent, final BoxType type, final boolean isSpecial) {
    	this.name = name;
    	this.cost = cost;
    	this.rent = rent;
    	this.type = type;
    	this.isSpecial = isSpecial;
    	this.owner = Optional.empty();
        this.builtHouses = 0;
    	this.sellable = !isSpecial;  // special boxes can't be sold
    }
    
    // METHODS
    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public int getRent() {
    	return this.rent;
    }

    public BoxType getType() {
    	return this.type;
    }
    
    public Optional<PlayerInterface> getOwner() {
        return this.owner;
    }
    
    public int fullSet() {
    	return this.rent * (this.builtHouses > 0 ? DOUBLE_RENT_FOR_COMPLETE_SERIES + this.builtHouses : 
    		                                       DOUBLE_RENT_FOR_COMPLETE_SERIES);
    }

    public void setOwner(Optional<PlayerInterface> owner) {
    	this.owner = owner;
    }
    
    public boolean isSpecial() {
    	return this.isSpecial;
    }

    public String toString() {
    	return this.name;
    }

    public boolean isSellable() {	
    	return this.sellable;
    }

    public void markAsSellable(boolean b) {
    	this.sellable = b;
    }

    public void buildHouse() {
        if (this.builtHouses < 2) {
            this.builtHouses++;
        }else {
            System.out.println("reached max limit of houses!!!");
        }
    }
    
    public int getBuiltHouses() {
    	return this.builtHouses;
    }
    
    // equals
    @Override
    public boolean equals(Object o) {
    	if (o == this)
    		return true;
    	if (!(o instanceof Box))
    		return false;
    	Box b = (Box)o;
    	return this.name.equals(b.getName()) && this.cost == b.getCost()
    		   && this.owner.equals(b.getOwner()) && this.type.equals(b.getType())
    		   && this.rent == b.getRent() && this.builtHouses == b.getBuiltHouses()
    		   && this.sellable == b.isSellable() && this.isSpecial == b.isSpecial();
    } 

}

