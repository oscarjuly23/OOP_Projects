/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package view;

import model.entity.Message;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

/**
 * Vista del chat amb un usuari
 */
public class ChatWindow extends JFrame {

    private static final String FILELOGO = "shared/src/data/Minder.png";
    private ChatPanel chatPanel;
    private JPanel contentPane;
    private JButton jbSend;
    private JButton jbBack;
    private JButton jbExit;
    private JTextField jtfSend;
    private JTextArea jtaTextChat;
    private String username;
    private int idChat;
    private JPanel jpChat;
    private JScrollPane jspChat;
    private boolean ajusta;

    /**
     * Constructor de la classe
     * @param username Nom de l'usuari del chat
     * @param img Imatge de l'usuari del chat
     * @param idChat Id del chat
     */
    public ChatWindow(String username, byte[] img, int idChat){
        configWindow(username);
        this.username = username;
        this.idChat = idChat;
        contentPane = new JPanel();
        contentPane.setBackground(Color.cyan);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel jlLogo = new JLabel();
        ImageIcon icono = new ImageIcon(FILELOGO);
        Image imagen = icono.getImage();
        ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(200,60,Image.SCALE_SMOOTH));
        jlLogo.setIcon(iconoEscalado);
        jlLogo.setBounds(120, 5, 450, 60);
        contentPane.add(jlLogo);

        chatPanel = new ChatPanel(username, img, idChat);
        chatPanel.getJbChat().setText("Rechazar");
        chatPanel.getJbChat().setBackground(Color.red);
        chatPanel.setBounds(40, 70, 350, 50);
        contentPane.add(chatPanel);

        ajusta = true;
        jpChat = new JPanel();
        jpChat.setLayout(new BoxLayout(jpChat, BoxLayout.Y_AXIS));
        jspChat = new JScrollPane(jpChat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jspChat.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(ajusta){
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    ajusta = false;
                }
            }
        });
        jspChat.setBounds(40, 140, 350, 195);
        this.getContentPane().add(jspChat);


        jtfSend = new JTextField();
        jtfSend.setFocusable(true);
        jtfSend.setBounds(40, 352, 280, 30);
        jtfSend.setSize(new Dimension(280, 30));
        jbSend = new JButton("Enviar");
        jbSend.setBounds(322, 352, 70, 30);

        contentPane.add(jtfSend);
        contentPane.add(jbSend);

        jbBack = new JButton("Volver");
        jbBack.setBounds(10, 10, 80, 23);
        contentPane.add(jbBack);

        jbExit = new JButton("Cerrar Sesión");
        jbExit.setBounds(291, 410, 120, 23);
        contentPane.add(jbExit);

        setVisible(true);
    }

    /**
     * Configuració del JFrame
     * @param username Nom de l'usuari del chat
     */
    private void configWindow(String username){
        //CONFIG PANTALLA
        setTitle("MINDER - CHAT: "+username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Getter del chat panel
     * @return El chat panel de la vista
     */
    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    /**
     * Fica un ActionListener a cada botó per a saber des del controlador a qui s'ha apretat
     * @param listener Crea un event a la que algu premi el botó
     */
    public void registraControladorChat(ActionListener listener){
        jbSend.addActionListener(listener);
        jbSend.setActionCommand("Send");
        jbBack.addActionListener(listener);
        jbBack.setActionCommand("Back");
        jbExit.addActionListener(listener);
        jbExit.setActionCommand("Exit");
        chatPanel.getJbChat().addActionListener(listener);
        chatPanel.getJbChat().setActionCommand("Rechazar");
    }

    /**
     * actualitza els missatges de la vista del chat
     * @param messageList Llista de missatges
     */
    public void loadMsg(ArrayList<Message> messageList) {

        jpChat.removeAll();
        for(Message msg: messageList){
            String info = msg.getUserFrom() +":  "+ msg.getMensaje() + " ";
            if(info.length() < 46){
                JLabel jlChat = new JLabel(info);
                jlChat.setPreferredSize(new Dimension(350,15));
                jpChat.add(jlChat);
            } else {
                JLabel jlChat = new JLabel(info.substring(0,46));
                jlChat.setPreferredSize(new Dimension(350,15));
                jpChat.add(jlChat);
                int aux = 46;
                int fi = aux +36;
                if(fi >= info.length() - 1){
                    fi = info.length() - 1;
                }
                boolean ultima = false;
                while(true){

                    JLabel jlChatAux = new JLabel("                " + info.substring(aux, fi));
                    jlChat.setPreferredSize(new Dimension(350,15));
                    jpChat.add(jlChatAux);
                    if(ultima){
                        break;
                    }
                    aux = aux + 36;
                    if(aux >= info.length()){
                        ultima = true;
                        aux = info.length();
                    }
                    fi = aux +35;
                    if(fi > info.length()){
                        fi = info.length();
                        ultima = true;
                    }
                }
            }
        }
        ajusta = true;
        jspChat.repaint();
        revalidate();
        repaint();
    }

    /**
     * Getter del missatge a enviar
     * @return El missatge a enviar
     */
    public String getMessage() {
        return jtfSend.getText();
    }

    /**
     * Getter de l'usuari del chat
     * @return Usuari del chat
     */
    public String getUsername(){
        return username;
    }

    /**
     * Getter del Id del chat
     * @return Id del chat
     */
    public int getIdChat() {
        return idChat;
    }

    /**
     * Getter del JTextField o escrius el missatge a enviar
     * @return JTextField o escrius el missatge a enviar
     */
    public JTextField getJtfSend() {
        return jtfSend;
    }

}