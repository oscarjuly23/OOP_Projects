/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.network;

import java.sql.*;

/**
 * Classe que guarda la informació llegida el json per realitzar la connexió amb la BBDD
 */

public class ConnectorDB {
    private static String dbIp;
    private static int port;
    private static String dbName;
    private static String dbUser;
    private static String dbPass;
    private static String url;
    private static Connection conn;
    private static Statement s;
    private static ConnectorDB instance;

    /**
     * Constructor de la classe
     * @param usr usuari per accedir a la bbdd
     * @param pass contraseña del usuari de la bbdd
     * @param db nom de la bbdd
     * @param ip ip de la bbdd
     * @param port port de la bbdd
     */
    public ConnectorDB(String usr, String pass, String db, String ip, int port) {
        this.dbUser = usr;
        this.dbPass = pass;
        this.dbName = db;
        this.dbIp = ip;
        this.port = port;
        this.url = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
        this.instance = null;
    }

    /**
     * Es crea una instancia de la classe per tal de que les dades siguin sempre les mateixes durant la execucció
     * @return entity ConnectorDB
     */
    public static ConnectorDB getInstance() {
        if (instance == null) {
            instance = new ConnectorDB(dbUser,dbPass,dbName,dbIp,port);
            instance.connect();
        }
        return instance;
    }

    /**
     * Métode que connecta amb la BBDD
     */
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = DriverManager.getConnection(url, dbUser, dbPass);
            if (conn != null) {
                System.out.println("Connexió a base de dades " + url + " ... Ok");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+ url);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Métode per fer insert (INSERT)
     * @param query insert que volem fer a la BBDD
     */
    public void insertQuery(String query){
        try {
            s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
        }
    }

    /**
     * Métode per fer updates (UPDATE)
     * @param query update que volem fer a la BBDD
     */
    public void updateQuery(String query){
        try {
            s = conn.createStatement();
            s.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Problema al Modificar --> " + ex.getSQLState());
        }
    }

    /**
     * Métode per eliminar una query
     * @param query delete d'una query a la BBDD
     */
    public void deleteQuery(String query){
        try {
            s = conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
        }

    }

    /**
     * Métode per fer queries (Select)
     * @param query query que volem llançar a la BBDD
     * @return resultat
     */
    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    /**
     * Métode que desconecta la connexió amb la BBDD
     */
    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
        }
    }

    /**
     * Métode toString() de la classe
     * @return string que permet visualitzar les dades
     */
    @Override
    public String toString() {
        return "ConnectorDB{" +
                "dbIp='" + dbIp + '\'' +
                ", port=" + port +
                ", dbName='" + dbName + '\'' +
                ", dbUser='" + dbUser + '\'' +
                ", dbPass='" + dbPass + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}