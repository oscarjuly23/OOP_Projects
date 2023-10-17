/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.entity;

import java.time.LocalDateTime;

/**
 * Clase Like que conté els atributs que es reben de la BBDD
 */

public class Like {
    private int id;
    private String userFrom;
    private String userTo;
    private LocalDateTime fecha;

    /**
     * Constructor de la classe Like
     * @param id identificador del like
     * @param userFrom usuari que ha fet like
     * @param userTo usuari que rep el like
     * @param fecha data que s'ha fet el like
     */
    public Like(int id, String userFrom, String userTo, LocalDateTime fecha) {
        this.id = id;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.fecha = fecha;
    }

    /**
     * Getter del id del like
     * @return id del like
     */
    public int getId() {
        return id;
    }

    /**
     * Setter del id del like
     * @param id id del like
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
        return "Like{" +
                "id=" + id +
                ", userFrom='" + userFrom + '\'' +
                ", userTo='" + userTo + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}