package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Interfaces.BoardInterface;
import model.Interfaces.BoxInterface;
import model.Interfaces.CardInterface;
import model.MonopolyTypes.ActionType;
import model.MonopolyTypes.BoxType;

public class Board implements BoardInterface{

	// FIELDS
	private List<CardInterface> chanceCards;
	private List<CardInterface> unexpectedCards;
	private List<BoxInterface> boxes;

	// CONSTRUCTOR
	public Board() {
		this.chanceCards = new ArrayList<>();
		this.unexpectedCards = new ArrayList<>();
		this.boxes = new ArrayList<>(); 

    	// unexpected cards list
    	this.unexpectedCards.add(new Card("Unexpected: Speeding fine. Pay 45.", -45, ActionType.BALANCE));
    	this.unexpectedCards.add(new Card("Unexpected: Building maintenance. Cost 90.", -90, ActionType.BALANCE));
    	this.unexpectedCards.add(new Card("Unexpected: Go to jail"));
    	this.unexpectedCards.add(new Card("Unexpected: Stocks down. Pay 65.", -65, ActionType.BALANCE));
    	this.unexpectedCards.add(new Card("Unexpected: Bank interest. Withdraw 125.", 125, ActionType.BALANCE));
    	this.unexpectedCards.add(new Card("Unexpected: Back 3 positions", -3, ActionType.POSITION));
    	this.unexpectedCards.add(new Card("Unexpected: Building maintenance. Pay 60.", -60, ActionType.BALANCE));
    	Collections.shuffle(this.unexpectedCards);
    	
    	// chance cards list
    	this.chanceCards.add(new Card("Chance: Dividend of 55", 55, ActionType.BALANCE));
    	this.chanceCards.add(new Card("Chance: Real estate coupons. Withdraw 105", 105, ActionType.BALANCE));
    	this.chanceCards.add(new Card("Chance: Lost lawsuit. To jail"));
    	this.chanceCards.add(new Card("Chance: Insurance due. Pay 125.", -125, ActionType.BALANCE));
    	this.chanceCards.add(new Card("Chance: Lost lawsuit. Pay 250." , -250, ActionType.BALANCE));
    	this.chanceCards.add(new Card("Chance: Sold stocks. Earn 125.", 125, ActionType.BALANCE));
    	this.chanceCards.add(new Card("Chance: Bonus: advance 5", 5, ActionType.POSITION));
    	Collections.shuffle(this.chanceCards);
    	
/*box*/ // board's boxes
 /*1*/  this.boxes.add(new Box("PARTENZA", BoxType.START));
 /*2*/  this.boxes.add(new Box("BASTIONI GRAN SASSO", 100, 6, BoxType.BLUE, true));
 /*3*/  this.boxes.add(new Box("VIALE MONTE ROSA", 100, 6, BoxType.BLUE, true));
 /*4*/  this.boxes.add(new Box("STAZIONE SUD", 200, 25, BoxType.STATION, true));
 /*5*/  this.boxes.add(new Box("VIALE VESUVIO", 120, 8, BoxType.BLUE, true));
 /*6*/  this.boxes.add(new Box("PROBABILITA'", BoxType.CHANCE));
 /*7*/  this.boxes.add(new Box("TRANSITO/PRIGIONE", BoxType.TRANSIT)); 
 /*8*/  this.boxes.add(new Box("VIA MARCO POLO", 220, 18, BoxType.RED, true));       
 /*9*/  this.boxes.add(new Box("CORSO MAGELLANO", 220, 18, BoxType.RED, true));
 /*10*/ this.boxes.add(new Box("STAZIONE OVEST", 200, 25, BoxType.STATION, true));
 /*11*/ this.boxes.add(new Box("LARGO COLOMBO", 240, 20, BoxType.RED, true));
 /*12*/ this.boxes.add(new Box("IMPREVISTI", BoxType.UNEXPECTED));
 /*13*/ this.boxes.add(new Box("TRANSITO", BoxType.TRANSIT));
 /*14*/ this.boxes.add(new Box("VIALE COSTANTINO", 260, 22, BoxType.YELLOW, true));
 /*15*/ this.boxes.add(new Box("VIALE TRAIANO", 260, 22, BoxType.YELLOW, true));
 /*16*/ this.boxes.add(new Box("STAZIONE NORD", 200, 25, BoxType.STATION, true));
 /*17*/ this.boxes.add(new Box("PIAZZA GIULIO CESARE", 280, 24, BoxType.YELLOW, true));
 /*18*/ this.boxes.add(new Box("PROBABILITA'", BoxType.CHANCE));
 /*19*/ this.boxes.add(new Box("VAI IN PRIGIONE", BoxType.JAIL));
 /*20*/ this.boxes.add(new Box("VIA ROMA", 300, 26, BoxType.GREEN, true));
 /*21*/ this.boxes.add(new Box("CORSO IMPERO", 300, 26, BoxType.GREEN, true));
 /*22*/ this.boxes.add(new Box("STAZIONE EST", 200, 25, BoxType.STATION, true));
 /*23*/ this.boxes.add(new Box("LARGO AUGUSTO", 320, 28, BoxType.GREEN, true));
 /*24*/ this.boxes.add(new Box("IMPREVISTI", BoxType.UNEXPECTED));
    }

	// METHODS
	public List<BoxInterface> getBoxes() {
		return this.boxes;
	}
	
	public List<CardInterface> getCards(BoxType boxtype){
		List<CardInterface> cards;
		switch(boxtype) { 
			case UNEXPECTED:
				cards = this.unexpectedCards;
				break;
			case CHANCE:
				cards = this.chanceCards;
				break;
			default:
				throw new IllegalArgumentException("The specified box type is not a card type");
		}
		return cards; 
	}

	public BoxInterface getBox(int boxIndex) {
		if (boxIndex >= 0 && boxIndex < boxes.size())
			return this.boxes.get(boxIndex);
		else 
			throw new IllegalArgumentException("The index of the box on the board is not valid");	
	}
}