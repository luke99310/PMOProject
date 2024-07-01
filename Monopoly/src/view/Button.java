package view;

import javax.swing.*;

import view.Interfaces.ButtonInterface;

import java.awt.*;

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

        //initialize the buttons
    	this.diceButton = new JButton("DICE THROW");
    	this.nextButton = new JButton("NEXT TURN   ⇛");
    	this.buyButton = new JButton("BUY PROPERTY");
    	this.auctionButton = new JButton("AUCTION");
    	this.buildHouse = new JButton("CREATE HOME   ⌂");

        //initialize labels
    	this.playerLabel = new JLabel("");
    	this.piecePlayer = new JLabel("");
    	this.balanceLabel = new JLabel(""); 
    	this.diceLabel = new JLabel("");
    	
        //create a new panel to hold the labels
    	JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS)); //sets the layout of the labelPanel  
        																   //so that its components are arranged vertically
        																   //(top to bottom)
        
        // create label player
        this.playerLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        this.playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //center the label
        labelPanel.add(playerLabel);

        //create label piecePlayer
        this.piecePlayer.setFont(new Font("Serif", Font.PLAIN, 20));
        this.piecePlayer.setAlignmentX(Component.CENTER_ALIGNMENT); //center the label
        labelPanel.add(piecePlayer);

        //create label per balance
        this.balanceLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //center the label
        labelPanel.add(balanceLabel);

        //player properties panel
        this.propertiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //display the components from left to right, centered vertically 
        															   //in each row, with each row aligned to the left        
        this.propertiesBox = new JComboBox<>();
        this.propertiesBox.setPreferredSize(new Dimension(250, 30));
        this.propertiesPanel.add(propertiesBox);
        this.propertiesPanel.add(auctionButton);
        labelPanel.add(this.propertiesPanel);

        //add labelPanel to panel2
        this.panel2.add(labelPanel, BorderLayout.NORTH);
        
        //set the size of the buttons
        this.diceButton.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 220, diceButton.getPreferredSize().height + 20));
        this.nextButton.setPreferredSize(new Dimension(nextButton.getPreferredSize().width + 10, nextButton.getPreferredSize().height + 20));
        this.buyButton.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 10, diceButton.getPreferredSize().height + 20));
        this.buildHouse.setPreferredSize(new Dimension(diceButton.getPreferredSize().width + 10, diceButton.getPreferredSize().height + 20));   

        //panel for buttons/label
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        
        //create a GridBagConstraints to place the buttons
        GridBagConstraints gbc = new GridBagConstraints();

        //set the spaces (in pixels) around the buttons (top, left, bottom, right)
        gbc.insets = new Insets(50, 0, 20, 20); 

        //position the "Buy" and "Create Home" buttons.
        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonPanel.add(this.buyButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        buttonPanel.add(this.buildHouse, gbc);

        //position the "Throw Dice" button.
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; //occupies 2 columns
        buttonPanel.add(this.diceButton, gbc);
        
        gbc.gridy = 2;
        diceLabel.setSize(100, 80);
        diceLabel.setFont(UIManager.getFont("Label.font"));
        diceLabel.setBorder(UIManager.getBorder("Label.border"));
        buttonPanel.add(this.diceLabel, gbc);
                
        //position the "Next Player" button.
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; //occupies 2 columns
        buttonPanel.add(this.nextButton, gbc);

        //add button panel to panel2
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
        propertiesBox.addItem(propertyName);//adds an element to the propertiesBox
        propertiesPanel.revalidate(); 		//the propertiesPanel panel is updated
        propertiesPanel.repaint();  		//the propertiesPanel panel is redrawn
    }

    public void clearProperties() {
        propertiesBox.removeAllItems(); //removes all elements from propertiesBox
        propertiesPanel.revalidate();
        propertiesPanel.repaint();
    }

    public String getSelectedProperty() {
        return (String) propertiesBox.getSelectedItem(); //returns the selected element to propertiesBox
    }

}