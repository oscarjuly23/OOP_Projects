/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package view;

import model.entity.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista que mostra la informació de l'usuari que estàs mostrant
 */
public class PerfilOtroWindow extends JFrame {
    private static final String FILELOGO = "shared/src/data/Minder.png";

    private JPanel contentPane;
    private JButton jbExit;
    private JButton jbVolver;
    private JLabel jlMostrarImage;
    private JLabel jlLanguage;
    private JTextArea jtaDescripcioText;
    private JLabel jlNomEdat;


    /**
     * Constructor de la classe
     * @param img Imatge de l'usuari que estàs mostrant
     * @param lang Llenguatge de l'usuari que estàs mostrant
     * @param description Descripció de l'usuari que estàs mostrant
     * @param username Nom de l'usuari que estàs mostrant
     * @param edad Edat de l'usuari que estàs mostrant
     */
    public PerfilOtroWindow(byte[] img, String lang, String description, String username, int edad) {

        configWindow(username);

        contentPane = new JPanel();
        contentPane.setBackground(Color.CYAN);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel jlLogo = new JLabel();
        ImageIcon icono = new ImageIcon(FILELOGO);
        Image imagen = icono.getImage();
        ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(200,60,Image.SCALE_SMOOTH));
        jlLogo.setIcon(iconoEscalado);
        jlLogo.setBounds(120, 5, 600, 60);
        contentPane.add(jlLogo);

        jlMostrarImage = new JLabel();
        ImageIcon imageIcon = new ImageIcon(img);
        Icon profileImg = new ImageIcon(imageIcon.getImage().getScaledInstance(220, 125,  Image.SCALE_DEFAULT));
        jlMostrarImage.setIcon(profileImg);
        jlMostrarImage.setBounds(110, 83, 220, 125);
        contentPane.add(jlMostrarImage);

        jlNomEdat = new JLabel(username + ", " + edad);
        Font f = jlNomEdat.getFont();
        jlNomEdat.setHorizontalAlignment(SwingConstants.CENTER);
        jlNomEdat.setOpaque(true);
        jlNomEdat.setBounds(147, 208, 145, 23);
        contentPane.add(jlNomEdat);

        JLabel jlDescripcio = new JLabel("Descripción:");
        jlDescripcio.setHorizontalAlignment(SwingConstants.LEFT);
        jlDescripcio.setBounds(40, 250, 140, 14);
        contentPane.add(jlDescripcio);

        jtaDescripcioText = new JTextArea(description);
        jtaDescripcioText.setEditable(false);
        jtaDescripcioText.setMargin(new Insets(10, 10, 10, 10));
        jtaDescripcioText.setFocusable(false);
        jtaDescripcioText.setColumns(20);
        jtaDescripcioText.setBounds(40, 270, 350, 50);
        contentPane.add(jtaDescripcioText);

        JLabel jlLlenguatge = new JLabel("Lenguaje:");
        jlLlenguatge.setHorizontalAlignment(SwingConstants.LEFT);
        jlLlenguatge.setBounds(40, 330, 114, 14);
        contentPane.add(jlLlenguatge);
        jlLanguage = new JLabel(lang);
        jlLanguage.setHorizontalAlignment(SwingConstants.LEFT);
        jlLlenguatge.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        jlLanguage.setBounds(120, 330, 114, 14);
        contentPane.add(jlLanguage);

        jbVolver = new JButton("Volver");
        jbVolver.setBounds(10, 11, 100, 23);
        contentPane.add(jbVolver);

        jbExit = new JButton("Cerrar Sesión");
        jbExit.setBounds(300, 330, 120, 23);
        contentPane.add(jbExit);

        setVisible(true);
    }

    /**
     * Configuració del JFrame
     * @param username Nom de l'usuari
     */
    private void configWindow(String username){
        //CONFIG PANTALLA
        setTitle("MINDER - PERFIL: " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 420);
        setLocationRelativeTo(null);
        setResizable(false);
    }


    /**
     * Fica un ActionListener a cada botó per a saber des del controlador a qui s'ha apretat
     * @param listener Crea un event a la que algu premi el botó
     */
    public void registraControladorPerfilOtro(ActionListener listener){
        jbVolver.addActionListener(listener);
        jbVolver.setActionCommand("volver");
        jbExit.addActionListener(listener);
        jbExit.setActionCommand("exit");
    }

    /**
     * Actualitza la informació del usuari en temps real
     * @param user Usuari canviat
     */
    public void actualitza(User user) {
        ImageIcon imageIcon = new ImageIcon(user.getImg());
        Icon profileImg = new ImageIcon(imageIcon.getImage().getScaledInstance(220, 125,  Image.SCALE_DEFAULT));
        jlMostrarImage.setIcon(profileImg);
        jtaDescripcioText.setText(user.getDescription());
        jlLanguage.setText(user.getLang());
        repaint();
    }
}