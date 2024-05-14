package model;

import java.util.List;
import enumeration.BoxType;

public interface GameInterface {
	
	void addPlayer(Player player);

	void startGame();
	
	// int rollDices();

	List<Player> getPlayers();

	Board getBoard();
	
	Player getCurrentPlayer();

    void nextPlayer();
    
    Dices getDices();
    
    Card drawCard(BoxType boxtype) throws IllegalArgumentException;
}
