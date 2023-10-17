/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.entity;

/**
 * Model de la classe d'un chat
 */
public class Chat {

    private User otherUser;
    private int id;
    private int idMatch;

    /**
     * Constructor de la classe
     * @param otherUser Usuari del chat
     * @param id Id del chat
     * @param idMatch Id del match
     */
    public Chat(User otherUser, int id, int idMatch) {
        this.otherUser = otherUser;
        this.id = id;
        this.idMatch = idMatch;
    }

    /**
     * Getter de l'usuari
     * @return l'usuari del chat
     */
    public User getOtherUser() {
        return otherUser;
    }

    /**
     * Getter del id del chat
     * @return Id del chat
     */
    public int getId() {
        return id;
    }
}