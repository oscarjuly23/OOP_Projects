package vista;

import java.awt.Color;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MolePanel extends JPanel {
	private static final int BROWN_R = 139;
	private static final int BROWN_G = 69;
	private static final int BROWN_B = 19;

	public MolePanel() {
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Function that changes the color of the panel to Brown
	 */
	public void changeToBrownColor() {
		setBackground(new Color(BROWN_R, BROWN_G, BROWN_B));
	}

	/**
	 * Function that changes the color of the panel to Ligh Gray
	 */
	public void resetColor() {
		setBackground(Color.LIGHT_GRAY);
	}
}
