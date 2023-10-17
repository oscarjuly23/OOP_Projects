/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.database.dao;

import model.UserDTO;
import model.network.ConnectorDB;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que fa totes les operacions corresponents per la taula Logins de la BBDD
 */

public class LoginDAO {

    /**
     * Consulta cuants inicis de sesió ha fet un usuari en total
     * @param userDTO usuari que inicia sesió
     * @return enter amb el numero de vegades que ha iniciat sesió
     */
    public int countLogins(UserDTO userDTO) {
        int countLogins = 0;
        String query = "SELECT COUNT(username) AS TOTAL FROM LOGINS WHERE username = '"+ userDTO.getUsername()+"';";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                countLogins = resultat.getInt("TOTAL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countLogins;
    }

    /**
     * Inserta un login cada vegada que un usuari inicia sesió
     * @param userDTO usuari que inicia sesió
     */
    public void newLogin(UserDTO userDTO) {
        String query = "INSERT INTO logins(username) " +
                "VALUES ('"+ userDTO.getUsername()+"');";
        System.out.println(query);
        ConnectorDB.getInstance().insertQuery(query);
    }
}