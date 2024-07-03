package view.Interfaces;

import view.Board;
import view.ButtonsPanel;
import view.PlayerField;

public interface ViewInterface {

	ButtonsPanel getButtons();

    Board getBoard();
    
    PlayerField getPlayerField();
}
