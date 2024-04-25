package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.*;

public class GameTest {
	
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
		player3 = new Player("Marco", game);
		// players automatically add themselves to the game (addPlayer is invoked inside the constructor)
		players = new ArrayList<Player>();
		players.addAll(Arrays.asList(player1, player2, player3));
	}

	// testing addPlayer method
	@Test
	public void testAddPlayer() {
		Assert.assertTrue(game.getPlayers().contains(player1));
	}
	
	// testing getPlayers method
	@Test
	public void testGetPlayers() {
		Assert.assertEquals(players, game.getPlayers());
	}
	

}