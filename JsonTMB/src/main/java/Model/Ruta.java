package Model;

/**
 * Class Ruta on guardem la informació de les rutes del Usuari.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class Ruta {

    private String origen;
    private String desti;
    private String origenF;
    private String destiF;
    private String data;
    private String hora;
    private char sortidaArribada;
    private int dist;

    /**
     * Method Ruta() constructor del objecte.
     * @param origen nom de la localització origen.
     * @param desti nom de la localització destí.
     * @param origenF coordenades de la localització origen.
     * @param destiF coordenades de la localització destí.
     * @param data data en la que obtenir fer la ruta.
     * @param hora en la que volem obtenir la ruta.
     * @param sortidaArribada ruta de sortida o de arribada.
     * @param dist distància màxima a caminar en la ruta.
     */
    public Ruta(String origen, String desti, String origenF, String destiF, String data, String hora, char sortidaArribada, int dist) {
        this.origen = origen;
        this.desti = desti;
        this.origenF = origenF;
        this.destiF = destiF;
        this.data = data;
        this.hora = hora;
        this.sortidaArribada = sortidaArribada;
        this.dist = dist;
    }

    public String getOrigen() {
        return origen;
    }
    public String getDesti() {
        return desti;
    }
    public String getData() {
        return data;
    }
    public String getHora() {
        return hora;
    }
    public char getSortidaArribada() {
        return sortidaArribada;
    }
    public int getDist() {
        return dist;
    }
    public String getOrigenF() {
        return origenF;
    }
    public String getDestiF() {
        return destiF;
    }
}
