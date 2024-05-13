package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game implements GameInterface{
		
	// FIELDS
	private List<Player> players;
	private Board board;
    private int currentPlayer; // current player index
	    
	// CONSTRUCTOR
	public Game() {
		this.players = new ArrayList<>();
		this.board = new Board();
        this.currentPlayer = 0;
	}

	// METHODS
	public void addPlayer(Player player) {
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

	public List<Player> getPlayers() {
		return this.players;
	}

	public Board getBoard() {
		return this.board;
	}
	
	// returns current player
    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    // passes to the next player
    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }
	    
}