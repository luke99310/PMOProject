package model;

import java.util.Optional;
import java.util.Random;

public class Dices {
	    
    private static Dices dicesInstance;
    private int dice1;
    private int dice2;
    private int doublesCounter;
    private Optional<Player> thrower;
    private Optional<Player> lastPlayer;
    private int dicesSum;
    
    private Dices() {
        this.dice1 = 0;
        this.dice2 = 0;
        this.dicesSum = 0;
        this.doublesCounter = 0;
        this.thrower = Optional.empty();
        this.lastPlayer = Optional.empty();
    }
    
    public int rollDices(Player player) {
    	
    	// rolls the two dices
    	this.dice1 = new Random().nextInt(6) + 1;
        this.dice2 = new Random().nextInt(6) + 1;
        
        // the first thrower initializes the lastPlayer field
        if (this.lastPlayer.isEmpty())
        	this.lastPlayer = Optional.ofNullable(player);
        
        // updates the thrower
        this.thrower = Optional.ofNullable(player);
        	
    	// if the thrower is the same two times in a row the counter increases
    	if (this.thrower.get().equals(lastPlayer.get())) {
    		// player got double so the counter increases
    		if (this.dice1 == this.dice2) 
    			this.doublesCounter++;
    		else
    			this.doublesCounter = -1; // prevents player from throwing the dices again
    	}
    	// if it is not the same player he can throw
    	else
    		this.doublesCounter = 0;
    	
    	switch (this.doublesCounter) {
    		// if 0,1,2 throw is allowed  
    		case 0,1,2:
    			this.dicesSum = this.dice1 + this.dice2;
    		//if 3 goes to prison(illegal throw)
    		case 3:
    			this.dicesSum = -1;
    		// if -1 player is not allowed to move again
    		case -1:
    			this.dicesSum = 0;
    	}
    
    	return this.dicesSum;
    }


    // pattern singleton only allows to create one object of this type
    public static Dices getInstance() {
    	if (dicesInstance == null)
    		dicesInstance = new Dices();
    	return dicesInstance;
    }
}
