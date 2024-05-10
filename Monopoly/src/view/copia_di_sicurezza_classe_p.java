package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.Game;
import model.Player;

public class copia_di_sicurezza_classe_p {

}

//da qui la classe p
public class copia_di_sicurezza_classe_p {
	
	//FIELDS
    private JFrame frame;
    private JPanel panel1;
    private HashMap<Integer, MonopolyCell> boardMap;
    private JTextField[] playerFields = new JTextField[4];
    private JButton diceButton;
    private JButton nextButton; 
    private JButton startButton;
    private JButton buyButton;
    private JLabel playerLabel;
    private JLabel piecePlayer;
    private JLabel balanceLabel;



    
    //CONSTRUCOTORS
    public copia_di_sicurezza_classe_p() {
    	//inizializzo startButton
        this.diceButton = new JButton("LANCIA I DADI");
        this.nextButton = new JButton("PROSSIMO TURNO ⇛");
        this.startButton = new JButton("Inizia Gioco");
        this.buyButton = new JButton("COMPRA PROPRIETA'");

        this.playerLabel = new JLabel("");
        this.piecePlayer = new JLabel("");
        this.balanceLabel = new JLabel("");

        
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Monopoly Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Creo panel1 
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
                            if (id > 24) 
                            	id = 1;
                        }
                        constraints.gridx = j;
                        constraints.gridy = i;
                        MonopolyCell cell = new MonopolyCell(names[id - 1] /*+ " id = " + (id)*/, id, colors[id - 1]);
                        System.out.println("Cella nome e id: " + cell.getName() + ". SOLO L'ID: " + cell.getId() + ".");
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
            
            // Creo panel2
            JPanel panel2 = new JPanel();
            panel2.setLayout(new BorderLayout());

            // Creo un nuovo pannello per contenere le labels
            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

            // Aggiungo un filler in cima
            labelPanel.add(Box.createVerticalGlue());

            //creo label player
            playerLabel.setFont(new Font("Serif", Font.PLAIN, 30));
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra la label
            labelPanel.add(playerLabel);

            //creo label piecePlayer
            piecePlayer.setFont(new Font("Serif", Font.PLAIN, 20));
            piecePlayer.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra la label
            labelPanel.add(piecePlayer);

            //creo label per balance
            balanceLabel.setFont(new Font("Serif", Font.PLAIN, 20));
            balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra la label
            labelPanel.add(balanceLabel);
            
            // Aggiungo un filler in fondo
            labelPanel.add(Box.createVerticalGlue());

            // Aggiungo labelPanel a panel2
            panel2.add(labelPanel, BorderLayout.NORTH);

            // Pannello per i pulsanti
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridBagLayout());
            
            // Creo i pulsanti
            buyButton.setPreferredSize(new Dimension(buyButton.getPreferredSize().width + 10, buyButton.getPreferredSize().height + 20));
            JButton sellButton = new JButton("VENDI PROPRIETA'");
            sellButton.setPreferredSize(new Dimension(sellButton.getPreferredSize().width + 10, sellButton.getPreferredSize().height + 20));

            this.diceButton.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 220, diceButton.getPreferredSize().height + 20));

            this.nextButton.setPreferredSize(new Dimension(nextButton.getPreferredSize().width + 10, nextButton.getPreferredSize().height + 20));

            // Creo un GridBagConstraints per posizionare i pulsanti
            GridBagConstraints gbc = new GridBagConstraints();

            // Imposto gli spazi intorno ai bottoni (top, left, bottom, right)
            gbc.insets = new Insets(50, 0, 50, 20); 

            // Posiziono i pulsanti "Compra" e "Vendi"
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
                     
            
            // Dopo aver creato panel1 e panel2...
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double width = screenSize.getWidth();
            panel1.setMinimumSize(new Dimension((int)(width * 0.7), 0));
            
            // Creo un nuovo JSplitPane
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1, panel2);

            // Disabilito il trascinamento del divisore
            splitPane.setEnabled(false);
            
            // Aggiungo il JSplitPane al frame
            frame.add(splitPane);

            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
            
            
            // Creo una finestra di dialogo per l'inserimento dei nomi dei giocatori
            JDialog dialog = new JDialog(frame, "Inserisci i nomi dei giocatori", true);
            dialog.setLayout(new GridLayout(5, 1));
            dialog.setSize(1000, 800);

            // Creo i campi di testo per l'inserimento dei nomi dei giocatori
            for (int i = 0; i < 4; i++) {
                playerFields[i] = new JTextField();
                dialog.add(playerFields[i]);
            }

            // Creo un pulsante "Inizia Gioco"
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
	public void setLabelVisibilityOnBoard(int id, int player, boolean visibility) {
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
    
    public JButton getBuyButton() {
        return this.buyButton;
    }
    
    public JLabel getPlayerLabel() {
        return this.playerLabel;
    }
    
    public JLabel getPiecePlayer() {
    	return this.piecePlayer;
    }
    
    public JLabel getBalanceLabel() {
    	return this.balanceLabel;
    }
    
    public JTextField[] getPlayers() {
        return this.playerFields;
    }
	

}
            
// da qui la classe controller
public class Controller {
	
    private static final int POSITION_ADJUSTMENT = 1;

	//FIELDS
    private Game game; // Il Model
    private p view; // La View

    
    //CONSTRUCOTORS
    public Controller(Game game, p view) {
        this.game = game;
        this.view = view;

        // Aggiungo un ActionListener al pulsante "Inizia Gioco"
        view.getStartButton().addActionListener(e -> addPlayers());
        
        // Aggiungo un ActionListener al pulsante "Lancia Dadi"
        view.getDiceButton().addActionListener(e -> handleDiceRoll());

        // Aggiungo un ActionListener al pulsante "Prossimo giocatore"
        view.getNextPlayerButton().addActionListener(e -> handleNextPlayer());
        
        // Aggiungo un ActionListener al pulsante "compra casella"
        view.getBuyButton().addActionListener(e -> buyBox());
        
        
    }
    
    
    //METHODS
    public void addPlayers() {
    	JTextField[] players = view.getPlayers();
    	for(int i = 0; i < players.length; i++) {
    	    if(!players[i].getText().equals("")) {
	    		String playerName = players[i].getText();
	    	    new Player(playerName, game);
	            //view.setLabelVisibilityOnBoard(1, i, true); 
    	    }
    	}    
        view.getPlayerLabel().setText("Giocatore attuale: " + game.getCurrentPlayer().getName());
        view.getPiecePlayer().setText("Numero pedina: " + (game.getPlayers().indexOf(game.getCurrentPlayer()) + 1));
        view.getBalanceLabel().setText("Saldo: " + game.getCurrentPlayer().getBalance());

        view.getDiceButton().setText((game.getCurrentPlayer().getName()) + " lancia i dadi");
        System.out.println("giocatori: " + game.getPlayers());
    }
    
    private void handleDiceRoll() {
        // Chiama il metodo muovi() sul giocatore corrente
        view.setLabelVisibilityOnBoard(game.getCurrentPlayer().getPositionIndex() + POSITION_ADJUSTMENT, game.getPlayers().indexOf(game.getCurrentPlayer()), false);
        
        if(game.getCurrentPlayer().isInJail()) {
        	view.getBuyButton().setEnabled(false);
        	view.getDiceButton().setEnabled(false);
        }
        
        System.out.println(" ");
        System.out.println("Saldo prima di lanciare i dadi: " + game.getCurrentPlayer().getBalance());
    	game.getCurrentPlayer().move(game.rollDices());
        System.out.println("Saldo dopo lancio dadi: " + game.getCurrentPlayer().getBalance());
        System.out.println("Proprietà: " + game.getCurrentPlayer().getProperties());

        // Aggiorna la View
        view.setLabelVisibilityOnBoard(game.getCurrentPlayer().getPositionIndex() + POSITION_ADJUSTMENT, game.getPlayers().indexOf(game.getCurrentPlayer()), true);
        
        //aggiorno il bilancio del giocatore corrente
        view.getBalanceLabel().setText("Saldo: " + game.getCurrentPlayer().getBalance());

    }

    private void handleNextPlayer() {
        // Passa al prossimo giocatore
        System.out.println("Il giocatore attuale è: ");
        game.nextPlayer();
   	
        // Aggiorna il testo del pulsante "Lancia Dadi"
        view.getDiceButton().setText((game.getCurrentPlayer().getName()) + " lancia i dadi");

        // Aggiorna la label con il nome del giocatore corrente
        view.getPlayerLabel().setText("Giocatore attuale: " + game.getCurrentPlayer().getName());
        
        // Aggiorna la label con il numero della pedian del giocatore corrente
        view.getPiecePlayer().setText("Numero pedina: " + (game.getPlayers().indexOf(game.getCurrentPlayer()) + 1));
        
        //aggiorno il bilancio del giocatore corrente
        view.getBalanceLabel().setText("Saldo: " + game.getCurrentPlayer().getBalance());
        System.out.println("saldo giocatore attuale: " + game.getCurrentPlayer().getBalance());
        
        if(game.getCurrentPlayer().isInJail()) {
        	view.getBuyButton().setEnabled(false);
	    	view.getDiceButton().setEnabled(true);
        	view.getDiceButton().setText("Clicca per scontare la pena !!");
        }else {
	    	view.getBuyButton().setEnabled(true);
	    	view.getDiceButton().setEnabled(true);
        }
    }
    
    private void buyBox() {
    	game.getCurrentPlayer().buyBox(game.getCurrentPlayer().getPosition(), game.getCurrentPlayer().getPosition().getCost());
    	
    	//aggiorno il bilancio del giocatore corrente
        view.getBalanceLabel().setText("Saldo: " + game.getCurrentPlayer().getBalance());
    }
}