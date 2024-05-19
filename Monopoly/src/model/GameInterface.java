package model;

import java.util.List;
import enumeration.BoxType;

public interface GameInterface {
	
	void addPlayer(PlayerInterface player);

	void startGame();
	
	int rollDices();

	List<PlayerInterface> getPlayers();

	BoardInterface getBoard();
	
	PlayerInterface getCurrentPlayer();

    void nextPlayer();
    
    CardInterface drawCard(BoxType boxType) throws IllegalArgumentException;
}
