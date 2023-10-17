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
 * Clase Chat que conté els atributs que es reben de la BBDD
 */

public class Chat {
    private int id;
    private int idMatch;

    /**
     * Constructor de la classe Chat
     * @param id identificador del chat
     * @param idMatch identificador del match
     */
    public Chat(int id, int idMatch) {
        this.id = id;
        this.idMatch = idMatch;
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
     * Métode toString() de la classe
     * @return string que permet visualitzar les dades
     */
    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", idMatch=" + idMatch +
                '}';
    }
}