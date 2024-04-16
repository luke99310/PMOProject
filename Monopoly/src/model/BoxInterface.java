package model;

import java.util.Optional;

public interface BoxInterface {
	
	String getName();

    int getCost();

    int getRent();

    BoxType getType();

    Optional<Player> getOwner();
    
    int fullColorRent();
    
    void setOwner(Optional<Player> owner);
    
    void sell(); //questo metodo fa partire l'asta, 
    			 //oppure se nessuno acquista la casella, la compra la banca 

	boolean isSpecial();

    String toString();

    boolean isSellable();

    void buildHouse();
	
	int getBuiltHouses();
	    
}
