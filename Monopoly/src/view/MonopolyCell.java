package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MonopolyCell extends JPanel {
	
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

        // Crea un pannello per il titolo
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(color); // Imposta il colore di sfondo

        // Crea la label per il titolo
        JLabel titleLabel = new JLabel(name, SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK); // Imposta il colore del testo
        titlePanel.add(titleLabel);

        // Aggiungi il pannello del titolo a questa cella
        this.add(titlePanel, BorderLayout.NORTH);

        JPanel subPanel = new JPanel(new GridLayout(2, 2));
        labels = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            int j = 1;
            labels[i] = new JLabel("" + (j+i), SwingConstants.CENTER);
            subPanel.add(labels[i]);
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
            labels[index].setText("ciaoo");
            labels[index].setVisible(visibility);
        }
    }
}