package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class p {
	
	//FIELDS
    private JFrame frame;
    private JPanel panel1;
    private HashMap<Integer, MonopolyCell> boardMap;
    private JTextField[] playerFields = new JTextField[4];
    private JButton diceButton; // Definisci diceButton come variabile di istanza
    private JButton nextButton; // Definisci nextButton come variabile di istanza
    private JButton startButton; // Definisci nextButton come variabile di istanza

    


    //CONSTRUCOTORS
    public p() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Monopoly Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Crea il primo JPanel che conterrà il tuo codice esistente
            panel1 = new JPanel();
            panel1.setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.weightx = 1;
            constraints.weighty = 1;
            constraints.fill = GridBagConstraints.BOTH;
            
            String[] names = {"PARTENZA", "BASTIONI GRAN SASSO", "VIALE MONTE ROSA", "STAZIONE SUD", "VIALE VESUVIO", "PROBABILITA'", 
                              "TRANSITO", "VIA MARCO POLO", "CORSO MAGELLANO", "STAZIONE OVEST", "LARGO COLOMBO", "IMPREVISTI", 
                              "TRANSITO", "VIALE COSTANTINO", "VIALE TRAIANO", "Stazione NORD", "PIAZZA GIULIO CESARE", "PROBABILITA'", 
                              "PRIGIONE", "VIA ROMA", "CORSO IMPERO", "STAZIONE EST", "LARGO COLOMBO", "IMRPEVISTI"};  
            
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
                            if (id > 24) id = 1;
                        }
                        constraints.gridx = j;
                        constraints.gridy = i;
                        MonopolyCell cell = new MonopolyCell(names[id - 1] /*+ " id = " + id*/, id, colors[id - 1]);
                        System.out.println("Cella nome e id: " + cell.getName() + ". SOLO L'ID: " + cell.getId() + ".");
                        cell.setPreferredSize(new Dimension(50, 50)); // Imposta le dimensioni preferite della casella
                        panel1.add(cell, constraints); // Aggiungi la cella a panel1
                        boardMap.put(nameIndex, cell);
                        nameIndex++;
                    }
                }
            }

            // Crea il container centrale con il titolo "MONOPOLI"
            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel centerLabel = new JLabel("MONOPOLY", SwingConstants.CENTER);
            centerLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 50)); // Imposta il font e la dimensione del testo
            centerPanel.add(centerLabel, BorderLayout.CENTER); // Aggiungi la label al centro del pannello

            constraints.gridx = 1; // Imposta la posizione x iniziale
            constraints.gridy = 1; // Imposta la posizione y iniziale
            constraints.gridwidth = 5; // Imposta la larghezza a 5 celle
            constraints.gridheight = 5; // Imposta l'altezza a 5 celle

            panel1.add(centerPanel, constraints); // Aggiungi centerPanel a panel1            
            //panel2
            // Crea il secondo JPanel per contenere altri componenti
            JPanel panel2 = new JPanel();
            panel2.setLayout(new BorderLayout());

            // Crea la label per il nome del giocatore
            JLabel playerLabel = new JLabel();
            playerLabel.setHorizontalAlignment(JLabel.CENTER);
            panel2.add(playerLabel, BorderLayout.NORTH);

            // Crea un pannello per i pulsanti
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridBagLayout());

            // Crea i pulsanti
            JButton buyButton = new JButton("COMPRA PROPRIETA'");
            buyButton.setPreferredSize(new Dimension(buyButton.getPreferredSize().width + 10, buyButton.getPreferredSize().height + 20));
            JButton sellButton = new JButton("VENDI PROPRIETA'");
            sellButton.setPreferredSize(new Dimension(sellButton.getPreferredSize().width + 10, sellButton.getPreferredSize().height + 20));
            this.diceButton = new JButton("LANCIA I DADI");
            this.diceButton.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 220, diceButton.getPreferredSize().height + 20));
            this.nextButton = new JButton("PROSSIMO TURNO ⇛");
            this.nextButton.setPreferredSize(new Dimension(nextButton.getPreferredSize().width + 10, nextButton.getPreferredSize().height + 20));

            // Crea un GridBagConstraints per posizionare i pulsanti
            GridBagConstraints gbc = new GridBagConstraints();

            // Imposta gli insetti (top, left, bottom, right)
            gbc.insets = new Insets(50, 0, 50, 20); // Aggiunge 10 pixel di spazio sopra e sotto ogni componente

            // Posiziona i pulsanti "Compra" e "Vendi"
            gbc.gridx = 0;
            gbc.gridy = 1;
            buttonPanel.add(buyButton, gbc);

            gbc.gridx = 1;
            buttonPanel.add(sellButton, gbc);

            // Posiziona il pulsante "Lancia Dadi"
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Occupa 2 colonne
            buttonPanel.add(diceButton, gbc);

            // Posiziona il pulsante "Prossimo Turno"
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2; // Occupa 1 colonna
            buttonPanel.add(nextButton, gbc);

            // Aggiungi il pannello dei pulsanti a panel2
            panel2.add(buttonPanel, BorderLayout.CENTER);

            // Aggiungi panel2 al frame
            frame.add(panel2, BorderLayout.EAST);
            //fine panel2
            
            
            
            // Dopo aver creato panel1 e panel2...
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            panel1.setMinimumSize(new Dimension((int)(width * 0.7), 0));
            
            // Crea un nuovo JSplitPane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1, panel2);

            // Disabilita il trascinamento del divisore
            splitPane.setEnabled(false);
            // Aggiungi il JSplitPane al frame
            frame.add(splitPane);

            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
            
            
            // Crea una finestra di dialogo per l'inserimento dei nomi dei giocatori
            JDialog dialog = new JDialog(frame, "Inserisci i nomi dei giocatori", true);
            dialog.setLayout(new GridLayout(5, 1));
            dialog.setSize(1000, 800);

            // Crea i campi di testo per l'inserimento dei nomi dei giocatori
            for (int i = 0; i < 4; i++) {
                playerFields[i] = new JTextField();
                dialog.add(playerFields[i]);
            }

            // Crea un pulsante "Inizia Gioco"
            this.startButton = new JButton("Inizia Gioco");
            this.startButton.addActionListener(e -> {
                // Controlla che ci siano almeno due giocatori
                int playerCount = 0;
                for (JTextField field : playerFields) {
                    if (!field.getText().isEmpty()) {
                        playerCount++;
                    }
                }

                if (playerCount >= 2) {
                    // Inizia il gioco
                    dialog.dispose();
                    // Qui puoi creare i tuoi oggetti giocatore e iniziare il gioco
                } else {
                    // Mostra un messaggio di errore
                    JOptionPane.showMessageDialog(dialog, "Devono unirsi almeno due giocatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            });
            dialog.add(startButton);

            dialog.pack();
            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        });
    }

    //METHODS
	// metodo per mettere a true o a false la label per le pedine dei giocatori
	public void setLabelVisibilityOnBoard(HashMap<Integer, MonopolyCell> boardMap, int id, int player, boolean visibility) {
	    MonopolyCell cell = boardMap.get(id);
	    if (cell != null) {
	        cell.setLabelVisibility(player, visibility);
	    }
	}
	
	public JButton getDiceButton() {
        return this.diceButton;
    }

    public JButton getNextPlayerButton() {
        return this.nextButton;
    }
    
    public JButton getStartButton() {
        return this.startButton;
    }
    
    public JTextField[] getPlayers() {
        return this.playerFields;
    }
	

}
            
