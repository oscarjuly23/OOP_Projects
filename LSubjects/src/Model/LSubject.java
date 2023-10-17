package Model;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class LSubject {

    private ArrayList<Subject> subjects = new ArrayList();
    private ArrayList<User> users = new ArrayList();

    public LSubject() {
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Ls{" +
                "subjects=" + subjects +
                ", users=" + users +
                '}';
    }

    public LSubject leerJson() {
        Gson gson = new Gson();
        // Llegim l'arxiu JSON proporcionat
        LSubject lss = null;
        try (Reader reader = new FileReader(".\\LSubjects.json")) {
            // Convert JSON File to Java Object
            lss = gson.fromJson(reader, LSubject.class);
            this.users = lss.getUsers();
            this.subjects = lss.getSubjects();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lss;
    }

    public boolean comprovarCredentials (String Username, String Password) {
        leerJson();
        for (int i = 0; i < users.size(); i++) {
            // Comprovem que l'usuari i la contrasenya coincideixen amb algun del arxiu Json.
            if (users.get(i).getName().equals(Username) && users.get(i).getPass().equals(Password)) {
                return true;
            }
        }
        return false;
    }
}