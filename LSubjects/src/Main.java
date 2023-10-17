import Controller.ControllerLogin;
import Model.LSubject;
import View.LoginWindow;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // Creem la VISTA
        LoginWindow vista = new LoginWindow();

        // Creem el MODEL
        LSubject lSubject = new LSubject();

        // Creem el CONTROLADOR
        // Relacions Controlador --> Vista, Model
        ControllerLogin controladorBotons = new ControllerLogin(vista, lSubject);

        // Relació Vista --> Controlador
        vista.registerController(controladorBotons);

        vista.setVisible(true);
    }
}