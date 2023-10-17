/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

import controller.MainWindowController;

import model.FileException;
import model.JsonManager;
import model.database.SystemService;
import model.network.ConnectorDB;
import model.network.Server;

import view.MainWindow;

import javax.swing.*;

/**
 * Método Main inicia la execució del servidor
 * @see JsonManager#llegirJson()
 * @see JsonManager#setValuesDB()
 * @see JsonManager#returnPort() 
 * @see Server#startServer()
 * @see MainWindow#setControladors(MainWindowController)
 * @throws FileException If JSON is not found
 */

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JsonManager lectorJson = new JsonManager();
                SystemService service = new SystemService();
                try {
                    //Llegeix la configuració de la bbdd i seteja el valors a la classe ConnectorDB
                    ConnectorDB connectorDB = lectorJson.setValuesDB();
                    //Es crea el server amb el port d'escolta llegit del json
                    Server server = new Server(lectorJson.returnPort());
                    //Iniciem el server
                    server.startServer();
                    //Creem la vista
                    MainWindow mainWindow = new MainWindow();
                    //Creem el controlador i establim la relació Controller->View
                    MainWindowController mainWindowController = new MainWindowController(mainWindow);
                    //Relació View->Controller
                    mainWindow.setControladors(mainWindowController);
                    mainWindow.setVisible(true);
                } catch (FileException e) {
                    System.out.println("No se ha encontrado el fichero!");
                }
            }
        });
    }
}