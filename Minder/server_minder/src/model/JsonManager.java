/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.network.ConnectorDB;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe encarregada de llegir les dades del arxiu json
 */
public class JsonManager {
    //Ruta del arxiu json
    private static final String FILENAME = "server_minder/src/data/config.json";

    /**
     * Constructor de la clase
     */
    public JsonManager() {}

    /**
     * Métode que llegeix les dades del JSON
     * @return clase Object generic.
     * @throws FileException En cas de que no trobi el fitxer
     */
    public static Object llegirJson() throws FileException {
        //Entitat Gson
        Gson gson = new Gson();
        //Reader de JSON
        JsonReader reader;
        try {
            File file = new File(FILENAME);
            System.out.println(file.getAbsolutePath());
            //Provem de llegir
            reader = new JsonReader(new FileReader(file));
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(reader);
        } catch (IOException e) {
            throw new FileException("No s'ha trobat el fitxer" + FILENAME + " config.json amb la connexió a la BBDD");
        }
    }

    /**
     * Método que seteja les configuracions de la BBDD a la clase ConnectorDB
     * @see ConnectorDB
     * @return classe ConnectorDB amb els atributs ja setejats
     * @throws FileException En cas de que no trobi el fitxer
     */

    public ConnectorDB setValuesDB() throws FileException {
        //Dades a carregar
        ConnectorDB data;
        JsonObject jsonObject = (JsonObject) llegirJson();
        String dbUser = jsonObject.get("dbUser").getAsString();
        String dbPass = jsonObject.get("dbPass").getAsString();
        String dbName = jsonObject.get("dbName").getAsString();
        String dbIp = jsonObject.get("dbIp").getAsString();
        int port = jsonObject.get("dbPort").getAsInt();
        //Instanciem la classe ConnectorDB amb el valors corresponents
        data = new ConnectorDB(dbUser,dbPass,dbName,dbIp,port);
        return data;
    }

    /**
     * Métode retorna el port pel cual el servidor rebrà les peticions
     * @return Port d'escolta del servidor
     * @throws FileException En cas de que no trobi el fitxer
     */
    public int returnPort() throws FileException {
        int port;
        JsonObject jsonObject = (JsonObject) llegirJson();
        port = jsonObject.get("port").getAsInt();
        return port;
    }
}