/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model;

/**
 * Classe que es guarda la informació del json
 */
public class ClasseJson {

    private int port;
    private String ip;

    /**
     * Constructor de la classe
     * @param port Port del socket
     * @param ip Ip del socket
     */
    public ClasseJson(int port, String ip) {
        this.port = port;
        this.ip = ip;

    }

    /**
     * Getter del port
     * @return Port del socket
     */
    public int getPort() {
        return port;
    }

    /**
     * Getter de la Ip
     * @return Ip del socket
     */
    public String getIp(){
        return ip;
    }
}