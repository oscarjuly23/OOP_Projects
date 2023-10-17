package Model;

import Model.Localitzacions.LocalitzacioPreferida;
import Model.Localitzacions.Location;

import java.util.ArrayList;

/**
 * Class JsonUsuariWrite on guardem la informació del Usuari.
 * @author Josep Montava
 * @author Oscar Julian
 */
public class Usuari {
    /**
     * Atributs necessaris per a crear l'objecte usuari.
     */
    private String nom;
    private String email;
    private int anyNaixement;
    private ArrayList<Location> localitzacionsUsuari;
    public ArrayList<LocalitzacioPreferida> localitzacionsPreferidas;
    public ArrayList<Location> localitzacionsBuscades;
    private ArrayList<Ruta> rutaUsuari;
    public ArrayList<String> paradesPreferides;

    /**
     * Method Usuari() constructor del objecte.
     * @param nom nom de usuari que ha introduit l'usuari.
     * @param email email vàlid que ha introduit l'usuari.
     * @param anyNaixement any de Naixament de l'usuari.
     */
    public Usuari(String nom, String email, int anyNaixement) {
        this.nom = nom;
        this.email = email;
        this.anyNaixement = anyNaixement;
        localitzacionsUsuari = new ArrayList<Location>();
        localitzacionsPreferidas= new ArrayList<LocalitzacioPreferida>();
        localitzacionsBuscades = new ArrayList<Location>();
        rutaUsuari = new ArrayList<Ruta>();
        paradesPreferides = new ArrayList<String>();
    }

    public ArrayList<String> getParadesPreferides() {
        return paradesPreferides;
    }
    public ArrayList<Location> getLocalitzacionsBuscades() {
        return localitzacionsBuscades;
    }
    public ArrayList<LocalitzacioPreferida> getLocalitzacionsPreferidas() {
        return localitzacionsPreferidas;
    }
    public ArrayList<Ruta> getRutaUsuari() {
        return rutaUsuari;
    }
    public ArrayList<Location> getLocalitzacionsUsuari() {
        return localitzacionsUsuari;
    }
    public String getNom() {
        return nom;
    }
    public String getEmail() {
        return email;
    }
    public int getAnyNaixement() {
        return anyNaixement;
    }

    @Override
    public String toString() {
        return "Usuari{" +
                "nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", anyNaixement=" + anyNaixement +
                '}';
    }
}