/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.database.dao;

import model.entity.User;
import model.network.ConnectorDB;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Clase que fa totes les operacions corresponents per la taula Users de la BBDD
 */

public class UserDAO {
    /**
     * Insereix un nou usuari a la BBDD
     * @see User
     * @param user entity User
     */
    public void addUser(User user) {
        String query = "INSERT INTO users(username, edat, esPremium, email, password) " +
                "VALUES ('"+ user.getUsername()+"', '"+ user.getEdad()+"', "+ user.isPremium()+", '"+ user.getEmail()+"','"+ user.getPassword()+"');";
        System.out.println(query);
        ConnectorDB.getInstance().insertQuery(query);
    }

    /**
     * Actualitza la descripció i el llenguatge d'un usuari
     * @param description descripció
     * @param lang llenguatge
     * @param name nom del usuari
     */
    public void addDescriptionLang(String description, String lang, String name){
        String query = "UPDATE users SET description='"+description+"',lang='"+lang+"' WHERE username='" + name+ "';";
        System.out.println(query);
        ConnectorDB.getInstance().updateQuery(query);
    }

    /**
     * Demana una llista de tots els usuaris registrats a la BBDD
     * @return llista dels usuaris
     */
    public LinkedList<User> getAllUsers() {
        LinkedList<User> users = new LinkedList<>();
        String query = "SELECT * FROM USERS;";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                String username = resultat.getString("username");
                int edad = resultat.getInt("edat");
                boolean esPremium = resultat.getBoolean("esPremium");
                String email = resultat.getString("email");
                String password = resultat.getString("password");
                String description = resultat.getString("description");
                String lang = resultat.getString("lang");
                // img = resultat.getString("img");
                users.add(new User(username,edad,esPremium,email,password,description,lang,null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Retorna la llista del usuaris als que encara no ha fet like i que tenen el mateix llenguatge que el usuari pasat per parametres
     * @param user entity User
     * @return llista dels users
     */
    public LinkedList<User> getUsersFor(User user) {
        LinkedList<User> users = new LinkedList<>();
        String query = "select * from users u where u.username NOT IN (select user_to from likes where user_from = '"+ user.getUsername()+"') and username <> '"+user.getUsername()+"' and u.lang = '" + user.getLang() +"';";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                String username = resultat.getString("username");
                int edad = resultat.getInt("edat");
                boolean esPremium = resultat.getBoolean("esPremium");
                String email = resultat.getString("email");
                String password = resultat.getString("password");
                String description = resultat.getString("description");
                String lang = resultat.getString("lang");
                // img = resultat.getString("img");
                users.add(new User(username,edad,esPremium,email,password,description,lang, user.convertToByteArray("./server_minder/src/data/usersImg/"+ username +".png")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Retorna una llista on apareixen el usuaris que han fet like al usuari pasat per parametres
     * @param user entity User
     * @return llista dels usuaris
     */
    public LinkedList<User> getUsersPremiumFor(User user) {
        LinkedList<User> users = new LinkedList<>();
        String query = "select * from users u where u.username in (select user_from from likes where user_to = '"+user.getUsername()+"');";
        System.out.println(query);
        ResultSet resultat = ConnectorDB.getInstance().selectQuery(query);
        try {
            while(resultat.next()) {
                String username = resultat.getString("username");
                int edad = resultat.getInt("edat");
                boolean esPremium = resultat.getBoolean("esPremium");
                String email = resultat.getString("email");
                String password = resultat.getString("password");
                String description = resultat.getString("description");
                String lang = resultat.getString("lang");
                users.add(new User(username,edad,esPremium,email,password,description,lang,user.convertToByteArray("./server_minder/src/data/usersImg/"+ username +".png")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}