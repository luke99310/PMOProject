package model;

import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;

public class PlayerTest {
	
	Player player;
	Box box1;
	Box box2;
	Box box3;
	Box notSellableBox;
	Set<Box> fullSet;
	
	@Before
	public void setUp() {
		player = new Player("Luca", new Game());
		box1 = new Box("Bastioni Gran Sasso", 500, 6, BoxType.BLUE, false);
		box2 = new Box("Viale Vesuvio", 1500, 8, BoxType.BLUE, false);
		box3 = new Box("Viale Monte Rosa", 2000, 6, BoxType.BLUE, false);
		fullSet = new HashSet<Box>();
		fullSet.addAll(Arrays.asList(box1, box2, box3));
		System.out.println(fullSet);
		notSellableBox = new Box("Transito", 0, 0, BoxType.TRANSIT, true);
	}
	
	// testing buyBox method
	@Test
	public void testBuyBox_1() {
		player.buyBox(box1, box1.getCost());
		Assert.assertEquals("the player spent 500$ on the property", 1000, player.getBalance());
		Assert.assertEquals("the player is the new owner of the box", Optional.of(player),box1.getOwner());
		Assert.assertTrue("box must be in player's properties", player.getProperties().contains(box1));
		Assert.assertFalse("the box should not be sellable", box1.isSellable());
		
	}
	
	@Test
	public void testBuyBox_2() {
		player.buyBox(box3, box3.getCost());
		Assert.assertEquals("the player doesn't buy the property", 1500, player.getBalance());
		Assert.assertTrue("none should own the property", box3.getOwner().isEmpty());
		Assert.assertFalse("box must not be in player's properties", player.getProperties().contains(box3));
		Assert.assertTrue("the box should be sellable", box3.isSellable());
		
	}
	
	@Test
	public void testBuyBox_3() {
		player.buyBox(notSellableBox, notSellableBox.getCost());
		Assert.assertEquals("the player can't buy the property", 1500, player.getBalance());
		Assert.assertTrue("none should own the property", notSellableBox.getOwner().isEmpty());
		Assert.assertFalse("box must be in player's properties", player.getProperties().contains(notSellableBox));
		Assert.assertFalse("the box should not be sellable", notSellableBox.isSellable());
		
	}
	
	// testing numberOfOwnedPropertiesOfType method
	@Test
	public void TestNumberOfOwnedPropertiesOfType_1() {
		Assert.assertEquals("player owns 0 blue properties", 0, player.numberOfOwnedPropertiesOfType(BoxType.BLUE));
	}
	
	@Test
	public void TestNumberOfOwnedPropertiesOfType_2() {
		player.buyBox(box1, 0);
		Assert.assertEquals("player owns 1 blue property", 1, player.numberOfOwnedPropertiesOfType(BoxType.BLUE));
	}
	
	@Test
	public void TestNumberOfOwnedPropertiesOfType_3() {
		player.buyBox(box1, 0);
		player.buyBox(box2, 0);
		Assert.assertEquals("player owns 2 blue properties", 2, player.numberOfOwnedPropertiesOfType(BoxType.BLUE));
	}

	@Test
	public void TestNumberOfOwnedPropertiesOfType_4() {
		player.buyBox(box1, 0);
		player.buyBox(box2, 0);
		Assert.assertEquals("player owns 0 red properties", 0, player.numberOfOwnedPropertiesOfType(BoxType.RED));
	}
	
	// testing ownsAllBoxesOfType method
	@Test
	public void TestOwnsAllBoxesOfType_1() {
		player.buyBox(box1, 0);
		player.buyBox(box2, 0);
		player.buyBox(box3, 0);
		Assert.assertTrue("player has all the boxes", player.ownsAllBoxesOfType(BoxType.BLUE));
	}
	
	@Test
	public void TestOwnsAllBoxesOfType_2() {
		player.buyBox(box1, 0);
		player.buyBox(box2, 0);
		Assert.assertFalse(" player doesn't own all the boxes", player.ownsAllBoxesOfType(BoxType.BLUE));
	}
	

}
