package main;

import model.Game;
import view.p;
import controller.Controller;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crea il Model
            Game game = new Game();

            // Crea la View
            p view = new p();

            // Crea il Controller e collega il Model e la View
            //Controller controller = new Controller(game, view);

            // Visualizza la View
            System.out.println("giocatori: " + game.getPlayers());
        });

    }
}
