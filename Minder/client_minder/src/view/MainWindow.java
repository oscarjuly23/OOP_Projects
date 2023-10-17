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

/**
 * Vista principal on pots acceptar i denegar usuaris
 */

public class MainWindow extends JFrame {

    private static final String FILELOGO = "shared/src/data/Minder.png";
    private JPanel contentPane;
    private JButton jbExit;
    private JButton jbPerfilUser;
    private JButton jbMensajes;
    private JButton jbFoto;
    private JButton jbLike;
    private JButton jbDislike;
    private JLabel jlNomEdat;
    private int edad;
    private String name;
    private boolean noMoreUsers;

    /**
     * Constructor de la classe
     */
    public MainWindow(){
        configWindow();

        edad = 0;
        name = "";

        contentPane = new JPanel();
        contentPane.setBackground(Color.CYAN);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel jlLogo = new JLabel();
        ImageIcon icono = new ImageIcon(FILELOGO);
        Image imagen = icono.getImage();
        ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(180,60,Image.SCALE_SMOOTH));
        jlLogo.setIcon(iconoEscalado);
        jlLogo.setBounds(120, 5, 600, 60);
        contentPane.add(jlLogo);

        jbExit = new JButton("Cerrar Sesión");
        jbExit.setBounds(291, 299, 120, 23);
        contentPane.add(jbExit);

        jbPerfilUser = new JButton("Perfil");
        jbPerfilUser.setBounds(10, 11, 64, 23);
        contentPane.add(jbPerfilUser);

        jbMensajes = new JButton("Chats");
        jbMensajes.setBounds(340, 11, 71, 23);
        contentPane.add(jbMensajes);

        jbFoto = new JButton();
        jbFoto.setBounds(100, 75, 220, 125);
        contentPane.add(jbFoto);

        jlNomEdat = new JLabel(name + ", " + edad);
        jlNomEdat.setHorizontalAlignment(SwingConstants.CENTER);
        jlNomEdat.setOpaque(true);
        jlNomEdat.setBounds(137, 199, 145, 23);
        contentPane.add(jlNomEdat);

        jbLike = new JButton("Acceptar");
        jbLike.setBounds(83, 240, 100, 30);
        jbLike.setBackground(Color.GREEN);
        contentPane.add(jbLike);

        jbDislike = new JButton("Rechazar");
        jbDislike.setBounds(240, 240, 100, 30);
        jbDislike.setBackground(Color.RED);
        contentPane.add(jbDislike);

        setVisible(true);
    }

    /**
     * Fica un ActionListener a cada botó per a saber des del controlador a qui s'ha apretat
     * @param listener Crea un event a la que algu premi el botó
     */
    public void registraControladorMain(ActionListener listener){
        jbFoto.addActionListener(listener);
        jbFoto.setActionCommand("foto");
        jbDislike.addActionListener(listener);
        jbDislike.setActionCommand("dislike");
        jbExit.addActionListener(listener);
        jbExit.setActionCommand("exit");
        jbLike.addActionListener(listener);
        jbLike.setActionCommand("like");
        jbMensajes.addActionListener(listener);
        jbMensajes.setActionCommand("chat");
        jbPerfilUser.addActionListener(listener);
        jbPerfilUser.setActionCommand("perfil");
    }

    /**
     * Setter de la imatge de l'usuari
     * @param imageProfile Imatge de l'usuari
     */
    public void setFoto(byte[] imageProfile){
        ImageIcon imageIcon = new ImageIcon(imageProfile);
        Icon profileImg = new ImageIcon(imageIcon.getImage().getScaledInstance(220, 125,  Image.SCALE_DEFAULT));
        jbFoto.setIcon(profileImg);
    }

    /**
     * Setter de diferents atributs de l'usuari
     * @param imageProfile Imatge de l'usuari
     * @param username Nom de l'usuari
     * @param edad Edat de l'usuari
     */
    public void setUserAtributes(byte[] imageProfile, String username, int edad){
        if(username == null){
            setNoMoreUsers();
        } else {
            setFoto(imageProfile);
            setNameEdad(username, edad);
            repaint();
            noMoreUsers = false;
        }
    }

    /**
     * Setter del nom i l'edat de l'usuari
     * @param username Nom de l'usuari
     * @param edad Edat de l'usuari
     */
    public void setNameEdad(String username, int edad){
        this.name = username;
        this.edad = edad;
        jlNomEdat.setText(username + ", " + edad);
    }

    /**
     * Configuració del JFrame
     */
    private void configWindow(){
        //CONFIG PANTALLA
        setTitle("MINDER - Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 440, 372);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Setter de que ja no queden més usuaris per a mostrar
     */
    public void setNoMoreUsers() {
        this.noMoreUsers = true;
        this.edad = 0;
        this.name = null;
        jbFoto.setIcon(null);
        jbFoto.setText("No quedan más usuarios");
        jlNomEdat.setText("Nombre, Edad");
    }

    /**
     * Getter de si queden més usuaris a mostrar o no
     * @return Boleà de si queden més usuaris a mostrar o no
     */
    public boolean getNoMoreUsers(){
        return noMoreUsers;
    }

    /**
     * Mostra un error quan intentes veure un usuari quan ja no queden
     */
    public void errorNoMoreList(){
        JOptionPane.showMessageDialog(null, "No puedes observar el usuario porqué no tienes más en tu lista", "Error", JOptionPane.ERROR_MESSAGE);
    }
}