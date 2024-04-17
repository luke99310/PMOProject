package model;

public class Card {
	
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

    // METODS
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