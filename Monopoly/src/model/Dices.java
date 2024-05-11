package model;

import java.util.ArrayList;
import java.util.List;
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
    private List<String> log;
    
    private Dices() {
        this.dice1 = 0;
        this.dice2 = 0;
        this.dicesSum = 0;
        this.doublesCounter = 0;
        this.thrower = Optional.empty();
        this.lastPlayer = Optional.empty();
        this.log = new ArrayList<String>();
    }
    
    public int rollDices(Player player) {
    	
    	// rolls the two dices
    	this.dice1 = new Random().nextInt(1) + 0;
        this.dice2 = new Random().nextInt(1) + 0;
        
        // lastPlayer tracks the last player who threw the dices
        // if player is the first one to throw the dices initializes the variable
        if (this.lastPlayer.isEmpty())
        	this.lastPlayer = Optional.ofNullable(player);
        else
        	this.lastPlayer = this.thrower;
        
        // updates the thrower
        this.thrower = Optional.ofNullable(player);
        	
        // if thrower is not the last player the counter resets
        if (!this.thrower.get().equals(lastPlayer.get()))
        	this.doublesCounter = 0;

        // player is able to throw the dice and got double so the counter increases
        if (this.dice1 == this.dice2 && this.doublesCounter != -1) 
        	this.doublesCounter++;
    	
        // using the doublesCounter to decide what value to return
        switch(this.doublesCounter) {
        	// the counter is -1, that means the player can't move
        	case -1:{
        		this.dicesSum = 0;
        		this.log.add("non puoi piu spostarti!!!!!     counter: "+this.doublesCounter);
        		break;
        	}
	    	// player did not make double so he can't throw again
        	case 0:{
	    		this.dicesSum = this.dice1 + this.dice2;
	    		this.doublesCounter = -1; // prevents the player from throwing again
	    		this.log.add("LANCIO REGOLARE!!! dado1: " + this.dice1 + " dado2: " + this.dice2 +" counter: "+ this.doublesCounter);
	    		break;
        	}
	    	// player moves normally after he got double
	    	case 1,2:{
	    		// if he gets a normal throw after a double he can't throw again
	    		if (this.dice1 != this.dice2)
	    			this.doublesCounter = -1; 
	    		this.dicesSum = this.dice1 + this.dice2;
	    		this.log.add("dado1: " + this.dice1 + " dado2: " + this.dice2 +" counter: "+ this.doublesCounter);
	    		break;
	    	}
	    	// player goes to prison
	    	case 3:{
	    		this.doublesCounter = -1; // prevents the player from throwing again
	    		this.dicesSum = -1;
	    		this.log.add("IN PRIGIONE!!! dado1: " + this.dice1 + " dado2: " + this.dice2+" counter: "+ this.doublesCounter);
	    		break;
	    	}
        }
        
    	return this.dicesSum;
    }


    // pattern singleton only allows to create one object of this type
    public static Dices getInstance() {
    	if (dicesInstance == null)
    		dicesInstance = new Dices();
    	return dicesInstance;
    }
    
    // DA TOGLIERE
    public void getLog() {
    	this.log.stream()
    			.forEach(i -> System.out.println(i));
    }
}
