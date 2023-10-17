package Api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class LecturaApi on fem la comunicació amb la API de TMB
 * @author Josep Montava
 * @author Oscar Julian
 */
public class LecturaApi {
    /**
     * Method llegirApiIbus() llegim la informació que tenim a la Api sobre una parada amb concret que ens pasa l'usuari.
     * @return ens retorna una ArrayList amb totes les linies de bus que passen per aquella parada en concret.
     * @param parada la parada que ens passa l'usuari
     */
    public static ArrayList<ParadaBus> llegirApiIbus(int parada) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.tmb.cat/v1/ibus/stops/"+parada+"?app_id=1599de04&app_key=87bd2cf1c5052d12ae444221153fa2cf").build();
        Response responses;
        try {
            responses = client.newCall(request).execute();

            if (!responses.isSuccessful()) {
               throw new IOException("Error. No hem pogut llegir la API.");
            }
            String jsonData = Objects.requireNonNull(responses.body()).string();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
            System.out.println("");
            ArrayList<ParadaBus> buslist = new ArrayList<>();
            JsonArray liniesBus = (jsonObject.get("data").getAsJsonObject()).get("ibus").getAsJsonArray();
            if(liniesBus.size()!=0) {
                for (int i = 0; i < liniesBus.size(); i++) {
                    String linia = (liniesBus.get(i).getAsJsonObject()).get("line").getAsString();
                    String destination = (liniesBus.get(i).getAsJsonObject()).get("destination").getAsString();
                    String temps = (liniesBus.get(i).getAsJsonObject()).get("text-ca").getAsString();
                    buslist.add(new ParadaBus(linia, destination, temps, parada));
                }
                return buslist;
            } else {
                return null;
            }
        } catch (
                IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method llegirApiTransitBus() llegim la Api Transit en concret els autobusos, on agafem informació sobre les diferents parades, en concret: coorenades, codi de la parada i nom de la parada
     * @return ens retorna una ArrayList de ApiTransit amb totes les parades que n'hi han.
     */
    public static ArrayList <ApiTransit> llegirApiTransitBus(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.tmb.cat/v1/transit/parades/?app_id=1599de04&app_key=87bd2cf1c5052d12ae444221153fa2cf").build();
        Response responses;
        try {
            responses = client.newCall(request).execute();
            if (!responses.isSuccessful()) {
                throw new IOException("Error..");
            }
            String jsonData = responses.body().string();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
            System.out.println("");
            ArrayList<ApiTransit> estacionsBusList = new ArrayList<>();

            JsonArray featuresLiniesBus = (jsonObject.get("features").getAsJsonArray());

            if(featuresLiniesBus.size()!=0) {
                for (int i = 0; i < featuresLiniesBus.size(); i++) {
                    double[] coordinates= new double[2];
                    for (int j =0;j<2;j++) {
                        coordinates[j] = featuresLiniesBus.get(i).getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(j).getAsDouble();
                    }
                    String codiParada = (featuresLiniesBus.get(i).getAsJsonObject().get("properties").getAsJsonObject().get("CODI_PARADA").getAsString());
                    String nomParada = (featuresLiniesBus.get(i).getAsJsonObject().get("properties").getAsJsonObject().get("NOM_PARADA").getAsString());
                    String tipus = "BUS";
                    estacionsBusList.add(new ApiTransit(coordinates,codiParada,nomParada,tipus, -1));
                }
                return estacionsBusList;
            } else {
                return null;
            }
        } catch (
                IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method llegirApiTransitMetro() llegim la Api Transit en concret els metros, on agafem informació sobre les diferents estacions, en concret: coorenades, codi de la parada i nom de la estació
     * @return ens retorna una ArrayList de ApiTransit amb totes les estacions que n'hi han.
     */
    public static ArrayList <ApiTransit> llegirApiTransitMetro() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.tmb.cat/v1/transit/estacions/?app_id=1599de04&app_key=87bd2cf1c5052d12ae444221153fa2cf").build();
        Response responses;
        try {
            responses = client.newCall(request).execute();
            if (!responses.isSuccessful()) {
                throw new IOException("Error..");
            }
            String jsonData = responses.body().string();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonData, JsonObject.class);
            System.out.println("");
            ArrayList<ApiTransit> estacionsMetroList = new ArrayList<>();

            JsonArray featuresLiniesMetro = (jsonObject.get("features").getAsJsonArray());

            if (featuresLiniesMetro.size()!=0) {
                for (int i = 0; i < featuresLiniesMetro.size(); i++) {
                    double[] coordinates= new double[2];
                    for (int j =0;j<2;j++) {
                        coordinates[j] = featuresLiniesMetro.get(i).getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray().get(j).getAsDouble();
                    }
                    String codiParada = (featuresLiniesMetro.get(i).getAsJsonObject().get("properties").getAsJsonObject().get("PICTO").getAsString());
                    String nomParada = (featuresLiniesMetro.get(i).getAsJsonObject().get("properties").getAsJsonObject().get("NOM_ESTACIO").getAsString());
                    String tipus = "METRO";
                    estacionsMetroList.add(new ApiTransit(coordinates,codiParada,nomParada,tipus, -1));
                }
                return estacionsMetroList;
            } else {
                return null;
            }
        } catch (
                IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method llegirApiPlanner() comunica amb l'apartat de la api per planejar una ruta.
     * @return ruta retorna el String sencer que retorna la Api amb molta informació.
     * @throws IOException necessaria per fer el call.execute().
     * @param origen li passem l'origen que ha introduit el usuari, en format de coordenades.
     * @param desti li passem el destí que ha introduit el usuari, en format de coordenades.
     * @param data li passem la data que ha introduit el usuari, en el seu format correcte.
     * @param hora li passem la hora que ha introduit el usuari, en el seu format correcte.
     * @param sa li passem true o false segons sigui de sortida o arribada.
     * @param dist li passem un int de distància màxima que caminar.
     */
    public static String llegirApiPlanner(String origen, String desti, String data, String hora, String sa, int dist) throws IOException {
        String ruta = "";
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.tmb.cat/v1/planner/plan?&app_id=1599de04&app_key=87bd2cf1c5052d12ae444221153fa2cf&fromPlace="+origen+"&toPlace="+desti+"&date="+data+"&time="+hora+"&arriveBy="+sa+"&mode=TRANSIT,WALK&maxWalkDistance="+dist;
        Request request = new Request.Builder()
                .url(url).build();
        //System.out.println(url);
        Call call = client.newCall(request);
        Response responses = call.execute();
        if (responses.isSuccessful()) {
            ruta  = responses.body().string();
        }
        //System.out.println(ruta);
        return ruta;
    }

    /**
     * Method paradaInaugurada() comunica amb l'apartat de estacions de metro.
     * @return parada retorna la informació que ens dona la Api sobre la parada.
     * @throws IOException necessaria per fer el call.execute().
     */
    public static String paradaInaugurada() throws IOException {
        String parada = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.tmb.cat/v1/transit/linies/metro/estacions?app_id=1599de04&app_key=87bd2cf1c5052d12ae444221153fa2cf").build();
        Call call = client.newCall(request);
        Response responses = call.execute();
        if (responses.isSuccessful()) {
            parada = responses.body().string();
        } else {
                return null;
        }
        return parada;
    }
}