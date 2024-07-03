package test;

import org.junit.Test;

import model.Box;
import model.Player;
import model.Interfaces.PlayerInterface;
import model.MonopolyTypes.BoxType;
import model.Game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;

public class PlayerTest {
	
	PlayerInterface player1;
	PlayerInterface player2;
	Box box1;
	Box box2;
	Box box3;
	Box notSellableBox;
	Box station1;
	Box station2;
	Set<Box> fullSet;
	Game game;
	
	@Before
	public void setUp() {
		game = new Game();
		// player starts with 1500$ in box 1 (index 0)
		this.game.addPlayer("Luca");
		player1 = this.game.getPlayers().get(0);
		this.game.addPlayer("Lorenzo");
		player2 = this.game.getPlayers().get(1);
		box1 = new Box("Bastioni Gran Sasso", 500, 6, BoxType.BLUE, false);
		box2 = new Box("Viale Vesuvio", 1500, 8, BoxType.BLUE, false);
		box3 = new Box("Viale Monte Rosa", 2000, 6, BoxType.BLUE, false);
		station1 = new Box("Stazione SUD", 200, 25, BoxType.STATION, false);
		station2 = new Box("Stazione NORD", 200, 25, BoxType.STATION, false);
		fullSet = new HashSet<Box>();
		fullSet.addAll(Arrays.asList(box1, box2, box3));
		notSellableBox = new Box("Transito", 0, 0, BoxType.TRANSIT, true);
		this.game.startGame();
	}
	
	// testing buyBox method
	@Test
	public void testBuyBox1() {
		player1.buyBox(box1, box1.getCost());
		Assert.assertEquals("the player spent 500$ on the property", 1000, player1.getBalance());
		Assert.assertEquals("the player is the new owner of the box", Optional.of(player1),box1.getOwner());
		Assert.assertTrue("box must be in player's properties", player1.getProperties().contains(box1));
		Assert.assertFalse("the box should not be sellable", box1.isSellable());
		
	}
	
	@Test
	public void testBuyBox2() {
		player1.buyBox(box3, box3.getCost());
		Assert.assertEquals("the player doesn't buy the property", 1500, player1.getBalance());
		Assert.assertTrue("none should own the property", box3.getOwner().isEmpty());
		Assert.assertFalse("box must not be in player's properties", player1.getProperties().contains(box3));
		Assert.assertTrue("the box should be sellable", box3.isSellable());
		
	}
	
	@Test
	public void testBuyBox3() {
		player1.buyBox(notSellableBox, notSellableBox.getCost());
		Assert.assertEquals("the player can't buy the property", 1500, player1.getBalance());
		Assert.assertTrue("none should own the property", notSellableBox.getOwner().isEmpty());
		Assert.assertFalse("box must be in player's properties", player1.getProperties().contains(notSellableBox));
		Assert.assertFalse("the box should not be sellable", notSellableBox.isSellable());
		
	}
	
	// testing updateBalance method
	@Test
	public void testUpdateBalance1() {
		player1.updateBalance(100);
		Assert.assertEquals(1600, player1.getBalance());
	}
		
	@Test
	public void testUpdateBalance2() {
		player1.updateBalance(0);
		Assert.assertEquals(1500, player1.getBalance());
	}

	@Test
	public void testUpdateBalance3() {
		player1.updateBalance(-100);
		Assert.assertEquals(1400, player1.getBalance());
	}
	
	@Test
	public void testUpdateBalance4() {
		Assert.assertTrue("player1 is in game", this.game.getPlayers().contains(player1));
		player1.updateBalance(-1600);
		Assert.assertFalse("player1 is no longer in the game", this.game.getPlayers().contains(player1));
	}
	
	// testing the move method forcing players to buy the properties
	@Test
	public void testMove1() {
		player1.move(-1);
		Assert.assertTrue("Player should be in jail", player1.isInJail());
		Assert.assertEquals("the index must be updated", 6, player1.getPositionIndex());
	}
	
	@Test
	public void testMove2() {
		// the first box on the board is a property that can be bought
		player1.move(1);
		Assert.assertEquals("the index must be updated", 1, player1.getPositionIndex());
		Assert.assertTrue("Player should be on box 2",
				          player1.getPosition() == game.getBoard().getBoxes().get(1));
	}
	
	@Test
	public void testMove3() {
		player2.buyBox(game.getBoard().getBox(1), 0);
		// box 2 belongs to player2 so player1 pays the rent
		player1.move(1);
		Assert.assertEquals("the index must be updated", 1, player1.getPositionIndex());
		Assert.assertTrue("Player should be on box 2",
				          player1.getPosition() == game.getBoard().getBoxes().get(1));
		Assert.assertEquals("player1 payed 6$ to player 2", 1494, player1.getBalance());
		Assert.assertEquals("player2 earned 6$", 1506, player2.getBalance());
	}
	
	@Test
	public void testMove4() {
		player1.buyBox(game.getBoard().getBox(1), 0);
		// player1 lands on his own property
		player1.move(1);
		Assert.assertEquals("the index must be updated", 1, player1.getPositionIndex());
		Assert.assertTrue("Player should be on box 2",
				          player1.getPosition() == game.getBoard().getBoxes().get(1));
		Assert.assertEquals("player1 still has his balance", 1500, player1.getBalance());
	}
	
	@Test
	public void testMove5() {
		// player1 completes a lap around the board
		player1.move(22);
		player1.move(2);
		Assert.assertEquals("the index must be updated to 0", 0, player1.getPositionIndex());
		Assert.assertTrue("Player should be on box 1 (start)",
				          player1.getPosition() == game.getBoard().getBoxes().get(0));
		Assert.assertEquals("player1 recieves 200$", 1700, player1.getBalance());
	}
	
	@Test
	public void testMove6() {
		// player draws a chance card FLAKY TEST!!!
		player1.move(5);
		Assert.assertEquals("balance not changed", 1500, player1.getBalance());
		Assert.assertEquals("the index must be updated", 5, player1.getPositionIndex());
		Assert.assertTrue("Player should be on box 6",
				          player1.getPosition() == game.getBoard().getBoxes().get(5));
	}
	
	@Test
	public void testMove7() {
		// player draws an unexpected card FLAKY TEST!!!
		player1.move(11);
		Assert.assertEquals("the index must be updated", 11, player1.getPositionIndex());
		Assert.assertTrue("Player should be on box 12",
				          player1.getPosition() == game.getBoard().getBoxes().get(11));
		Assert.assertEquals("balance changed", 1500, player1.getBalance());
	}
	
	@Test
	public void testMove8() {
		// player lands on "go to jail"
		player1.move(18);
		Assert.assertEquals("the index must be updated", 6, player1.getPositionIndex());
		Assert.assertTrue("Player should be in jail", player1.isInJail());	
	}

	@Test
	public void testMove9() {
		// player is in jail and tries to move"
		player1.move(18);
		player1.move(1);
		Assert.assertEquals("player doesn't move", 6, player1.getPositionIndex());
		Assert.assertTrue("Player should be in jail", player1.isInJail());
	}
	
	@Test
	public void testMove10() {
		// player comes out of jail after 3 turns (starts moving on the 4th turn)"
		player1.move(18); // goes to jail
		player1.move(1);
		player1.move(1);
		player1.move(1);
		player1.move(1);
		Assert.assertEquals("player moved 1 space (jail box has index 6) ", 7, player1.getPositionIndex());
		Assert.assertFalse("Player shouldn't be in jail", player1.isInJail());
	}
	
	@Test
	public void testMove11() {
		player2.buyBox(game.getBoard().getBox(1), 0);
		player2.buyBox(game.getBoard().getBox(2), 0);
		player2.buyBox(game.getBoard().getBox(4), 0);
		player1.move(1);
		Assert.assertEquals("the index must be updated", 1, player1.getPositionIndex());
		Assert.assertEquals("player1 payed 12$ to player 2 (fullSet)", 1488, player1.getBalance());
		Assert.assertEquals("player2 earned 12$", 1512, player2.getBalance());
	}
	
	@Test
	public void testMove12() {
		player2.buyBox(game.getBoard().getBox(3), 0);
		player2.buyBox(game.getBoard().getBox(9), 0);
		// box 2 belongs to player2 so player1 pays the rent
		player1.move(3);
		Assert.assertEquals("the index must be updated", 3, player1.getPositionIndex());
		Assert.assertTrue("Player should be on box 4",
				          player1.getPosition() == game.getBoard().getBoxes().get(3));
		Assert.assertEquals("player1 payed 50$ to player 2", 1450, player1.getBalance());
		Assert.assertEquals("player2 earned 6$", 1550, player2.getBalance());
	}
	
	// testing putUpForAuction method FLAKY TESTS (1 and 2)!!!
	@Test
	public void testPutUpForAuction1() {
		player1.buyBox(box1, 0);
		player1.putUpForAuction(box1);
		Assert.assertEquals("player1 sells the property for 450$",1950, player1.getBalance());
		Assert.assertEquals("player2 buys the property for 450$",1050, player2.getBalance());
		Assert.assertTrue("player1 doesn't own the property anymore", box1.getOwner().get() != player1);
		Assert.assertTrue("player2 does own the property anymore", box1.getOwner().get() == player2);
	}
		
	@Test
	public void testPutUpForAuction2() {
		Assert.assertFalse(player1.getProperties().contains(box1));
		Assert.assertFalse(box1.getOwner().isPresent());
		player1.buyBox(box1, 0);
		Assert.assertTrue(player1.getProperties().contains(box1));
		player2.putUpForAuction(box1);
		Assert.assertEquals("player1 doesn't buy the property",1500, player1.getBalance());
		Assert.assertTrue("player1 still owns the property", box1.getOwner().get() == player1);			
	}
		
	@Test
	public void testPutUpForAuction3() {
		player2.putUpForAuction(notSellableBox);
		Assert.assertEquals("player1 can't buy the property",1500, player1.getBalance());
		Assert.assertTrue("none can buy this property", notSellableBox.getOwner().isEmpty());	
	}
	
	@Test
	public void testPutUpForAuction4() {
		player1.buyBox(box1, 0);
		player2.buyBox(box2, 1500);
		player1.putUpForAuction(box1);
		Assert.assertEquals("player1 sold it to the bank",1850, player1.getBalance());
		Assert.assertEquals("player2 has 0$",0, player2.getBalance());
		Assert.assertTrue("none owns the property", box1.getOwner().isEmpty());
	}
		
	// testing buildHouse method
	@Test
	public void testBuildHouse1() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		player1.buyBox(box3, 0);
		player1.buildHouse(box1);
		Assert.assertEquals("player1 bough a house (100$)", 1400, player1.getBalance());
		Assert.assertEquals("the house is built", 1, box1.getBuiltHouses());
	}
		
	@Test
	public void testBuildHouse2() {
		player1.buyBox(box1, 0);
		player1.buildHouse(box1);
		Assert.assertEquals("player1 can't buy a house (no full set)", 1500, player1.getBalance());
		Assert.assertEquals("the house is not built", 0, box1.getBuiltHouses());
	}
		
	@Test
	public void testBuildHouse3() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		player1.buyBox(box3, 1500);
		player1.buildHouse(box1);
		Assert.assertEquals("player1 can't buy a house (no money)", 0, player1.getBalance());
		Assert.assertEquals("the house is not built", 0, box1.getBuiltHouses());
	}
		
	@Test
	public void testBuildHouse4() {
		player1.buyBox(station1, 0);
		player1.buildHouse(station1);
		Assert.assertEquals("player1 can't buy a house for this box", 1500, player1.getBalance());
		Assert.assertEquals("the house is not built", 0, box1.getBuiltHouses());
	}
	
	@Test
	public void testBuildHouse5() {
		player1.buildHouse(box1);
		Assert.assertEquals("player1 doesn't own the property", 1500, player1.getBalance());
		Assert.assertEquals("the house is not built", 0, box1.getBuiltHouses());
	}
		
	@Test
	public void testBuildHouse6() {
		player1.buildHouse(notSellableBox);
		Assert.assertEquals("player1 can't own the property", 1500, player1.getBalance());
		Assert.assertEquals("the house is not built", 0, box1.getBuiltHouses());
	}
	
	@Test
	public void testEquals1() {
		Assert.assertTrue(player1.equals(player1));
	}
	
	@Test
	public void testEquals2() {
		Assert.assertFalse(player1.equals(player2));
	}
	
	@Test
	public void testEquals3() {
		PlayerInterface player3 = player1;
		Assert.assertTrue("different reference same player", player1.equals(player3));
	}
	
	@Test
	public void testEquals4() {
		player1.buyBox(box1, 0);
		Player player3 = new Player("Luca", game, 1500);
		Assert.assertFalse("same fields except properties", player1.equals(player3));
	}
	
	@Test
	public void testEquals5() {
		Assert.assertFalse("not a player", player1.equals(box1));
	}
	
}