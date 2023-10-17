/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model;

import java.io.Serializable;

/***
 * Classe ChatDTO (Data Transfer Object) per enviar a través dels socket entre client-servidor
 */

public class ChatDTO implements Serializable {
    private UserDTO otherUser;
    private int id;
    private int idMatch;

    /**
     * Constructor de la clase
     * @see UserDTO
     * @param otherUser usuari amb el que s'esta chetejant
     * @param id identificador del chat
     * @param idMatch identificador del match
     */
    public ChatDTO(UserDTO otherUser, int id, int idMatch) {
        this.otherUser = otherUser;
        this.id = id;
        this.idMatch = idMatch;
    }

    /**
     * Getter del usuari
     * @return UserDTO
     */
    public UserDTO getOtherUser() {
        return otherUser;
    }

    /**
     * Setter del usuari
     * @param otherUser UserDTO
     */
    public void setOtherUser(UserDTO otherUser) {
        this.otherUser = otherUser;
    }

    /**
     * Getter del id del chat
     * @return id del chat
     */
    public int getId() {
        return id;
    }

    /**
     * Setter del id del chat
     * @param id id del chat
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter del id del match
     * @return id del match
     */
    public int getIdMatch() {
        return idMatch;
    }

    /**
     * Setter del id del match
     * @param idMatch id del match
     */
    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }
}