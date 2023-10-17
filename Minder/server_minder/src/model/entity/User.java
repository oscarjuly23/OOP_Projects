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
 * Clase User que conté els atributs que es reben de la BBDD
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
     * Constructor buit de la classe User
     */
    public User(){};

    /**
     * Constructor de la classe User
     * @param username nom del usuari
     * @param edad numero enter que indica l'edad del usuari
     * @param esPremium booleà que indica si l'usuari es premium o no
     * @param email correu electronic del usuari
     * @param password contraseña del usuari
     * @param description descrpció del perfi del usuari
     * @param lang llenguatge preferit del usuari (Java o C)
     * @param img array de byte que conté la imatge de perfil
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
     * Getter del nom d'usuari
     * @return nom d'usuari
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter del nom d'usuari
     * @param username nom d'usuari
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter de l'edad del usuari
     * @return edat del usuari
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Getter del email del usuari
     * @return email del usuari
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter de la contraseña del usuari
     * @return contraseña del usuari
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter de la descripció de l'usuari
     * @return descripció del perfil del usuari
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter de descripció de l'usuari
     * @param description Descripció de l'usuari
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter del llenguatge del usuari
     * @return llenguatge (Java o C)
     */
    public String getLang() {
        return lang;
    }

    /**
     * Setter del llenguatge del usuari
     * @param lang llenguatge (Java o C)
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Getter de l'imatge de perfil
     * @return imatge en array de byte
     */
    public byte[] getImg() {
        return img;
    }

    /**
     * Setter de l'imatge de perfil
     * @param img imatge en array de byte
     */
    public void setImg(byte[] img) {
        this.img = img;
    }

    /**
     * @return boolean. True si l'usuari es premium
     *                  False si l'usuari no es premium
     */
    public boolean isPremium() {
        return esPremium;
    }

    /**
     * Métode per setejar el valors de UserDTO a la entity User
     * @see UserDTO
     * @param u UserDTO
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
     * Métode toString() de la classe
     * @return string que permet visualitzar les dades
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

    /**
     * Métode que converteix la imatge en format png a un array de byte
     * @param path ruta on es troba guardada la imatge
     * @return imatge convertida a array de byte
     * @throws IOException si s'ha produit una excepció d'entrada o sortida
     */
    public byte[] convertToByteArray(String path) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(path));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte [] data = bos.toByteArray();
        return data;
    }

    /**
     * Métode que comprova si el usuari ha cambiat el seu llenguatge preferit
     * @param langActual llenguatge (Java o C)
     * @return true si ha canviat el llenguatge
     *         false si no ha canviat el llenguatge
     */
    public boolean checkIfLanguageChange(String langActual) {
        if(lang.equals(langActual)){
            return false;
        } else {
            return true;
        }
    }
}