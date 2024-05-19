package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.*;

import enumeration.BoxType;
import model.CardInterface;
import model.Game;
import model.Player;
import model.PlayerInterface;

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
	
	// the player goes to prison FLAKY TEST
	@Test
	public void testRollDices1() {
		game.rollDices();
		game.rollDices();
		int diceThrow = game.rollDices();
		Assert.assertEquals("the player is sent to prison", -1, diceThrow);
		game.getCurrentPlayer().move(diceThrow);
		Assert.assertTrue(game.getCurrentPlayer().isInJail());
	}
	
	// the player can't throw the dices
	@Test
	public void testRollDices2() {
		game.rollDices();
		game.rollDices();
		game.rollDices();
		int diceThrow = game.rollDices();
		Assert.assertEquals("the player can't throw the dices", 0, diceThrow);
	}
	
	// a new player can throw the dices and move normally
	@Test
	public void testRollDices3() {
		game.rollDices();
		game.nextPlayer();
		int diceThrow = game.rollDices();
		Assert.assertNotEquals("the player can move", 0, diceThrow);
		Assert.assertNotEquals("the player is not sent to prison", -1, diceThrow);
	}
	
	// testing next player method
	@Test
	public void testNextPlayer() {
		PlayerInterface player1 = game.getCurrentPlayer();
		game.nextPlayer();
		Assert.assertNotEquals("the current player is changed", player1, game.getCurrentPlayer());
	}
	
	// testing the draw card method
	@Test
	public void testDrawCard1() {	
		CardInterface card = game.drawCard(BoxType.UNEXPECTED);
		game.drawCard(BoxType.UNEXPECTED);
		game.drawCard(BoxType.UNEXPECTED);
		game.drawCard(BoxType.UNEXPECTED);
		game.drawCard(BoxType.UNEXPECTED);
		game.drawCard(BoxType.UNEXPECTED);
		game.drawCard(BoxType.UNEXPECTED);
		Assert.assertNotEquals("the card are shuffled", card, game.drawCard(BoxType.UNEXPECTED));
	}

	@Test
	public void testDrawCard2() {	
		CardInterface card = game.drawCard(BoxType.CHANCE);
		game.drawCard(BoxType.CHANCE);
		game.drawCard(BoxType.CHANCE);
		game.drawCard(BoxType.CHANCE);
		game.drawCard(BoxType.CHANCE);
		game.drawCard(BoxType.CHANCE);
		game.drawCard(BoxType.CHANCE);
		Assert.assertNotEquals("the card are shuffled", card, game.drawCard(BoxType.CHANCE));
	}
	
	// passing the wrong card type 
	@Test(expected = IllegalArgumentException.class)
	public void testDrawCard3() {
		game.drawCard(BoxType.BLUE);
	}
	
}