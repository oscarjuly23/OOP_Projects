package Controller;

import View.LSubjectWindow;
import View.NewJPane;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerSubject implements ActionListener {
    private LSubjectWindow vista;

    public ControllerSubject(LSubjectWindow vista){
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boto = (JButton) e.getSource();
        // Panells de Favorits:
        JScrollPane jScrollNuevo = vista.getJscrollcFavSubjects();
        JPanel jpNuevo = vista.getJpFavSubjects();

        for (int i = 0; i < vista.getArrayFavs().size(); i++) {
         // He de crear un nou panell per als favorits
         NewJPane panell = vista.getArrayFavs().get(i);
         // Quan cliquem al botó:
         if (boto.getActionCommand().equals(vista.getArrayFavs().get(i).getJbAddToFavorites().getActionCommand())) {
             // Si posa "Afegir a favorits"
             if (vista.getArraySubjects().get(i).getJbAddToFavorites().getText().equals("Add to favorites")) {
                 // Afegim la assinatura del botó en el que hem clicat en el nou panell de FavoriteSubjects.
                 jpNuevo.add(panell);
                 // Amaguem el botó de afegir a favorits ja que aquesta assignatura ja es troba en favorites.
                 vista.getArrayFavs().get(i).getJbAddToFavorites().setVisible(false);
                 // Canviem el botó que hem clicat de AllSubjects a Remove from favorites
                 vista.getArraySubjects().get(i).getJbAddToFavorites().setText("Remove from favorites");

             // Si el que posa el botó no es "Afegir a favorits" per tant ja l'hem afegir i posa remove.
             } else {
                 // Tornem a canviar el text del botó per afegir a favorits.
                 vista.getArraySubjects().get(i).getJbAddToFavorites().setText("Add to favorites");
                 // Eliminem el panell que teniem afegit als favorits.
                 jpNuevo.remove(panell);
             }
            }
        }
        // El VierportView del Scroll de Favorits serà el nou panell de favorits.
        jScrollNuevo.setViewportView(jpNuevo);
        // El Scroll Panel de Favorits serà el nou que hem construit.
        vista.setJscrollcFavSubjects(jScrollNuevo);
        // El Panell de Favorits també serà el nou que hem contruit.
        vista.setJpFavSubjects(jpNuevo);
    }
}