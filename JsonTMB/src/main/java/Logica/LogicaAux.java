package Logica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Intellij em recomana escriure els métodes sense posar encapsulació.
 */
public class LogicaAux {

    /**
     * Comrpova si la hora té el format correcte.
     * @param hour la hora a comprovar
     * @return true si el format és correcte.
     */
    static boolean esHora(String hour){
        return hour.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9](am|pm)$");
    }

    /**
     * Fer comprovacions de que la data sigui del format necessari.
     * @param date data a comprovar.
     * @return true si el format és correcte.
     */
    static boolean esData(String date){
        return date.matches("^(?:(?:(?:0?[13578]|1[02])(\\/|-|\\.)31)\\1|(?:(?:0?[1,3-9]|1[0-2])(\\/|-|\\.)" +
                "(?:29|30)\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:0?2(\\/|-|\\.)29\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]" +
                "|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:(?:0?[1-9])|(?:1[0-2]))(\\/|-|\\.)" +
                "(?:0?[1-9]|1\\d|2[0-8])\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
    }

    static boolean esCoordenada(String coords){
        return coords.matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$");
    }

    /**
     * Method validarEmail() Valida el format de la hora introduida.
     * @param email email de la ruta que es vol fer introduida per al usuari.
     * @return true si el email és correcte.
     */
    public static boolean validarEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }


    /**
     * Method distanciaCoord() donades dues localitzacions, ens retorna la distància en metres que n'hi ha entre cada una d'elles.
     * @param lat1 Latitud localització 1
     * @param lng1 Longitud localització 1
     * @param lat2 Latitud localització 2
     * @param lng2 Longitud localització 2
     * @return Distància entre les dues localitzacions (m)
     */
    static int distanciaCoord(double lat1, double lng1, double lat2, double lng2) {
        double radioTierra = 6371000;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distancia = radioTierra * va2;

        return (int)distancia;
    }


}
