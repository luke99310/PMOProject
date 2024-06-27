/*
package view;

import javax.swing.*;
import java.awt.*;

public class p {
	
	//FIELDS
    private JFrame frame;
    private Board board;
    private Button buttons;
    private PlayerField playerFields;
    
    //CONSTRUCOTORS
    public p() {
    	
    		frame = new JFrame("Monopoly Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            board = new Board();
            buttons = new Button();
            playerFields = new PlayerField();
            
            // Aggiungi panel2 al frame
            frame.add(buttons.getPanel(), BorderLayout.EAST);
                     
            // Dopo aver creato panel1 e panel2...
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            board.getPanel().setMinimumSize(new Dimension((int)(width * 0.7), 0));
            
            // Creo un nuovo JSplitPane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board.getPanel(), buttons.getPanel());

            // Disabilito il trascinamento del divisore
            splitPane.setEnabled(false);
            
            // Aggiungo il JSplitPane al frame
            frame.add(splitPane);

            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
                        
            playerFields.getJDialog().pack();
            playerFields.getJDialog().setLocationRelativeTo(frame);
            playerFields.getJDialog().setVisible(true);
    }

    //METHODS	
	public Button getButtons() {
        return this.buttons;
    }

    public Board getBoard() {
        return this.board;
    }
    
    public PlayerField getPlayerField() {
        return this.playerFields;
    }
}
*/  