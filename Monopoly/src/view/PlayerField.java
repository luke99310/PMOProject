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
    private JDialog dialog; //finestra di dialogo
    private JButton startButton;

    //CONSTRUCTORS
    public PlayerField() {
    	
    	playerFields = new JTextField[MAX_PLAYERS];
    	
    	//creo il bottone
        this.startButton = new JButton("Start game");
        this.startButton.setPreferredSize(new Dimension(10, 20));

    	//creo la finestra di dialogo per l'inserimento dei nomi dei giocatori
        dialog = new JDialog();
        dialog.setLayout(new GridLayout(5, 1)); //imposto griglia di 5 righe e 1 colonna
        dialog.setSize(1000, 800);
        
        //impedisco la chiusura della finestra quando viene creata
        dialog.setDefaultCloseOperation(dialog.DO_NOTHING_ON_CLOSE);

        //creo i campi di testo per l'inserimento dei nomi dei giocatori e li aggiungo al JDialog
        for (int i = 0; i < MAX_PLAYERS; i++) {
            playerFields[i] = new JTextField();
            dialog.add(playerFields[i]);
        }
        
        //aggiungo il bottone al JDialog
        dialog.add(startButton);
    }

    //METHODS    
    public JTextField[] getFields() {
        return playerFields;
    }
    
    public JDialog getJDialog() {
        return dialog;
    }
    
    public JButton getStartButton() {
        return startButton;
    }
}