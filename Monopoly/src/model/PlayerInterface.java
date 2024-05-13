package model;

import java.util.Set;

public interface PlayerInterface {

    public String buyBox(Box box, int cost);

    void updateBalance(int amount);

    String move(int displacement);

    void putUpForAuction(Box propertyToSell);

    String buildHouse(Box box);

    String getName();

    int getBalance();

    Box getPosition();

    int getPositionIndex();
    
    public int getTurnsInJail();

    boolean isInJail();

    Set<Box> getProperties();

}
