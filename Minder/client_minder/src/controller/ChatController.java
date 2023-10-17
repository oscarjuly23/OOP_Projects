/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package controller;

import model.MessageDTO;
import model.entity.Chat;
import model.entity.Message;
import model.entity.User;
import model.network.NetworkManager;
import network.ComunicationData;
import view.ChatListWindow;
import view.ChatWindow;
import view.LoginWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * És el controlador de la vista d'un chat.
 */

public class ChatController implements ActionListener {
    private ChatWindow chatWindow;
    private NetworkManager networkManager;
    private LinkedList<Chat> chats;
    private User userMostrant;
    private ArrayList<Message> msgs;
    private boolean tancar;

    /**
     * Constructor de la classe
     * @param chatWindow Vista del chat
     * @param networkManager Classe que comunica amb el servidor
     */
    public ChatController(ChatWindow chatWindow, NetworkManager networkManager) {
        this.tancar = false;
        this.chatWindow = chatWindow;
        this.networkManager = networkManager;
    }

    /**
     * Espera a que hi hagi un event en els botons de la vista i selecciona el corresponent per el botó apretat
     * @param e Event d'apretar un botó
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Send":
                System.out.println("Message");
                if(!chatWindow.getMessage().equals("")){
                    networkManager.sendData(new ComunicationData("MSG", new MessageDTO(0, networkManager.getUser().getUsername(),chatWindow.getUsername(),chatWindow.getIdChat(), chatWindow.getMessage(), LocalDateTime.now())));
                    chatWindow.getJtfSend().setText("");
                }
                break;
            case "Back":
                tancar = true;
                networkManager.sendData(new ComunicationData("CHATS", networkManager.getUser().getUsername()));
                break;
            case "Exit":
                chatWindow.dispose();
                LoginWindow loginWindow = new LoginWindow();
                LoginController loginController = new LoginController(loginWindow, networkManager);
                networkManager.setLoginController(loginController);
                loginWindow.registraControladorLogin(loginController);
                networkManager.sendData(new ComunicationData("ADEU", networkManager.getUser().getUsername()));
                break;
            case "Rechazar":
                String[] infoDismatch = {chatWindow.getUsername(), String.valueOf(chatWindow.getIdChat())};
                tancar = true;
                networkManager.sendData(new ComunicationData("DISMATCH", infoDismatch));
                networkManager.sendData(new ComunicationData("CHATS", networkManager.getUser().getUsername()));
                break;
        }
    }

    /**
     * Carrega els missatges al chat
     * @param messages Llista de missatges
     */
    public void loadMsg(ArrayList<Message> messages) {
        chatWindow.loadMsg(messages);
        this.msgs = messages;
    }

    /**
     * Setter dels chats
     * @param chats Chats de l'usuari
     */
    public void setChats(LinkedList<Chat> chats) {
        this.chats = chats;
    }

    /**
     * Setter del usuari que està mostrant
     * @param userMostrant Usuari que està mostrant
     */
    public void setUserMostrant(User userMostrant) {
        this.userMostrant = userMostrant;
    }

    /**
     * Getter de la vista del chat
     * @return Retorna la vista del chat
     */
    public ChatWindow getChatWindow() {
        return chatWindow;
    }

    /**
     * Obra la vista de la llista del chat i tanca la d'un chat específic
     * @param chats Llista de chats del usuari
     */
    public void openChatListWindow(LinkedList<Chat> chats) {
        chatWindow.dispose();
        ChatListWindow chatListWindow = new ChatListWindow(chats);
        ChatListController chatListController = new ChatListController(chatListWindow, networkManager);
        chatListController.setChatList(chats);
        chatListController.setUserMostrado(userMostrant);
        networkManager.setChatListController(chatListController);
        chatListWindow.registraControladorChatList(chatListController);
    }

    /**
     * Actualitza l'usuari del chat en temps real quan canvia la foto
     * @param user Usuari amb qui chateja
     */
    public void actualizaUser(User user) {
        if (user.getUsername().equals(chatWindow.getChatPanel().getUsername())){
            chatWindow.getChatPanel().actualiza(user);
        }
    }

    /**
     * Retorna true si s'ha apretat el botò de tirar enrere.
     * @return Si s'ha apretat el botó de tirar enrere
     */
    public boolean isTancar() {
        return tancar;
    }

    public void setTancar() {
        tancar = true;
    }
}