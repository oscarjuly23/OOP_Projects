/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.database.dao;

import model.ChatDTO;
import model.UserDTO;
import model.network.ConnectorDB;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Clase que fa totes les operacions corresponents per la taula Chats de la BBDD
 */

public class ChatDAO {
    /**
     * Métode que consulta a la BBDD tots els chats en curs que té un usuari
     * @param username nom del usuari
     * @return llista de ChatsDTO
     */
    public LinkedList<ChatDTO> chatsList(String username) {
        LinkedList<ChatDTO> chats = new LinkedList<>();
        String query = "(SELECT u.username, u.edat, u.esPremium, u.email, u.password, u.description, u.lang, c.id as idchat, m.id as idmatch, m.fecha FROM users U\n" +
                " LEFT JOIN matchs M on M.username1 = U.username OR M.username2 = U.username\n" +
                " LEFT JOIN chats C ON M.id = C.id_match \n" +
                " WHERE M.username2 = '"+username+"' and U.username <> '"+username+"' and m.isMatch = 1)\n" +
                "UNION \n" +
                "(SELECT u.username, u.edat, u.esPremium, u.email, u.password, u.description, u.lang, c.id as idchat, m.id as idmatch, m.fecha FROM users U\n" +
                " LEFT JOIN matchs M on M.username1 = U.username OR M.username2 = U.username\n" +
                " LEFT JOIN chats C ON M.id = C.id_match \n" +
                " WHERE M.username1 = '"+username+"' and U.username <> '"+username+"' and m.isMatch = 1)\n" +
                " ORDER BY fecha DESC";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                String nom = resultat.getString("username");
                int edad = resultat.getInt("edat");
                boolean esPremium = resultat.getBoolean("esPremium");
                String email = resultat.getString("email");
                String password = resultat.getString("password");
                String description = resultat.getString("description");
                String lang = resultat.getString("lang");
                int idChat = resultat.getInt("idchat");
                int idMatch = resultat.getInt("idmatch");
                UserDTO u = new UserDTO(nom,edad,esPremium,email,password,description, lang, null);
                u.setImg(u.convertToByteArray("./server_minder/src/data/usersImg/"+ nom +".png"));
                chats.add(new ChatDTO(u,idChat,idMatch));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chats;
    }
}