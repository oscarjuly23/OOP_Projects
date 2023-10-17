package Vista;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class ConfigWindow extends JFrame {

    private Integer[] heighV = {5, 7, 9};
    private Integer[] widthV = {7, 9, 11};

    private final String F_HEIGHT = "Flag height: ";
    private final String F_WIDTH = "Flag width: ";
    private final String Create = "Create";

    private final JLabel jlFilas = new JLabel(F_HEIGHT);
    private final JLabel jlColumnas = new JLabel(F_WIDTH);

    private final JComboBox<Integer> jcbFilas = new JComboBox<>(heighV);
    private final JComboBox<Integer> jcbColumnas = new JComboBox<>(widthV);
    private final JButton jbCreate = new JButton(Create);

    private JPanel jPanel;
    private JPanel jPanelNorth;
    private JPanel jPaneSouth;

    private MainWindow mainWindow;

    public ConfigWindow() {
        setSize(300, 300);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setTitle("Config Flag Panel");

        jPanel = new JPanel(new BorderLayout());
        jPanel.add(createNorth(), BorderLayout.NORTH);
        jPanel.add(createSouth(), BorderLayout.SOUTH);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(jPanel);
    }

    public void registerController(Controller controller){
        this.jbCreate.addMouseListener(controller);
    }
}
