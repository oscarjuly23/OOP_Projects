/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.network;

import model.UserDTO;
import model.entity.Message;
import model.entity.User;
import network.ComunicationData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe Sever que escolta totes les peticions de connexió dels clients i asigna cadascun a un servidor dedicat
 */
public class Server extends Thread{
    private boolean isOn;
    private ServerSocket minder;
    private List<DedicatedServer> connUsers;
    private int port;

    /**
     * Constructor de la classe
     * @param port port pel cual escolta les peticions
     */
    public Server(int port){
        try {
            this.port = port;
            this.minder = new ServerSocket(port);
            this.isOn = false;
            this.connUsers = new LinkedList<>();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Inicia el thread del servidor
     */
    public void startServer() {
        isOn = true;
        this.start();
    }

    /**
     * Atura el thread del servidor
     */
    public void stopServer() {
        isOn = false;
        this.interrupt();
    }

    /**
     * Elimina un servidor dedicat cuan un usuari es desconnecta
     * @see DedicatedServer
     * @param dedicatedServer servidor dedicat a eliminar
     */
    public void removeDedicatedServer(DedicatedServer dedicatedServer){
        for (DedicatedServer connUser : connUsers) {
            if (connUser == dedicatedServer) {
                connUsers.remove(dedicatedServer);
                dedicatedServer.stopDedicatedServer();
            }
        }
        System.out.println(connUsers.size());
        System.out.println("Logout");
    }

    /**
     * Mostra el numero de clients connectats
     */
    public void showClients() {
        System.out.println("***** SERVER ***** (" + connUsers.size() +" clients / dedicated servers running)");
    }

    /**
     * Métode que gestiona les peticions mentre el thread esitgui executan-se
     */
    public void run(){
        while(isOn) {
            try {
                System.out.println("Waiting for clients");
                Socket socket = minder.accept();
                System.out.println("Client connectat");
                System.out.println(port);
                DedicatedServer dedicatedServer = new DedicatedServer(socket, this);
                connUsers.add(dedicatedServer);
                dedicatedServer.startDedicatedServer();
                showClients();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (DedicatedServer dServer : connUsers) {
            dServer.startDedicatedServer();
        }
    }

    /**
     * Quan es produeix un match s'encarrega d'enviar-li a l'altre client involucrat en temps real
     * @param otherUser usuari des del que s'envia
     * @param user usuari al que s'envia
     */
    public void sendMatch(String otherUser, String user) {
        for (DedicatedServer connUser : connUsers) {
            if (connUser.getDataModel() != null){
                if(connUser.getDataModel().getUser().getUsername().equals(user)){
                    connUser.sendData(new ComunicationData("MATCH", otherUser));
                    connUser.sendData(new ComunicationData("UPDATECHATS", connUser.getService().chatsFor(user)));
                    break;
                }
            }
        }
    }

    /**
     * Afegeix un nou usuari a la cua dels que estan pendents de mostrarse
     * @param user usuari que volem afegir a la cua
     */
    public void addNewUser(User user) {
        for (DedicatedServer connUser : connUsers) {
            if (connUser.getDataModel() != null){
                connUser.getDataModel().getUsers().add(user);
            }
        }
    }

    /**
     * Actualitza les cues dels servidors dedicats en temps real quan un usuari s'ha actualitzat
     * @param user usuari actualitzat
     */
    public void actualitzaCues(User user) {
        for (DedicatedServer connUser : connUsers) {
            connUser.sendChats();
            connUser.sendData(new ComunicationData("PROFILECHAT",new UserDTO(user.getUsername(),user.getEdad(),user.isPremium(),user.getEmail(),user.getPassword(),user.getDescription(),user.getLang(),user.getImg())));
            if (connUser.getDataModel() != null && connUser.getDataModel().getMostrant() != (null)){
                //El usuari actualitzat es el que esta visualitzant el client en aquell moment
                if(connUser.getDataModel().getMostrant().getUsername().equals(user.getUsername())){
                    connUser.actualitzaUsuariTempsReal(user);
                } else {
                    connUser.getDataModel().actualitzaUsuari(user);
                }
            }
        }
    }

    /**
     * Gestiona el chat de dos usuaris en temps real amb els dos clients
     * @param msg missatge a enviar
     */
    public void sendMsgToOtherUser(Message msg) {
        for (DedicatedServer connUser : connUsers) {
            if (connUser.getDataModel() != null){
                if(connUser.getDataModel().getUser().getUsername().equals(msg.getUserTo())){
                    connUser.sendMessage(msg);
                }
            }
        }
    }

    /**
     * Es notifica el dismatch en temps real als usuaris involucrats
     * @param username usuari al que s'envia
     * @param me usuari que ha fet dismatch
     * @param idChat id del chat
     */
    public void sendDismatch(String username, String me, String idChat) {
        String[] info = {me,idChat};
        for (DedicatedServer connUser : connUsers) {
            if (connUser.getDataModel() != null) {
                if (connUser.getDataModel().getUser().getUsername().equals(username)) {
                    connUser.sendData(new ComunicationData("DISMATCH", info));
                }
            }
        }
    }

    /**
     * Afegeix o esborra un usuari de la cua
     * @param user usuari a afegir/esborrar
     */
    public void afegeixBorra(User user) {
        for (DedicatedServer connUser : connUsers) {
            if (connUser.getDataModel() != null && connUser.getDataModel().getMostrant() != (null)){
                if(connUser.getDataModel().getMostrant().getUsername().equals(user.getUsername())){
                    connUser.getDataModel().setMostrant(null);
                } else {
                    if(connUser.getDataModel().getUser().getLang().equals(user.getLang())){
                        connUser.getDataModel().getUsers().add(user);
                    } else {
                        connUser.getDataModel().deleteUser(user);
                    }
                }
            }
        }
    }
}