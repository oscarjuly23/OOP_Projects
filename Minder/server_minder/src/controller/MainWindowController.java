/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package controller;

import model.database.SystemService;
import view.GraphicsPanelView;
import view.MainWindow;
import view.TopUserPanelView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Classe encarregada del controlador de la MainWindow. Controller
 */
public class MainWindowController implements ChangeListener {
    //Relació amb la view
    private MainWindow mainWindow;
    //Relació amb la model
    private SystemService service;

    /**
     * Constructor de la clase. Rep la MainWindow (view)
     * @param mainWindow
     */
    public MainWindowController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        //Inicialitzem el service
        service = new SystemService();
        //Per defecte mostrem la view amb la gráfica
        //Creem la vista
        GraphicsPanelView graphicsPanelView = new GraphicsPanelView(service.groupMatchByDay());
        //Creem el controlador i establim la relació Controller->View
        GraphicsController graphicsController = new GraphicsController(graphicsPanelView);
        //Relació View->Controller
        graphicsPanelView.setGraphicsViewController(graphicsController);
        //Setejem la view al panel de la MainWindow
        mainWindow.setGraphicsPanelView(graphicsPanelView);
    }

    /**
     * Métode que controla si el usuari canvia de pestaña a la view i canvia el contingut d'aquesta
     * @param e rep l'event per quan l'usuari canvia de pestaña a la view
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        //Comprovem quina pestaña s'ha clicat
        if (mainWindow.getJtpOptions().getSelectedIndex() == 1) {
            //Mostrem la view TopUserPanelView
            TopUserPanelView topUserPanelView = new TopUserPanelView(service.top5UsersLiked(), service.top5UsersNumMatchs());
            //Setejem la view al panel de la MainWindow
            mainWindow.setTopUserPanelView(topUserPanelView);
        } else if (mainWindow.getJtpOptions().getSelectedIndex() == 0) {
            //Mostrem la view GraphicsPanelView
            GraphicsPanelView graphicsPanelView = new GraphicsPanelView(service.groupMatchByDay());
            GraphicsController graphicsController = new GraphicsController(graphicsPanelView);
            graphicsPanelView.setGraphicsViewController(graphicsController);
            //Setejem la view al panel de la MainWindow
            mainWindow.setGraphicsPanelView(graphicsPanelView);
        }
    }
}