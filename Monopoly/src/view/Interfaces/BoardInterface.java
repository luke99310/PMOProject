package view.Interfaces;

import java.util.HashMap;

import javax.swing.JPanel;

import view.MonopolyCell;

public interface BoardInterface {

	JPanel getPanel();

    MonopolyCell getCell(int id);
    
    HashMap<Integer, MonopolyCell> getCells();
    
    void setLabelVisibilityOnBoard(int id, int player, boolean visibility);

}
