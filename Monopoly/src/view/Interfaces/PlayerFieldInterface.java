package view.Interfaces;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public interface PlayerFieldInterface {
	
	JTextField[] getFields();
    
    JDialog getJDialog();
    
    JButton getStartButton();
}
