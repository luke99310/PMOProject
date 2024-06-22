package model.Interfaces;

import java.util.List;

import model.MonopolyTypes.BoxType;

public interface GameInterface {
	
	void addPlayer(String name);

	void startGame();
	
	int rollDices();

	List<PlayerInterface> getPlayers();

	BoardInterface getBoard();
	
	PlayerInterface getCurrentPlayer();
	
	public BankInterface getBank();

    void nextPlayer();
    
    CardInterface drawCard(BoxType boxType) throws IllegalArgumentException;
    
    void notEnoughPlayers();
}
