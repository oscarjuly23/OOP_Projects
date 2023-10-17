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
 * Clase Match que conté els atributs que es reben de la BBDD
 */

public class Match {
    private int id;
    private String username1;
    private String username2;
    private boolean isMatch;
    private LocalDateTime fecha;

    /**
     * Constructor de la classe Match
     * @param id identificador del match
     * @param username1 usuari1 que forma el match
     * @param username2 usuari2 que forma el match
     * @param isMatch boolea que indica si es match o s'ha desfet
     * @param fecha data que s'ha produit el match
     */
    public Match(int id, String username1, String username2, boolean isMatch, LocalDateTime fecha) {
        this.id = id;
        this.username1 = username1;
        this.username2 = username2;
        this.isMatch = isMatch;
        this.fecha = fecha;
    }

    /**
     * Getter del id del match
     * @return id del match
     */
    public int getId() {
        return id;
    }

    /**
     * Setter del id del match
     * @param id id del match
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter de la data que s'ha produit el match
     * @return data del match
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Métode toString() de la classe
     * @return string que permet visualitzar les dades
     */
    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", username1='" + username1 + '\'' +
                ", username2='" + username2 + '\'' +
                ", isMatch=" + isMatch +
                ", fecha=" + fecha +
                '}';
    }
}