package model;

import java.util.List;

public class UnexpectedBox extends CardBox{

	public UnexpectedBox(String name, List<Card> cards, BoxType boxType) {
		super(name, cards, BoxType.UNEXPECTED);
	}
	
	@Override
	protected String messageToDisplay() {
		return "Unexpected card: ";
	} 
}