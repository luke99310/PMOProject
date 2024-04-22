package model;

import java.util.Optional;

public class Box implements BoxInterface{
    
	private static final int DOUBLE_RENT_FOR_COMPLETE_SERIES = 2;
    	
	//CAMPI
	private final String name;		//nome casella
	private final int cost;			//costo casella
	private Optional<Player> owner;	//proprietario della casella
	private final BoxType type;		//tipo casella
	private final int rent;			//affitto della casella
    private int builtHouses;		//numero case costruite sulla casella
	private boolean sellable;		//se è in vendita
	private final boolean isSpecial;//se è speciale
        
    //COSTRUTTORI
    public Box(final String name, final int cost, final int rent, final BoxType type, final boolean isSpecial) {
    	this.name = name;
    	this.cost = cost;
    	this.rent = rent;
    	this.type = type;
    	this.isSpecial = isSpecial;
    	this.owner = Optional.empty();
        this.builtHouses = 0;
    	this.sellable = !isSpecial;  //le caselle speciali non possono essere in vendita
  	}
    
    //METODI
    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public Optional<Player> getOwner() {
        return this.owner;
    }

    public BoxType getType() {
        return this.type;
    }
    
    public int getRent() {
        return this.rent;
    }
    
    public int fullColorRent() {
    	return this.rent * (this.builtHouses > 0 ? DOUBLE_RENT_FOR_COMPLETE_SERIES + this.builtHouses : 
    		                                       DOUBLE_RENT_FOR_COMPLETE_SERIES);
    }
    
	public int getBuiltHouses() {
        return this.builtHouses;
    }
	
    public void buildHouse() {
        if (this.builtHouses < 2) {
            this.builtHouses++;
        }else {
            System.out.println("Hai raggiunto il limite massimo di numero di case che puoi costruire !!");
        }
    }
    
	public boolean isSpecial() {
		return this.isSpecial;
	}

    public boolean isSellable() {
        return this.sellable;
    }

    public void sell() {
        this.sellable = true;
    }
    
    public void markAsSold() {
    	this.sellable = false;
    }
    
    public void setOwner(Optional<Player> owner) {
        this.owner = owner;
    }

    public String toString() {
    	return this.name;
    }

}

