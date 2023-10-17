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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Vista que conté el formulari per a registrar-se
 */
public class RegistroWindow extends JFrame{

    private static final String FILELOGO = "shared/src/data/Minder.png";
    private JPanel contentPane;
    private JTextField jtfNomUser;
    private JPasswordField jpfPasswordConfirm;
    private JPasswordField jpfPassword;
    private JTextField jtfEmail;
    private JRadioButton jrbTipusNormal;
    private JRadioButton jrbTipusPremium;
    private JButton jbVolver;
    private JButton jbRegistro;
    private JComboBox jcbEdad;

    /**
     * Constructor de la classe
     */
    public RegistroWindow(){
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
        jlLogo.setBounds(120, 12, 600, 60);
        contentPane.add(jlLogo);

        JLabel jlNomUser = new JLabel("Usuario:");
        jlNomUser.setHorizontalAlignment(SwingConstants.CENTER);
        jlNomUser.setBounds(84, 71, 68, 14);
        contentPane.add(jlNomUser);

        jbRegistro = new JButton("Registrarse");
        jbRegistro.setBounds(150, 289, 114, 31);
        contentPane.add(jbRegistro);

        jtfNomUser = new JTextField();
        jtfNomUser.setColumns(10);
        jtfNomUser.setBounds(224, 67, 145, 23);
        contentPane.add(jtfNomUser);

        jpfPasswordConfirm = new JPasswordField();
        jpfPasswordConfirm.setBounds(224, 237, 145, 23);
        contentPane.add(jpfPasswordConfirm);

        JLabel jlEdad = new JLabel("Edad:");
        jlEdad.setHorizontalAlignment(SwingConstants.CENTER);
        jlEdad.setBounds(89, 108, 56, 14);
        contentPane.add(jlEdad);

        jcbEdad = new JComboBox();
        jcbEdad.setBounds(224, 104, 40, 23);
        for(int i = 17; i<100; i++) {
            jcbEdad.addItem(i);
        }
        contentPane.add(jcbEdad);

        JLabel jlTipus = new JLabel("Tipo de cuenta:");
        jlTipus.setHorizontalAlignment(SwingConstants.CENTER);
        jlTipus.setBounds(60, 142, 114, 14);
        contentPane.add(jlTipus);

        jrbTipusNormal = new JRadioButton("Normal");
        jrbTipusNormal.setBackground(Color.CYAN);
        jrbTipusNormal.setBounds(224, 138, 78, 23);
        jrbTipusNormal.setSelected(true);
        contentPane.add(jrbTipusNormal);

        jrbTipusPremium = new JRadioButton("Premium");
        jrbTipusPremium.setBackground(Color.CYAN);
        jrbTipusPremium.setBounds(304, 138, 78, 23);
        contentPane.add(jrbTipusPremium);

        ButtonGroup bg = new ButtonGroup();
        bg.add(jrbTipusNormal);
        bg.add(jrbTipusPremium);

        JLabel jlEmail = new JLabel("Email:");
        jlEmail.setHorizontalAlignment(SwingConstants.CENTER);
        jlEmail.setBounds(89, 173, 56, 14);
        contentPane.add(jlEmail);

        JLabel jlPassword = new JLabel("Contraseña:");
        jlPassword.setHorizontalAlignment(SwingConstants.CENTER);
        jlPassword.setBounds(77, 207, 87, 14);
        contentPane.add(jlPassword);

        JLabel jlPasswordConfirm = new JLabel("Confirmar Contraseña:");
        jlPasswordConfirm.setHorizontalAlignment(SwingConstants.CENTER);
        jlPasswordConfirm.setBounds(50, 241, 137, 14);
        contentPane.add(jlPasswordConfirm);

        jpfPassword = new JPasswordField();
        jpfPassword.setBounds(224, 203, 145, 23);
        contentPane.add(jpfPassword);

        jtfEmail = new JTextField();
        jtfEmail.setColumns(10);
        jtfEmail.setBounds(224, 170, 145, 23);
        contentPane.add(jtfEmail);

        jbVolver = new JButton("Volver");
        jbVolver.setBounds(10, 11, 74, 23);
        contentPane.add(jbVolver);

        setVisible(true);
    }

    /**
     * Configuració del JFrame
     */
    private void configWindow(){
        setTitle("MINDER - REGISTER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 437, 372);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Fica un ActionListener a cada botó per a saber des del controlador a qui s'ha apretat
     * @param listener Crea un event a la que algu premi el botó
     */
    public void registraControladorRegistro(ActionListener listener){
        jbRegistro.addActionListener(listener);
        jbRegistro.setActionCommand("registrar");
        jbVolver.addActionListener(listener);
        jbVolver.setActionCommand("volver");
    }

    /**
     * Getter del nom de l'usuari
     * @return Nom de l'usuari
     */
    public String getUser(){
        return jtfNomUser.getText();
    }

    /**
     * Getter del password de l'usuari
     * @return Password de l'usuari
     */
    public String getPassword(){
        return String.valueOf(jpfPassword.getPassword());
    }

    /**
     * Getter del confirma password de l'usuari
     * @return Confirma password de l'usuari
     */
    public String getPasswordConfirm(){
        return String.valueOf(jpfPasswordConfirm.getPassword());
    }

    /**
     * Valida si el password és correcte
     * @return True si és correcta i false si és incorrecte
     */
    public boolean validaPassword(){
        if (jpfPassword.getPassword().length < 8){
            return false;
        } else{
            boolean min = false;
            boolean max = false;
            boolean num = false;
            String passText = new String(jpfPassword.getPassword());
            for(int i = 0; i<passText.length(); i++){
                if(Character.isUpperCase(passText.charAt(i))){
                    max = true;
                } else if (Character.isLowerCase(passText.charAt(i))){
                    min = true;
                } else if(Character.isDigit(passText.charAt(i))){
                    num = true;
                }
            }
            if (max && min && num){
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Getter del email de l'usuari
     * @return Email de l'usuari si és correcte i null si no compleix el format
     */
    public String getEmail(){
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(jtfEmail.getText());
        if(matcher.find() == true){
            return jtfEmail.getText();
        } else {
            return null;
        }
    }

    /**
     * Getter del tipus de subscripció de l'usuari
     * @return Tipus de subscripció de l'usuari
     */
    public String getSubscriptionType(){
        if(jrbTipusNormal.isSelected()){
            return "normal";
        } else {
            return "premium";
        }
    }

    /**
     * Getter de l'edat de l'usuari
     * @return Edat de l'usuari
     */
    public int getEdad(){
        return (int)jcbEdad.getSelectedItem();
    }
}