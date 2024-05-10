package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import model.Box;
import model.BoxType;
import model.Game;
import model.Player;
import view.MonopolyCell;
import view.p;

public class Controller {
    private static final int POSITION_ADJUSTMENT = 1;

    //FIELDS
    private Game game; // Il Model
    private p view; // La View

    //CONSTRUCTORS
    public Controller(Game game, p view) {
        this.game = game;
        this.view = view;
        
        // Aggiungo un ActionListener al pulsante "Inizia Gioco"
        view.getPlayerField().getStartButton().addActionListener(e -> addPlayers());

        // Aggiungo un ActionListener al pulsante "Lancia Dadi"
        view.getButtons().getDiceButton().addActionListener(e -> handleDiceRoll());

        // Aggiungo un ActionListener al pulsante "Prossimo giocatore"
        view.getButtons().getNextButton().addActionListener(e -> handleNextPlayer());

        // Aggiungo un ActionListener al pulsante "compra casella"
        view.getButtons().getBuyButton().addActionListener(e -> buyBox());
        
        // Aggiungo un ActionListener al pulsante "Crea casa"
        view.getButtons().getBuildHouse().addActionListener(e -> buildHouse());
        
        // Aggiungo un ActionListener al pulsante "Vendi"        
        view.getButtons().getSellButton().addActionListener(e -> sellProperty(view.getButtons().getSelectedProperty()));

    }

    //METHODS
    public void addPlayers() {
        // Controlla che ci siano almeno due giocatori
        int playerCount = 0;
        for (JTextField field : view.getPlayerField().getFields()) {
            if (!field.getText().isEmpty()) {
                playerCount++;
            }
        }

        if (playerCount >= 2) {
            // Inizia il gioco
            // Impedisci la chiusura della finestra quando viene creata
            view.getPlayerField().getJDialog().dispose();

            int counter = 0;
            JTextField[] players = view.getPlayerField().getFields();
            for(int i = 0; i < players.length; i++) {
                if(!players[i].getText().equals("")) {
                    String playerName = players[i].getText();
                    new Player(playerName, game);
                    counter++;
                }
            }    
            System.out.println("valore di i: " + counter);
            for(int i = 0; i < counter; i++) {
                view.getBoard().setLabelVisibilityOnBoard(1, i, true); 
            }
            
            view.getButtons().getPlayerLabel().setText("Giocatore attuale: " + game.getCurrentPlayer().getName());
            view.getButtons().getPiecePlayer().setText("Numero pedina: " + (game.getPlayers().indexOf(game.getCurrentPlayer()) + 1));
            view.getButtons().getBalanceLabel().setText("Saldo: " + game.getCurrentPlayer().getBalance());

            /*
            Set<Box> boxes = game.getCurrentPlayer().getProperties();
            Set<String> properties = boxes.stream().map(Box::getName).collect(Collectors.toSet());
            String propertiesText = properties.stream().collect(Collectors.joining("\n"));
            view.getButtons().getProperties().setText(propertiesText);
            */
            this.updateProperties();

            view.getButtons().getBuyButton().setEnabled(false);
            view.getButtons().getBuildHouse().setEnabled(false);

            
            System.out.println("giocatori: " + game.getPlayers());

        } else {
            // Mostra un messaggio di errore
            JOptionPane.showMessageDialog(view.getPlayerField().getJDialog(), "Devono unirsi almeno due giocatori.", "Errore", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    private void handleDiceRoll() {
        // Chiama il metodo muovi() sul giocatore corrente
        view.getBoard().setLabelVisibilityOnBoard(game.getCurrentPlayer().getPositionIndex() + POSITION_ADJUSTMENT, game.getPlayers().indexOf(game.getCurrentPlayer()), false);
        
        System.out.println(" ");
        System.out.println("Saldo prima di lanciare i dadi: " + game.getCurrentPlayer().getBalance());
        int numeroDadi = game.rollDices();
        view.getButtons().getDiceButton().setText("LANCIO DADI: " + numeroDadi);
        view.getButtons().getDiceLabel().setText(game.getCurrentPlayer().move(numeroDadi));
        
        if(numeroDadi > 0)
	    	view.getButtons().getDiceButton().setEnabled(false);
        
        
        if(game.getCurrentPlayer().getPosition().isSpecial() || !game.getCurrentPlayer().getPosition().isSellable()) {
        	view.getButtons().getBuyButton().setEnabled(false);
        	view.getButtons().getDiceButton().setEnabled(false);
        	view.getButtons().getBuildHouse().setEnabled(false);
        }else
        	view.getButtons().getBuyButton().setEnabled(true);

        
        
        System.out.println("Saldo dopo lancio dadi: " + game.getCurrentPlayer().getBalance());
        System.out.println("Proprietà: " + game.getCurrentPlayer().getProperties());

        // Aggiorna la View
        view.getBoard().setLabelVisibilityOnBoard(game.getCurrentPlayer().getPositionIndex() + POSITION_ADJUSTMENT, game.getPlayers().indexOf(game.getCurrentPlayer()), true);
        
        //aggiorno il bilancio del giocatore corrente
        view.getButtons().getBalanceLabel().setText("Saldo: " + game.getCurrentPlayer().getBalance());

    }

    private void handleNextPlayer() {
    	
    	//il giocatore non può comprare la casella in cui è perché deve ancora lancviare i dadi
        view.getButtons().getBuyButton().setEnabled(false);

        // Passa al prossimo giocatore
        System.out.println("Il giocatore attuale è: ");
        game.nextPlayer();
   	
        // Aggiorna la label con il nome del giocatore corrente
        view.getButtons().getPlayerLabel().setText("Giocatore attuale: " + game.getCurrentPlayer().getName());

        
        // Aggiorna la label con il numero della pedian del giocatore corrente
        view.getButtons().getPiecePlayer().setText("Numero pedina: " + (game.getPlayers().indexOf(game.getCurrentPlayer()) + 1));
        
        //aggiorno il bilancio del giocatore corrente
        view.getButtons().getBalanceLabel().setText("Saldo: " + game.getCurrentPlayer().getBalance());
        System.out.println("saldo giocatore attuale: " + game.getCurrentPlayer().getBalance());
        
        // azzero la label diceLabel
        view.getButtons().getDiceLabel().setText("");

        if(game.getCurrentPlayer().isInJail()) {
	    	view.getButtons().getDiceButton().setEnabled(false);
	    	game.getCurrentPlayer().move(game.rollDices());
        }else {
	    	view.getButtons().getDiceButton().setEnabled(true);
        	view.getButtons().getDiceButton().setText("LANCIA DADI");
        }
        
        view.getButtons().getBuyButton().setText("COMPRA PROPRIETA'");
        view.getButtons().getBuildHouse().setText("CREA CASA   ⌂");
        
        /*
        Set<Box> boxes = game.getCurrentPlayer().getProperties();
        Set<String> properties = boxes.stream().map(Box::getName).collect(Collectors.toSet());
        String propertiesText = properties.stream().collect(Collectors.joining("\n"));
        view.getButtons().getProperties().setText(propertiesText);
        */
        view.getButtons().clearProperties();
        this.updateProperties();

        
    }
    
    private void buyBox() {    	
    	String message = game.getCurrentPlayer().buyBox(game.getCurrentPlayer().getPosition(), game.getCurrentPlayer().getPosition().getCost());
    	
    	// Crea un nuovo timer
        view.getButtons().getBuyButton().setText(message);
        view.getButtons().getBuyButton().setEnabled(false);
    	
    	//aggiorno il bilancio del giocatore corrente
        view.getButtons().getBalanceLabel().setText("Saldo: " + game.getCurrentPlayer().getBalance());
        
        //stampo tutte le proprietà del giocatore
        /*
        Set<Box> boxes = game.getCurrentPlayer().getProperties();
        Set<String> properties = boxes.stream().map(Box::getName).collect(Collectors.toSet());
        String propertiesText = properties.stream().collect(Collectors.joining("\n"));
        view.getButtons().getProperties().setText(propertiesText);
        */
        this.updateProperties();

    }
    
    private void buildHouse() {
    	String message = game.getCurrentPlayer().buildHouse(game.getCurrentPlayer().getPosition());
    	
    	view.getButtons().getBuildHouse().setText("CREA CASA   ⌂");
    	view.getButtons().getBuildHouse().setText(message);
  
    }
    
    private void updateProperties() {
        view.getButtons().clearProperties();

        for (Box box : game.getCurrentPlayer().getProperties()) {
            view.getButtons().addProperty(box.getName());
        }
    }

    private void sellProperty(String propertyName) {
        // codice...
        this.updateProperties();
    }
}