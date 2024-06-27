package view;

import javax.swing.*;

import view.Interfaces.BoardInterface;

import java.awt.*;
import java.util.HashMap;

public class Board implements BoardInterface{
	
	//FIELDS
    private HashMap<Integer, MonopolyCell> boardMap; //elenco caselle numerate
    private JPanel panel1;

    //CONSTRUCTORS
    public Board() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout()); //serve per posizionare i componenti in una griglia di celle

        GridBagConstraints constraints = new GridBagConstraints(); //serve per specificare le restrizioni per i componenti 
        constraints.weightx = 1; //quando la finestra viene ridimensionata lo spazio orizzontale si distribuisce in modo uniforme
        constraints.weighty = 1; //quando la finestra viene ridimensionata lo spazio verticale si distribuisce in modo uniforme
        constraints.fill = GridBagConstraints.BOTH; //il componente viene ridimensionato sia orizzontalmente che verticalmente 
        											//per occupare tutto lo spazio disponibile nella cella
        
        String[] names = {"PARTENZA", "BASTIONI GRAN SASSO", "VIALE MONTE ROSA", "STAZIONE SUD", "VIALE VESUVIO", "PROBABILITA'",
                "TRANSITO / PRIGIONE", "VIA MARCO POLO", "CORSO MAGELLANO", "STAZIONE OVEST", "LARGO COLOMBO", "IMPREVISTI",
                "TRANSITO", "VIALE COSTANTINO", "VIALE TRAIANO", "Stazione NORD", "PIAZZA GIULIO CESARE", "PROBABILITA'",
                "VAI IN PRIGIONE", "VIA ROMA", "CORSO IMPERO", "STAZIONE EST", "LARGO AUGUSTO", "IMRPEVISTI"};

        Color[] colors = {Color.WHITE, Color.BLUE, Color.BLUE, Color.ORANGE, Color.BLUE, Color.CYAN, Color.WHITE,
                Color.RED, Color.RED, Color.ORANGE, Color.RED, Color.MAGENTA, Color.WHITE, Color.YELLOW,
                Color.YELLOW, Color.ORANGE, Color.YELLOW, Color.CYAN, Color.GRAY, Color.GREEN, Color.GREEN,
                Color.ORANGE, Color.GREEN, Color.MAGENTA};

        boardMap = new HashMap<>();
        
        //assegno ad ogni cella un id: da 1 a 24
        //7 è il numero di caselle in una riga
        //6 è il numero di righe senza contare la prima riga in basso)
        //casella in basso a destra compresa nel calcolo delle 7 caselle in una riga
        for (int i = 6; i >= 0; i--) {
            for (int j = 6; j >= 0; j--) {
                if ((i == 0 || i == 6) || (j == 0 || j == 6)) { //viene calcalato id e assegnata cella solo se la cella si trova:
                												//sulla riga 0 o riga 6 --> i, colonna 0 o colonna 6 --> j
                    int id; //id da assegnare alla casella
                    if (i == 6 && j <= 6) { // siamo sulla 1° riga e scorriamo tutte le colonne
                        id = 7 - j; // 7 - il numero della colonna
                    } else if (j == 0 && i < 6) { // siamo sulla 1° colonna e scorriamo verso l'alto tutte le righe
                        id = 7 + (6 - i); //dal valore 7 (casella in cui siamo arrivati) aggiungo (6 - numero di riga) per numerare da 1 a 6                       				  
                    } else if (i == 0 && j > 0) { //siamo sulla 7° riga e scorriamo tutte le colonne
                        id = 13 + j; //dal valore 13 (casella in cui siamo arrivati) aggoungo il valore della colonna
                    } else { // siamo sulla 7° colonna e scorriamo verso il basso le righe
                        //id = 25 - (6 - i); 
                        id = 19 + i; //dal valore 19 (casella in cui siamo arrivati) aggiungo il valore della riga
                        if (id > 24)
                            id = 1; //la prima casella viene ricontrollata e per non assegnare il valore 25, viene settato a 1
                    }
                    
                    //coordinate della cella nella griglia 7x7
                    constraints.gridx = j;
                    constraints.gridy = i;
                    MonopolyCell cell = new MonopolyCell(names[id - 1], id, colors[id - 1]); //il -1 per trovare il corrispodente nell'array 
                    																		 //in cui gli elementi partono da indice 0
                    																		 //mentre l'id parte da 1
                    cell.setPreferredSize(new Dimension(50, 50)); // dimensioni casella
                    panel1.add(cell, constraints); //aggiungo la cella a panel1
                    							   //constraints specifa dove la cella verrà posizionata nel layout 
                    boardMap.put(id, cell); //l'id serve come chiave di ricerca della casella
                }
            }
        }
        
        // container centrale con il titolo "MONOPOLY"
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //setto i bordi
        JLabel centerLabel = new JLabel("MONOPOLY", SwingConstants.CENTER); //creo label con il titolo
        centerLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 50)); //setto il font del titolo
        centerPanel.add(centerLabel, BorderLayout.CENTER); //aggiungo la label al centro del centerPanel

        //punto in cui inizia la centerLabel è il punto in alto a sinistra
        constraints.gridx = 1;  	//imposto la posizione x iniziale
        constraints.gridy = 1; 		//imposto la posizione y iniziale
        constraints.gridwidth = 5; 	//imposto la larghezza a 5 celle
        constraints.gridheight = 5; //imposto l'altezza a 5 celle

        panel1.add(centerPanel, constraints); //aggiungo centerPanel a panel1            
        
    }

    //METHODS
    public JPanel getPanel() {
        return panel1;
    }

    public MonopolyCell getCell(int id) {
        return boardMap.get(id);
    }
    
    public HashMap<Integer, MonopolyCell> getCells() {
        return boardMap;
    }
    
    public void setLabelVisibilityOnBoard(int id, int player, boolean visibility) {
        MonopolyCell cell = boardMap.get(id);
        if (cell != null) {
            cell.setLabelVisibility(player, visibility);
        }
    }
}