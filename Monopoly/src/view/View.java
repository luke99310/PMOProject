package view;

import javax.swing.*;

import view.Interfaces.ViewInterface;

import java.awt.*;
import java.util.ArrayList;

public class View implements ViewInterface{
	
	//FIELDS
    private JFrame frame;
    private Board board;
    private Button buttons;
    private PlayerField playerFields;
    private JSplitPane splitPane;
    
    //CONSTRUCOTORS
    public View() {
    
    		frame = new JFrame("Monopoly Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            board = new Board();
            buttons = new Button();
            playerFields = new PlayerField();
                                 
            //get the screen size            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
            double width = screenSize.getWidth();
            
            board.getPanel().setMinimumSize(new Dimension((int)(width * 0.7), 0)); //set the minimum board size to 70%.
            
            //create a JSplitPane to horizontally split the board panel and the button panel.
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board.getPanel(), buttons.getPanel());

            //disabled the dragging of the divider
            splitPane.setEnabled(false);
            
            //add the JSplitPane to the frame
            frame.add(splitPane);

            frame.pack(); //adapt the frame to the size of the components
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //the window takes up the whole screen
            frame.setVisible(true); //make the frame visible
                        
            playerFields.getJDialog().pack();  //resize JDialog so that it fits its components
            playerFields.getJDialog().setLocationRelativeTo(frame);  //the JDialog is placed in the center of the frame
            playerFields.getJDialog().setVisible(true);  //make the JDialog visible
    }

    //METHODS	
    public void initBoard(ArrayList<String> names) {
        //creation of the Board with names
    	this.board.update(names);

        //replacing the blank panel with the Board panel.
    	this.splitPane.setLeftComponent(board.getPanel());
    }
    
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
      