package Controller;

import Model.LSubject;
import View.LSubjectWindow;
import View.LoginWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerLogin implements ActionListener {

    // VISTA
    private LoginWindow vista;
    private LSubjectWindow view;

    // MODEL
    private LSubject model;

    public ControllerLogin(LoginWindow vista, LSubject model) {
        this.vista = vista;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Quan cliquem el botó de Log in i amb la funció de ComprovarCredencials, si dona correcte fem els següent:
        if (model.comprovarCredentials(vista.Username(), vista.Password())) {
            // Amaguem la finestra de Login.
            vista.setVisible(false);
            // Carreguem les dades del Json:
            LSubject lss = model.leerJson();
            // Les passem a la finestra nova de assignatures.
            view = new LSubjectWindow(lss.getSubjects());

            // Controlador --> Vista
            ControllerSubject controladorPreferits = new ControllerSubject(view);
            // Vista --> Controlador
            view.favoritesController(controladorPreferits, view.getArraySubjects());
            // Posem en visimble la nova finestra de assignatures.
            view.setVisible(true);

        // En el cas de que la funció de ComprovarCredencials no dona correcte vol dir que hem errat.
        } else {
            // Obrim un missatge que ens informa que les credencials no són correctes.
            JOptionPane.showMessageDialog(null, "The credentials are not valid.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}