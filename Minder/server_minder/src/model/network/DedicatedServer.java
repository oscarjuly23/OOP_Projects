/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.network;

import model.DataModel;
import model.MessageDTO;
import model.UserDTO;
import model.database.SystemService;
import model.entity.Message;
import model.entity.User;
import network.ComunicationData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Queue;

/**
 * Classe que es basa en un servidor dedicat per atendre i gestionar les accions d'un client
 */

public class DedicatedServer extends Thread {
    private boolean isOn;
    private Socket socket;
    private SystemService service;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Server server;
    private DataModel dataModel;

    /**
     * Constructor de la classe
     * @param socket per crear la comunicació amb el client
     * @param server servidor replicar les configuracions
     */
    public DedicatedServer(Socket socket, Server server) {
        try {
            this.isOn = false;
            this.socket = socket;
            this.service = new SystemService();
            this.server = server;
            this.ois = new ObjectInputStream(socket.getInputStream());
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia el servidor dedicat
     */
    public void startDedicatedServer() {
        isOn = true;
        this.start();
    }

    /**
     * Atura el servidor dedicat
     */
    public void stopDedicatedServer() {
        // aturem el servidor dedicat
        this.isOn = false;
        this.interrupt();
        //this.stop();
    }

    /**
     * Mentre el thread esta actiu, escolta totes les peticions rebudes
     */
    public void run() {
        while (isOn) {
            try {
                ComunicationData data = (ComunicationData) ois.readObject();
                gestionaDades(data);
            } catch (SocketException e) {
                server.removeDedicatedServer(this);
                System.out.println("Client tancat");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                stopDedicatedServer();
            }
        }
    }

    /**
     * Gestiona les dades que li envien per sockets desde el client a partir de l'id que incorpora el element enviat
     * @param data Classe la qual utilitzem per enviar informació entre client-servidor
     * @throws IOException si s'ha produit una excepció d'entrada o sortida
     */
    public void gestionaDades(ComunicationData data) throws IOException {
        switch (data.getId()) {
            case "LOG":
                //El client envia una solicitut d'inici de sessió
                UserDTO userDTOLOG = (UserDTO) data.getObj();
                ComunicationData comunicationDataLOG;
                if(userDTOLOG.checkDataLogin()){
                    User user = service.logUser(userDTOLOG.getUsername(), userDTOLOG.getPassword());
                    if (user == (null)) {
                        comunicationDataLOG = new ComunicationData("LOGKO", 1);
                    } else {
                        UserDTO userDTO1 = new UserDTO(user.getUsername(), user.getEdad(), user.isPremium(), user.getEmail(), user.getPassword(), user.getDescription(), user.getLang(), user.getImg());
                        comunicationDataLOG = new ComunicationData("LOGOK",userDTO1);

                    }
                } else {
                    comunicationDataLOG = new ComunicationData("LOGKO", 2);
                }
                sendData(comunicationDataLOG);
                break;
            case "REG":
                //El client registra un nou usuari
                ComunicationData comunicationDataREG;
                UserDTO userDTOREG = (UserDTO) data.getObj();
                if(userDTOREG.checkDataRegister()){
                    User user = new User();
                    user.setAll(userDTOREG);
                    if (service.regUser(user)) {
                        comunicationDataREG= new ComunicationData("REGOK", "Registrat amb éxit!");
                    } else {
                        comunicationDataREG = new ComunicationData("REGKO", 1);
                    }
                } else {
                    comunicationDataREG = new ComunicationData("REGKO", 2);
                }
                sendData(comunicationDataREG);
                break;
            case "EDIT":
                //Es la primera vegada que el client inicia sessió, per tant el client acaba de completar les dades del seu perfil
                UserDTO userDTOEdit = (UserDTO)data.getObj();
                saveImage(userDTOEdit.getImg(),userDTOEdit.getUsername());
                if(userDTOEdit.checkDataEdit()){
                    service.addDescriptionLanguage(userDTOEdit.getDescription(), userDTOEdit.getLang(), userDTOEdit.getUsername());
                    sendData(new ComunicationData("EDITOK", "0"));
                    User user = new User();
                    user.setAll(userDTOEdit);
                    server.addNewUser(user);
                    Queue<User> users = service.getUsersFor(user);
                    dataModel = new DataModel(user, users);
                    if(dataModel.getUsers().isEmpty()){
                        sendData(new ComunicationData("NOMOREUSERS", null));
                    } else {
                        user = dataModel.getUsers().remove();
                        dataModel.setMostrant(user);
                        UserDTO userDTOUsers = new UserDTO(user.getUsername(), user.getEdad(), user.isPremium(), user.getEmail(), user.getPassword(), user.getDescription(), user.getLang(), user.getImg());
                        sendData(new ComunicationData("SHOWUSERS", userDTOUsers));
                    }
                } else {
                    sendData(new ComunicationData("EDITKO", "0"));
                }
                break;
            case "DISMATCH":
                //El client desfa el match amb un usuari
                String[] infoDismatch = (String[]) data.getObj();
                String username = infoDismatch[0];
                String chatId = infoDismatch[1];
                service.removeMatch(chatId);
                server.sendDismatch(username,dataModel.getUser().getUsername(),chatId);
                break;
            case "PERFIL":
                //El client ha editat el seu perfil
                UserDTO userDTOPerfil= (UserDTO)data.getObj();
                saveImage(userDTOPerfil.getImg(),userDTOPerfil.getUsername());
                User userPerfil = new User();
                userPerfil.setAll(userDTOPerfil);
                if(userDTOPerfil.checkDataEdit()){
                    service.addDescriptionLanguage(userDTOPerfil.getDescription(), userDTOPerfil.getLang(), userDTOPerfil.getUsername());
                    dataModel.setUser(userPerfil);
                    server.actualitzaCues(userPerfil);
                    sendData(new ComunicationData("EDITOK", "1"));
                    if(userPerfil.checkIfLanguageChange(dataModel.getUser().getLang())){
                        Queue<User> users = service.getUsersFor(userPerfil);
                        dataModel.setUsers(users);
                        User user;
                        if(dataModel.getUsers().isEmpty()){
                            sendData(new ComunicationData("NOMOREUSERS", null));
                        } else {
                            user = dataModel.getUsers().remove();
                            dataModel.setMostrant(user);
                            UserDTO userDTOUsers = new UserDTO(user.getUsername(), user.getEdad(), user.isPremium(), user.getEmail(), user.getPassword(), user.getDescription(), user.getLang(), user.getImg());
                            sendData(new ComunicationData("SHOWUSERS", userDTOUsers));
                        }
                        server.afegeixBorra(userPerfil);
                    }
                } else {
                    sendData(new ComunicationData("EDITKO", "1"));
                }
                break;
            case "LIKE":
                //El client ha fet like a un usuari
                UserDTO userDTOLike = (UserDTO) data.getObj();
                User userLike = new User();
                userLike.setAll(userDTOLike);
                service.newLike(dataModel.getUser().getUsername(), userLike.getUsername());
                //Es comprova si s'ha fet match
                if(service.comprovaMatch(dataModel.getUser().getUsername(), userLike.getUsername(), true)){
                    sendData(new ComunicationData("MATCH", userLike.getUsername()));
                    server.sendMatch(dataModel.getUser().getUsername(), userLike.getUsername());
                }
                if(dataModel.getUsers().isEmpty()){
                    sendData(new ComunicationData("NOMOREUSERS", null));
                } else {
                    //S'actualitza la cua
                    userLike = dataModel.getUsers().remove();
                    dataModel.setMostrant(userLike);
                    UserDTO userDTO = new UserDTO(userLike.getUsername(), userLike.getEdad(), userLike.isPremium(), userLike.getEmail(), userLike.getPassword(), userLike.getDescription(), userLike.getLang(), userLike.getImg());
                    sendData(new ComunicationData("SHOWUSERS", userDTO));
                }
                break;
            case "DISLIKE":
                //El client ha declinat a un usuari
                UserDTO userDTODislike = (UserDTO) data.getObj();
                User userDislike = new User();
                userDislike.setAll(userDTODislike);
                dataModel.getUsers().add(userDislike);
                userDislike = dataModel.getUsers().remove();
                //S'actualitza la cua
                dataModel.setMostrant(userDislike);
                UserDTO userDTO = new UserDTO(userDislike.getUsername(), userDislike.getEdad(), userDislike.isPremium(), userDislike.getEmail(), userDislike.getPassword(), userDislike.getDescription(), userDislike.getLang(), userDislike.getImg());
                sendData(new ComunicationData("SHOWUSERS", userDTO));
                break;
            case "LLISTAMSG":
                //El client demana la llista de missatges per un chat
                int idChat = Integer.parseInt(String.valueOf(data.getObj()));
                sendData(new ComunicationData("MSG",service.getMessages(idChat)));
                break;
            case "MSG":
                //El client ha enviat un nou missatge
                MessageDTO msgDTO = (MessageDTO) data.getObj();
                Message msg = new Message(msgDTO.getId(), msgDTO.getUserFrom(), msgDTO.getUserTo(), msgDTO.getChat(), msgDTO.getMensaje(), msgDTO.getFecha());
                service.addMessage(msg);
                sendData(new ComunicationData("MSG",service.getMessages(msg.getIdChat())));
                server.sendMsgToOtherUser(msg);
                break;
            case "CHATS":
                //El client demana els chats
                sendData(new ComunicationData("CHATS", service.chatsFor(String.valueOf(data.getObj()))));
                break;
            case "ADEU":
                //Client tanca sessió
                dataModel = null;
                System.out.println("Adeu " + data.getObj().toString());
                break;
            case "NUMLOG":
                //Demana el numero de logins que ha fet el usuari
                UserDTO userDTONUM = (UserDTO) data.getObj();
                boolean first = service.firstLog(userDTONUM);
                if (first) {
                    //Si es la primera vegada que inicia sessió
                    sendData( new ComunicationData("FIRSTLOG", first));
                } else {
                    //Inicia sessió normalment
                    String path = "./server_minder/src/data/usersImg/"+ userDTONUM.getUsername() +".png";
                    service.newLog(userDTONUM);
                    userDTONUM.setImg(userDTONUM.convertToByteArray(path));
                    sendData(new ComunicationData("NOTFIRSTLOG", userDTONUM));
                    User user = new User();
                    user.setAll(userDTONUM);
                    Queue<User> users = service.getUsersFor(user);
                    dataModel = new DataModel(user, users);
                    if(dataModel.getUsers().isEmpty()){
                        sendData(new ComunicationData("NOMOREUSERS", null));
                    } else {
                        user = dataModel.getUsers().remove();
                        dataModel.setMostrant(user);
                        UserDTO userDTOUsers = new UserDTO(user.getUsername(), user.getEdad(), user.isPremium(), user.getEmail(), user.getPassword(), user.getDescription(), user.getLang(), user.getImg());
                        sendData(new ComunicationData("SHOWUSERS", userDTOUsers));
                    }
                }
                break;
            case "REGLOG":
                //Els logins de l'usuari es registren a la BBDD
                service.newLog((UserDTO) data.getObj());
                break;
        }
    }

    /**
     * Envia la informació al client
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
     * Métode que converteix la imatge que arriba en array de byte a .png i la guarda en el servidor
     * @param img imatge en array de byte
     * @param username nom del usuari al cual correspon la imatge
     * @throws IOException si s'ha produit una excepció d'entrada o sortida
     */
    private void saveImage(byte[] img, String username) throws IOException {
        File outputfile = new File("./server_minder/src/data/usersImg/"+username+".png");
        ByteArrayInputStream bis = new ByteArrayInputStream(img);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "png", outputfile);
    }

    /**
     * Getter del DataModel del servidor dedicat
     * @see DataModel
     * @return classe DataModel
     */
    public DataModel getDataModel() {
        return dataModel;
    }

    /**
     * Envia un usuari que ha sigut actualitzat per qué també s'actualitzi en temps real al client
     * @param user usuari que s'ha d'actualitzar
     */
    public void actualitzaUsuariTempsReal(User user) {
        UserDTO userDTO = new UserDTO(user.getUsername(), user.getEdad(), user.isPremium(), user.getEmail(), user.getPassword(), user.getDescription(), user.getLang(), user.getImg());
        sendData(new ComunicationData("SHOWUSERS", userDTO));
    }

    /**
     * Getter del service
     * @return SystemService
     */
    public SystemService getService() {
        return service;
    }

    /**
     * Envia els missatge que rep en temps real al client
     * @param msg entity Message
     */
    public void sendMessage(Message msg) {
        sendData(new ComunicationData("MSG",service.getMessages(msg.getIdChat())));
    }

    /**
     * Envia el chats al client connectat
     */
    public void sendChats() {
        if (getDataModel() != null) {
            sendData(new ComunicationData("CHATS", service.chatsFor(getDataModel().getUser().getUsername())));
        }
    }
}