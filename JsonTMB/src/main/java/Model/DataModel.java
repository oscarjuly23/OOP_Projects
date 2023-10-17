package Model;

import Model.Localitzacions.Hotel;
import Model.Localitzacions.Location;
import Model.Localitzacions.Monument;
import Model.Localitzacions.Restaurant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.util.LinkedList;

/**
 * Class DataModel serveix per agafar la informació del JSON.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class DataModel {
    private LinkedList<Location> locations;

    public DataModel() {
        locations= new LinkedList<>();
        importacio();
    }

    public LinkedList<Location> getLocations() {
        return locations;
    }

    /**
     * Method importacio() importem el contingut del Json "localitzacions.json"
     */
    private void importacio() {

        Gson gson = new Gson();

        try {
            FileReader reader = new FileReader("localitzacions.json");
            JsonObject jsonObject = gson.fromJson(reader,JsonObject.class);

            JsonArray jsonArray  = jsonObject.getAsJsonArray("locations");

            for (int i=0;i<jsonArray.size();i++){
                double[] coordinatesAux = new double[2];
                String nameAux;
                String decriptionAux;
                JsonArray coordinates;

                JsonObject location = jsonArray.get(i).getAsJsonObject();
                nameAux = location.get("name").getAsString();

                coordinates = location.get("coordinates").getAsJsonArray();
                coordinatesAux[0] = coordinates.get(0).getAsDouble();
                coordinatesAux[1] = coordinates.get(1).getAsDouble();

                decriptionAux = location.get("description").getAsString();

                if (location.has("inauguration")){
                    int inaugurationAux;
                    String architectAux;
                    inaugurationAux = location.get("inauguration").getAsInt();
                    architectAux = location.get("architect").getAsString();

                    Location monument = new Monument(nameAux,coordinatesAux,decriptionAux,inaugurationAux,architectAux);
                    locations.add(monument);
                }  else if(location.has("stars")){
                    int starsAux;
                    starsAux=  location.get("stars").getAsInt();
                    Location hotel = new Hotel(nameAux,coordinatesAux,decriptionAux,starsAux);
                    locations.add(hotel);
                } else if(location.has("characteristics")){
                    String[] characteristicsAux = new String[location.get("characteristics").getAsJsonArray().size()];
                    JsonArray characteristics;
                    for(int j=0;j<location.get("characteristics").getAsJsonArray().size();j++){
                        characteristics= location.get("characteristics").getAsJsonArray();
                        characteristicsAux[j]=characteristics.get(j).getAsString();
                    }
                    Location restaurant = new Restaurant(nameAux,coordinatesAux,decriptionAux,characteristicsAux);
                    locations.add(restaurant);
                } else {
                    Location location1 = new Location(nameAux,coordinatesAux,decriptionAux);
                    locations.add(location1);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}