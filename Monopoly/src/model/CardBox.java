package model;

import java.util.List;

import enumeration.BoxType;

public class CardBox extends Box{
	
	// FIELDS
	private List<Card> cards;

	// CONSTRUCTOR
	public CardBox(final String name, List<Card> cards, BoxType boxType) {
		super(name, 0, 0, boxType, true);
		this.cards = cards;
	}
	
	// METHODS
	public List<Card> getCards(){
		return this.cards;
	}
}