package model;

import enumeration.ActionType;

public class Card implements CardInterface{
	
	// FIELDS
	private final String description;
    private final int value;
    private final ActionType action;
    
    // CONSTRUCTOR
    public Card(final String description, final int value, final ActionType action) {
        this.description = description;
    	this.value = value;
    	this.action = action;
    }
    
    // JAIL CARD CONSTRUCTOR
    public Card(final String description, final ActionType action) {
    	this(description, -1, action);
    }

    // METHODS
    public String getDescription() {
        return this.description;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public ActionType getAction() {
        return this.action;
    }
}