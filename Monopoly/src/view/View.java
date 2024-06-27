package view;

import javax.swing.*;

import view.Interfaces.ViewInterface;

import java.awt.*;

public class View implements ViewInterface{
	
	//FIELDS
    private JFrame frame;
    private Board board;
    private Button buttons;
    private PlayerField playerFields;
    
    //CONSTRUCOTORS
    public View() {
    
    		frame = new JFrame("Monopoly Board");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            board = new Board();
            buttons = new Button();
            playerFields = new PlayerField();
                                 
            //ottengo le dimensioni dello schermo            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
            double width = screenSize.getWidth();
            
            board.getPanel().setMinimumSize(new Dimension((int)(width * 0.7), 0)); //imposto la dimensione minima del pannello di board al 70%
            
            //creo un JSplitPane per dividere orizzontalmente il pannello della board e il pannello dei pulsanti.
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board.getPanel(), buttons.getPanel());

            //disabilito il trascinamento del divisore
            splitPane.setEnabled(false);
            
            //aggiungo il JSplitPane al frame
            frame.add(splitPane);

            frame.pack(); //adatto il frame alle dimensioni dei componenti
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //la finestra occupa tutto lo schermo
            frame.setVisible(true); //rendo il frame visibile
                        
            playerFields.getJDialog().pack(); //ridimensiono JDialog in modo che si adatti ai suoi componenti
            playerFields.getJDialog().setLocationRelativeTo(frame); //il JDialogo viene posizionato al centro del frame
            playerFields.getJDialog().setVisible(true); //rendo il JDialog visibile
    }

    //METHODS	
	public Button getButtons() {
        return this.buttons;
    }

    public Board getBoard() {
        return this.board;
    }
    
    public PlayerField getPlayerField() {
        return this.playerFields;
    }
}
      