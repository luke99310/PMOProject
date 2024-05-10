package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.*;

public class AuctionGUI {
	
	private JFrame frame;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JButton b1;
	private JButton b2;
	private JLabel text;
	
	public AuctionGUI() {
		// creating the frame
		this.frame = new JFrame("AUCTION");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(300, 130);
		
		// adding the panel to the frame
		this.panel1 = new JPanel(new BorderLayout());
		this.frame.getContentPane().add(panel1);
		
		// adding the question
		this.panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.text = new JLabel("Do you want to buy the property?");
	    this.panel3.add(text);
		this.panel1.add(panel3, BorderLayout.CENTER);
		
		// new panel for the two buttons
		this.panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		// adding the two buttons
		this.b1 = new JButton("YES");
		this.b1.setBackground(Color.GREEN);
		this.b2 = new JButton("NO");
		this.b2.setBackground(Color.RED);
		this.panel2.add(b1);
		this.panel2.add(new JLabel("    "));
		this.panel2.add(new JLabel("    "));
		this.panel2.add(b2);
		this.panel1.add(panel2, BorderLayout.SOUTH);
		
		this.panel1.add(new JLabel("    "),BorderLayout.NORTH);
		
		// the panel is now visible
		this.frame.setVisible(true);
	}
	
}
