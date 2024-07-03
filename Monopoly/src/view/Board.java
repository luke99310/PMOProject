package view;

import javax.swing.*;

import view.Interfaces.BoardInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Board implements BoardInterface{
	
	//FIELDS
    private HashMap<Integer, MonopolyCell> boardMap; //numbered boxes list
    private JPanel panel1;

    // CONSTRUCTORS
    public Board() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout()); // it is used to place components in a grid of cells  
        boardMap = new HashMap<>();        
    }
 
    // METHODS
    public void update(ArrayList<String> names) {
    	GridBagConstraints constraints = new GridBagConstraints(); //it is used to specify restrictions for components  
        constraints.weightx = 1; //when the window is resized the horizontal space is distributed evenly
        constraints.weighty = 1; //when the window is resized the vertical space is evenly distributed
        constraints.fill = GridBagConstraints.BOTH; //the component is resized both horizontally and vertically  
        											//to occupy all available space in the cell
        
        Color[] colors = {Color.WHITE, Color.BLUE, Color.BLUE, Color.ORANGE, Color.BLUE, Color.CYAN, Color.WHITE,
                Color.RED, Color.RED, Color.ORANGE, Color.RED, Color.MAGENTA, Color.WHITE, Color.YELLOW,
                Color.YELLOW, Color.ORANGE, Color.YELLOW, Color.CYAN, Color.GRAY, Color.GREEN, Color.GREEN,
                Color.ORANGE, Color.GREEN, Color.MAGENTA};
        
        //assign each cell an id: 1 to 24
        //7 is the number of cells in a row
        //6 is the number of rows without counting the first bottom row
        //bottom right cell included in the calculation of 7 boxes in a row
        for (int i = 6; i >= 0; i--) {
            for (int j = 6; j >= 0; j--) {
                if ((i == 0 || i == 6) || (j == 0 || j == 6)) { // id is computed and cell assigned only if cell is located:
                												//on row 0 or row 6 --> i, column 0 or column 6 --> j
                    int id; //id to be assigned to the box
                    if (i == 6 && j <= 6) { // we are on the 1st row and scroll through all the columns
                        id = 7 - j; // 7 - the number of the column
                    } else if (j == 0 && i < 6) { //we are on the 1st column and we scroll up all the rows
                        id = 7 + (6 - i);  //from the value 7 (box we arrived in) I add (6 - row number) to number from 1 to 6                       				                     				  
                    } else if (i == 0 && j > 0) {  //we are on the 7th row and scroll up all the columns
                        id = 13 + j; //from value 13 (box we arrived in) I add the column value
                    } else {  //we are on the 7th column and scroll down the rows
                        id = 19 + i; //from value 19 (box we arrived in) I add the value of the row
                        if (id > 24)
                            id = 1;  //the first box is checked again and to not assign the value 25, it is set to 1
                    }
                    
                    //coordinates of the cell in the 7x7 grid
                    constraints.gridx = j;
                    constraints.gridy = i;
                    MonopolyCell cell = new MonopolyCell(names.get(id - 1), id, colors[id - 1]); //the -1 to find the corresponding in the array  
                    																			 //where elements start at index 0
                    																			 //while id starts from 1
                    cell.setPreferredSize(new Dimension(50, 50)); //box size
                    panel1.add(cell, constraints);  //add cell to panel1
                    								//constraints specifies where the cell will be placed in the layout 
                    boardMap.put(id, cell); //the id serves as the search key for the box
                }
            }
        }
        
        // central container with the title "MONOPOLY"
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //set the borders
        JLabel centerLabel = new JLabel("MONOPOLY", SwingConstants.CENTER); //create label with the title
        centerLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 50)); //set title font
        centerPanel.add(centerLabel, BorderLayout.CENTER); //add the label to the center of the centerPanel

        //starting point of centerLabel is the top left point
        constraints.gridx = 1;  	//set the initial x position
        constraints.gridy = 1; 		//set the initial y position
        constraints.gridwidth = 5; 	//set the width to 5 cells
        constraints.gridheight = 5; //set the height to 5 cells

        panel1.add(centerPanel, constraints); //add centerPanel to panel1                  
    }
    
    public JPanel getPanel() {
        return this.panel1;
    }

    public MonopolyCell getCell(int id) {
        return this.boardMap.get(id);
    }
    
    public HashMap<Integer, MonopolyCell> getCells() {
        return this.boardMap;
    }
    
    public void setLabelVisibilityOnBoard(int id, int player, boolean visibility) {
        MonopolyCell cell = boardMap.get(id);
        if (cell != null) {
            cell.setLabelVisibility(player, visibility);
        }
    }
}