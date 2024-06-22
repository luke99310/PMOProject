package model;

import java.util.Set;

import enumeration.BoxType;

public interface PlayerInterface {

    String buyBox(BoxInterface box, int cost);
    
    int numberOfOwnedPropertiesOfType(BoxType type);
    
    boolean hasFullSet(BoxType type);

    void updateBalance(int amount);

    String move(int displacement);
    
    void managePlayerChoice(BoxInterface BoxUpForAuction, int cost);

    void putUpForAuction(BoxInterface propertyToSell);

    String buildHouse(BoxInterface box);

    String getName();

    int getBalance();

    BoxInterface getPosition();

    int getPositionIndex();
    
    public int getTurnsInJail();

    boolean isInJail();

    Set<BoxInterface> getProperties();

}
