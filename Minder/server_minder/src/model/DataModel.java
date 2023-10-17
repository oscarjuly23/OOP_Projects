/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model;

import model.entity.User;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase que guarda la informació del usuari que esta connectat
 */

public class DataModel {
    private User user;
    private Queue<User> users;
    private User mostrant;

    /**
     * Constructor de la classe
     * @param user Usuari connectat en el DedicatedSever
     * @param users Llista dels usuaris que podrá aceptar o declinar
     */
    public DataModel(User user, Queue<User> users) {
        this.user = user;
        this.users = users;
    }

    /**
     * Getter del usuari
     * @return Usuari connectat
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter del usuari
     * @param user Usuari que s'ha connectat
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter de la llista dels usuaris que aceptarà o declinarà
     * @return Llista del usuaris que s'enviaran al client
     */
    public Queue<User> getUsers() {
        return users;
    }

    /**
     * Setter de la llista dels usuaris que aceptarà o declinarà
     * @param users Llista del usuaris que s'enviaran al client
     */
    public void setUsers(Queue<User> users) {
        this.users = users;
    }

    /**
     * Actualitza un usuari de la cua
     * @param userPerfil Usuari que s'ha d'actualitzar
     */
    public void actualitzaUsuari(User userPerfil) {
        LinkedList<User> list = new LinkedList<>();
        while(!users.isEmpty()){
            list.add(users.remove());
        }
        for(int i = 0; i< list.size() ; i++){
            if(list.get(i).getUsername().equals(userPerfil.getUsername())){
                list.set(i, userPerfil);
            }
        }
        users = list;
    }

    /**
     * Getter del usuari que el client esta visualitzant
     * @return Usuari que está veient el client
     */
    public User getMostrant(){
        return mostrant;
    }

    /**
     * Setter del usuari que el client esta visualitzant
     * @param userLike Usuari que veu el client
     */
    public void setMostrant(User userLike) {
        this.mostrant = userLike;
    }

    /**
     * Elimina un usuari de la cua
     * @param user Usuari a esborrar
     */
    public void deleteUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                users.remove(u);
            }
        }
    }
}