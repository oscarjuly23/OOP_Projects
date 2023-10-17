/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Classe UserDTO (Data Transfer Object) per enviar a través dels socket entre client-servidor
 */

public class UserDTO implements Serializable {
    private String username;
    private int edad;
    private boolean esPremium;
    private String email;
    private String password;
    private String description;
    private String lang;
    private byte[] img;

    /**
     * Constructor buit de la classe
     */
    public UserDTO(){ }

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
    public UserDTO(String username, int edad, boolean esPremium, String email, String password, String description, String lang, byte[] img) {
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
     * Getter de l'edad de l'usuari
     * @return edat del usuari
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Getter del email de l'usuari
     * @return email del usuari
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter de la contraseña de l'usuari
     * @return contraseña del usuari
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter del password de l'usuari
     * @param password Password de l'usuari
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter de la descripció del usuari
     * @return descripció del perfil del usuari
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter del llenguatge de l'usuari
     * @return llenguatge (Java o C)
     */
    public String getLang() {
        return lang;
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
     * Setter de la variable que diu si ets premium o no
     * @param esPremium
     */
    public void setIsPremium(boolean esPremium) {
        this.esPremium = esPremium;
    }

    /**
     * Comprova que hi ha nom d'usuari i password
     * @return True si la data és correcte i false
     */
    public boolean checkDataLogin(){
        if(username != null && username != "" && password != null && password != ""){
            return true;
        }
        return false;
    }

    /**
     * Passa la classe a Json Array
     * @return El Json Array de la classe
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
     * Mira si la informació és correcte
     * @return True si és correcte i false si no ho és
     */
    public boolean checkDataRegister() {
        if(username == "" || username == null){
            return false;
        }
        if(edad < 18 || edad > 99){
            return false;
        }
        if(email == null || email == ""){
            return false;
        }
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        if(matcher.find() != true){
              return false;
        }
        if (password.length() < 8 || password == null || password == ""){
            return false;
        } else{
            boolean min = false;
            boolean max = false;
            boolean num = false;
            String passText = password;
            for(int i = 0; i<passText.length(); i++){
                if(Character.isUpperCase(passText.charAt(i))){
                    max = true;
                } else if (Character.isLowerCase(passText.charAt(i))){
                    min = true;
                } else if(Character.isDigit(passText.charAt(i))){
                    num = true;
                }
            }
            if (!max || !min || !num){
                return false;
            }
        }

        return true;
    }

    /**
     * Converteix la imatge a array de bytes
     * @param path Ubicació de la imatge
     * @return Retorna l'array de bytes de la imatge
     * @throws IOException Excepció per si hi ha algun error
     */
    public byte[] convertToByteArray(String path) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(path));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte [] data = bos.toByteArray();
        return data;
    }

    /**
     * Comprova si la informació és correcte
     * @return Si és correcte retorna True, sinó retorna false.
     */
    public boolean checkDataEdit(){
        if(!checkDataRegister()){
            return false;
        }
        if(description == null || description == ""){
            return false;
        }
        if(lang == null || lang == ""){
            return false;
        }
        return true;
    }

}