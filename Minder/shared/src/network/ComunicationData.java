/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package network;

import java.io.Serializable;

/**
 * Classe que s'utilitza per enviar la informació amb un id i un objecte a través del sockets entre client i servidor
 */
public class ComunicationData implements Serializable{
    private String id;
    private Object obj;

    /**
     * Constructor de la classe
     * @param id id amb el que s'identificarà la comunicació
     * @param obj objecte que s'envia
     */
    public ComunicationData(String id, Object obj) {
        this.id = id;
        this.obj = obj;
    }

    /**
     * Getter del id
     * @return id de la comunicació
     */
    public String getId() {
        return id;
    }

    /**
     * Setter del id
     * @param id id de la comunicació
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter del objecte que s'envia
     * @return tipo Object generic
     */
    public Object getObj() {
        return obj;
    }

    /**
     * Setter del objecte que s'envia
     * @param obj tipo Objecte generic
     */
    public void setObj(Object obj) {
        this.obj = obj;
    }
}