package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import enumeration.BoxType;

public class Game implements GameInterface{
		
	// FIELDS
	private List<PlayerInterface> players;
	private BoardInterface board;
	private int lastPlayerIndex;
    private int currentPlayerIndex;
    private DicesInterface dices;
    private int doublesCounter;
    private int unexpectedIndex; // this index is used for drawing a new card every time
    private int chanceIndex;     // same as above but in the chance cards deck 
	private BankInterface bank;
	
	// CONSTRUCTOR
	public Game() {
		this.players = new ArrayList<>();
		this.board = new Board();
        this.lastPlayerIndex = this.currentPlayerIndex = 0;
        dices = Dices.getInstance();
        bank = Bank.getInstance();
        this.doublesCounter = 0;
        this.unexpectedIndex = 0;
        this.chanceIndex = 0;
	}

	// METHODS
	public void addPlayer(PlayerInterface player) {
		this.players.add(player);
	}

	public void startGame() {
		Collections.shuffle(players);
	}
	
	// this method manages the results of the throws every player does
	public int rollDices(){
		
		// rolls the two dices
		dices.throwDices();
		int dice1 = dices.getDice1(); 
		int dice2 = dices.getDice2();
        	
        // if current player is not the last player the counter resets
        if (this.currentPlayerIndex != this.lastPlayerIndex)
        	this.doublesCounter = 0;

        // player is able to throw the dice and got double so the counter increases
        if (dice1 == dice2 && this.doublesCounter != -1) 
        	this.doublesCounter++;
    	
        // using the doublesCounter to decide what value to return
        switch(this.doublesCounter) {
        	// the counter is -1, that means the player can't move anymore
        	case -1:
        		System.out.println(this.getCurrentPlayer() + " can't move (-1)");
        		return 0;
	    	// player did not make double so he can't throw again
        	case 0:
	    		this.doublesCounter = -1;
	    		System.out.println(this.getCurrentPlayer() + " got a normal throw (0)");
	    		return dice1 + dice2;
	    	// player moves normally after he got double
	    	case 1,2:
	    		// if he gets a normal throw after a double he can't throw again
	    		if (dice1 != dice2) {
	    			this.doublesCounter = -1; 
	    			System.out.println(this.getCurrentPlayer() + " got double then regular ");
	    		}
	    		else
	    			System.out.println(this.getCurrentPlayer() + " got double (1/2)");
	    		return dice1 + dice2;
	    	// player goes to prison because he got double three times in a row
	    	case 3:
	    		this.doublesCounter = -1;
	    		System.out.println(this.getCurrentPlayer() + " got double three times");
	    		return -1;
	    	default:
	    		return 0;
        }
	}

	public List<PlayerInterface> getPlayers() {
		return this.players;
	}

	public BoardInterface getBoard() {
		return this.board;
	}
	
	public BankInterface getBank() {
		return this.bank;
	}

	// returns current player
    public PlayerInterface getCurrentPlayer() {
        return players.get(this.currentPlayerIndex);
    }

    // passes the turn to the next player
    public void nextPlayer() {
    	this.lastPlayerIndex = this.currentPlayerIndex;
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
    }
	
	public CardInterface drawCard(BoxType boxType) {
		switch (boxType) {
			case UNEXPECTED:
				// checking if there are cards available 
				this.checkDeck(boxType);
				// drawing the unexpected card
				System.out.println("the player is drawing the unexpected card number " +
								   (this.unexpectedIndex + 1));
				return this.board.getCards(boxType).get(this.unexpectedIndex++);
			case CHANCE:
				// checking if there are cards available 
				this.checkDeck(boxType);
				// drawing the chance card
				System.out.println("the player is drawing the chance card number " +
						   (this.chanceIndex + 1));
				return this.board.getCards(boxType).get(this.chanceIndex++);
			// if the wrong type of box is given an exception is thrown
			default:
				throw new IllegalArgumentException("the specified box type is not a card type");
		}
	}
	
	// method that checks if there are card left in the deck
	private void checkDeck(BoxType boxType) {
		// depending on the box type passed the corresponding deck is checked 
		int index = (boxType.equals(BoxType.CHANCE))? this.chanceIndex: this.unexpectedIndex;
			
		// if the last card has been drawn
		if (index == this.board.getCards(boxType).size()) {
			// deck is shuffled
			Collections.shuffle(this.board.getCards(boxType));
			// index resets
			if (boxType.equals(BoxType.CHANCE))
				this.chanceIndex = 0;
			else
				this.unexpectedIndex = 0;
		}
	}
	
		
		
		
		
		
}