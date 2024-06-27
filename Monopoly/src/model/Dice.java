package model;

import java.util.Random;

import model.Interfaces.DiceInterface;

public class Dice implements DiceInterface {
	    
    private static DiceInterface diceInstance;
    private int dice1;
    private int dice2;

    // PRIVATE CONSTRUCTOR
    private Dice() {
        this.dice1 = 0;
        this.dice2 = 0;
    }
    
    // METHODS
    // this method simulates the throw of two dices
    public void throwDices() {
    	this.dice1 = new Random().nextInt(6) + 1;
    	this.dice2 = new Random().nextInt(6) + 1;
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
	public static DiceInterface getInstance() {
		if (diceInstance == null)
			diceInstance = new Dice();
		return diceInstance;
	}
    
}
