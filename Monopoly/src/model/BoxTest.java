package model;

import org.junit.*;

public class BoxTest {
	
	Box box1;

	@Before
	public void setUpBox() {
		box1 = new Box("Bastioni Gran Sasso", 500, 6, BoxType.BLUE, false);
	}
	
	@Test
	public void testBuildHouse() {
		box1.buildHouse();
		Assert.assertEquals(1, box1.getBuiltHouses());
		box1.buildHouse();
		Assert.assertEquals(2, box1.getBuiltHouses());
		box1.buildHouse();
		Assert.assertEquals(2, box1.getBuiltHouses());
	}
	
	@Test
	public void testFullColorRent() {
		Assert.assertEquals(12, box1.fullSet());
		box1.buildHouse();
		Assert.assertEquals(18, box1.fullSet());
		box1.buildHouse();
		Assert.assertEquals(24, box1.fullSet());
	}
}
