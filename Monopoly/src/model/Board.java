package model;

import java.util.ArrayList;
import java.util.List;

public class Board implements BoardInterface{
		
	// FIELDS
	private List<Card> chanceCards;
	private List<Card> unexpectedCards;
	private List<Box> boxes;

	// CONSTRUCTOR
	public Board() {
		this.chanceCards = new ArrayList<>();
		this.unexpectedCards = new ArrayList<>();
		this.boxes = new ArrayList<>(); 

    	// unexpected cards list
    	this.unexpectedCards.add(new Card("Avete preso una multa per eccesso di velocità. Pagate 45.", -45, ActionType.BALANCE));
    	this.unexpectedCards.add(new Card("Eseguite dei lavori di manutenzione su tutti i vostri edifici. Il costo totale della manutenzione è 90.", -90, ActionType.BALANCE));
    	this.unexpectedCards.add(new Card("Andate in prigione", ActionType.JAIL));
    	this.unexpectedCards.add(new Card("Le vostre azioni sono andate male. Pagate 65 euro alla banca.", -65, ActionType.BALANCE));
    	this.unexpectedCards.add(new Card("La banca vi paga gli interessi del vostro conto corrente, ritirate 125 euro. ", 125, ActionType.BALANCE));
    	this.unexpectedCards.add(new Card("Tornate indietro di 3 poszioni", -3, ActionType.POSITION));
    	this.unexpectedCards.add(new Card("Avete tutti i vostri stabili da riparare, pagate 60 euro.", -60, ActionType.BALANCE));

    	// chance cards list
    	this.chanceCards.add(new Card("La banca vi paga un dividendo di 55", 55, ActionType.BALANCE));
    	this.chanceCards.add(new Card("Maturano le cedole dei vostri fondi immobiliari. Ritirate 105", 105, ActionType.BALANCE));
    	this.chanceCards.add(new Card("Avete perso una causa. Andate in prigione", ActionType.JAIL));
    	this.chanceCards.add(new Card("Scade il vostro premio di assicurazione pagate 125 euro. ", -125, ActionType.BALANCE));
    	this.chanceCards.add(new Card("Avete perso una causa: pagate 250 euro." , -250, ActionType.BALANCE));
    	this.chanceCards.add(new Card("Avete ceduto delle azioni: ricavate 125 euro. ", 125, ActionType.BALANCE));
    	this.chanceCards.add(new Card("Bonus: avanzate di altre 5 poszioni", 5, ActionType.POSITION));

    	
/*box*/ // board's boxes
 /*1*/  this.boxes.add(new Box("Partenza", 0, 0, BoxType.START, true));
 /*2*/  this.boxes.add(new Box("Bastioni Gran Sasso", 100, 6, BoxType.BLUE, false));
 /*3*/  this.boxes.add(new Box("Viale Monte Rosa", 100, 6, BoxType.BLUE, false));
 /*4*/  this.boxes.add(new Box("Stazione SUD", 200, 25, BoxType.STATION, false));
 /*5*/  this.boxes.add(new Box("Viale Vesuvio", 120, 8, BoxType.BLUE, false));
 /*6*/  this.boxes.add(new CardBox("Probabilità", chanceCards,BoxType.CHANCE));
 /*7*/  this.boxes.add(new Box("Transito/Prigione", 0, 0, BoxType.TRANSIT, true)); 
 /*8*/  this.boxes.add(new Box("Via Marco Polo", 220, 18, BoxType.RED, false));       
 /*9*/  this.boxes.add(new Box("Corso Magellano", 220, 18, BoxType.RED, false));
 /*10*/ this.boxes.add(new Box("Stazione OVEST", 200, 25, BoxType.STATION, false));
 /*11*/ this.boxes.add(new Box("Largo Colombo", 240, 20, BoxType.RED, false));
 /*12*/ this.boxes.add(new CardBox("Imprevisti", unexpectedCards,BoxType.UNEXPECTED));
 /*13*/ this.boxes.add(new Box("Transito", 0, 0, BoxType.TRANSIT, true));
 /*14*/ this.boxes.add(new Box("Viale Costantino", 260, 22, BoxType.YELLOW, false));
 /*15*/ this.boxes.add(new Box("Viale Traiano", 260, 22, BoxType.YELLOW, false));
 /*16*/ this.boxes.add(new Box("Stazione NORD", 200, 25, BoxType.STATION, false));
 /*17*/ this.boxes.add(new Box("Piazza Giulio Cesare", 280, 24, BoxType.YELLOW, false));
 /*18*/ this.boxes.add(new CardBox("Probabilità", chanceCards,BoxType.CHANCE));
 /*19*/ this.boxes.add(new Box("Vai in prigione", 0, 0, BoxType.JAIL, true));
 /*20*/ this.boxes.add(new Box("Via Roma", 300, 26, BoxType.GREEN, false));
 /*21*/ this.boxes.add(new Box("Corso Impero", 300, 26, BoxType.GREEN, false));
 /*22*/ this.boxes.add(new Box("Stazione EST", 200, 25, BoxType.STATION, false));
 /*23*/ this.boxes.add(new Box("Largo Augusto", 320, 28, BoxType.GREEN, false));
 /*24*/ this.boxes.add(new CardBox("Imprevisti", unexpectedCards,BoxType.UNEXPECTED));
    }

	// METHODS
	public List<Box> getBoxes() {
		return this.boxes;
	}

	public Box getBox(int boxIndex) {
		if (boxIndex >= 0 && boxIndex < boxes.size()) 
			return boxes.get(boxIndex);
		else 
			throw new IllegalArgumentException("The index of the box on the board is not valid");	
	}
}