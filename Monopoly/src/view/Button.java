package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button {
	
	//FIELDS
    private JButton diceButton;
    private JButton nextButton;
    private JButton buyButton;
    private JButton sellButton;
    private JButton buildHouse;

    private JLabel playerLabel;
    private JLabel piecePlayer;
    private JLabel balanceLabel;
    
    
    private JTextArea diceLabel;

	private JPanel propertiesPanel;
    private JComboBox<String> propertiesBox;
    
    private JPanel panel2;

    
    //CONSTRUTORS
    public Button() {
    	
    	this.panel2 = new JPanel();
    	this.panel2.setLayout(new BorderLayout());

        // Inizializzo i pulsanti
    	this.diceButton = new JButton("LANCIA I DADI");
    	this.nextButton = new JButton("PROSSIMO TURNO   ⇛");
    	this.buyButton = new JButton("COMPRA PROPRIETA'");
    	this.sellButton = new JButton("VENDI PROPRIETA'");
    	this.buildHouse = new JButton("CREA CASA   ⌂");

        // Inizializzo le etichette
    	this.playerLabel = new JLabel("");
    	this.piecePlayer = new JLabel("");
    	this.balanceLabel = new JLabel("");
    	this.diceLabel = new JTextArea("");
    	
    	propertiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Usa FlowLayout
        propertiesBox = new JComboBox<>();
        propertiesBox.setPreferredSize(new Dimension(250, 30));
        sellButton = new JButton("Vendi");
        propertiesPanel.add(propertiesBox);
        propertiesPanel.add(sellButton);


        // Imposto le dimensioni dei pulsanti
        this.diceButton.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 220, diceButton.getPreferredSize().height + 20));
        this.nextButton.setPreferredSize(new Dimension(nextButton.getPreferredSize().width + 10, nextButton.getPreferredSize().height + 20));
        this.buyButton.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 10, diceButton.getPreferredSize().height + 20));
        //this.sellButton.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 10, diceButton.getPreferredSize().height + 20));
        this.buildHouse.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 10, diceButton.getPreferredSize().height + 20));

        // Creo un nuovo pannello per contenere le labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        // Aggiungo un filler in cima
        labelPanel.add(Box.createVerticalGlue());

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
        
        //creo textArea per balance
        /*
        this.properties.setFont(new Font("Serif", Font.PLAIN, 20));
        this.properties.setAlignmentX(Component.CENTER_ALIGNMENT); // Centro la label
        properties.setWrapStyleWord(true);
        properties.setLineWrap(true);
        properties.setOpaque(false);
        properties.setEditable(false);
        properties.setFocusable(false);
        properties.setBackground(UIManager.getColor("Label.background"));
        properties.setFont(UIManager.getFont("Label.font"));
        properties.setBorder(UIManager.getBorder("Label.border"));
                */

        labelPanel.add(this.propertiesPanel);
        
        // Aggiungo un filler in fondo
        labelPanel.add(Box.createVerticalGlue());

        // Aggiungo labelPanel a panel2
        this.panel2.add(labelPanel, BorderLayout.NORTH);

        // Pannello per i pulsanti/label
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        
        // Creo un GridBagConstraints per posizionare i pulsanti
        GridBagConstraints gbc = new GridBagConstraints();

        // Imposto gli spazi intorno ai bottoni (top, left, bottom, right)
        gbc.insets = new Insets(50, 0, 20, 20); 

        // Posiziono i pulsanti "Compra" e "Crea Casa"
        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonPanel.add(this.buyButton, gbc);

        gbc.gridx = 1;
        buttonPanel.add(this.buildHouse, gbc);

        // Posiziono il pulsante "Lancia Dadi"
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Occupa 2 colonne
        buttonPanel.add(this.diceButton, gbc);
        
        gbc.gridy = 2;
        diceLabel.setWrapStyleWord(true);
        diceLabel.setSize(100, 80);
        diceLabel.setOpaque(false);
        diceLabel.setEditable(false);
        diceLabel.setFocusable(false);
        diceLabel.setBackground(UIManager.getColor("Label.background"));
        diceLabel.setFont(UIManager.getFont("Label.font"));
        diceLabel.setBorder(UIManager.getBorder("Label.border"));
        buttonPanel.add(this.diceLabel, gbc);
                
        // Posiziono il pulsante "Prossimo Turno"
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Occupa 1 colonna
        buttonPanel.add(this.nextButton, gbc);

        // Aggiungo il pannello dei pulsanti a panel2
        this.panel2.add(buttonPanel, BorderLayout.CENTER);
    }

    // Metodi getter per i pulsanti e le etichette
    public JButton getDiceButton() {
        return this.diceButton;
    }

    public JButton getNextButton() {
        return this.nextButton;
    }

    public JButton getBuyButton() {
        return this.buyButton;
    }
    
    public JButton getSellButton() {
        return this.sellButton;
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
    
    public JTextArea getDiceLabel() {
        return this.diceLabel;
    }
    
    public JPanel getPanel() {
        return this.panel2;
    }
    
    public void addProperty(String propertyName) {
        propertiesBox.addItem(propertyName);
        propertiesPanel.revalidate();
        propertiesPanel.repaint();
    }

    public void clearProperties() {
        propertiesBox.removeAllItems();
        propertiesPanel.revalidate();
        propertiesPanel.repaint();
    }

    public String getSelectedProperty() {
        return (String) propertiesBox.getSelectedItem();
    }

    public void addSellActionListener(ActionListener sellAction) {
        sellButton.addActionListener(sellAction);
    }
    
}