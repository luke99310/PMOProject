package model;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

public class PlayerTest {
	
	Player player;
	Box box;
	
	@Before
	public void setUp() {
		player = new Player("Luca", new Game());
		box = new Box("vicolo corto", 500, 50, BoxType.BLUE, false);
	}

	@Test
	public void testEquals() {
		Assert.assertEquals(1500, player.getBalance());
	}

}
