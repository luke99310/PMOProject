package model;

import java.util.List;
import java.util.Random;

public class UnexpectedBox extends Box{
		
	private static final int JAIL_POSITION_INDEX = 6;
		
	//CAMPI
	private List<Card> cards;

	    
	//COSTRUTTORI
	public UnexpectedBox(final String name, List<Card> cards) {
		super(name, 0, 0, BoxType.UNEXPECTED, true);
		this.cards = cards;
	}

	//METODI
	public void executeAction(Player player) {
		int cardIndex = new Random().nextInt(cards.size());
		Card card = cards.get(cardIndex);
		System.out.println("Carta Imprevisti: " + card.getDescription());
	        
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
}