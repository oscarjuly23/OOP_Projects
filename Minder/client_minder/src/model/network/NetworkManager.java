/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.network;

import controller.*;
import model.MessageDTO;
import model.entity.Chat;
import model.ChatDTO;
import model.entity.Message;
import model.entity.User;
import model.UserDTO;
import network.ComunicationData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe que es comunica amb el servidor
 */
public class NetworkManager extends Thread {
    private boolean isOn;
    private int port;
    private String ip;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private User user;
    private LoginController loginController;
    private RegistroController registroController;
    private MainController mainController;
    private CompletaPerfilController completaPerfilController;
    private EditPerfilController editPerfilController;
    private PerfilOtroController perfilOtroController;
    private ChatController chatController;
    private ChatListController chatListController;

    /**
     * Constructor de la classe
     * @param ip Ip del socket
     * @param port Port del socket
     */
    public NetworkManager(String ip, int port) {
        try {
            this.port = port;
            this.isOn = false;
            this.ip = ip;
            this.socket = new Socket(ip, port);
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
            this.user = new User();
            startServerComunication();
        } catch (ConnectException e){
            System.out.println("No s'ha trobat cap servidor obert amb aquest port i IP");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia la comunicació amb el servidor
     */
    public void startServerComunication() {
        isOn = true;
        this.start();
    }

    /**
     * Atura la comunicació amb el servidor
     */
    public void stopServerComunication() {
        this.isOn = false;
        this.interrupt();
    }

    /**
     * Executa el thread i va llegint el que li envien
     */
    public void run() {
        while (isOn) {
            try {
                ComunicationData data = (ComunicationData) ois.readObject();
                gestionaDades(data);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                stopServerComunication();
                System.out.println("*** ESTA EL SERVIDOR EN EXECUCIO? ***");
            }
        }
    }

    /**
     * Gestiona les dades que li envien per sockets a partir de l'id que incorpora el element enviat
     * @param data Classe la qual utilitzem per enviar informació entre client-servidor
     */
    public void gestionaDades(ComunicationData data) {
        switch (data.getId()) {
            case "LOGOK":
                //quan l'usuari s'ha loguejat correctament
                System.out.println("Your user is " + data.getObj());
                UserDTO userDTO = (UserDTO) data.getObj();
                user.setAll(userDTO);
                sendData(new ComunicationData("NUMLOG", data.getObj()));
                break;
            case "LOGKO":
                //quan l'usuari ha introduit dades erròneas al loguejar-se
                int error = (int)data.getObj();
                loginController.openLoginError(error);
                System.out.println("Usuari o contraseña incorrectes");
                break;
            case "REGOK":
                //quan l'usuari s'ha registrat correctament
                registroController.openLoginView();
                System.out.println(data.getObj().toString());
                break;
            case "REGKO":
                //quan hi ha hagut algun error al registrars-se
                int err = (int)data.getObj();
                registroController.openRegisterError(err);
                System.out.println(data.getObj().toString());
                break;
            case "EDITOK":
                //quan l'usuari completa el perfil correctament
                if (data.getObj().toString().equals("0")){
                    completaPerfilController.openMainWindow();
                } else{
                    editPerfilController.openMainWindow();
                }
                System.out.println("Perfil ben completat");
                break;
            case "EDITKO":
                //quan hi ha hagut algun error al editar perfil
                if (data.getObj().toString().equals("0")){
                    completaPerfilController.openPerfilError();
                } else{
                    editPerfilController.openPerfilError();
                }
                break;
            case "CHATS":
                //el servidor t'envia els chats quan els necessites
                LinkedList<ChatDTO> chatsDTO = (LinkedList<ChatDTO>) data.getObj();
                LinkedList<Chat> chats = new LinkedList<>();
                for(ChatDTO c : chatsDTO){
                    User user = new User();
                    user.setAll(c.getOtherUser());
                    chats.add(new Chat(user, c.getId(), c.getIdMatch()));
                }
                if (chatListController != (null)) {
                    chatListController.updateChats(chats);
                } else if (mainController != (null) && mainController.isTancar()){
                    mainController.openChatListWindow(chats);
                } else if (chatController != (null) && chatController.isTancar()){
                    chatController.openChatListWindow(chats);
                }else if(chatController != null){
                    chatController.openChatListWindow(chats);
                }
                break;
            case "UPDATECHATS":
                //quan hi ha una actualització als chats
                LinkedList<ChatDTO> chatsDTO1 = (LinkedList<ChatDTO>) data.getObj();
                LinkedList<Chat> chats1 = new LinkedList<>();
                for(ChatDTO c : chatsDTO1){
                    User user = new User();
                    user.setAll(c.getOtherUser());
                    chats1.add(new Chat(user, c.getId(), c.getIdMatch()));
                }
                if (chatListController != (null)) {
                    chatListController.updateChats(chats1);
                }
                break;
            case "MATCH":
                //t'avisa que has fet un match
                OptionPaneController optionPaneController = new OptionPaneController( "Has fet match amb " + data.getObj().toString(), "Match");
                break;
            case "DISMATCH":
                //quan un usuari t'ha desfet el match
                String[] info = (String[]) data.getObj();
                String username = info[0];
                String idChat = info[1];
                OptionPaneController optionPaneController1 = new OptionPaneController( "L'usuari " + username + " ha desfet el match.", "Dismatch");
                if (chatListController != (null)) {
                    sendData(new ComunicationData("CHATS", username));
                } else if (chatController != (null)) {
                    if (String.valueOf(chatController.getChatWindow().getIdChat()).equals(idChat)) {
                        chatController.setTancar();
                        sendData(new ComunicationData("CHATS", username));
                    }
                }
                break;
            case "MSG":
                //quan t'envien nous missatges
                ArrayList<Message> msgs = new ArrayList<>();
                ArrayList<MessageDTO> msgsDTO = (ArrayList<MessageDTO>) data.getObj();
                for (MessageDTO msgDto : msgsDTO) {
                    msgs.add(new Message(msgDto.getId(),msgDto.getUserFrom(),msgDto.getUserTo(),msgDto.getChat(),msgDto.getMensaje(),msgDto.getFecha()));
                }
                if(chatController != null){
                    chatController.loadMsg(msgs);
                } else {
                    OptionPaneController optionPaneControllerMsg = new OptionPaneController( msgs.get(msgs.size()-1).getUserFrom() +":  " + msgs.get(msgs.size()-1).getMensaje(), "Mensaje Nuevo");
                }
                break;
            case "NOMOREUSERS":
                //quan no et queden usuaris per a mostrar
                mainController.setNoMoreUsers();
                break;
            case "SHOWUSERS":
                //quan t'envien un nou usuari a mostrar
                UserDTO userDTOUsers = (UserDTO)data.getObj();
                User actuallUser = new User();
                actuallUser.setAll(userDTOUsers);
                if(mainController != null){
                    mainController.setUser(actuallUser);
                } else if (perfilOtroController != null){
                    perfilOtroController.setUser(actuallUser);
                }
                break;
            case "FIRSTLOG":
                //quan et logueges per primer cop
                System.out.println("Es el primer cop que iniciem sessió.");
                loginController.openPerfilWindow();
                break;
            case "NOTFIRSTLOG":
                //quan ja t'has loguejat més vegades
                UserDTO userDTO1 = (UserDTO)data.getObj();
                this.user.setAll(userDTO1);
                System.out.println("Ja havies iniciat sessió anteriorment.");
                loginController.openMainWindow();
                break;
            case "PROFILECHAT":
                //quan canvia el perfil del chat obert que tens
                UserDTO userDTOChat = (UserDTO) data.getObj();
                User userchat = new User();
                userchat.setAll(userDTOChat);
                if (chatController != (null)){
                    chatController.actualizaUser(userchat);
                }
                break;
        }
    }

    /**
     * Envia informació al servidor
     * @param c Classe en la qual està guardada la informació
     */
    public void sendData(ComunicationData c) {
        try {
            oos.writeObject(c);
            oos.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter del controlador de la vista login
     * @param loginController Controlador de la vista login
     */
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
        this.registroController = null;
        this.mainController = null;
        this.completaPerfilController = null;
        this.editPerfilController=null;
        this.perfilOtroController = null;
        this.chatController = null;
        this.chatListController = null;
    }

    /**
     * Setter del controlador de la vista registre
     * @param registroController Controlador de la vista registre
     */
    public void setRegistroController(RegistroController registroController) {
        this.registroController = registroController;
        this.loginController = null;
        this.mainController = null;
        this.completaPerfilController = null;
        this.editPerfilController=null;
        this.perfilOtroController = null;
        this.chatController = null;
        this.chatListController = null;
    }

    /**
     * Setter del controlador de la vista principal
     * @param mainController Controlador de la vista principal
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        this.loginController = null;
        this.registroController = null;
        this.completaPerfilController = null;
        this.editPerfilController=null;
        this.perfilOtroController = null;
        this.chatController = null;
        this.chatListController = null;
    }

    /**
     * Setter del controlador de la vista de completar perfil
     * @param completaPerfilController Controlador de la vista de completar perfil
     */
    public void setCompletaPerfilController(CompletaPerfilController completaPerfilController) {
        this.completaPerfilController = completaPerfilController;
        this.loginController = null;
        this.registroController = null;
        this.mainController = null;
        this.editPerfilController=null;
        this.perfilOtroController = null;
        this.chatController = null;
        this.chatListController = null;
    }

    /**
     * Setter del controlador de la vista d'editar perfil
     * @param editPerfilController Controlador de la vista d'editar perfil
     */
    public void setEditPerfilController(EditPerfilController editPerfilController) {
        this.editPerfilController = editPerfilController;
        this.loginController = null;
        this.registroController = null;
        this.mainController = null;
        this.completaPerfilController = null;
        this.perfilOtroController = null;
        this.chatController = null;
        this.chatListController = null;
    }

    /**
     * Setter del controlador de la vista del perfil de l'usuari que s'està mostrant
     * @param perfilOtroController Controlador de la vista del perfil de l'usuari que s'està mostrant
     */
    public void setPerfilOtroController(PerfilOtroController perfilOtroController) {
        this.perfilOtroController = perfilOtroController;
        this.loginController = null;
        this.registroController = null;
        this.mainController = null;
        this.completaPerfilController = null;
        this.editPerfilController = null;
        this.chatController = null;
        this.chatListController = null;
    }

    /**
     * Setter del controlador de la vista d'un chat
     * @param chatController Controlador de la vista d'un chat
     */
    public void setChatController(ChatController chatController){
        this.chatController = chatController;
        this.perfilOtroController = null;
        this.loginController = null;
        this.registroController = null;
        this.mainController = null;
        this.completaPerfilController = null;
        this.editPerfilController = null;
        this.chatListController = null;
    }

    /**
     * Setter del controlador de la vista de la llista de chats
     * @param chatListController Controlador de la vista de la llista de chats
     */
    public void setChatListController(ChatListController chatListController){
        this.chatListController = chatListController;
        this.chatController = null;
        this.perfilOtroController = null;
        this.loginController = null;
        this.registroController = null;
        this.mainController = null;
        this.completaPerfilController = null;
        this.editPerfilController = null;
    }

    /**
     * Getter de l'usuari
     * @return Usuari de la conta
     */
    public User getUser(){
        return user;
    }
    /**
     * Setter de l'usuari
     * @param user Usuari de la conta
     */
    public void setUser(User user){
        this.user = user;
    }
}