package test;

import org.junit.*;

import model.Board;
import model.Box;
import model.MonopolyTypes.BoxType;

public class BoardTest {

	Board board;
	Box box1;
	Box box2;
	
	@Before
	public void setUpBoard() {
		board = new Board();
		box1 = new Box("BASTIONI GRAN SASSO", 500, 6, BoxType.BLUE, true);
		box2 = new Box("STAZIONE OVEST", 200, 25, BoxType.STATION, true);
	}
	
	// testing getbox method
	@Test
	public void testGetBox_1() {
		Assert.assertEquals("first box of the board", box1.getName(), board.getBox(1).getName());
	}

	@Test
	public void testGetBox_2() {
		Assert.assertEquals("box at index 9", box2.getName(), board.getBox(9).getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetBox_3() {
		board.getBox(100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDraw1() {
		board.getCards(BoxType.BLUE);
	}
}
