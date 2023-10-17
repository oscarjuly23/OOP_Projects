package Model.Localitzacions;

/**
 * Class Hotel on guardem la informació de les localitzacions que són Hotels.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class Hotel extends Location {

    /**
     * Method Hotel() constructor del objecte.
     * @param name nom de la localització hotel.
     * @param description descripció de la localització hotel.
     * @param coordinates coordenades de la localització hotel.
     * @param stars estrellas de la localització hotel.
     */
    public Hotel(String name, double[] coordinates, String description, int stars) {
        super(name, coordinates, description);
    }
}