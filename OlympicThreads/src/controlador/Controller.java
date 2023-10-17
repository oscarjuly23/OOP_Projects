package controlador;

import model.Model;
import vista.ConfigWindow;
import vista.MainWindow;
import vista.MolePanel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements MouseListener {
    private MainWindow      mainWindow;
    private ConfigWindow    configWindow;
    private Model           model;

    public Controller(MainWindow mainWindow, ConfigWindow configWindow) {
        this.mainWindow = mainWindow;
        this.configWindow = configWindow;
    }

    /**
     * Function that starts as many Moles as the user entered in the configWindow.
     */
    private void startMoles() {
        for(int i=0; i < model.getMolesNumber(); i++){
            new MoleThread(this.mainWindow, this.model).start();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //We check if the source was a MolePanel so we need to change it's color in order
        //to afterwards count the point for the user.
        if (e.getSource() instanceof MolePanel) {
            MolePanel molePanel = (MolePanel) e.getSource();
            molePanel.resetColor();
        }

        //We check if the source was a JButton. In this case it means it comes from the configWindow
        //so we need to save all the info, the moles number and the board size.
        if (e.getSource() instanceof JButton) {
            try {
                int moles = Integer.parseInt(configWindow.getJtfMolesNumber());

                try {
                    int board = Integer.parseInt(configWindow.getJtfBoardSize());

                    if (moles <= board * board) {
                        this.model = new Model(board, moles);

                        configWindow.setVisible(false);

                        mainWindow.initView(model.getBoardGameSize());
                        mainWindow.registerController(this);
                        startMoles();
                    }

                } catch (NumberFormatException e1) {
                    System.err.println("Error with board size");
                }

            } catch (NumberFormatException e2) {
                System.err.println("Error with moles number");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
