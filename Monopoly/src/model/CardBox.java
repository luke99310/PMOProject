package model;

import java.util.List;
import java.util.Random;

public abstract class CardBox extends Box{
	private static final int JAIL_POSITION_INDEX = 6;
	
	// FIELDS
	private List<Card> cards;

	// CONSTRUCTOR
	public CardBox(final String name, List<Card> cards, BoxType boxType) {
		super(name, 0, 0, boxType, true);
		this.cards = cards;
	}

	// METHODS
	// method that executes the action that the box correspond
	public void executeAction(Player player) {
		int cardIndex = new Random().nextInt(cards.size());
		Card card = cards.get(cardIndex);
		System.out.println(this.messageToDisplay() + card.getDescription());
	        
		switch (card.getAction()) {
		case BALANCE :
			player.updateBalance(card.getValue());
			break;
		case POSITION:
			player.move(card.getValue());
			break;
		case JAIL:
			player.move(JAIL_POSITION_INDEX - player.getPositionIndex());
			break;
		default:
			System.out.println("Errore, azione non riconosciuta !!");
			break;
		}    
	}
	
	// method that returns the message to display 
	protected abstract String messageToDisplay();
}
