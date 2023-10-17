package Model.Localitzacions;

/**
 * Class Location on guardem la informació de les localitzacions.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class Location {
    /**
     * Atributs necessaris per a crear l'objecte Location.
     */
    private String name;
    private double[] coordinates;
    private String description;

    /**
     * Method Location() constructor del objecte.
     * @param name nom de la localització.
     * @param description descripció de la localització.
     * @param coordinates coordenades de la localització.
     */
    public Location(String name, double[] coordinates, String description) {
        this.name = name;
        this.coordinates = coordinates;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public double[] getCoordinates() {
        return coordinates;
    }
    public String getDescription() {
        return description;
    }
}
