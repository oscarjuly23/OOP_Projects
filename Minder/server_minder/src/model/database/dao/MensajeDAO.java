/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.database.dao;

import model.entity.Message;
import model.network.ConnectorDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que fa totes les operacions corresponents per la taula Mensajes de la BBDD
 */

public class MensajeDAO {
    /**
     * Demana tots el missatges d'un chat entre dos usuaris
     * @param idChat id del chat
     * @return llista dels missatges
     */
    public List<Message> getListMsg(int idChat) {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM mensajes WHERE id_chat = " + idChat + " ORDER BY fecha ASC;";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                int id = resultat.getInt("id");
                String userFrom = resultat.getString("user_from");
                String userTo = resultat.getString("user_to");
                int chatId = resultat.getInt("id_chat");
                String mensaje = resultat.getString("mensaje");
                Timestamp timestamp = resultat.getTimestamp("fecha");
                LocalDateTime fecha = timestamp.toLocalDateTime();
                messages.add(new Message(id,userFrom,userTo,chatId,mensaje,fecha));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    /**
     * Insereix un nom missatge a la BBDD
     * @see Message
     * @param msg entity Message.
     */
    public void addMsg(Message msg) {
        LocalDateTime now = msg.getFecha();
        Timestamp timestamp = Timestamp.valueOf(now);
        String query = "INSERT INTO mensajes(user_from, user_to, id_chat, mensaje, fecha) " +
                "VALUES ('"+ msg.getUserFrom()+"', '"+ msg.getUserTo()+"', "+ msg.getIdChat()+", '"+ msg.getMensaje()+"', '"+ timestamp +"');";
        System.out.println(query);
        ConnectorDB.getInstance().insertQuery(query);
    }
}