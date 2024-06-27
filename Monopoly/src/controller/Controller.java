package controller;

import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.Game;
import model.Interfaces.BoxInterface;
import model.Interfaces.PlayerInterface;
import model.MonopolyTypes.BoxType;
import view.View;

public class Controller {
	
	//CONSTANTS
    private static final int POSITION_ADJUSTMENT = 1;
    private static final int STARTING_BOX_INDEX = 1;
    private static final int MAX_HOUSE_LIMIT = 2;

    //FIELDS
    private Game game; //Model
    private View view; //View

    //CONSTRUCTORS
    public Controller(Game game, View view) {
    	
        this.game = game;
        this.view = view;
        
        //aggiungo un ActionListener al pulsante "Inizia Gioco"
        view.getPlayerField().getStartButton().addActionListener(e -> addPlayers());

        //aggiungo un ActionListener al pulsante "Lancia Dadi"
        view.getButtons().getDiceButton().addActionListener(e -> handleDiceRoll());

        //aggiungo un ActionListener al pulsante "Prossimo giocatore"
        view.getButtons().getNextButton().addActionListener(e -> handleNextPlayer());

        //aggiungo un ActionListener al pulsante "Compra Casella"
        view.getButtons().getBuyButton().addActionListener(e -> buyBox());
        
        //aggiungo un ActionListener al pulsante "Crea Casa"
        view.getButtons().getBuildHouse().addActionListener(e -> buildHouse());
        
        //aggiungo un ActionListener al pulsante "Metti all'Asta"        
        view.getButtons().getAuctionButton().addActionListener(e -> auctionProperty(view.getButtons().getSelectedProperty()));
    }

    //METHODS
    public void addPlayers() {
        //controllo che ci siano almeno due giocatori
    	int playerCount = 0;
    	JTextField[] players = view.getPlayerField().getFields();
    	for (JTextField field : players) {
    	    if (!field.getText().isEmpty()) {
    	        this.game.addPlayer(field.getText());
    	        playerCount++;
    	    }
    	}

        if (playerCount >= 2) {
            //inizio il gioco
        	game.startGame();
        	
            //chiudo la finestra di dialogo quando i giocatori sono almeno 2
            view.getPlayerField().getJDialog().dispose();  

            //rendo visibili le pedine nella casella di partenza
            for(int i = 0; i < playerCount; i++) {
                view.getBoard().setLabelVisibilityOnBoard(STARTING_BOX_INDEX, i, true); 
            }
            
            view.getButtons().getPlayerLabel().setText("Giocatore attuale: " + game.getCurrentPlayer().getName());
            view.getButtons().getPiecePlayer().setText("Numero pedina: " + (game.getPlayers().indexOf(game.getCurrentPlayer()) + POSITION_ADJUSTMENT));
            view.getButtons().getBalanceLabel().setText("Saldo: " + game.getCurrentPlayer().getBalance());
            this.updateProperties();

            view.getButtons().getBuyButton().setEnabled(false);
            view.getButtons().getBuildHouse().setEnabled(false);
            
        }else {
            //mostro un messaggio di errore
            JOptionPane.showMessageDialog(view.getPlayerField().getJDialog(), "Devono unirsi almeno due giocatori.", "Errore", JOptionPane.ERROR_MESSAGE);
            for (PlayerInterface p : game.getPlayers()) {
        	        this.game.removePlayer(p);
        	}
        }
    }
    
    private void handleDiceRoll() {
    	//aggiorno i test dei bottoni
    	this.resetButtonsText();
        

        //chiamo il metodo muovi() sul giocatore corrente
        view.getBoard().setLabelVisibilityOnBoard(game.getCurrentPlayer().getPositionIndex() + POSITION_ADJUSTMENT, game.getPlayers().indexOf(game.getCurrentPlayer()), false);
        int numeroDadi = game.rollDice();
        view.getButtons().getDiceButton().setText("LANCIO DADI: " + numeroDadi);
        view.getButtons().getDiceLabel().setText(game.getCurrentPlayer().move(numeroDadi));

        //aggiorno la pedina
        view.getBoard().setLabelVisibilityOnBoard(game.getCurrentPlayer().getPositionIndex() + POSITION_ADJUSTMENT, game.getPlayers().indexOf(game.getCurrentPlayer()), true);
        
        if(game.getDoublesCounter() == -1 || game.getCurrentPlayer().isInJail())
	    	view.getButtons().getDiceButton().setEnabled(false);
        
        if(!game.getCurrentPlayer().getPosition().isSellable()) {
        	view.getButtons().getBuyButton().setEnabled(false);
        }else {
        	view.getButtons().getBuyButton().setEnabled(true);
        }
        
        //abilito/disabilito il bottone crea casa
        this.enableCreateHomeButton();             

        //aggiorno il bilancio
        this.updateBalance();
    }

    private void handleNextPlayer() {
        //passo al prossimo giocatore
        game.nextPlayer();
        
        //aggiorno la label con il nome del giocatore corrente
        view.getButtons().getPlayerLabel().setText("Giocatore attuale: " + game.getCurrentPlayer().getName());
        
        //aggiorno la label con il numero della pedina del giocatore corrente
        view.getButtons().getPiecePlayer().setText("Numero pedina: " + (game.getPlayers().indexOf(game.getCurrentPlayer()) + POSITION_ADJUSTMENT));
        
        //aggiorno il bilancio del giocatore corrente
        this.updateBalance();
        
        //azzero la label diceLabel
        view.getButtons().getDiceLabel().setText("");
        
    	//aggiorno i test dei bottoni
        this.resetButtonsText();
        
        //aggiorno le proprietà
        this.updateProperties();       
        
    	//il giocatore non può comprare la casella o creare case in cui è perché deve ancora lanciare i dadi
        view.getButtons().getBuyButton().setEnabled(false);
        view.getButtons().getBuildHouse().setEnabled(false);   	        

        if(game.getCurrentPlayer().isInJail()) {
	    	view.getButtons().getDiceButton().setEnabled(false);
	    	game.getCurrentPlayer().move(game.rollDice()); //per scontare un giro in prigione
        }else {
	    	view.getButtons().getDiceButton().setEnabled(true);
        }
    }
    
    private void buyBox() {    
    	//aggiorno i test dei bottoni
        this.resetButtonsText();
        
    	//mostro il messaggio nel bottone "COMPRA PROPRIETA'"
    	String message = game.getCurrentPlayer().buyBox(game.getCurrentPlayer().getPosition(), game.getCurrentPlayer().getPosition().getCost());
        view.getButtons().getBuyButton().setText(message);
        view.getButtons().getBuyButton().setEnabled(false);
    	
    	//aggiorno il bilancio del giocatore corrente
        this.updateBalance();

        //aggiorno le proprietà
        this.updateProperties();
        
        //abilito/disabilito il bottone crea casa
        this.enableCreateHomeButton();
    }
    
    private void buildHouse() {
    	//aggiorno i test dei bottoni
        this.resetButtonsText();
        
        //mostro il messaggio nel bottone "CREA CASA"
    	String message = game.getCurrentPlayer().buildHouse(game.getCurrentPlayer().getPosition());
    	view.getButtons().getBuildHouse().setText(message);
    	
    	//aggiorno il bilancio
        this.updateBalance();
    }
    
    private void updateProperties() {
        view.getButtons().clearProperties();
        for (BoxInterface box : game.getCurrentPlayer().getProperties()) {
            view.getButtons().addProperty(box.getName());
        }
    }

    private void auctionProperty(String  propertyName) {
    	//aggiorno i test dei bottoni
        this.resetButtonsText();

        //cerco la casella selezionata nella comboBox
        Optional<BoxInterface> box = game.getCurrentPlayer().getProperties().stream()
            .filter(b -> b.getName().equals(propertyName))
            .findFirst();

        //metto all'asta la casella selezionata
        if(box.isPresent()) {
            game.getCurrentPlayer().putUpForAuction(box.get());
        }
        
        //aggiorno il bilancio
        this.updateBalance();
        
        //aggiorno le proprietà
        this.updateProperties();
    }
    
    private void resetButtonsText() {
        view.getButtons().getDiceButton().setText("LANCIA DADI");
        view.getButtons().getBuyButton().setText("COMPRA PROPRIETA'");
        view.getButtons().getBuildHouse().setText("CREA CASA   ⌂");
    }
    
    private void updateBalance() {
        view.getButtons().getBalanceLabel().setText("Saldo: " + game.getCurrentPlayer().getBalance());
    }
  
    private void enableCreateHomeButton() {
        if(!game.getCurrentPlayer().getPosition().getType().equals(BoxType.STATION) && game.getCurrentPlayer().hasFullSet(game.getCurrentPlayer().getPosition().getType()) &&
        	game.getCurrentPlayer().getPosition().getBuiltHouses() <= MAX_HOUSE_LIMIT) {
         	view.getButtons().getBuildHouse().setEnabled(true);
        }else {
          	view.getButtons().getBuildHouse().setEnabled(false);
      	} 
    }
}