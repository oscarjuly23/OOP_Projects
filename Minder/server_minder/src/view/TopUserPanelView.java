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

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;

/**
 * Classe de la taula de TopUsers. View
 */

public class TopUserPanelView extends JPanel {
    private JTable table;
    private String[][] datos = new String[5][3];
    private JScrollPane scroll;

    /**
     * Constructor de la clase
     * @param users Llista ordenada dels usuaris que estan al top 5
     * @param nums Numero de likes corresponents per cada usuari
     * @see #centerText()
     */
    public TopUserPanelView(LinkedList<String> users, int[] nums){
        if(users.size() == 0){
            JLabel jLabel = new JLabel("No hay ningun usuario con un like!");
            jLabel.setBounds(12,22,300,300);
            add(jLabel);
        } else {
            //Creem les 3 columnes
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("Posición");
            modelo.addColumn("Usuarios");
            modelo.addColumn("Matchs");
            //Afegim els valors del usuari per fila a cada columna
            for(int i = 0; i<users.size(); i++){
                datos[i][0] = String.valueOf(i+1);
                datos[i][1] = users.get(i);
                datos[i][2] = String.valueOf(nums[i]);
                modelo.addRow(datos[i]);
            }
            table = new JTable(modelo);
            //Centrem el contingut de la taula
            centerText();
            scroll = new JScrollPane(table);
            table.setBounds(12,22,300,300);
            setSize(400,400);
            scroll.setBounds(12,22,300,300);
            add(scroll);
            setBorder(BorderFactory.createEmptyBorder(60, 30, 0, 30));
        }
        setVisible(true);
    }

    /**
     * Centra el text de cada celda de la taula
     */
    private void centerText() {
        DefaultTableCellRenderer modelocentrar = new DefaultTableCellRenderer();
        modelocentrar.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(modelocentrar);
        table.getColumnModel().getColumn(1).setCellRenderer(modelocentrar);
        table.getColumnModel().getColumn(2).setCellRenderer(modelocentrar);
    }
}