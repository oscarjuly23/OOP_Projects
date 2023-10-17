package vista;

import controlador.Controller;
import model.Coordinate;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	
	private ArrayList<ArrayList<MolePanel>> panels;
	
	public synchronized void initView(int size) {
		getContentPane().setLayout(new GridLayout(size, size));

		//We create all the panels that we need in order to create the board game
		this.panels = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			this.panels.add(i, new ArrayList<>(size));
			for (int j = 0; j < size; j++) {
				MolePanel molePanel = new MolePanel();
				this.panels.get(i).add(j, molePanel);
				getContentPane().add(molePanel);
			}
		}

		//We set to each panel a size of 100 x 100.
		int widthAndHeight = size * 100;
		
		setSize(widthAndHeight, widthAndHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setTitle("Moles Game");
		setVisible(true);
	}
	
	public void registerController(Controller controller) {
		for (int i = 0; i < this.panels.size(); i++) {
			for (int j = 0; j < this.panels.get(i).size(); j++) {
				this.panels.get(i).get(j).addMouseListener(controller);
			}
		}
	}

	public MolePanel getPanel(Coordinate c) {
		return this.panels.get(c.getRow()).get(c.getCol());
	}

	/**
	 * Function that checks if the panel has changed color
	 * @param c
	 * @return true if the color is Light Gray (has ben clicked)
	 */
	public boolean hasBeenClicked(Coordinate c) {
		return this.getPanel(c).getBackground() == Color.LIGHT_GRAY;
	}
}
