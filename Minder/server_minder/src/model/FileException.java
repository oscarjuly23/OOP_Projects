/**
 * @author Oscar Julian
 * @author Miquel Prats
 * @author Victor Valles
 * @author Jan Fite
 * @author Carles Torrubiano
 * @version %I%, %G%
 */

package model;

/**
 * Clase que generarà l'excepció quan no es pugui llegir o no es trobi el JSON
 */
public class FileException extends Exception {
    public FileException(String message) {
        super(message);
    }
}