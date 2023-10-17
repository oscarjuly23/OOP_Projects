package View;

import Controller.ControllerLogin;
import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private JButton jbLogin;
    private JPanel jpCredenciales;
    private JLabel jlUsername;
    private JLabel jlPassword;
    private JTextField jtfUsername;
    private JPasswordField jtfPassword;

    public LoginWindow() {
        // Primeres configuracions de la finestra:
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(600, 400));
        this.setLocationRelativeTo(null);
        this.setTitle("Login");
        // Per a que no poguem fer-la més gran ni més petita:
        this.setResizable(false);

        jpCredenciales = new JPanel();
        jpCredenciales.setLayout(new GridLayout(2, 2));

        jlUsername = new JLabel("Username:");
        jlPassword = new JLabel("Password:");

        jbLogin = new JButton("Log In");

        jtfUsername = new JTextField(10);
        jtfPassword = new JPasswordField(10);

        jpCredenciales.add(jlUsername);
        jpCredenciales.add(jtfUsername);
        jpCredenciales.add(jlPassword);
        jpCredenciales.add(jtfPassword);

        getContentPane().add(jpCredenciales, BorderLayout.WEST);
        getContentPane().add(jbLogin, BorderLayout.SOUTH);
        pack();

        jlUsername.setFont(jlUsername.getFont().deriveFont(Font.BOLD));
        jlPassword.setFont(jlPassword.getFont().deriveFont(Font.BOLD));
    }

    public void registerController(ControllerLogin controladorBotons) {
        jbLogin.addActionListener(controladorBotons);
    }

    public String Username () {
        return jtfUsername.getText();
    }

    public String Password () {
        return jtfPassword.getText();
    }
}