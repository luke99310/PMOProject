package view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import view.Interfaces.PlayerFieldInterface;

public class PlayerField implements PlayerFieldInterface{
	
	//CONSTANTS
	private static final int MAX_PLAYERS = 4;
	
	//FIELDS
    private JTextField[] playerFields;
    private JDialog dialog; //dialog box
    private JButton startButton;

    //CONSTRUCTORS
    public PlayerField() {   	
    	playerFields = new JTextField[MAX_PLAYERS];
    	
    	//create button
    	this.startButton = new JButton("Start game");
        this.startButton.setPreferredSize(new Dimension(10, 20));

    	//create dialog box for entering player names.
        this.dialog = new JDialog();
        this.dialog.setLayout(new GridLayout(5, 1)); //set grid of 5 rows and 1 column
        this.dialog.setSize(1000, 800);
        
        //prevent the window from closing when it is created
        this.dialog.setDefaultCloseOperation(dialog.DO_NOTHING_ON_CLOSE);
        this.dialog.setAlwaysOnTop(true); //the dialog box will not close if you click outside of it
        
        //create text fields for entering player names and add them to the JDialog
        for (int i = 0; i < MAX_PLAYERS; i++) {
            this.playerFields[i] = new JTextField();
            this.dialog.add(playerFields[i]);
        }
        
        //add the button to the JDialog
        this.dialog.add(startButton);
    }

    //METHODS    
    public JTextField[] getFields() {
        return this.playerFields;
    }
    
    public JDialog getJDialog() {
        return this.dialog;
    }
    
    public JButton getStartButton() {
        return this.startButton;
    }
}