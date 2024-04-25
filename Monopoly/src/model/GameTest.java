package model;

import org.junit.*;

public class GameTest {
	
	Game game;
	Player player1;
	Player player2;
	Player player3;
	
	@Before
	public void setUpGameTest() {
		game = new Game();
		this.player1 = new Player("Luca", game);
		this.player2 = new Player("Lorenzo", game);
		this.player3 = new Player("Marco", game);
	}

	@Test
	public void testCheckPlayerFunds() {
		Assert.assertTrue(game.getPlayers().contains(player3));
		game.checkPlayerFunds(player3);
		Assert.assertFalse("player3 non aveva proprietà, quindi va rimosso", game.getPlayers().contains(player3));
	}
}