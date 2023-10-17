package Vista;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class FlagPanel extends JPanel {
    private static final int BROWN_R = 139;
    private static final int BROWN_G = 69;
    private static final int BROWN_B = 19;

    public FlagPanel() {
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        setBackground(Color.LIGHT_GRAY);
    }
    public void changeToBrownColor() {
        setBackground(new Color(BROWN_R, BROWN_G, BROWN_B));
    }
    public void resetColor() {
        setBackground(Color.LIGHT_GRAY);
    }
}
