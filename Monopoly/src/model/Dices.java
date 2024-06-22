package model;

import java.util.Random;

import model.Interfaces.DicesInterface;

public class Dices implements DicesInterface {
	    
    private static DicesInterface dicesInstance;
    private int dice1;
    private int dice2;

    // PRIVATE CONSTRUCTOR
    private Dices() {
        this.dice1 = 0;
        this.dice2 = 0;
    }
    
    // METHODS
    // this method simulates the throw of two dices
    public void throwDices() {
    	this.dice1 = new Random().nextInt(1) + 1;
    	this.dice2 = new Random().nextInt(1) + 1;
    }
    
    // returns the first dice
	public int getDice1() {
		return this.dice1;
	}
	
	// returns the second dice
	public int getDice2() {
		return this.dice2;
	}
    
	// returns the only possible instance of the singleton class dices
	public static DicesInterface getInstance() {
		if (dicesInstance == null)
			dicesInstance = new Dices();
		return dicesInstance;
	}
    
}
