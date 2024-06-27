package view;

import javax.swing.*;

import view.Interfaces.ButtonInterface;

import java.awt.*;
import java.awt.event.ActionListener;

public class Button implements ButtonInterface{
	
	//FIELDS
    private JPanel panel2;
	private JPanel propertiesPanel;

    private JLabel playerLabel;
    private JLabel piecePlayer;
    private JLabel balanceLabel;
    private JLabel diceLabel;
	
    private JComboBox<String> propertiesBox;
    
    private JButton auctionButton;
    private JButton diceButton;
    private JButton buyButton;
    private JButton buildHouse;
    private JButton nextButton;

    //CONSTRUTORS
    public Button() {
    	
    	this.panel2 = new JPanel();
    	this.panel2.setLayout(new BorderLayout());

        //inizializzo i pulsanti
    	this.diceButton = new JButton("LANCIA I DADI");
    	this.nextButton = new JButton("PROSSIMO TURNO   ⇛");
    	this.buyButton = new JButton("COMPRA PROPRIETA'");
    	this.auctionButton = new JButton("METTI ALL'ASTA");
    	this.buildHouse = new JButton("CREA CASA   ⌂");

        //inizializzo le etichette
    	this.playerLabel = new JLabel("");
    	this.piecePlayer = new JLabel("");
    	this.balanceLabel = new JLabel(""); 
    	this.diceLabel = new JLabel("");
    	
        //creo un nuovo pannello per contenere le labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS)); //imposta il layout del pannello labelPanel 
        																   //in modo che i suoi componenti siano disposti verticalmente
        																   //(dall'alto al basso)

        //creo label player
        this.playerLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centro la label
        labelPanel.add(playerLabel);

        //creo label piecePlayer
        this.piecePlayer.setFont(new Font("Serif", Font.PLAIN, 20));
        this.piecePlayer.setAlignmentX(Component.CENTER_ALIGNMENT); // Centro la label
        labelPanel.add(piecePlayer);

        //creo label per balance
        this.balanceLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centro la label
        labelPanel.add(balanceLabel);

        //panel delle proprietà del giocatore
    	propertiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //dispongo i componenti da sinistra a destra, centrati verticalmente 
    																   //in ogni riga, con ogni riga allineata a sinistra
        propertiesBox = new JComboBox<>();
        propertiesBox.setPreferredSize(new Dimension(250, 30));
        propertiesPanel.add(propertiesBox);
        propertiesPanel.add(auctionButton);
        labelPanel.add(this.propertiesPanel);

        //imposto le dimensioni dei pulsanti
        this.diceButton.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 220, diceButton.getPreferredSize().height + 20));
        this.nextButton.setPreferredSize(new Dimension(nextButton.getPreferredSize().width + 10, nextButton.getPreferredSize().height + 20));
        this.buyButton.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 10, diceButton.getPreferredSize().height + 20));
        this.buildHouse.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 10, diceButton.getPreferredSize().height + 20));   

        //aggiungo labelPanel a panel2
        this.panel2.add(labelPanel, BorderLayout.NORTH);

        //pannello per i pulsanti/label
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        
        //creo un GridBagConstraints per posizionare i pulsanti
        GridBagConstraints gbc = new GridBagConstraints();

        //imposto gli spazi (in pixel) intorno ai bottoni (top, left, bottom, right)
        gbc.insets = new Insets(50, 0, 20, 20); 

        //posiziono i pulsanti "Compra" e "Crea Casa"
        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonPanel.add(this.buyButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        buttonPanel.add(this.buildHouse, gbc);

        //posiziono il pulsante "Lancia Dadi"
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; //occupa 2 colonne
        buttonPanel.add(this.diceButton, gbc);
        
        gbc.gridy = 2;
        diceLabel.setSize(100, 80);
        diceLabel.setFont(UIManager.getFont("Label.font"));
        diceLabel.setBorder(UIManager.getBorder("Label.border"));
        buttonPanel.add(this.diceLabel, gbc);
                
        //posiziono il pulsante "Prossimo Turno"
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; //occupa 2 colonna
        buttonPanel.add(this.nextButton, gbc);

        //aggiungo il pannello dei pulsanti a panel2
        this.panel2.add(buttonPanel, BorderLayout.CENTER);
    }

    //METHODS
    public JButton getDiceButton() {
        return this.diceButton;
    }

    public JButton getNextButton() {
        return this.nextButton;
    }

    public JButton getBuyButton() {
        return this.buyButton;
    }
    
    public JButton getAuctionButton() {
        return this.auctionButton;
    }
    
    public JButton getBuildHouse() {
        return this.buildHouse;
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
    
    public JLabel getDiceLabel() {
        return this.diceLabel;
    }
    
    public JPanel getPanel() {
        return this.panel2;
    }
    
    public void addProperty(String propertyName) {
        propertiesBox.addItem(propertyName); //aggiunge un elemento alla propertiesBox
        propertiesPanel.revalidate(); //il pannello propertiesPanel viene aggiornato
        propertiesPanel.repaint(); //il pannello propertiesPanel viene ridisegnato
    }

    public void clearProperties() {
        propertiesBox.removeAllItems(); //rimuove tutti gli elementi da propertiesBox
        propertiesPanel.revalidate();
        propertiesPanel.repaint();
    }

    public String getSelectedProperty() {
        return (String) propertiesBox.getSelectedItem(); //restituisce l’elemento selezionato in propertiesBox
    }

    public void addSellActionListener(ActionListener auctionAction) {
    	auctionButton.addActionListener(auctionAction);
    }
    
}