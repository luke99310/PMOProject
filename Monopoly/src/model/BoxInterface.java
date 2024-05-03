package model;

import java.util.Optional;

public interface BoxInterface {
	
	String getName();

    int getCost();

    int getRent();

    BoxType getType();

    Optional<Player> getOwner();
    
    int fullSet();
    
    void setOwner(Optional<Player> owner);

	boolean isSpecial();

    String toString();

    boolean isSellable();
    
    void markAsSellable(boolean b);

    void buildHouse();
	
	int getBuiltHouses();
	    
}
