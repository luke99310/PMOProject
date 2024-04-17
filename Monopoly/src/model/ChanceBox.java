package model;

import java.util.List;

public class ChanceBox extends CardBox {
	
	public ChanceBox(String name, List<Card> cards, BoxType boxType) {
		super(name, cards, BoxType.CHANCE);
	}
	
	@Override
	protected String messageToDisplay() {
		return "Chance card: ";
	} 
}