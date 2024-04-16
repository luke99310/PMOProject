package controller;

import model.Game;
import view.p;

public class App {
    public static void main(String[] args) {
        // Crea il modello
        Game game = new Game();

        // Crea la view
        p view = new p();

        // Crea il controller, passando il modello e la view
        Controller controller = new Controller(game, view);

        // Inizio dell'applicazione
        controller.start();
    }
}