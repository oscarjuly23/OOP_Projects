/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model.entity;

import model.UserDTO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Model de la classe de l'usuari
 */
public class User{
    private String username;
    private int edad;
    private boolean esPremium;
    private String email;
    private String password;
    private String description;
    private String lang;
    private byte[] img;

    /**
     * Constructor de la classe buit
     */
    public User(){};

    /**
     * Constructor de la classe amb paràmetres
     * @param username Nom de l'usuari
     * @param edad Edat de l'usuari
     * @param esPremium Usuari és premium o no
     * @param email Email de l'usuari
     * @param password Password de l'usuari
     * @param description Descripció de l'usuari
     * @param lang Llanguatge preferit de l'usuari
     * @param img Imatge de l'usuari
     */
    public User(String username, int edad, boolean esPremium, String email, String password, String description, String lang, byte[] img) {
        this.username = username;
        this.edad = edad;
        this.esPremium = esPremium;
        this.email = email;
        this.password = password;
        this.description = description;
        this.lang = lang;
        this.img = img;
    }

    /**
     * Getter del nom de l'usuari
     * @return Nom de l'usuari
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter de l'edad de l'usuari
     * @return Edad de l'usuari
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Getter del email de l'usuari
     * @return Email de l'usuari
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter del password de l'usuari
     * @return Password de l'usuari
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter de la descripció de l'usuari
     * @return Descripció de l'usuari
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter de la descripció de l'usuari
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter del llenguatge de l'usuari
     * @return Llenguatge de l'usuari
     */
    public String getLang() {
        return lang;
    }

    /**
     * Setter del llenguatge de l'usuari
     * @param lang Llenguatge de l'usuari
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Getter de la imatge de l'usuari
     * @return Imatge de l'usuari
     */
    public byte[] getImg() {
        return img;
    }

    /**
     * Setter de la imatge de l'usuari
     * @param img Imatge de l'usuari
     */
    public void setImg(byte[] img) {
        this.img = img;
    }

    /**
     * Setter de si l'usuari és premium
     * @return Usuari és premium o no
     */
    public boolean isPremium() {
        return esPremium;
    }

    /**
     * Setter d'un UserDTO a User
     * @param u Usuari que enviem per sockets
     */
    public void setAll(UserDTO u){
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.description = u.getDescription();
        this.edad = u.getEdad();
        this.email= u.getEmail();
        this.esPremium = u.isPremium();
        this.lang = u.getLang();
        this.img = u.getImg();
    }

    /**
     * Passa l'usuari a format Json Array
     * @return usuari en format Json Array
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", edad=" + edad +
                ", esPremium=" + esPremium +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", lang='" + lang + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public byte[] convertToByteArray(String path) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(path));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte [] data = bos.toByteArray();
        return data;
    }
}