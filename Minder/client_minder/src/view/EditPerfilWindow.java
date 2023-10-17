/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Vista que mostra un formulari per a editar el perfil de l'usuari
 */
public class EditPerfilWindow extends JFrame {

    private static final String FILELOGO = "shared/src/data/Minder.png";
    private JPanel contentPane;
    private JButton jbExit;
    private JFileChooser jfcImage;
    private JTextArea jtaDescripcio;
    private JRadioButton jrbJava;
    private JRadioButton jrbC;
    private JButton jbImatge;
    private JButton jbVolver;
    private JButton jbAceptar;
    private File archivo;
    private JLabel jlMostrarImage;
    private JLabel jlNomEdat;

    /**
     * Constructor de la classe
     * @param img Imatge de l'usuari
     * @param lang Llenguatge de l'usuari
     * @param description Descripció de l'usuari
     * @param username Nom de l'usuari
     * @param edad Id de l'usuari
     */
    public EditPerfilWindow(byte[] img, String lang, String description, String username, int edad){
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

        jbExit = new JButton("Cerrar Sesión");
        jbExit.setBounds(291, 420, 120, 23);
        contentPane.add(jbExit);


        jbImatge = new JButton("Puja una imatge");
        jbImatge.setBounds(125, 228, 190, 31);
        contentPane.add(jbImatge);

        jfcImage = new JFileChooser();

        jlMostrarImage = new JLabel();

        ImageIcon imageIcon = new ImageIcon(img);
        Icon profileImg = new ImageIcon(imageIcon.getImage().getScaledInstance(220, 125,  Image.SCALE_DEFAULT));
        jlMostrarImage.setIcon(profileImg);
        jlMostrarImage.setBounds(110, 73, 220, 125);
        contentPane.add(jlMostrarImage);

        jlNomEdat = new JLabel(username + ", " + edad);
        Font f = jlNomEdat.getFont();
        jlNomEdat.setHorizontalAlignment(SwingConstants.CENTER);
        jlNomEdat.setOpaque(true);
        jlNomEdat.setBounds(147, 198, 145, 23);
        contentPane.add(jlNomEdat);

        JLabel jlDescripcio = new JLabel("Escriba una descripción:");
        jlDescripcio.setHorizontalAlignment(SwingConstants.LEFT);
        jlDescripcio.setBounds(40, 275, 140, 14);
        contentPane.add(jlDescripcio);

        jtaDescripcio = new JTextArea(description);
        jtaDescripcio.setColumns(20);
        jtaDescripcio.setMargin(new Insets(10, 10, 10, 10));
        jtaDescripcio.setBounds(40, 310, 350, 50);
        contentPane.add(jtaDescripcio);

        JLabel jlLlenguatge = new JLabel("Lenguaje:");
        jlLlenguatge.setHorizontalAlignment(SwingConstants.LEFT);
        jlLlenguatge.setBounds(40, 380, 114, 14);
        contentPane.add(jlLlenguatge);

        jrbJava = new JRadioButton("Java");
        if (lang.equals("Java")){
            jrbJava.setSelected(true);
        }
        jrbJava.setBackground(Color.CYAN);
        jrbJava.setBounds(150, 380, 78, 23);
        contentPane.add(jrbJava);

        jrbC = new JRadioButton("C");
        if (lang.equals("C")){
            jrbC.setSelected(true);
        }
        jrbC.setBackground(Color.CYAN);
        jrbC.setBounds(250, 380, 78, 23);
        contentPane.add(jrbC);

        ButtonGroup bg = new ButtonGroup();
        bg.add(jrbJava);
        bg.add(jrbC);

        jbAceptar = new JButton("Guardar");
        jbAceptar.setBounds(150, 415, 114, 31);
        contentPane.add(jbAceptar);

        jbVolver = new JButton("Cancelar");
        jbVolver.setBounds(10, 11, 100, 23);
        contentPane.add(jbVolver);

        setVisible(true);
    }

    /**
     * Configuració del JFrame
     * @param username Nom de l'usuari
     */
    private void configWindow(String username){
        setTitle("MINDER - EDITA: " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 510);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Actualitza l'imatge de l'usuari
     * @param path Ubicació de l'imatge de l'usuari
     */
    public void mostrarImagen(String path){
        ImageIcon imageProfile=  new ImageIcon(path);
        Icon profileImg = new ImageIcon(imageProfile.getImage().getScaledInstance(220, 125,  Image.SCALE_DEFAULT));
        jlMostrarImage.setIcon(profileImg);
        repaint();
    }

    /**
     * Getter del llenguatge de l'usuari
     * @return Llenguatge de l'usuari
     */
    public String getLanguage(){
        if(jrbJava.isSelected()){
            return "Java";
        } else {
            return "C";
        }
    }

    /**
     * Fica un ActionListener a cada botó per a saber des del controlador a qui s'ha apretat
     * @param listener Crea un event a la que algu premi el botó
     */
    public void registraControladorPerfil(ActionListener listener){
        jbAceptar.addActionListener(listener);
        jbAceptar.setActionCommand("aceptar");
        jbVolver.addActionListener(listener);
        jbVolver.setActionCommand("volver");
        jbImatge.addActionListener(listener);
        jbImatge.setActionCommand("imagen");
        jbExit.addActionListener(listener);
        jbExit.setActionCommand("exit");
    }

    /**
     * Getter del JFileChooser per a seleccionar la imatge
     * @return JFileChooser per a seleccionar la imatge
     */
    public JFileChooser getJfcImage() {
        return jfcImage;
    }

    /**
     * Getter de l'arxiu seleccionat
     * @return Arxiu seleccionat
     */
    public File getArchivo() {
        return archivo;
    }

    /**
     * Setter de l'arxiu seleccionat
     * @param archivo Arxiu seleccionat
     */
    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    /**
     * Getter de la descripció de l'usuari
     * @return descripció de l'usuari
     */
    public String getDescripcio() {
        return jtaDescripcio.getText();
    }
}