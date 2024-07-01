package model.Interfaces;

import java.util.Optional;

import model.MonopolyTypes.BoxType;

public interface BoxInterface {
	
	String getName();

    int getCost();

    int getRent();

    BoxType getType();

    Optional<PlayerInterface> getOwner();
    
    int fullSet();
    
    void setOwner(Optional<PlayerInterface> owner);

	//boolean isSpecial();

    String toString();

    boolean isSellable();
    
    void markAsSellable(boolean b);

    boolean buildHouse();
	
	int getBuiltHouses();
	    
}