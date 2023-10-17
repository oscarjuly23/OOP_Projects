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
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Classe que llegeix el fitxer config.json
 */
public class LlegirJsonConfig {

    private static final String FILENAME = "client_minder/src/data/config.json";

    /**
     * Constructor de la classe
     */
    public LlegirJsonConfig() {}

    /**
     * Retorna la informació de llegir el Json
     * @return Classe Json amb la informació omplerta
     */
    public ClasseJson obtenirConfig() {

        ClasseJson data;      // Dades a carregar
        Gson gson = new Gson();  // Entitat Gson
        JsonReader reader;       // Reader de JSON
        try {
            reader = new JsonReader(new FileReader(FILENAME)); //provem de llegir
            data = gson.fromJson(reader, ClasseJson.class);
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}