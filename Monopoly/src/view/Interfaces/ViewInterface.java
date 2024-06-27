package view.Interfaces;

import view.Board;
import view.Button;
import view.PlayerField;

public interface ViewInterface {

	Button getButtons();

    Board getBoard();
    
    PlayerField getPlayerField();
}
