package Api;

/**
 * Class ApiIBus on guardem la informació de la api de la id, destinacio i parada.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class ParadaBus {
    /**
     * Atributs necessaris per a crear l'objecte ApiIBus.
     */
    private String idBus;
    private String destination;
    private String temps;
    private int parada;

    /**
     * Method ApiIbus() constructor del objecte.
     * @param idBus identificador de l'autobús
     * @param destination destinació de l'autobús
     * @param temps el tems que falta perquè arribi el bus
     * @param parada la parada on para l'autobús
     */
    public ParadaBus(String idBus, String destination, String temps, int parada) {
        this.idBus = idBus;
        this.destination = destination;
        this.temps = temps;
        this.parada = parada;
    }

    public String getIdBus() {
        return idBus;
    }
    public String getDestination() {
        return destination;
    }
    public String getTemps() {
        return temps;
    }
}