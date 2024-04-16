package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JTextField;

import model.Game;
import model.Player;
import view.p;

public class Controller {
	
	//FIELDS
    private Game game; // Il Model
    private p view; // La View

    
    //CONSTRUCOTORS
    public Controller(Game game, p view) {
        this.game = game;
        this.view = view;

        // Aggiungi un ActionListener al pulsante "Inizia Gioco"
        view.getStartButton().addActionListener(e -> addPlayers());
        
        // Aggiungi un ActionListener al pulsante "Lancia Dadi"
        view.getDiceButton().addActionListener(e -> handleDiceRoll());

        // Aggiungi un ActionListener al pulsante "Prossimo giocatore"
        view.getNextPlayerButton().addActionListener(e -> handleNextPlayer());
        
        
    }

    
    //METHODS
    private void addPlayers() {
    	JTextField[] players = view.getPlayers();
    	for(int i = 0; i < players.length; i++) {
    	    String playerName = players[i].getText();
    	    Player newPlayer = new Player(playerName, game);
    	    game.addPlayer(newPlayer);
    	}    
    }
    
    private void handleDiceRoll() {
        // Chiama il metodo muovi() sul giocatore corrente
        game.getCurrentPlayer().move(game.rollDice());

        // Aggiorna la View
        //view.setLabelVisibilityOnBoard(view.getBoardMap(), game.getCurrentPlayer().getPositionIndex(), game.getCurrentPlayer(), true);
    }

    private void handleNextPlayer() {
        // Nascondi la pedina del giocatore corrente
        //view.setLabelVisibilityOnBoard(view.getBoardMap(), game.getCurrentPlayer().getPosition(), game.getCurrentPlayer(), false);

        // Passa al prossimo giocatore
        System.out.println("Il giocatore attuale è: ");
        game.nextPlayer();
        // Mostra la pedina del nuovo giocatore corrente
        //view.setLabelVisibilityOnBoard(view.getBoardMap(), game.getCurrentPlayer().getPosition(), game.getCurrentPlayer(), true);

        // Aggiorna il testo del pulsante "Lancia Dadi"
        view.getDiceButton().setText("Giocatore " + (game.getCurrentPlayer().getName()) + " lancia i dadi");

        // Aggiorna la label con il nome del giocatore corrente
        //view.getPlayerLabel().setText(game.getCurrentPlayer().getName() + " è il tuo turno");
    }
}