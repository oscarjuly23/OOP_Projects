/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package view;

import model.entity.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Vista que mostra un panel amb la foto de l'usuari, el nom i un botó
 */
public class ChatPanel extends JPanel {
    private JButton jbChat;
    private JLabel jlImage;
    private JLabel jlUsername;
    private String username;

    /**
     * Constructor de la classe
     * @param username Nom de l'usuari
     * @param img Imatge de l'usuari
     * @param id Id de l'usuari
     */
    public ChatPanel(String username, byte[] img, int id){

        this.username = username;
        setSize(new Dimension(320, 50));
        Border border = BorderFactory.createLineBorder(Color.black);
        setBorder(border);
        setLayout(new BorderLayout());
        jbChat = new JButton("Chat");
        String idChat = String.valueOf(id);
        jbChat.setActionCommand(idChat);
        ImageIcon imageIcon = new ImageIcon(img);
        Image scaledImage = imageIcon.getImage().getScaledInstance(50 ,50,Image.SCALE_SMOOTH);
        ImageIcon defPic = new ImageIcon(scaledImage);
        jlImage = new JLabel(defPic);
        add(jlImage,BorderLayout.WEST);
        jlUsername = new JLabel("       " + username);
        add(jlUsername, BorderLayout.CENTER);
        add(jbChat, BorderLayout.EAST);

    }

    /**
     * Getter del botó del panel
     * @return El botó del panel
     */
    public JButton getJbChat() {
        return jbChat;
    }

    /**
     * Getter del nom de l'usuari
     * @return Nom de l'usuari
     */
    public String getUsername() {
        return username;
    }

    /**
     * Actulitza el panel quan hi ha algun canvi
     * @param user Usuari a actualitzar
     */
    public void actualiza(User user) {
        ImageIcon imageIcon = new ImageIcon(user.getImg());
        Image scaledImage = imageIcon.getImage().getScaledInstance(50 ,50,Image.SCALE_SMOOTH);
        ImageIcon defPic = new ImageIcon(scaledImage);
        jlImage.setIcon(defPic);
        repaint();
    }
}