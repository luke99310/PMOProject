package model;

import java.util.List;

public interface GameInterface {
	void addPlayer(Player player);

	void startGame();

	List<Player> getPlayers();

	Board getBoard();
	
	Player getCurrentPlayer();

    void nextPlayer();
}
