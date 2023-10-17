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
 * Vista que conté el formulari de login
 */
public class LoginWindow extends JFrame {

    private static final String FILELOGO = "shared/src/data/Minder.png";
    private JPanel contentPane;
    private JTextField jtfNomUserOEmail;
    private JPasswordField jpfPassword;
    private JButton jbLogin;
    private JButton jbRegistrar;

    /**
     * Constructor de la classe
     */
    public LoginWindow(){
        configWindow();
        contentPane = new JPanel();
        contentPane.setBackground(Color.cyan);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel jlLogo = new JLabel();
        ImageIcon icono = new ImageIcon(FILELOGO);
        Image imagen = icono.getImage();
        ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(200,60,Image.SCALE_SMOOTH));
        jlLogo.setIcon(iconoEscalado);
        jlLogo.setBounds(120, 18, 600, 60);
        contentPane.add(jlLogo);

        JLabel jlNomUserOEmail = new JLabel("Usuario o email:");
        jlNomUserOEmail.setBounds(78, 100, 209, 14);
        contentPane.add(jlNomUserOEmail);

        JLabel jlPassword = new JLabel("Contraseña:");
        jlPassword.setBounds(95, 136, 72, 14);
        contentPane.add(jlPassword);

        jbLogin = new JButton("Iniciar Sesión");
        jbLogin.setBounds(60, 193, 127, 31);
        contentPane.add(jbLogin);

        jbRegistrar = new JButton("Registrarse");
        jbRegistrar.setBounds(237, 193, 134, 31);
        contentPane.add(jbRegistrar);

        jtfNomUserOEmail = new JTextField();
        jtfNomUserOEmail.setColumns(10);
        jtfNomUserOEmail.setBounds(224, 95, 145, 23);
        contentPane.add(jtfNomUserOEmail);

        jpfPassword = new JPasswordField();
        jpfPassword.setBounds(224, 132, 145, 23);
        contentPane.add(jpfPassword);

        setVisible(true);
    }

    /**
     * Configuració del JFrame
     */
    private void configWindow(){
        //CONFIG PANTALLA
        setTitle("MINDER - LOGIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Fica un ActionListener a cada botó per a saber des del controlador a qui s'ha apretat
     * @param listener Crea un event a la que algu premi el botó
     */
    public void registraControladorLogin(ActionListener listener){
        jbLogin.addActionListener(listener);
        jbLogin.setActionCommand("login");
        jbRegistrar.addActionListener(listener);
        jbRegistrar.setActionCommand("registrar");
    }

    /**
     * Getter del email o username de l'usuari
     * @return
     */
    public String getUserOEmail() {
        return jtfNomUserOEmail.getText();
    }

    /**
     * Getter del password de l'usuari
     * @return Password de l'usuari
     */
    public String getPassword() {
        return String.valueOf(jpfPassword.getPassword());
    }
}