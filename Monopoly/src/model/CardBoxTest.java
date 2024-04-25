package model;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

public class CardBoxTest {

	Board board;
	Player player;
	ChanceBox chance;
	UnexpectedBox unexpected;
	Card chanceCard;
	Card unexpectedCard;
	private List<Card> chanceCards;
	private List<Card> unexpectedCards;
	
	@Before
	public void setUpCardBoxTest() {
		board = new Board();
		player = new Player("Luca", new Game());
		this.chanceCards = new ArrayList<>();
		this.chanceCards.add(new Card("La banca vi paga un dividendo di 55", 55, ActionType.BALANCE));;
    	this.unexpectedCards = new ArrayList<>();
    	this.unexpectedCards.add(new Card("Andate in prigione", 0, ActionType.JAIL));
    	chance = new ChanceBox("Probabilità", chanceCards,BoxType.CHANCE);
    	unexpected = new UnexpectedBox("Imprevisti", unexpectedCards,BoxType.UNEXPECTED);
	}
	
	// testing executeAction method
	@Test
	public void testExecuteActionUnexpected() {
		this.unexpected.executeAction(player);
		Assert.assertEquals("player goes to jail", 6, player.getPositionIndex());
	}
	
	@Test
	public void testExecuteActionChance() {
		this.chance.executeAction(player);
		Assert.assertEquals("player earns 55$", 1555, player.getBalance());
	}
	
	
	
	
	
	
	
	
	
	
}
