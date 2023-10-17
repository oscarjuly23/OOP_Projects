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
 * Model de la classe d'un missatge
 */
public class Message {
        private int id;
        private String userFrom;
        private String userTo;
        private int idChat;
        private String mensaje;
        private LocalDateTime fecha;

    /**
     * Constructor de la classe
     * @param id Id del missatge
     * @param userFrom Usuari que envia el missatge
     * @param userTo Usuari al qui va destinat el missatge
     * @param idChat Id del chat
     * @param mensaje Text a enviar
     * @param fecha Data del missatge
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
     * Getter de l'usuari al qui envia el missatge
     * @return usuari que envia el missatge
     */
    public String getUserFrom() {
            return userFrom;
    }

    /**
     * Getter del text del missatge
     * @return Text del missatge
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Passa el missatge a format Json Array
     * @return Missatge en format Json Array
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