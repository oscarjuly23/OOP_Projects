/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.database.dao;

import model.entity.Like;
import model.network.ConnectorDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * Clase que fa totes les operacions corresponents per la taula Likes de la BBDD
 */

public class LikeDAO {
    /**
     * Métode que insereix un nou like a la BBDD
     * @param userFrom nom del usuari que ha donat like
     * @param userTo nom del usuari que ha rebut el like
     */
    public void insertNewLike(String userFrom, String userTo) {
        String query = "INSERT INTO likes(user_from, user_to) VALUES ('"+ userFrom +"', '" + userTo + "');";
        System.out.println(query);
        ConnectorDB.getInstance().insertQuery(query);
    }

    /**
     * Métode que consulta si dos usuaris s'han fet like mutuament, per tant comprova si existeix un match
     * @param userFrom nom del usuari1
     * @param userTo nom del usuari2
     * @return retorna una entity Like
     */
    public Like comprovaMatch(String userFrom, String userTo){
        Like like = null;
        String query = "select * from likes where user_from = '" + userFrom + "' AND user_to = '" + userTo + "';";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                int id = resultat.getInt("id");
                Timestamp timestamp = resultat.getTimestamp("fecha");
                LocalDateTime date = timestamp.toLocalDateTime();
                like = new Like(id, userFrom, userTo, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return like;
    }

    /**
     * Demana el top5 usuaris que tenen més likes
     * @return llista amb els noms del 5 usuaris
     */
    public LinkedList<String> top5() {
        LinkedList<String> topUsers = new LinkedList<>();
        String query = "select count(user_to) as num, user_to as user from likes group by user_to ORDER BY num DESC LIMIT 5";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                String user = resultat.getString("user");
                topUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topUsers;
    }
}