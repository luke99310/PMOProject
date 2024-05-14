package model;

import java.util.List;

import enumeration.BoxType;

public interface BoardInterface {

	List<Box> getBoxes();
	
	List<Card> getCards(BoxType boxtype) throws IllegalArgumentException;

	Box getBox(int boxIndex);
	
}
