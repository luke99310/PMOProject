package model;

import model.Interfaces.CardInterface;
import model.MonopolyTypes.ActionType;

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
    public Card(final String description) {
    	this(description, -1, ActionType.JAIL);
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