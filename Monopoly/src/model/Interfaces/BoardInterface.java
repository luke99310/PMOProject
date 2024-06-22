package model.Interfaces;

import java.util.List;

import model.MonopolyTypes.BoxType;

public interface BoardInterface {

	List<BoxInterface> getBoxes();
	
	List<CardInterface> getCards(BoxType boxtype) throws IllegalArgumentException;

	BoxInterface getBox(int boxIndex);
	
}
