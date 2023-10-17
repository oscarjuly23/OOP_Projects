/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.database.dao;

import com.mysql.jdbc.log.Log;
import model.entity.Match;
import model.network.ConnectorDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Clase que fa totes les operacions corresponents per la taula Logins de la BBDD
 */

public class MatchDAO {
    /**
     * Insereix un nou match a la BBDD
     * @param userFrom nom del usuari1
     * @param userTo nom del usuari2
     */
    public void newMatch(String userFrom, String userTo) {
        String query = "INSERT INTO matchs(username1, username2) VALUES ('"+ userFrom +"', '" + userTo + "');";
        System.out.println(query);
        ConnectorDB.getInstance().insertQuery(query);
    }

    /**
     * Consulta el número de matchs que té un total un usuari
     * @param username nom del usuari
     * @return enter amb el nombre de matchs en total
     */
    public int numMatchs(String username) {
        int count = 0;
        String query = "select count(*) as num from matchs where username1 = '"+ username + "' or username2 = '" + username +"';";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                count = resultat.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Métode que desfa un match
     * @param idChat id del chat
     */
    public void dismatch(String idChat) {
        String query = "UPDATE matchs LEFT JOIN chats c ON c.id_match = matchs.id SET matchs.isMatch = 0 WHERE c.id = '" + idChat + "';";
        System.out.println(query);
        ConnectorDB.getInstance().insertQuery(query);
    }

    public List<String[]> listMatchDay(){
        List<String[]> list = new ArrayList<>();
        String query = "Select count(*) as num, DATE_FORMAT(fecha,\"%Y-%m-%d-%H\") as data FROM matchs WHERE STR_TO_DATE(fecha,\"%Y-%m-%d\")=STR_TO_DATE(NOW(),\"%Y-%m-%d\") GROUP BY data ORDER BY data DESC";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                String[] llista = new String[2];
                llista[0] = resultat.getString("num");
                llista[1] = resultat.getString("data");
                list.add(llista);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String[]> listMatchWeek(){
        List<String[]> list = new ArrayList<>();
        String query = "Select count(*) as num, DATE_FORMAT(fecha,\"%Y-%m-%d\") as data FROM matchs WHERE WEEK(date(fecha))=WEEK(NOW()) GROUP BY data ORDER BY data DESC";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                String[] llista = new String[2];
                llista[0] = resultat.getString("num");
                llista[1] = resultat.getString("data");
                list.add(llista);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



    public List<String[]> listMatchMonth(){
        List<String[]> list = new ArrayList<>();
        String query = "Select count(*) as num, DATE_FORMAT(fecha,\"%m-%d\") as data FROM matchs WHERE DATE_FORMAT(fecha,\"%m\")=DATE_FORMAT(NOW(),\"%m\") GROUP BY data ORDER BY data DESC";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                String[] llista = new String[2];
                llista[0] = resultat.getString("num");
                llista[1] = resultat.getString("data");
                list.add(llista);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}