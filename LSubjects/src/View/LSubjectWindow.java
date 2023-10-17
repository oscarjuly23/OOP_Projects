package View;

import Model.Subject;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LSubjectWindow extends JFrame {

    // Un Split Pane que separa la pantalla en dos (verticalment)
    private JSplitPane jsplitpPanellPrincipal;

    // Dos Scroll Pane, el de la part de dalt (favorites) i el de la part de abaix (all)
    private JScrollPane jscrollcFavSubjects;
    private JScrollPane jscrollcAllSubjects;

    // Creem els bordes dels Scroll Pane i de les Subjects
    private TitledBorder tbFavSubjects;
    private TitledBorder tbAllSubjects;

    // Tindrem JPanel general on afegirem un JPanel per cada assignatura;
    private JPanel jpAllSubjects;
    private JPanel jpFavSubjects;

    private NewJPane assignatura;
    private NewJPane assignaturaFav;

    // Fem un Array List que s'omplira amb els JPane de cada Suject
    private ArrayList<NewJPane> arraySubjects = new ArrayList<>();
    private ArrayList<NewJPane> arrayFavs = new ArrayList<>();

    public LSubjectWindow(ArrayList<Subject> subjects) {

        // Configuracions inicials
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(540, 440));
        this.setLocationRelativeTo(null);
        this.setTitle("LSubjects");
        this.setResizable(false);

        // Split Pane general que divineix la finestra en les dues parts
        jsplitpPanellPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jscrollcFavSubjects, jscrollcAllSubjects);
        jsplitpPanellPrincipal.setDividerLocation(getSize().height / 2-25);
        jsplitpPanellPrincipal.setEnabled(false);   // Per que no es pugui moure la barra de Split

        // Scroll Pane
        jscrollcFavSubjects = new JScrollPane();
        //jscrollcFavSubjects.setPreferredSize(new Dimension(450, 110));
        jscrollcAllSubjects = new JScrollPane();
        //jscrollcAllSubjects.setPreferredSize(new Dimension(450, 110));

        // Titled borders to Scroll Panes
        Border greyline = BorderFactory.createLineBorder(Color.GRAY);

        tbFavSubjects = BorderFactory.createTitledBorder(greyline, "Favorite subjects");
        //tbFavSubjects.setTitleJustification(TitledBorder.LEFT);
        jscrollcFavSubjects.setBorder(tbFavSubjects);

        tbAllSubjects = BorderFactory.createTitledBorder(greyline,"All subjects");
        jscrollcAllSubjects.setBorder(tbAllSubjects);

        // JPanel de Assignatures on guardarem el JPanel de cada assignarura.
        jpAllSubjects = new JPanel(new GridLayout());
        jpFavSubjects = new JPanel(new GridLayout());

        // Recorrerem els Subjects, creem un JPane per cadascun (amb les  i els afegim al Scroll Pane
        for (int i = 0; i < subjects.size(); i++) {

            // Creem un JPanel per cadascun
            assignatura = new NewJPane(subjects.get(i));
            assignaturaFav = new NewJPane(subjects.get(i));

            // Afegim el JPanel de l'assignatura en el JPanel general de les assignatures.
            jpAllSubjects.add(assignatura);

            arraySubjects.add(assignatura);
            arrayFavs.add(assignaturaFav);
        }

        // Afegim el JPanel general de les assignarures al Scroll Pane
        jscrollcAllSubjects.setViewportView(jpAllSubjects);
        add(jscrollcAllSubjects);

        // Afegim els dos Scroll Pane en el Split Pane
        jsplitpPanellPrincipal.setTopComponent(jscrollcFavSubjects);
        jsplitpPanellPrincipal.setBottomComponent(jscrollcAllSubjects);

        // Añado el JSplit Pane en la finestra
        getContentPane().add(jsplitpPanellPrincipal);

        // Posarem en BOLD els seguents text:
        tbAllSubjects.setTitleFont(tbAllSubjects.getTitleFont().deriveFont(Font.BOLD));
        tbFavSubjects.setTitleFont(tbFavSubjects.getTitleFont().deriveFont(Font.BOLD));
    }

    public void setJscrollcFavSubjects(JScrollPane jscrollcFavSubjects) {
        this.jscrollcFavSubjects = jscrollcFavSubjects;
    }
    public void setJpFavSubjects(JPanel jpFavSubjects) {
        this.jpFavSubjects = jpFavSubjects;
    }

    public ArrayList<NewJPane> getArraySubjects() {
        return arraySubjects;
    }
    public ArrayList<NewJPane> getArrayFavs() {
        return arrayFavs;
    }
    public JPanel getJpFavSubjects() {
        return jpFavSubjects;
    }
    public JScrollPane getJscrollcFavSubjects() {
        return jscrollcFavSubjects;
    }

    public void favoritesController(ActionListener controladorSubjects, ArrayList<NewJPane> arraySubjects) {
        // Recorrem el array de JPanel de cada assignatura i fem un Action Listener de cada botó.
        for (int i = 0; i < arraySubjects.size(); i++) {
            this.arraySubjects.get(i).getJbAddToFavorites().addActionListener(controladorSubjects);
        }
    }
}