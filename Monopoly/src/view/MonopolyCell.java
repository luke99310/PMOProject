package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.Interfaces.MonopolyCellInterface;
 
public class MonopolyCell extends JPanel implements MonopolyCellInterface{
	
	//CONSTANTS
	private static final int MAX_PLAYERS = 4;
		
	//FIELDS
    private static final long serialVersionUID = 1L;
    private String name;
    private int id;
    private JLabel[] labels;

    //CONSTRUCOTORS
    public MonopolyCell(String name, int id, Color color) {
        super(new BorderLayout());
        this.name = name;
        this.id = id;
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //create a title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(color); //set the background color

        //create the label for the title
        JLabel titleLabel = new JLabel(name, SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK); //set the color of the text
        titlePanel.add(titleLabel);

        //add title panel to this cell --> because MonopolyCell has extended JPanel and behaves like a JPanel
        this.add(titlePanel, BorderLayout.NORTH);

        JPanel subPanel = new JPanel(new GridLayout(2, 2)); //subdividing the subpanel into 4 parts.
        labels = new JLabel[MAX_PLAYERS];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel("" + (i+1), SwingConstants.CENTER); //put values from 1 to 4, to indicate the 4 pawns
            subPanel.add(labels[i]);
            labels[i].setVisible(false);
        }
        this.add(subPanel, BorderLayout.CENTER);
    }

    //METHODS
    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public void setLabelVisibility(int index, boolean visibility) {
        if (index >= 0 && index < labels.length) {
            labels[index].setVisible(visibility);
        }
    }
}