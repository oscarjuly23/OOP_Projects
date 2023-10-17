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
import java.io.*;


/**
 * Vista per a completar el perfil de l'usuari amb la foto i la descripció
 */
public class CompletaPerfilWindow extends JFrame {

    private static final String FILELOGO = "shared/src/data/Minder.png";
    private JPanel contentPane;
    private JFileChooser jfcImage;
    private JTextArea jtaDescripcio;
    private JRadioButton jrbJava;
    private JRadioButton jrbC;
    private JButton jbImatge;
    private JButton jbVolver;
    private JButton jbAceptar;
    private File archivo;
    private JLabel jlMostrarImage;

    /**
     * Constructor de la classe
     */
    public CompletaPerfilWindow(){
        configWindow();

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

        jbImatge = new JButton("Puja una imatge");
        jbImatge.setBounds(125, 213, 180, 31);
        contentPane.add(jbImatge);

        jfcImage = new JFileChooser();

        jlMostrarImage = new JLabel("No tienes ninguna imagen", SwingConstants.CENTER);
        jlMostrarImage.setBounds(110, 83, 220, 125);
        contentPane.add(jlMostrarImage);

        JLabel jlDescripcio = new JLabel("Escriba una descripción:");
        jlDescripcio.setHorizontalAlignment(SwingConstants.LEFT);
        jlDescripcio.setBounds(40, 265, 140, 14);
        contentPane.add(jlDescripcio);

        jtaDescripcio = new JTextArea();
        jtaDescripcio.setMargin(new Insets(10, 10, 10, 10));
        jtaDescripcio.setColumns(20);
        jtaDescripcio.setBounds(40, 300, 350, 50);
        contentPane.add(jtaDescripcio);

        JLabel jlLlenguatge = new JLabel("Lenguaje:");
        jlLlenguatge.setHorizontalAlignment(SwingConstants.LEFT);
        jlLlenguatge.setBounds(40, 370, 114, 14);
        contentPane.add(jlLlenguatge);

        jrbJava = new JRadioButton("Java");
        jrbJava.setSelected(true);
        jrbJava.setBackground(Color.CYAN);
        jrbJava.setBounds(150, 370, 78, 23);
        contentPane.add(jrbJava);

        jrbC = new JRadioButton("C");
        jrbC.setBackground(Color.CYAN);
        jrbC.setBounds(250, 370, 78, 23);
        contentPane.add(jrbC);

        ButtonGroup bg = new ButtonGroup();
        bg.add(jrbJava);
        bg.add(jrbC);

        jbAceptar = new JButton("Aceptar");
        jbAceptar.setBounds(150, 405, 114, 31);
        contentPane.add(jbAceptar);

        jbVolver = new JButton("Volver");
        jbVolver.setBounds(10, 11, 74, 23);
        contentPane.add(jbVolver);

        setVisible(true);
    }

    /**
     * Configura el JFrame de la vista
     */
    private void configWindow(){
        //CONFIG PANTALLA
        setTitle("MINDER - COMPLETA PERFIL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Mostra la imatge que l'usuari ha seleccionat
     * @param path Ubicació de la imatge de l'usuari
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