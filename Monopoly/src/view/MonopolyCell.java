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

        //creo un pannello per il titolo
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(color); //imposto il colore di sfondo

        //creo la label per il titolo
        JLabel titleLabel = new JLabel(name, SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK); //imposto il colore del testo
        titlePanel.add(titleLabel);

        //aggiungo il pannello del titolo a questa cella --> perché MonopolyCell ha esteso JPanel e si comporta come un JPanel
        this.add(titlePanel, BorderLayout.NORTH);

        JPanel subPanel = new JPanel(new GridLayout(2, 2)); //suddivido il subpanel in 4 parti
        labels = new JLabel[MAX_PLAYERS];
        for (int i = 0; i < labels.length; i++) {
            int j = 1;
            labels[i] = new JLabel("" + (j+i), SwingConstants.CENTER); //metto i valori da 1 a 4, per indicare le 4 pedine
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