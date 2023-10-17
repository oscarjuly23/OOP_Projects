package Vista;

import Controller.Controller;
import Model.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow extends JFrame {

    private ArrayList<ArrayList<FlagPanel>> panels;

    public synchronized void initView(int filas, int columnas) {
        configWindow(filas, columnas);
        getContentPane().setLayout(new GridLayout(filas, columnas));
        panels = new ArrayList<>();
        for (int i = 0; i < filas; i++) {
            panels.add(i, new ArrayList<>(filas));
            for (int j = 0; j < columnas; j++) {
                FlagPanel flagPanel = new FlagPanel();
                panels.get(i).add(j, flagPanel);
                getContentPane().add(flagPanel);

            }

        }
    }

    private void configWindow(int filas, int columnas) {
        setSize(columnas*100, filas*100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Olympic Games 2020");
        setVisible(true);
    }

    public void registerController(Controller controller) {
//        this.jbCreate.addMouseListener(controller);
//        for (int i = 0; i < this.panels.size(); i++) {
//            for (int j = 0; j < this.panels.get(i).size(); j++) {
//                this.panels.get(i).get(j).addMouseListener(controller);
//            }
//        }
    }
    public FlagPanel getPanel(Coordinate c) {
        return this.panels.get(c.getFilas()).get(c.getColumnas());
    }

    public boolean hasBeenClicked (Coordinate c){
        return this.getPanel(c).getBackground() == Color.LIGHT_GRAY;
    }
    }
