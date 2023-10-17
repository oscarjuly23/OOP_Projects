package controlador;

import model.Coordinate;
import model.Model;
import vista.MainWindow;
import java.util.Random;

public class MoleThread extends Thread {
    private final Model         model;
    private final MainWindow    view;
    private Random              random;

    public MoleThread(MainWindow view, Model model) {
        this.view = view;
        this.model = model;
        this.random = new Random();
    }

    public void run() {
        double time;

        while (true) {

            time = this.random.nextDouble() * 4 + 1;

            try {
                Thread.sleep(Math.round(time * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            //Initialize the local variable to store the actual location of the mole.
            Coordinate actualPos;

            //Create a random Coordinate and we ask the model if it's occupied or not.
            //Create one as many times as needed until it founds one that it isn't.
            do {
                actualPos = new Coordinate(this.random.nextInt(model.getBoardGameSize()),
                        this.random.nextInt((model.getBoardGameSize())));
            } while (!model.checkIfNew(actualPos));

            //Ppaint the mole in the board game.
            this.view.getPanel(actualPos).changeToBrownColor();

            //Give time to the user to click the moles
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Check if the mole has been clicked and increase the points if necessary.
            if (this.view.hasBeenClicked(actualPos)) {
                this.model.increasePoints();
            }

            //We reset the game board.
            this.view.getPanel(actualPos).resetColor();
            this.model.resetPosition(actualPos);

        }
    }
}
