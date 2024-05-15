package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import enumeration.BoxType;

public class Game implements GameInterface{
		
	// FIELDS
	private List<PlayerInterface> players;
	private BoardInterface board;
    private int currentPlayer; // current player index
    public static DicesInterface dices;
    private int unexpectedIndex;
    private int chanceIndex;
	    
	// CONSTRUCTOR
	public Game() {
		this.players = new ArrayList<>();
		this.board = new Board();
        this.currentPlayer = 0;
        dices = Dices.getInstance();
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
	
	/*public int rollDices(){
		int sum = 0;
		int counter = 0;
		int throw1 = 0;
		int throw2 = 0;
		for (int i = 0;(throw1 == throw2) && (i < 3); i++) {
			throw1 = new Random().nextInt(6) + 1;
			System.out.println("You got " + throw1 + " with the first dice");
			throw2 = new Random().nextInt(6) + 1;
			System.out.println("You got " + throw2 + " with the second dice");
			sum = throw1 + throw2;
			if (throw1 == throw2)
				counter++;
		}
		if (counter != 3)
			return sum;
		else {
			System.out.println("Illegal throw, go to prison!!!");
			return 0;
		}
		
	}*/

	public List<PlayerInterface> getPlayers() {
		return this.players;
	}

	public BoardInterface getBoard() {
		return this.board;
	}
	
	// returns current player
    public PlayerInterface getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    // passes to the next player
    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }

    // return the dices
	public DicesInterface getDices() {
		return dices;
	} 
	
	public CardInterface drawCard(BoxType boxtype) {
		CardInterface drawnCard;
		switch (boxtype) {
			case UNEXPECTED:
				// if even the last card has been drawn the deck is shuffled
				if (this.unexpectedIndex == this.board.getCards(boxtype).size()) {
					this.unexpectedIndex = 0;
					Collections.shuffle(this.board.getCards(boxtype));
				}
				// drawing the card
				drawnCard = this.board.getCards(boxtype).get(this.unexpectedIndex++);
				break;
			case CHANCE:
				// if even the last card has been drawn the deck is shuffled
				if (this.chanceIndex == this.board.getCards(boxtype).size()) {
					this.chanceIndex = 0;
					Collections.shuffle(this.board.getCards(boxtype));
				}
				// drawing the card
				drawnCard = this.board.getCards(boxtype).get(this.chanceIndex++);
				break;
			// if the wrong type of box is given an exception is thrown
			default:
				throw new IllegalArgumentException("the specified box type is not a card type");
		}
		return drawnCard;
	}
	
	
	/*private void checkDeck(BoxType boxtype) {
		if (this.unexpectedIndex == this.board.getCards(boxtype).size()) {
			this.unexpectedIndex = 0;
			this.board.shuffleCards(boxtype);
		}
	}*/
	
		
		
		
		
		
}