package model;

import java.util.List;

import enumeration.BoxType;

public interface BoardInterface {

	List<BoxInterface> getBoxes();
	
	List<CardInterface> getCards(BoxType boxtype) throws IllegalArgumentException;

	BoxInterface getBox(int boxIndex);
	
}
