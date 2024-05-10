package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestDices {
	
	Game game;
	Player player1;
	Player player2;
	Player player3;
	List<Player> players;
	
	@Before
	public void setUpGameTest() {
		game = new Game();
		player1 = new Player("Luca", game);
		player2 = new Player("Lorenzo", game);
		// players automatically add themselves to the game (addPlayer is invoked inside the constructor)
		players = new ArrayList<Player>();
		players.addAll(Arrays.asList(player1, player2));
	}
	
	// tsting the rollDices method
	@Test
	public void testRollDices1() {
		player1.move(game.getDices().rollDices(player1));
		Assert.assertNotEquals("player should have moved",0, player1.getPositionIndex());
	}
	
}
