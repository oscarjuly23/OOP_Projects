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
 * Clase Login que conté els atributs que es reben de la BBDD
 */

public class Login {
    private int id;
    private String username;
    private LocalDateTime last_date;

    /**
     * Constructor de la classe Login
     * @param id identificador del login
     * @param username usuari que ha fet login
     * @param last_date data que s'ha fet login
     */
    public Login(int id, String username, LocalDateTime last_date) {
        this.id = id;
        this.username = username;
        this.last_date = last_date;
    }

    /**
     * Getter del id del login
     * @return id del login
     */
    public int getId() {
        return id;
    }

    /**
     * Setter del id del login
     * @param id del login
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter del nom d'usuari
     * @return nom d'usuari
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter del nom d'usuari
     * @param username nom d'usuari
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Métode toString() de la classe
     * @return string que permet visualitzar les dades
     */
    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", last_date=" + last_date +
                '}';
    }
}