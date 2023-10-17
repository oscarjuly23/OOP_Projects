/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package view;

import controller.GraphicsController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class GraphicsPanelView extends JPanel {

    private JComboBox jcbFecha;
    private Graphics2D graphics2D;
    private String[] data;

    public GraphicsPanelView(String[] data) {
        setBounds(200, 60, 450, 350);

        String[] tiempo = {"dia", "semana", "mes"};
        jcbFecha = new JComboBox(tiempo);
        jcbFecha.setBounds(190, 50, 200, 50);
        add(jcbFecha);

        this.data = data;
    }

    public void drawLines(Graphics graphics){
        Graphics2D g2d = (Graphics2D) graphics;
        int max = 0;
        int[][] posicio = new int[data.length][2];

        g2d.draw(new Line2D.Float(20.0f, 300.0f, 440.0f, 300.0f));
        g2d.draw(new Line2D.Float(20.0f, 300.0f, 20.0f, 20.0f));

        g2d.drawString("Matchs", 10, 10);

        for (int i = 0; i < data.length; i++) {
            if (Integer.parseInt(data[i])>max) {
                max= Integer.parseInt(data[i]);
            }
            if (data.length == 31 && i%2 == 1) {
                g2d.drawString(String.valueOf(i), 20+(420/data.length)*i,315);
                g2d.drawString("Dias", 200, 335);
            } else if (data.length == 24) {
                g2d.drawString(String.valueOf(i), 20+(420/data.length)*i,315);
                g2d.drawString("Hores", 200, 335);
            } else if (data.length == 7) {
                String[] nomsDies = {"Dl","Dm","Dx","Dj","Dv","Ds","Dg"};
                g2d.drawString(nomsDies[i], 20+(420/data.length)*i,315);
                g2d.drawString("Horas", 200, 335);
            }
            posicio[i][0] = 20+(420/data.length)*i;
        }
        max = max+2;

        g2d.drawString("0",5 ,305);
        for (int i = 1; i <= max; i++) {
            g2d.drawString(String.valueOf(i),5 ,305-(270/max)*i);
        }

        for (int i = 0; i < data.length; i++) {
            if (i!=data.length-1){
                g2d.draw(new Line2D.Float(posicio[i][0], 300-(270/max)*Integer.parseInt(data[i]), posicio[i+1][0], 300-(270/max)*Integer.parseInt(data[i+1])));
            }
        }
    }

    public void paint(Graphics graphics){
        super.paint(graphics);
        drawLines(graphics);
    }

    public JComboBox getJcbFecha() {
        return jcbFecha;
    }

    public void setGraphicsViewController(GraphicsController graphicsController) {
        jcbFecha.addItemListener(graphicsController);
    }

    public void setData(String[] data) {
        this.data = data;
    }
}