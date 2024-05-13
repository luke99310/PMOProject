package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DicesTest {
	
	Game game;
	Player player1;
	Player player2;
	Player player3;
	List<Player> players;
	
	// IMPORTANT, SINGLETON PATTERN CLASSES DO NOT RESET BETWEEN TESTS
	@Before
	public void setUpGameTest() {
		game = new Game();
		player1 = new Player("Luca", game);
		player2 = new Player("Lorenzo", game);
		players = new ArrayList<Player>();
		players.addAll(Arrays.asList(player1, player2));
	}
	
    // testing the rollDices method FLAKY TESTS!!!!!
	@Test
	public void testRollDices1() {
		int diceThrow = game.getDices().rollDices(player1);
		Assert.assertNotEquals("the sum is returned",0, diceThrow);
	}
	
	// the singleton class remembers that dices were thrown before by player 1 
	@Test
	public void testRollDices2() {
		int diceThrow = game.getDices().rollDices(player2);
		Assert.assertNotEquals("the sum is returned",0, diceThrow);
	}
	
	// player1 can't throw the dices
	@Test
	public void testRollDices3() {
		game.getDices().rollDices(player1);
		game.getDices().rollDices(player1);
		game.getDices().rollDices(player1);
		int diceThrow = game.getDices().rollDices(player1);
		Assert.assertEquals("player1 can't throw the dices", 0, diceThrow);
	}
	
	// player2 goes to prison
	@Test
	public void testRollDices4() {
		game.getDices().rollDices(player2);
		game.getDices().rollDices(player2);
		int diceThrow = game.getDices().rollDices(player2);
		game.getDices().getLog();
		Assert.assertEquals("player2 goes to prison", -1, diceThrow);
	}
	
	
	 
	
}
