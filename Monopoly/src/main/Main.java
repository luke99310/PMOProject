package main;

import model.Game;
import view.View;
import controller.Controller;
 
public class Main {
	public static void main(String[] args) {
		//creo il Model
		Game game = new Game();

		//creo la View
		View view = new View();
 
		//creo il Controller e collego il Model e la View
		Controller controller = new Controller(game, view);
	}
}