/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Controlador dels missatges emergents
 */
public class OptionPaneController extends Thread {

    private JOptionPane jOptionPane;
    private JDialog dialog;

    /**
     * Constructor de la classe
     * @param message Missatge de la vista emergent
     * @param title Titol del la vista emergent
     */
    public OptionPaneController(String message, String title){

        jOptionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

        dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setBounds(400,400,150,50);

        dialog.setContentPane(jOptionPane);

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        start();
    }

    /**
     * Fa correr el thread durnt dos segons i tanca la vista emergent
     */
    public void run(){
        Timer timer = new Timer(2000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dialog.dispose();
            }
        });
        timer.setRepeats(false);//the timer should only go off once
        timer.start();
        dialog.setVisible(true);
    }
}