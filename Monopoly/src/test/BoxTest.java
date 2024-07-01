package test;

import org.junit.*;

import model.Box;
import model.Game;
import model.Player;
import model.MonopolyTypes.BoxType;

public class BoxTest {
	
	Box box1;
	Box box2;
	Player player1;

	@Before
	public void setUpBox() {
		box1 = new Box("Bastioni Gran Sasso", 500, 6, BoxType.BLUE, false);
		box2 = new Box("Via Marco Polo", 220, 18, BoxType.RED, false);
		player1 = new Player("Luca", new Game(), 1500);
	}
	
	// testing buildHouse method
	@Test
	public void testBuildHouse() {
		box1.buildHouse();
		Assert.assertEquals("building house1", 1, box1.getBuiltHouses());
		box1.buildHouse();
		Assert.assertEquals("building house2", 2, box1.getBuiltHouses());
		box1.buildHouse();
		Assert.assertEquals("can't build house3", 2, box1.getBuiltHouses());
	}
	
	// testing fullSet method
	@Test
	public void testFullSet() {
		Assert.assertEquals("double rent (6$)", 12, box1.fullSet());
		box1.buildHouse();
		Assert.assertEquals("tripe rent (6$)", 18, box1.fullSet());
		box1.buildHouse();
		Assert.assertEquals("quadruple rent(6$)", 24, box1.fullSet());
	}
	
	// testing equals
	@Test
	public void testEquals1() {
		Assert.assertTrue(box1.equals(box1));
	}
	
	@Test
	public void testEquals2() {
		Assert.assertFalse(box1.equals(box2));
	}
	
	@Test
	public void testEquals3() {
		Box box3 = box1;
		Assert.assertTrue("different reference same box", box1.equals(box3));
	}
	
	@Test
	public void testEquals4() {
		Box box3 = new Box("Bastioni Gran Sasso", 500, 6, BoxType.BLUE, true);
		Assert.assertFalse("same fields except isSpecial", box1.equals(box3));
	}
	
	@Test
	public void testEquals5() {
		Assert.assertFalse("not a box", box1.equals(player1));
	}
}
