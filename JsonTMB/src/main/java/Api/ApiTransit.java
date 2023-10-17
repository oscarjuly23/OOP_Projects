package Api;

import org.jetbrains.annotations.NotNull;

/**
 * Class ApiTransit on guardem la informació de la api sobre les parades, coordenades, distancies...
 * @author Josep Montava
 * @author Oscar Julian
 */
public class ApiTransit implements Comparable {

    /**
     * Atributs necessaris per a crear l'objecte ApiTransit.
     */
    private double[] coordinates;
    private String codiParada;
    private String name;
    private String tipus;
    private int distancia;

    /**
     * Method ApiTransit() constructor del objecte.
     * @param coordinates coordenades de la estació/parada.
     * @param codiParada codi de la parada
     * @param name nom de la parada
     * @param tipus tipus BUS / METRO
     * @param distancia distancia que hi ha entre la parada preferida i la estació/parada
     */
    public ApiTransit(double[] coordinates, String codiParada, String name, String tipus, int distancia) {
        this.coordinates = coordinates;
        this.codiParada = codiParada;
        this.name = name;
        this.tipus = tipus;
        this.distancia = distancia;
    }

    /**
     * Funció amb la que comparem les distàncies per poder ordenar-les  a l'opció 1.d.
     * @param o Passem una estació/parada
     * @return Retornem la distancia que hi ha.
     */
    @Override
    public int compareTo(@NotNull Object o) {
        int compareDistance = ((ApiTransit) o).getDistancia();
        /* For Ascending order*/
        return this.distancia - compareDistance;
    }

    public double[] getCoordinates() {
        return coordinates;
    }
    public String getCodiParada() {
        return codiParada;
    }
    public String getName() {
        return name;
    }
    public String getTipus() {
        return tipus;
    }
    public int getDistancia() {
        return distancia;
    }
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}