package model;

import java.util.List;

public interface GameInterface {
	void addPlayer(Player player);

	void startGame();

	int rollDices();

	List<Player> getPlayers();

	Board getBoard();

	void checkPlayerFunds(Player p);
	
	Player getCurrentPlayer();

    void nextPlayer();
}
