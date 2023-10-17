/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.database;

import model.ChatDTO;
import model.MessageDTO;
import model.UserDTO;
import model.database.dao.*;
import model.entity.Message;
import model.entity.User;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * Classe encarregada de gestionar les peticions del servidor cap a la bbdd
 */

public class SystemService {
    private ChatDAO chatDAO;
    private LikeDAO likeDAO;
    private LoginDAO loginDAO;
    private MatchDAO matchDAO;
    private MensajeDAO mensajeDAO;
    private UserDAO userDAO;

    /**
     * Constructor de la classe
     */
    public SystemService() {
        chatDAO = new ChatDAO();
        likeDAO = new LikeDAO();
        loginDAO = new LoginDAO();
        matchDAO = new MatchDAO();
        mensajeDAO = new MensajeDAO();
        userDAO = new UserDAO();
    }

    /**
     * Demana tots el usuaris registrats a la BBDD
     * @return llista de tots els usuaris de la bbdd
     */
    public LinkedList<User> getUsers() {
        LinkedList<User> users = userDAO.getAllUsers();
        return users;
    }

    /**
     * Métode que s'encarrega d'actualitzar la descricpió i llenguatge del usuari
     * @param description descrpició del usuari
     * @param lang llenguatge del usuari (Java o C)
     * @param nom nom del usuari a actualitzar
     */
    public void addDescriptionLanguage(String description, String lang, String nom){
        userDAO.addDescriptionLang(description,lang, nom);
    }

    /**
     * Comprova si les creedencials per iniciar sesió d'un usuari son correctes
     * @param username nom o email del usuari
     * @param password contraseña del usuari
     * @return User en cas de que les creedencials siguien correctes
     */
    public User logUser(String username, String password) {
        User user = null;
        for (User u : userDAO.getAllUsers()) {
            if ((u.getEmail().equals(username) || u.getUsername().equals(username) ) && u.getPassword().equals(password)) {
                user = u;
                break;
            }
        }
        return user;
    }

    /**
     * Métode per registrar un nou usuari
     * @param u User que conté tots els atributs necesaris per a fer el regisre
     * @return sino existeix l'usuari ja a la BBDD i es pot insertar correctament retorna True, sino False
     */
    public boolean regUser(User u) {
        boolean userExists = false;
        boolean userReg = false;
        for (User user : userDAO.getAllUsers()) {
            if (u.getUsername().equals(user.getUsername()) || u.getEmail().equals(user.getEmail())) {
                userExists = true;
                break;
            }
        }
        if(!userExists) {
            userDAO.addUser(u);
            userReg = true;
        }
        return userReg;
    }

    /**
     * Insereix els likes d'un usuari a un altre
     * @param userFrom nom del usuari que ha fet el like
     * @param userTo nom del usuari al que han fet like
     */
    public void newLike(String userFrom, String userTo){
        likeDAO.insertNewLike(userFrom,userTo);
    }

    /**
     * Comprovar si es la primera vegada que l'usuari inicia sesió
     * @param u usuari
     * @return True si es la primera vegada.
     *         False si ja havia entrat anteriorment.
     */
    public boolean firstLog(UserDTO u) {
        if (loginDAO.countLogins(u) >= 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Demana els usuaris que es mostrarant al client.
     * @param user usuari que solicitat la llista
     * @return retorna una cua amb el usuaris els cuals podrá fer like o dislike
     */
    public Queue<User> getUsersFor(User user){
        //Si el user es premium es posen els que ja havien donat like al principi de la cua
        if(user.isPremium()){
            LinkedList<User> liked = userDAO.getUsersPremiumFor(user);
            Queue<User> ordenats = new LinkedList<>();
            for(User us: liked) {
                if (!comprovaMatch(us.getUsername(),user.getUsername(),false)) {
                    ordenats.add(us);
                }
            }
            LinkedList<User> tots = userDAO.getUsersFor(user);
            for (User u: tots){
                boolean trobat = false;
                for(User userL: liked){
                    if(u.getUsername().equals(userL.getUsername())){
                        trobat = true;
                    }
                }
                if(!trobat){
                    ordenats.add(u);
                }
            }
            return ordenats;
        } else{
            return userDAO.getUsersFor(user);
        }
    }

    /**
     * Comprova si existeix un match per a dos usuaris
     * @param userFrom nom del usuari1
     * @param userTo nom del usuari2
     * @param create si es true consulta si existeix el match i sino el crea. Si es false unicament consulta si existeix el match
     * @return True si existeix el match, false sino
     */
    public boolean comprovaMatch(String userFrom, String userTo, boolean create){
        if (likeDAO.comprovaMatch(userTo, userFrom) == (null)){
            return false;
        } else {
            if (create) {
                matchDAO.newMatch(userFrom, userTo);
            }
            return true;
        }
    }

    /**
     * Registra un nou login cada vegada que un usuari inicia sesió
     * @param u usuari que ha iniciat sesió
     */
    public void newLog(UserDTO u) {
        loginDAO.newLogin(u);
    }

    /**
     * Demana el top5 usuaris amb més likes
     * @return llista amb el nom del usuaris
     */
    public LinkedList<String> top5UsersLiked() {
        return likeDAO.top5();
    }

    /**
     * Demana el número de matchs que tenen els usuaris que estan en el top5 amb mes likes
     * @see #top5UsersNumMatchs()
     * @return array d'enters amb el numero de matchs per cada usuari
     */
    public int[] top5UsersNumMatchs() {
        int[] nums = new int[5];
        LinkedList<String> users = top5UsersLiked();
        for (int i = 0; i < users.size(); i++) {
            nums[i] = matchDAO.numMatchs(users.get(i));
        }
        return nums;
    }

    /**
     * Demana la llista de chats en els que està un usuari
     * @param username nom del usuari
     * @return llista de chats
     */
    public LinkedList<ChatDTO> chatsFor(String username) {
        LinkedList<ChatDTO> chats = chatDAO.chatsList(username);
        return chats;
    }

    /**
     * Demana la llista de missatges per un chat
     * @param idChat id del chat
     * @return llista del missatges per el chat
     */
    public List<MessageDTO> getMessages(int idChat) {
        List<Message> msgs = mensajeDAO.getListMsg(idChat);
        List<MessageDTO> messages = new ArrayList<>();
        for (Message m : msgs) {
            messages.add(new MessageDTO(m.getId(),m.getUserFrom(),m.getUserTo(),m.getIdChat(),m.getMensaje(),m.getFecha()));
        }
        return messages;
    }

    /**
     * Inserta el missatge a la BBDD
     * @param msg missatge a inserir
     */
    public void addMessage(Message msg) {
        mensajeDAO.addMsg(msg);
    }

    /**
     * Métode que s'encarrega de desfer un match
     * @param idChat id del chat
     */
    public void removeMatch(String idChat) {
        matchDAO.dismatch(idChat);
    }

    public String[] groupMatchByDay() {
        List<String[]> matchList = matchDAO.listMatchDay();

        String[] counts = new String[24];

        for (int i = 0; i < counts.length; i++) {
            counts[i] = "0";
        }

        for (int i = 0; i < matchList.size(); i++) {
            String data = matchList.get(i)[1];
            String[] hour = data.split("-");
            int hora = Integer.parseInt(hour[3]);

            counts[hora] = matchList.get(i)[0];
        }

        return counts;
    }

    public String[] groupMatchByWeek() {
        List<String[]> matchList = matchDAO.listMatchWeek();

        Calendar c = Calendar.getInstance();
        int avui = c.get(Calendar.DATE);
        int mes = c.get(Calendar.MONTH);
        int any = c.get(Calendar.YEAR);

        int date = diaSemana(avui, mes, any);
        String[] counts = new String[7];
        int[] dias = new int[7];
        dias[date-1] = avui;

        int j=0;
        for (int i = date; i >= 0; i--) {
            dias[i] = avui-j;
            j++;
        }

        for (int i = 0; i < counts.length; i++) {
            counts[i] = "0";
        }

        for (int i = 0; i < matchList.size(); i++) {
            String data = matchList.get(i)[1];
            String[] dia = data.split("-");
            int day = Integer.parseInt(dia[2]);

            for (int k = 0; k < dias.length; k++) {
                if (dias[k] == day) {
                    counts[k] = matchList.get(i)[0];
                }
            }
        }
        return counts;
    }

    public String[] groupMatchByMonth() {
        List<String[]> matchList = matchDAO.listMatchMonth();

        String[] counts = new String[31];

        for (int i = 0; i < counts.length; i++) {
            counts[i] = "0";
        }

        for (int i = 0; i < matchList.size(); i++) {
            String data = matchList.get(i)[1];
            String[] dia = data.split("-");
            int day = Integer.parseInt(dia[1]);

            counts[day] = matchList.get(i)[0];
        }

        return counts;
    }

    public int diaSemana (int dia, int mes, int ano) {
        String letraD="";
        /*Calendar c = Calendar.getInstance();
        c.set(ano, mes, dia, 0, 0, 0);
        nD=c.get(Calendar.DAY_OF_WEEK);*/
        TimeZone timezone = TimeZone.getDefault();
        Calendar calendar = new GregorianCalendar(timezone);
        calendar.set(ano, mes-1, dia);
        int nD=calendar.get(Calendar.DAY_OF_WEEK);
        return nD;
    }
}