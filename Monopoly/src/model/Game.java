package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game implements GameInterface{
		
	//CAMPI
	private List<Player> players;
	private Board board;
    private int currentPlayer; // L'indice del giocatore corrente

	    
	    
	//COSTRUTTORI
	public Game() {
		players = new ArrayList<>();
		board = new Board();
        this.currentPlayer = 0;
	}

	    
	//METODI
	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public void startGame() {
		Collections.shuffle(players);
	}
	
	public int rollDices(){
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
		
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public Board getBoard() {
		return this.board;
	}

	//capire come implementare il check: saldo non disponibile per affitto
	public void checkPlayerFunds(Player player) {
		if (player.getProperties().size() > 0) {
			System.out.println("Non hai abbastanza fondi per pagare. "
							   + "Hai l'opzione di vendere le tue proprietà per continuare a giocare.");
			//chiamare metodo per gestire la vendita delle proprietà
		}else {
			System.out.println("Non hai abbastanza fondi per pagare e non hai proprietà da vendere. "
							   + "Sei fuori dal gioco.");
			this.players.remove(player);
		}
	}
	
	// Restituisce il giocatore corrente
    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    // Passa al prossimo giocatore
    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }
	    
}