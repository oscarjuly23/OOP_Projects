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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Classe encarregada del controllador del panell de la gràfica. Controller
 */
public class GraphicsController implements ItemListener {
    //Relació amb al view
    private GraphicsPanelView graphicsPanelView;
    //Relació amb la model
    private SystemService service;

    /**
     * Constructor de la classe, Rep la GraphicsPanelView (view)
     * @param graphicsPanelView
     */
    public GraphicsController(GraphicsPanelView graphicsPanelView){
        this.graphicsPanelView = graphicsPanelView;
        this.service = new SystemService();
    }

    /**
     * Métode que controla si el usuari canvia la forma de visualitzar la grafica (dia, semana, mes)
     * @param e rep l'event cuan l'usuari canvia la opció de la ComboBox
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        //Comprovem l'opció escollida
        if (graphicsPanelView.getJcbFecha().getSelectedItem().equals("dia")){
            graphicsPanelView.setData(service.groupMatchByDay());
            graphicsPanelView.repaint();
        }else if (graphicsPanelView.getJcbFecha().getSelectedItem().equals("semana")){
            graphicsPanelView.setData(service.groupMatchByWeek());
            graphicsPanelView.repaint();
        }else if(graphicsPanelView.getJcbFecha().getSelectedItem().equals("mes")){
            graphicsPanelView.setData(service.groupMatchByMonth());
            graphicsPanelView.repaint();
        }
    }
}