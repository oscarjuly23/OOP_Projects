package Model.Localitzacions;

/**
 * Class Restaurant on guardem la informació de les localitzacions que són restaurants.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class Restaurant extends Location {
    /**
     * Atributs necessaris per a crear l'objecte Restaurant.
     */
    private String[] characteristics;

    /**
     * Method Restaurant() constructor del objecte.
     * @param name nom de la localització restaurant.
     * @param description descripció de la localització restaurant.
     * @param coordinates coordenades de la localització restaurant.
     * @param characteristics caracteristiques varies de la localització restaurant.
     */
    public Restaurant(String name, double[] coordinates, String description, String[] characteristics) {
        super(name, coordinates, description);
        this.characteristics = characteristics;
    }

    public String[] getCharacteristics() {
        return characteristics;
    }
}
