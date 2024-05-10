package view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

public class PlayerField {
	
	//FIELDS
    private JTextField[] playerFields;
    private JDialog dialog;
    private JButton startButton;



    //CONSTRUCTORS
    public PlayerField() {
    	
    	playerFields = new JTextField[4];
    	
    	//creo il bottone
        this.startButton = new JButton("Inizia Gioco");
        this.startButton.setPreferredSize(new Dimension(10, 20));

    	// Creo la finestra di dialogo per l'inserimento dei nomi dei giocatori
        dialog = new JDialog();
        dialog.setLayout(new GridLayout(5, 1));
        dialog.setSize(1000, 800);
        
        // Impedisci la chiusura della finestra quando viene creata
        dialog.setDefaultCloseOperation(dialog.DO_NOTHING_ON_CLOSE);


        // Creo i campi di testo per l'inserimento dei nomi dei giocatori
        for (int i = 0; i < 4; i++) {
            playerFields[i] = new JTextField();
            dialog.add(playerFields[i]);
        }
        
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