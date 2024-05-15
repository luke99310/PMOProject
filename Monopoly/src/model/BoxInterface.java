package model;

import java.util.Optional;

import enumeration.BoxType;

public interface BoxInterface {
	
	String getName();

    int getCost();

    int getRent();

    BoxType getType();

    Optional<PlayerInterface> getOwner();
    
    int fullSet();
    
    void setOwner(Optional<PlayerInterface> owner);

	boolean isSpecial();

    String toString();

    boolean isSellable();
    
    public void markAsSellable(boolean b);

    void buildHouse();
	
	int getBuiltHouses();
	    
}
