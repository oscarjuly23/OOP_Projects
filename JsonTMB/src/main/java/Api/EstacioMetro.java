package Api;

/**
 * Class ApiEstacio on guardem la informació de la api de la estació i la seva línia.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class EstacioMetro {
    /**
     * Atributs necessaris per a crear l'objecte ApiEstacio.
     */
    private String estacio;
    private String linia;

    /**
     * Method ApiEstacio() constructor del objecte.
     */
    public EstacioMetro() {
    }

    public String getEstacio() {
        return estacio;
    }
    public void setEstacio(String estacio) {
        this.estacio = estacio;
    }
    public String getLinia() {
        return linia;
    }
    public void setLinia(String linia) {
        this.linia = linia;
    }
}
