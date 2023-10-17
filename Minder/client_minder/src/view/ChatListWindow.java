/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package view;

import model.entity.Chat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Vista de la llista de chats de l'usuari
 */
public class ChatListWindow extends JFrame{

    private static final String FILELOGO = "shared/src/data/Minder.png";
    private JButton jbBack;
    private JButton jbExit;
    private JPanel matchpanel;
    private JScrollPane matchsScrollPanel;
    private ArrayList<ChatPanel> chatPanels;
    private int MINHEIGHT;
    private int MAXHEIGHT;

    /**
     * Constructor de la classe
     * @param matches Llista de chats
     */
    public ChatListWindow(LinkedList<Chat> matches){
        configWindow();
        getContentPane().setBackground(Color.cyan);
        getContentPane().setLayout(null);
        JLabel jlLogo = new JLabel();
        ImageIcon icono = new ImageIcon(FILELOGO);
        Image imagen = icono.getImage();
        ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(200,60,Image.SCALE_SMOOTH));
        jlLogo.setIcon(iconoEscalado);
        jlLogo.setBounds(120, 5, 450, 60);
        this.getContentPane().add(jlLogo);

       /** show all matches **/
        matchpanel = new JPanel();
        matchpanel.setLayout(new BoxLayout(matchpanel, BoxLayout.Y_AXIS));
        chatPanels = new ArrayList<>();
        for(Chat chat: matches){
            ChatPanel chatPanel = new ChatPanel(chat.getOtherUser().getUsername(), chat.getOtherUser().getImg(), chat.getId());
            chatPanel.setPreferredSize(new Dimension(320,50));
            matchpanel.add(chatPanel);
            chatPanels.add(chatPanel);
        }
        for(int i= 0; i < 6- chatPanels.size(); i++){
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(320,50));
            matchpanel.add(panel);
        }

        matchsScrollPanel = new JScrollPane(matchpanel);
        matchsScrollPanel.setBounds(30, 80, 400, 300);

        this.getContentPane().add(matchsScrollPanel);

        jbBack = new JButton("Volver");
        jbBack.setBounds(10, 10, 80, 23);
        this.getContentPane().add(jbBack);

        jbExit = new JButton("Cerrar Sesión");
        jbExit.setBounds(291, 410, 120, 23);
        this.getContentPane().add(jbExit);

        setVisible(true);
    }

    /**
     * Configuració del JFrame
     */
    private void configWindow(){
        //CONFIG PANTALLA
        setTitle("MINDER - CHAT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Fica un ActionListener a cada botó per a saber des del controlador a qui s'ha apretat
     * @param listener Crea un event a la que algu premi el botó
     */
    public void registraControladorChatList(ActionListener listener){
        for(ChatPanel panel: chatPanels){
            JButton button = panel.getJbChat();
            button.addActionListener(listener);
        }
        jbBack.addActionListener(listener);
        jbBack.setActionCommand("Back");
        jbExit.addActionListener(listener);
        jbExit.setActionCommand("Exit");
    }

    /**
     * Actualitza els chats quan hi ha algun canvi
     * @param chats Chats de l'usuari
     */
    public void actualitzaChats(LinkedList<Chat> chats) {
        matchpanel.removeAll();
        chatPanels = new ArrayList<>();
        for(Chat chat: chats){
            ChatPanel chatPanel = new ChatPanel(chat.getOtherUser().getUsername(), chat.getOtherUser().getImg(), chat.getId());
            chatPanel.setPreferredSize(new Dimension(320,50));
            matchpanel.add(chatPanel);
            chatPanels.add(chatPanel);
        }
        for(int i= 0; i < 6- chatPanels.size(); i++){
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(320,50));
            matchpanel.add(panel);
        }

        matchsScrollPanel.repaint();
        revalidate();
        repaint();
    }
}