package Controller;

import Model.Model;
import Vista.ConfigWindow;
import Vista.FlagPanel;
import Vista.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements MouseListener {
    private MainWindow mainWindow;
    private ConfigWindow configWindow;
    private Model model;

    public Controller(MainWindow mainWindow, ConfigWindow configWindow) {
        this.mainWindow = mainWindow;
        this.configWindow = configWindow;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
            JButton jButton = ((JButton)e.getSource());

            if (jButton.getActionCommand().equals("Create")) {
                int altura, anchura;
                altura = (int) configWindow.get
            }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}