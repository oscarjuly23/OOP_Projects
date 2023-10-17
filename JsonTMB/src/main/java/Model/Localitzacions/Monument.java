package Model.Localitzacions;

/**
 * Class Monument on guardem la informació de les localitzacions que són monuments.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class Monument extends Location {
    /**
     * Atributs necessaris per a crear l'objecte Monument.
     */
    private int inauguration;
    private String architect;

    /**
     * Method Monument() constructor del objecte.
     * @param name nom de la localització monument.
     * @param description descripció de la localització monument.
     * @param coordinates coordenades de la localització monument.
     * @param inauguration any de inauguració.
     * @param architect arquitecte que l'ha dissenyat.
     */
    public Monument(String name, double[] coordinates, String description, int inauguration, String architect) {
        super(name, coordinates, description);
        this.inauguration = inauguration;
        this.architect = architect;
    }

    public int getInauguration() {
        return inauguration;
    }
    public String getArchitect() {
        return architect;
    }
}