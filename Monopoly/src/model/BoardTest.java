package model;

import org.junit.*;

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
	
	@Test
	public void testGetBox_1() {
		String expected = box1.getName();
		Assert.assertEquals("casella all'indice 1 del tabellone", expected, board.getBox(1).getName());
	}

	@Test
	public void testGetBox_2() {
		String expected = box2.getName();
		Assert.assertEquals("casella all'indice 9", expected, board.getBox(9).getName());
	}
}
