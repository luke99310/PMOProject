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
	
	// testing updateBalance method
	
	// testing buyBox method
	@Test
	public void testBuyBox_1() {
		player1.buyBox(box1, box1.getCost());
		Assert.assertEquals("the player spent 500$ on the property", 1000, player1.getBalance());
		Assert.assertEquals("the player is the new owner of the box", Optional.of(player1),box1.getOwner());
		Assert.assertTrue("box must be in player's properties", player1.getProperties().contains(box1));
		Assert.assertFalse("the box should not be sellable", box1.isSellable());
		
	}
	
	@Test
	public void testBuyBox_2() {
		player1.buyBox(box3, box3.getCost());
		Assert.assertEquals("the player doesn't buy the property", 1500, player1.getBalance());
		Assert.assertTrue("none should own the property", box3.getOwner().isEmpty());
		Assert.assertFalse("box must not be in player's properties", player1.getProperties().contains(box3));
		Assert.assertTrue("the box should be sellable", box3.isSellable());
		
	}
	
	@Test
	public void testBuyBox_3() {
		player1.buyBox(notSellableBox, notSellableBox.getCost());
		Assert.assertEquals("the player can't buy the property", 1500, player1.getBalance());
		Assert.assertTrue("none should own the property", notSellableBox.getOwner().isEmpty());
		Assert.assertFalse("box must be in player's properties", player1.getProperties().contains(notSellableBox));
		Assert.assertFalse("the box should not be sellable", notSellableBox.isSellable());
		
	}
	
	// testing numberOfOwnedPropertiesOfType method
	@Test
	public void TestNumberOfOwnedPropertiesOfType_1() {
		Assert.assertEquals("player owns 0 blue properties", 0, player1.numberOfOwnedPropertiesOfType(BoxType.BLUE));
	}
	
	@Test
	public void TestNumberOfOwnedPropertiesOfType_2() {
		player1.buyBox(box1, 0);
		Assert.assertEquals("player owns 1 blue property", 1, player1.numberOfOwnedPropertiesOfType(BoxType.BLUE));
	}
	
	@Test
	public void TestNumberOfOwnedPropertiesOfType_3() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		Assert.assertEquals("player owns 2 blue properties", 2, player1.numberOfOwnedPropertiesOfType(BoxType.BLUE));
	}

	@Test
	public void TestNumberOfOwnedPropertiesOfType_4() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		Assert.assertEquals("player owns 0 red properties", 0, player1.numberOfOwnedPropertiesOfType(BoxType.RED));
	}
	
	// testing ownsAllBoxesOfType method
	@Test
	public void TestOwnsAllBoxesOfType_1() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		player1.buyBox(box3, 0);
		Assert.assertTrue("player has all the boxes", player1.ownsAllBoxesOfType(BoxType.BLUE));
	}
	
	@Test
	public void TestOwnsAllBoxesOfType_2() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		Assert.assertFalse(" player doesn't own all the boxes", player1.ownsAllBoxesOfType(BoxType.BLUE));
	}
	
	// testing payRent method
	@Test
	public void testPayRent_1() {
		player1.buyBox(box1, 0);
		player2.payRent(box1);
		Assert.assertEquals("player1 recieves 6$",1506, player1.getBalance());
		Assert.assertEquals("player2 pays 6$",1494, player2.getBalance());
	}
	
	@Test
	public void testPayRent_2() {
		player1.buyBox(box1, 0);
		player1.payRent(box1);
		Assert.assertEquals("player1 does not pay for his property",1500, player1.getBalance());
	}
	
	@Test
	public void testPayRent_3() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		player1.buyBox(box3, 0);
		player2.payRent(box1);
		Assert.assertEquals("player1 recieves double the money",1512, player1.getBalance());
		Assert.assertEquals("player2 pays extra for full set",1488, player2.getBalance());
	}
	
	@Test
	public void testPayRent_4() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		player1.buyBox(box3, 0);
		player2.payRent(box1);
		Assert.assertEquals("player1 recieves double the money",1512, player1.getBalance());
		Assert.assertEquals("player2 pays extra for full set",1488, player2.getBalance());
	}
	
	@Test
	public void testPayRent_5() {
		player1.buyBox(box1, 0);
		player1.buyBox(box2, 0);
		player1.buyBox(box3, 0);
		player1.buildHouse(box1);
		player2.payRent(box1);
		Assert.assertEquals("player1 recieves triple the money (- house_cost)",1418, player1.getBalance());
		Assert.assertEquals("player2 pays extra for full set",1482, player2.getBalance());
	}
	
	@Test
	public void testPayRent_6() {
		player1.payRent(notSellableBox);
		Assert.assertEquals("player1 doesn't pay any rent on this box",1500, player1.getBalance());
	}
	
	@Test
	public void testPayRent_7() {
		player1.buyBox(station1, 0);
		player2.payRent(station1);
		Assert.assertEquals("player1 recieves 25$ (25$ every station)",1525, player1.getBalance());
		Assert.assertEquals("player2 pays 25$ (25$ every station)",1475, player2.getBalance());
	}
	
	@Test
	public void testPayRent_8() {
		player1.buyBox(station1, 0);
		player1.buyBox(station2, 0);
		player2.payRent(station1);
		Assert.assertEquals("player1 recieves 50$ (25$ every station)",1550, player1.getBalance());
		Assert.assertEquals("player2 pays 50$ (25$ every station)",1450, player2.getBalance());
	}

	// testing putUpForAuction method (forcing the player to buy the property) 
	@Test
	public void testPutUpForAuction_1() {
		player1.buyBox(box1, 0);
		player1.putUpForAuction(player1, box1);
		Assert.assertEquals("player1 sells the property for 450$",1950, player1.getBalance());
		Assert.assertEquals("player2 buys the property for 450$",1050, player2.getBalance());
		Assert.assertTrue("player1 doesn't own the property anymore", box1.getOwner().get() != player1);
		Assert.assertTrue("player2 does own the property anymore", box1.getOwner().get() == player2);
	}
	
	@Test
	public void testPutUpForAuction_2() {
		player2.buyBox(box1, 0);
		player2.putUpForAuction(player2, box1);
		Assert.assertEquals("player1 sells the property for 450$",1950, player2.getBalance());
		Assert.assertEquals("player2 buys the property for 450$",1050, player1.getBalance());
		Assert.assertTrue("player1 doesn't own the property anymore", box1.getOwner().get() != player2);
		Assert.assertTrue("player2 does own the property anymore", box1.getOwner().get() == player1);
	}
	
	@Test
	public void testPutUpForAuction_3() {
		player1.buyBox(box1, 0);
		player2.putUpForAuction(player2, box1);
		Assert.assertEquals("player1 doesn't buy the property",1500, player1.getBalance());
		Assert.assertTrue("player1 still owns the property", box1.getOwner().get() == player1);	
	}
	
	@Test
	public void testPutUpForAuction_4() {
		player2.putUpForAuction(player2, notSellableBox);
		Assert.assertEquals("player1 can't buy the property",1500, player1.getBalance());
		Assert.assertTrue("none can buy this property", notSellableBox.getOwner().isEmpty());	
	}
	
	@Test
	public void testPutUpForAuction_5() {
		player1.buyBox(box1, 0);
		player2.buyBox(box2, 1500);
		player1.putUpForAuction(player1, box1);
		Assert.assertEquals("player1 sold it to the bank",1850, player1.getBalance());
		Assert.assertEquals("player2 is broke",0, player2.getBalance());
		Assert.assertTrue("none owns the property", box1.getOwner().isEmpty());
	}
	
}
