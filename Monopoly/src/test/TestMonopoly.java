package test;

import model.Game;
import model.Player;

public class TestMonopoly {
	public static void main (String args[]) {      
		Game game = new Game();
		
		Player lorenzo = new Player("Lorenzo", game);
		Player luca = new Player("Luca", game);
		Player marco = new Player("Marco", game);
		
		System.out.println("lorenzo bilancio: " + lorenzo.getBalance());
		System.out.println("Si sposta di: ");
		lorenzo.move(1);
		System.out.println("Bastioni Gran Sasso: " + lorenzo.getPosition());
		System.out.println("costo casella: " + lorenzo.getPosition().getCost());
		System.out.println("bilancio: " + lorenzo.getBalance());
		System.out.println("indice poszione: " + lorenzo.getPositionIndex());
		
		System.out.println("");

		System.out.println("luca bilancio: " + luca.getBalance());
		System.out.println("Si sposta di: ");
		luca.move(1);
		System.out.println("Bastioni Gran Sasso: " + luca.getPosition());
		System.out.println("affitto: " + luca.getPosition().getRent());
		System.out.println("bilancio: " + luca.getBalance());
		System.out.println("indice poszione: " + luca.getPositionIndex());
		System.out.println("lorenzo bilancio: " + lorenzo.getBalance());

		System.out.println("");

		
		System.out.println("lorenzo bilancio: " + lorenzo.getBalance());
		System.out.println("Si sposta di: ");
		lorenzo.move(1);
		System.out.println("costo casella: " + lorenzo.getPosition().getCost());
		System.out.println("bilancio: " + lorenzo.getBalance());
		System.out.println("indice poszione: " + lorenzo.getPositionIndex());
		
		System.out.println("");

		System.out.println("lorenzo bilancio: " + lorenzo.getBalance());
		System.out.println("Si sposta di: ");
		lorenzo.move(2);
		System.out.println("costo casella: " + lorenzo.getPosition().getCost());
		System.out.println("bilancio: " + lorenzo.getBalance());
		System.out.println("indice poszione: " + lorenzo.getPositionIndex());
		System.out.println("lorenzo proprietà: " + lorenzo.getProperties());

		System.out.println("");


		

		System.out.println("luca bilancio: " + luca.getBalance());
		System.out.println("Si sposta di: ");
		luca.move(1);
		System.out.println("affitto: " + luca.getPosition().getRent());
		System.out.println("bilancio: " + luca.getBalance());
		System.out.println("indice poszione: " + luca.getPositionIndex());
		System.out.println("");
		System.out.println("lorenzo bilancio: " + lorenzo.getBalance());

		System.out.println("");

		System.out.println("marco bilancio: " + marco.getBalance());
		System.out.println("Si sposta di: ");
		marco.move(4);
		System.out.println("affitto: " + marco.getPosition().getRent());
		System.out.println("bilancio: " + marco.getBalance());
		System.out.println("indice poszione: " + marco.getPositionIndex());
		System.out.println("lorenzo bilancio: " + lorenzo.getBalance());

		System.out.println("");


		
	}
}

