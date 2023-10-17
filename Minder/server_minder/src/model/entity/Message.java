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
 * Clase Message que conté els atributs que es reben de la BBDD
 */

public class Message {
    private int id;
    private String userFrom;
    private String userTo;
    private int idChat;
    private String mensaje;
    private LocalDateTime fecha;

    /**
     * Constructor de la classe Message
     * @param id identificador del missatge
     * @param userFrom usuari que ha enviat el missatge
     * @param userTo usuari que rep missatge
     * @param idChat identificador del chat
     * @param mensaje text que s'ha enviat
     * @param fecha data que s'envia el missatge
     */

    public Message(int id, String userFrom, String userTo, int idChat, String mensaje, LocalDateTime fecha) {
        this.id = id;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.idChat = idChat;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    /**
     * Getter del id del missatge
     * @return id del missatge
     */
    public int getId() {
        return id;
    }

    /**
     * Setter del id del missatge
     * @param id id del missatge
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter del usuari que ha enviat el missatge
     * @return nom del usuari
     */
    public String getUserFrom() {
        return userFrom;
    }

    /**
     * Setter del usuari que ha enviat el missatge
     * @return nom del usuari
     */
    public String getUserTo() {
        return userTo;
    }

    /**
     * Getter del id del chat
     * @return id del chat
     */
    public int getIdChat() {
        return idChat;
    }

    /**
     * Setter del id del chat
     * @param idChat id del chat
     */
    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    /**
     * Getter del missatge enviat
     * @return string amb el text del missatge
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Getter de la data que s'ha enviat el missatge
     * @return data del missatge
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
        return "Mensaje{" +
                "id=" + id +
                ", userFrom='" + userFrom + '\'' +
                ", userTo='" + userTo + '\'' +
                ", idChat=" + idChat +
                ", mensaje='" + mensaje + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}