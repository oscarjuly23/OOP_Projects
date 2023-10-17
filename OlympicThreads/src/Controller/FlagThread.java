package Controller;

import Model.Model;
import Vista.MainWindow;

import java.util.Random;

public class FlagThread extends Thread {
    private final Model model;
    private final MainWindow view;
    private Random random;


    public FlagThread(MainWindow view, Model model) {
        this.model = model;
        this.view = view;
        this.random = new Random();
    }
}
