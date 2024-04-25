package model;

import java.util.Set;

public interface PlayerInterface {

    public void buyBox(Box box, int cost);

    void updateBalance(int amount);

    void move(int displacement);

    void putUpForAuction(Box propertyToSell);

    void buildHouse(Box box);

    String getName();

    int getBalance();

    Box getPosition();

    int getPositionIndex();

    boolean isInJail();

    Set<Box> getProperties();

}
