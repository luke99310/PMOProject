package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Board {
	
	//FIELDS
    private HashMap<Integer, MonopolyCell> boardMap;
    private JPanel panel1;

    //CONSTRUCTORS
    public Board() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;

        String[] names = {"PARTENZA", "BASTIONI GRAN SASSO", "VIALE MONTE ROSA", "STAZIONE SUD", "VIALE VESUVIO", "PROBABILITA'",
                "TRANSITO / PRIGIONE", "VIA MARCO POLO", "CORSO MAGELLANO", "STAZIONE OVEST", "LARGO COLOMBO", "IMPREVISTI",
                "TRANSITO", "VIALE COSTANTINO", "VIALE TRAIANO", "Stazione NORD", "PIAZZA GIULIO CESARE", "PROBABILITA'",
                "VAI IN PRIGIONE", "VIA ROMA", "CORSO IMPERO", "STAZIONE EST", "LARGO COLOMBO", "IMRPEVISTI"};

        Color[] colors = {Color.WHITE, Color.BLUE, Color.BLUE, Color.ORANGE, Color.BLUE, Color.CYAN, Color.WHITE,
                Color.RED, Color.RED, Color.ORANGE, Color.RED, Color.MAGENTA, Color.WHITE, Color.YELLOW,
                Color.YELLOW, Color.ORANGE, Color.YELLOW, Color.CYAN, Color.GRAY, Color.GREEN, Color.GREEN,
                Color.ORANGE, Color.GREEN, Color.MAGENTA};

        boardMap = new HashMap<>();
        int nameIndex = 0;
        for (int i = 6; i >= 0; i--) {
            for (int j = 6; j >= 0; j--) {
                if ((i == 0 || i == 6) || (j == 0 || j == 6)) {
                    int id;
                    if (i == 6 && j <= 6) {
                        id = 7 - j;
                    } else if (j == 0 && i < 6) {
                        id = 7 + (6 - i);
                    } else if (i == 0 && j > 0) {
                        id = 13 + j;
                    } else { // j == 6 && i > 0
                        id = 25 - (6 - i);
                        if (id > 24)
                            id = 1;
                    }
                    constraints.gridx = j;
                    constraints.gridy = i;
                    MonopolyCell cell = new MonopolyCell(names[id - 1], id, colors[id - 1]);
                    cell.setPreferredSize(new Dimension(50, 50)); // dimensioni casella
                    panel1.add(cell, constraints); // Aggiungo la cella a panel1
                    boardMap.put(id, cell);
                    nameIndex++;
                }
            }
        }
        
        // container centrale 
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel centerLabel = new JLabel("MONOPOLY", SwingConstants.CENTER);
        centerLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 50)); 
        centerPanel.add(centerLabel, BorderLayout.CENTER); // Aggiungo la label al centro del centerPanel

        constraints.gridx = 1; // Imposta la posizione x iniziale
        constraints.gridy = 1; // Imposta la posizione y iniziale
        constraints.gridwidth = 5; // Imposta la larghezza a 5 celle
        constraints.gridheight = 5; // Imposta l'altezza a 5 celle

        panel1.add(centerPanel, constraints); // Aggiungo centerPanel a panel1            
        
    }

    //METHODS
    public JPanel getPanel() {
        return panel1;
    }

    public MonopolyCell getCell(int id) {
        return boardMap.get(id);
    }
    
    public void setLabelVisibilityOnBoard(int id, int player, boolean visibility) {
        MonopolyCell cell = boardMap.get(id);
        if (cell != null) {
            cell.setLabelVisibility(player, visibility);
        }
    }
}