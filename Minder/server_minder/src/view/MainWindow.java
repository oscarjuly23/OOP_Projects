/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package view;

import controller.MainWindowController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Classe de la MainWindow del servidor. View
 */

public class MainWindow extends JFrame {
    private static final String FILELOGO = "shared/src/data/Minder.png";
    private TopUserPanelView topUserPanelView;
    private GraphicsPanelView graphicsPanelView;
    private JPanel contentPane;
    private JTabbedPane jtpOptions;

    /**
     * Constructor de la classe
     * @see #configWindow()
     */
    public MainWindow() {
        configWindow();
        contentPane = new JPanel();
        contentPane.setBackground(Color.CYAN);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //Afegim el logo
        JLabel jlLogo = new JLabel();
        ImageIcon icono = new ImageIcon(FILELOGO);
        Image imagen = icono.getImage();
        ImageIcon iconoEscalado = new ImageIcon(imagen.getScaledInstance(200, 60, Image.SCALE_SMOOTH));
        jlLogo.setIcon(iconoEscalado);
        jlLogo.setBounds(150, 5, 500, 60);
        contentPane.add(jlLogo);
        jtpOptions = new JTabbedPane();
        jtpOptions.setBounds(20, 60, 450, 380);
        graphicsPanelView = null;
        //Afegim la pestanya per la grafica
        jtpOptions.addTab("Gráfica Evolución", graphicsPanelView);
        topUserPanelView = null;
        //Afegim la pestaña per al top usuaris
        jtpOptions.addTab("Top Usuaris", topUserPanelView);
        //Controlador per saber quan es canvia de pestaña al JTabbedPane
        jtpOptions.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            }
        });

        contentPane.add(jtpOptions);
    }

    /**
     *  Configuracions a la finestra
     */
    private void configWindow(){
        //CONFIG PANTALLA
        setTitle("MINDER - Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200, 200, 500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Setter del MainWindowController
     * @param mainWindowController controlador de la MainWindow
     * @see MainWindowController
     */
    public void setControladors(MainWindowController mainWindowController) {
        jtpOptions.addChangeListener(mainWindowController);
    }

    /**
     * Getter el JTabbedPane
     * @return JTabbedPane instanciat al inici de la classe
     */
    public JTabbedPane getJtpOptions() {
        return jtpOptions;
    }

    /**
     * Setter del GraphicsPanelView
     * @param graphicsPanelView View de la gràfica
     */
    public void setGraphicsPanelView(GraphicsPanelView graphicsPanelView) {
        this.graphicsPanelView = graphicsPanelView;
        this.jtpOptions.setComponentAt(0,graphicsPanelView);
    }

    /**
     * Setter del TopUserPanelView
     * @param topUserPanelView View del la taula de top usuaris
     */
    public void setTopUserPanelView(TopUserPanelView topUserPanelView) {
        this.topUserPanelView = topUserPanelView;
        this.jtpOptions.setComponentAt(1,topUserPanelView);
    }
}