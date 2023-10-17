package Model.Localitzacions;

import java.util.Date;

/**
 * Class LocalitzacioPreferida on guardem la informació de les localitzacions preferides.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class LocalitzacioPreferida extends Location {

    /**
     * Method LocalitzacioPreferida() constructor del objecte.
     * @param data data de la localitzacio preferida guardada.
     * @param tipus tipus de localització. (casa, oficina...)
     * @param location objecte location. (nom, coordenades i descripció)
     */
    public LocalitzacioPreferida(Date data, String tipus, Location location) {
        super(location.getName(), location.getCoordinates(),location.getDescription());
    }
}
