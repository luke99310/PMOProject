package controller;

import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.Game;
import model.Interfaces.BoxInterface;
import model.Interfaces.PlayerInterface;
import model.MonopolyTypes.BoxType;
import view.View;

public class Controller {
	
	// CONSTANTS
    private static final int POSITION_ADJUSTMENT = 1;
    private static final int STARTING_BOX_INDEX = 1;
    private static final int MAX_HOUSE_LIMIT = 2;

    // FIELDS
    private Game game; //Model
    private View view; //View
 
    // CONSTRUCTORS
    public Controller(Game game, View view) {  	
        this.game = game;
        this.view = view;
        this.view.initBoard(new ArrayList<>(game.getBoard().getBoxes().stream().map(box -> box.getName()).toList()));
        
        //add an ActionListener to the "Start Game" button.
        view.getPlayerField().getStartButton().addActionListener(e -> addPlayers());

        //add an ActionListener to the "Throw Dice" button.
        view.getButtons().getDiceButton().addActionListener(e -> handleDiceRoll());

        //add an ActionListener to the "Next Player" button.
        view.getButtons().getNextButton().addActionListener(e -> handleNextPlayer());

        //add an ActionListener to the "Buy Box" button.
        view.getButtons().getBuyButton().addActionListener(e -> buyBox());
        
        //add an ActionListener to the "Create Home" button.
        view.getButtons().getBuildHouse().addActionListener(e -> buildHouse());
        
        //add an ActionListener to the "Auction" button.        
        view.getButtons().getAuctionButton().addActionListener(e -> auctionProperty(view.getButtons().getSelectedProperty()));
    }

    // METHODS
    private void addPlayers() {
        //check that there are at least two players
    	int playerCount = 0;
    	JTextField[] players = view.getPlayerField().getFields();
    	for (JTextField field : players) {
    	    if (!field.getText().isEmpty()) {
    	        this.game.addPlayer(field.getText());
    	        playerCount++;
    	    }
    	}

        if (playerCount >= 2) {
            //start the game
        	game.startGame();
        	
            //close the dialog box when there are at least 2 players
        	view.getPlayerField().getJDialog().dispose();  

            //make the checkers visible in the starting box
        	for(int i = 0; i < playerCount; i++) {
                view.getBoard().setLabelVisibilityOnBoard(STARTING_BOX_INDEX, i, true); 
            }
            
            view.getButtons().getPlayerLabel().setText("Current player: " + game.getCurrentPlayer().getName());
            view.getButtons().getPiecePlayer().setText("Pawn number: " + (game.getPlayers().indexOf(game.getCurrentPlayer()) + POSITION_ADJUSTMENT));
            view.getButtons().getBalanceLabel().setText("Balance: " + game.getCurrentPlayer().getBalance());

            view.getButtons().getBuyButton().setEnabled(false);
            view.getButtons().getBuildHouse().setEnabled(false);
            
        }else {
            //show an error message
        	JOptionPane.showMessageDialog(view.getPlayerField().getJDialog(), "At least two players must join.", "Error", JOptionPane.ERROR_MESSAGE);
        	for (PlayerInterface p : game.getPlayers()) {
        	        this.game.removePlayer(p);
        	}
        }
    }
    
    private void handleDiceRoll() {
    	//add button tests
    	this.resetButtonsText();

        //let's run the move() method on the current player
    	view.getBoard().setLabelVisibilityOnBoard(game.getCurrentPlayer().getPositionIndex() + POSITION_ADJUSTMENT, game.getPlayers().indexOf(game.getCurrentPlayer()), false);
        int diceNumber = game.rollDice();
        view.getButtons().getDiceButton().setText("DICE THROW:" + diceNumber);
        view.getButtons().getDiceLabel().setText(game.getCurrentPlayer().move(diceNumber));

        //add the pawn
        view.getBoard().setLabelVisibilityOnBoard(game.getCurrentPlayer().getPositionIndex() + POSITION_ADJUSTMENT, game.getPlayers().indexOf(game.getCurrentPlayer()), true);
        
        if(game.getDoublesCounter() == -1 || game.getCurrentPlayer().isInJail())
	    	view.getButtons().getDiceButton().setEnabled(false);
        
        if(!game.getCurrentPlayer().getPosition().isSellable()) {
        	view.getButtons().getBuyButton().setEnabled(false);
        }else {
        	view.getButtons().getBuyButton().setEnabled(true);
        }
        
        //enable/disable CreateHomeButton
        this.enableCreateHomeButton();             

        // update the budget
        this.updateBalance();
    }

    private void handleNextPlayer() {
        //pass to next player
    	game.nextPlayer();
        
        // update the label with the current player's name
    	view.getButtons().getPlayerLabel().setText("Current player: " + game.getCurrentPlayer().getName());
        
        // update the label with the current player's token number
    	view.getButtons().getPiecePlayer().setText("Pawn number: " + (game.getPlayers().indexOf(game.getCurrentPlayer()) + POSITION_ADJUSTMENT));
        
        // update the current player's budget
    	this.updateBalance();
        
        //reset the label saysLabel
    	view.getButtons().getDiceLabel().setText("");
        
    	// update the button tests
    	this.resetButtonsText();
        
        // update the properties
        this.updateProperties();       
        
    	//the player cannot buy the box or create houses where he is because he has yet to roll the dice
        view.getButtons().getBuyButton().setEnabled(false);
        view.getButtons().getBuildHouse().setEnabled(false);   	        

        if(game.getCurrentPlayer().isInJail()) {
	    	view.getButtons().getDiceButton().setEnabled(false);
	    	game.getCurrentPlayer().move(game.rollDice()); //player passes 1 turn in jail
        }else {
	    	view.getButtons().getDiceButton().setEnabled(true);
        }
    }
    
    private void buyBox() {    
    	// update the button tests
    	this.resetButtonsText();
        
    	//show the message in the "BUY PROPERTY" button.
    	String message = game.getCurrentPlayer().buyBox(game.getCurrentPlayer().getPosition(), game.getCurrentPlayer().getPosition().getCost());
        view.getButtons().getBuyButton().setText(message);
        view.getButtons().getBuyButton().setEnabled(false);
    	
    	// update the current player's budget
        this.updateBalance();

        // update the properties
        this.updateProperties();
        
        //enable/disable create home button
        this.enableCreateHomeButton();
    }
    
    private void buildHouse() {
    	// update the button tests
    	this.resetButtonsText();
        
        //show the message in the "CREATE HOME" button.
    	String message = game.getCurrentPlayer().buildHouse(game.getCurrentPlayer().getPosition());
    	view.getButtons().getBuildHouse().setText(message);
    	
    	// update the budget
    	this.updateBalance();
    	

        // update the properties
        this.updateProperties();
    }
    
    private void updateProperties() {
        view.getButtons().clearProperties();
        for (BoxInterface box : game.getCurrentPlayer().getProperties()) {
            view.getButtons().addProperty(box.getName() + " " + box.getBuiltHouses() + "⌂");
        }
    }

    private void auctionProperty(String  propertyName) {
    	// update the button tests
    	this.resetButtonsText();

        //find the selected box in the comboBox
    	String s = propertyName.substring(0, propertyName.length() -3); // string -3 characters
    	
    	Optional<BoxInterface> box = game.getCurrentPlayer().getProperties().stream()
            .filter(b -> b.getName().equals(s))
            .findFirst();

        // auction off the selected box
    	if(box.isPresent()) {
            game.getCurrentPlayer().putUpForAuction(box.get());
        }
        
        // update the budget
    	this.updateBalance();
        
        // update the properties
    	this.updateProperties();
    }
    
    private void resetButtonsText() {
        view.getButtons().getDiceButton().setText("DICE THROW");
        view.getButtons().getBuyButton().setText("BUY PROPERTY");
        view.getButtons().getBuildHouse().setText("CREATE HOME   ⌂");
    }
    
    private void updateBalance() {
        view.getButtons().getBalanceLabel().setText("Balance: " + game.getCurrentPlayer().getBalance());
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