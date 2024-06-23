package model;

import java.util.List;
import enumeration.BoxType;

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
    
    void removePlayer(PlayerInterface player);
    
    void notEnoughPlayers();
}
