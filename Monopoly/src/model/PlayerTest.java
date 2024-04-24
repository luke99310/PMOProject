package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;

public class PlayerTest {
	
	Player player1;
	Player player2;
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
		player1 = new Player("Luca", game);
		player2 = new Player("Lorenzo", game);
		box1 = new Box("Bastioni Gran Sasso", 500, 6, BoxType.BLUE, false);
		box2 = new Box("Viale Vesuvio", 1500, 8, BoxType.BLUE, false);
		box3 = new Box("Viale Monte Rosa", 2000, 6, BoxType.BLUE, false);
		station1 = new Box("Stazione SUD", 200, 25, BoxType.STATION, false);
		station2 = new Box("Stazione NORD", 200, 25, BoxType.STATION, false);
		fullSet = new HashSet<Box>();
		fullSet.addAll(Arrays.asList(box1, box2, box3));
		notSellableBox = new Box("Transito", 0, 0, BoxType.TRANSIT, true);
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
	
	// testing numberOfOwnedPropertiesOfType method
	@Test
	public void TestNumberOfOwnedPropertiesOfType1() {
		Assert.assertEquals("player owns 0 blue properties", 0, player1.numberOfOwnedPropertiesOfType(BoxType.BLUE));
	}
	
	@Test
	public void TestNumberOfOwnedPropertiesOfType2() {
		player1.buyBox(box1, 0);
		Assert.assertEquals("player owns 1 blue property", 1, player1.numberOfOwnedPropertiesOfType(BoxType.BLUE));
	}
	
	@Test
	public void TestNumberOfOwnedPropertiesOfType3() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		Assert.assertEquals("player owns 2 blue properties", 2, player1.numberOfOwnedPropertiesOfType(BoxType.BLUE));
	}

	@Test
	public void TestNumberOfOwnedPropertiesOfType4() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		Assert.assertEquals("player owns 0 red properties", 0, player1.numberOfOwnedPropertiesOfType(BoxType.RED));
	}
	
	// testing ownsAllBoxesOfType method
	@Test
	public void TestOwnsAllBoxesOfType1() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		player1.buyBox(box3, 0);
		Assert.assertTrue("player has all the boxes", player1.ownsAllBoxesOfType(BoxType.BLUE));
	}
	
	@Test
	public void TestOwnsAllBoxesOfType2() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		Assert.assertFalse(" player doesn't own all the boxes", player1.ownsAllBoxesOfType(BoxType.BLUE));
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
	
	// testing payRent method
	@Test
	public void testPayRent1() {
		player1.buyBox(box1, 0);
		player2.payRent(box1);
		Assert.assertEquals("player1 recieves 6$",1506, player1.getBalance());
		Assert.assertEquals("player2 pays 6$",1494, player2.getBalance());
	}
	
	@Test
	public void testPayRent2() {
		player1.buyBox(box1, 0);
		player1.payRent(box1);
		Assert.assertEquals("player1 does not pay for his property",1500, player1.getBalance());
	}
	
	@Test
	public void testPayRent3() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		player1.buyBox(box3, 0);
		player2.payRent(box1);
		Assert.assertEquals("player1 recieves double the money",1512, player1.getBalance());
		Assert.assertEquals("player2 pays extra for full set",1488, player2.getBalance());
	}
	
	@Test
	public void testPayRent4() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		player1.buyBox(box3, 0);
		player2.payRent(box1);
		Assert.assertEquals("player1 recieves double the money",1512, player1.getBalance());
		Assert.assertEquals("player2 pays extra for full set",1488, player2.getBalance());
	}
	
	@Test
	public void testPayRent5() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		player1.buyBox(box3, 0);
		player1.buildHouse(box1);
		player2.payRent(box1);
		Assert.assertEquals("player1 recieves triple the money (- house_cost)",1418, player1.getBalance());
		Assert.assertEquals("player2 pays extra for full set",1482, player2.getBalance());
	}
	
	@Test
	public void testPayRent6() {
		player1.payRent(notSellableBox);
		Assert.assertEquals("player1 doesn't pay any rent on this box",1500, player1.getBalance());
	}
	
	@Test
	public void testPayRent7() {
		player1.buyBox(station1, 0);
		player2.payRent(station1);
		Assert.assertEquals("player1 recieves 25$ (25$ every station)",1525, player1.getBalance());
		Assert.assertEquals("player2 pays 25$ (25$ every station)",1475, player2.getBalance());
	}
	
	@Test
	public void testPayRent8() {
		player1.buyBox(station1, 0);
		player1.buyBox(station2, 0);
		player2.payRent(station1);
		Assert.assertEquals("player1 recieves 50$ (25$ every station)",1550, player1.getBalance());
		Assert.assertEquals("player2 pays 50$ (25$ every station)",1450, player2.getBalance());
	}
	
	// testing the move method forcing players to buy the properties
	@Test
	public void testMove1() {
		player1.move(0);
		Assert.assertTrue("Player should be in jail", player1.isInJail());
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
		// player lands on a ChanceBox FLAKY TEST!!!
		player1.move(5);
		Assert.assertEquals("the index must be updated", 5, player1.getPositionIndex());
		Assert.assertTrue("Player should be on box 6",
				          player1.getPosition() == game.getBoard().getBoxes().get(5));
	}
	
	@Test
	public void testMove7() {
		// player lands on an UnexpectedBox FLAKY TEST!!!
		player1.move(11);
		Assert.assertEquals("the index must be updated", 11, player1.getPositionIndex());
		Assert.assertTrue("Player should be on box 12",
				          player1.getPosition() == game.getBoard().getBoxes().get(11));
	}
	
	@Test
	public void testMove8() {
		// player lands on "go to jail"
		player1.move(18);
		Assert.assertEquals("the index must be updated", 18, player1.getPositionIndex());
		Assert.assertTrue("Player should be in jail", player1.isInJail());	
	}

	@Test
	public void testMove9() {
		// player is in jail and tries to move"
		player1.move(18);
		player1.move(1);
		Assert.assertEquals("player doesn't move", 18, player1.getPositionIndex());
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
		Assert.assertEquals("player moved 1 space (exit box is index 6) ", 7, player1.getPositionIndex());
		Assert.assertFalse("Player shouldn't be in jail", player1.isInJail());
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
		player1.buyBox(box1, 0);
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
		Player player3 = player1;
		Assert.assertTrue("different reference same player", player1.equals(player3));
	}
	
	@Test
	public void testEquals4() {
		player1.buyBox(box1, 0);
		Player player3 = new Player("Luca", game);
		Assert.assertFalse("same fields except properties", player1.equals(player3));
	}
	
	@Test
	public void testEquals5() {
		Assert.assertFalse("not a player", player1.equals(box1));
	}
	
}