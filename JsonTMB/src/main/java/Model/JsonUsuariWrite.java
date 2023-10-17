package Model;

import Model.Localitzacions.LocalitzacioPreferida;
import Model.Localitzacions.Location;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class JsonUsuariWrite on escribim la informació del Usuari  en un Json.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class JsonUsuariWrite {
    /**
     * Atributs necessaris per a crear l'objecte usuari.
     */
    private Usuari user;
    public ArrayList<Location> localitzacionsUsuari;
    public ArrayList<LocalitzacioPreferida> localitzacionsPreferidas;

    public void writeUser(Usuari user) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("Nom", user.getNom());
        obj.put("Email", user.getEmail());
        obj.put ("Any de naixement",user.getAnyNaixement());

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter("user.json")) {
            file.write(obj.toJSONString());
        }
    }
    public void writeLocations (ArrayList<Location> localitzacionsUsuari,ArrayList<LocalitzacioPreferida> localitzacionsPreferidas) {
    }
}