package test;

import org.junit.*;

import enumeration.BoxType;
import model.Board;
import model.Box;

public class BoardTest {

	Board board;
	Box box1;
	Box box2;
	
	@Before
	public void setUpBoard() {
		board = new Board();
		box1 = new Box("Bastioni Gran Sasso", 500, 6, BoxType.BLUE, false);
		box2 = new Box("Stazione OVEST", 200, 25, BoxType.STATION, false);
	}
	
	// testing getbox method
	@Test
	public void testGetBox_1() {
		Assert.assertEquals("casella all'indice 1 del tabellone", box1.getName(), board.getBox(1).getName());
	}

	@Test
	public void testGetBox_2() {
		Assert.assertEquals("casella all'indice 9", box2.getName(), board.getBox(9).getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetBox_3() {
		board.getBox(100);
	}
}
