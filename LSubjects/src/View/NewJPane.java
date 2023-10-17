package View;

import Model.Subject;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class NewJPane extends JPanel {
    // Informació de les Subjects
    private JLabel jlNameSubject;
    private JLabel jlCredits;
    private JButton jbAddToFavorites;

    // Borde del JPanel
    private TitledBorder tbSubject;

    // Color del Borde segons sigui optativa o no.
    Border yellowline = BorderFactory.createLineBorder(Color.YELLOW);
    Border greenline = BorderFactory.createLineBorder(Color.GREEN);

    public NewJPane(Subject subjects) {
        // Fem un Box Layout per  a poder ordenar els nostres elements.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Creem i afegim per cada assignatura el seu nom, els credits i el botó de "Add to Favorites"
        jlNameSubject = new JLabel(subjects.getName());
        jlCredits = new JLabel(subjects.getCredits() + " credits");
        jbAddToFavorites = new JButton("Add to favorites");

        // Important li donem un Action Comand a cada botó (identificat amb el seu ID) per a que
        // cada botó tingui la seva funcionalitat per separat.
        jbAddToFavorites.setActionCommand(subjects.getId());

        // Afegim els JLabels en el JPanel en la seba posició (Box Layout)
        add(Box.createRigidArea(new Dimension(0,3)));

        add(jlNameSubject);
        jlNameSubject.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlNameSubject.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(jlCredits);
        jlCredits.setAlignmentX(Component.CENTER_ALIGNMENT);
        jlCredits.setBorder(BorderFactory.createEmptyBorder(15, 0, 25, 0));

        add(jbAddToFavorites);
        jbAddToFavorites.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hem de mirar si és optativa o no, posarem el seu ID en el borde.
        if (subjects.isOptional()) {     // Si ho és posem el Borde de color Groc.
            tbSubject = BorderFactory.createTitledBorder(yellowline, "(" + subjects.getId() + ")");
            setBorder(tbSubject);
        } else {    // Si no ho és el Borde serà Verd.
            tbSubject = BorderFactory.createTitledBorder(greenline, "(" + subjects.getId() + ")");
            setBorder(tbSubject);
        }

        // Aliniem els JLabels i el JButton al centre del JPanel de cada Subject
        jlNameSubject.setHorizontalAlignment(SwingConstants.CENTER);
        jlCredits.setHorizontalAlignment(SwingConstants.CENTER);
        jbAddToFavorites.setHorizontalAlignment(SwingConstants.CENTER);

        // Posem en negrita els següents elements:
        tbSubject.setTitleFont(tbSubject.getTitleFont().deriveFont(Font.BOLD));
        jlNameSubject.setFont(jlNameSubject.getFont().deriveFont(Font.BOLD));
        jlCredits.setFont(jlCredits.getFont().deriveFont(Font.BOLD));
    }

    public JButton getJbAddToFavorites() {
        return jbAddToFavorites;
    }
}