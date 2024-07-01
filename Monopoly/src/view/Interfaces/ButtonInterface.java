package view.Interfaces;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public interface ButtonInterface {
	
	JButton getDiceButton();

    JButton getNextButton();

    JButton getBuyButton();
    
    JButton getAuctionButton();
    
    JButton getBuildHouse();

    JLabel getPlayerLabel();

    JLabel getPiecePlayer();

    JLabel getBalanceLabel();
    
    JLabel getDiceLabel();
    
    JPanel getPanel();
    
    void addProperty(String propertyName);

    void clearProperties();

    String getSelectedProperty();

}
