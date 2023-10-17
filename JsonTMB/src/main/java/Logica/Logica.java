package Logica;

import Api.*;

import Model.DataModel;
import Model.Localitzacions.LocalitzacioPreferida;
import Model.Localitzacions.Location;
import Model.Localitzacions.Monument;
import Model.Localitzacions.Restaurant;
import Model.Ruta;
import Model.Usuari;
import UI.Menu;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.Date;

import static Logica.LogicaAux.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Class Logica on tenim totes les funcionalitats que pot fer l'Usuari en la App
 * @author Josep Montava
 * @author Oscar Julian
 */
public class Logica {

    private static Scanner sn = new Scanner(System.in);
    private DataModel dataModel;
    private Usuari usuari;
    public Logica() {
        dataModel = new DataModel();
    }
    public Usuari getUsuari() {
        return usuari;
    }

    /**
     * Method start() Arranquem el projecte
     * @throws IOException necessaria per a la lectura del JSON.
     */
    public void start() throws IOException {
        usuari = Menu.registreUsuari();
        Menu.menuPrincipal(this);
    }


    /**
     * Method mostrarLocalitzacions() Mostrem les localitzacions que l'usuari ha creat. També en creem de noves.
     * @see Usuari#getLocalitzacionsUsuari() Mira les localitzacions creades per l'usuari.
     * @see Logica#registrarLocalitzacio() L'usuari registra una nova localització.
     */
    public void mostraLocalitzacions() {
        Scanner sn = new Scanner(System.in);
        boolean sortir = false;
        String opcio;
        while (!sortir) {
            /*
             * Mirem si l'usuari té creada alguna localització, si en té les mostrem, si no mostrem missatge.
             */
            if (usuari.getLocalitzacionsUsuari().size() == 0) {
                System.out.println("No tens cap localització creada.");
            } else {
                for (int i = 0; i < usuari.getLocalitzacionsUsuari().size(); i++) {
                    System.out.println("- " + usuari.getLocalitzacionsUsuari().get(i).getName());
                }
            }
            System.out.println();
            System.out.println("Vols crear una nova localització? (sí/no)");
            try {
                opcio = sn.nextLine().toLowerCase();
                //Eliminem els accents i ho posem en minúscules
                opcio = Normalizer.normalize(opcio, Normalizer.Form.NFD)
                        .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                String opcioLower = opcio.toLowerCase();
                switch (opcioLower) {
                    case "si":
                        registrarLocalitzacio();
                        break;
                    case "no":
                        sortir = true;
                        System.out.println();
                        break;
                    default:
                        System.out.println("Error! S'ha d'introduir \"sí\" o \"no\".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error! S'ha d'introduir \"sí\" o \"no\"");
            }
        }
    }

    /**
     * Method registrarLocalitzacio() on l'usuari registra una nova localització.
     * @see Usuari#getLocalitzacionsUsuari() Mira les localitzacions creades per l'usuari.
     * @see DataModel#getLocations() Mira les localitzacions del JSon.
     */
    private void registrarLocalitzacio() {
        String nom;
        String descripcio;
        double[] coordinates = {0, 0};
        boolean sortir = false, sortir3 = false, sortir4 = false;
        int error1;
        Scanner sn = new Scanner(System.in);

        while (!sortir) {
            error1 = 0;
            System.out.println("Nom de la localització:");
            nom = sn.nextLine();
            /*
             * Recorre la llista de localitzaions tant del Usuari com del JSon per tal de mirar si ja existeix.
             */
            for (int i = 0; i < usuari.getLocalitzacionsUsuari().size(); i++) {
                if (usuari.getLocalitzacionsUsuari().get(i).getName().equals(nom)) {
                    error1 = 1;
                    break;
                }
            }
            for (int i = 0; i < dataModel.getLocations().size(); i++) {
                if (dataModel.getLocations().get(i).getName().equals(nom)) {
                    error1 = 1;
                    break;
                }
            }
            /*
             * Si ja existeix mostrem error. Si no demanem dades per crear-la.
             */
            if (error1 == 1) {
                System.out.println("Error! Aquesta localització ja existeix.");
                System.out.println();
            } else {
                sortir = true;
                while (!sortir3) {
                    System.out.println();
                    System.out.println("Longitud:");
                    coordinates[0] = parseDouble(sn.nextLine());
                    /*
                     * Les coordenades han de ser coherents.
                     */
                    if (coordinates[0] > 180 || coordinates[0] < -180) {
                        System.out.println("Error! Introdueix una Longitud correcte (-180 / 180)");
                        System.out.println();
                    } else {
                        sortir3 = true;
                        while (!sortir4) {
                            System.out.println();
                            System.out.println("Latitud:");
                            coordinates[1] = parseDouble(sn.nextLine());
                            if (coordinates[1] > 90 || coordinates[1] < -90) {
                                System.out.println("Error! Introdueix una Latitud correcte (-90 / 90)");
                                System.out.println();
                            } else {
                                System.out.println();
                                System.out.println("Descripció:");
                                Scanner sn1 = new Scanner(System.in);
                                descripcio = sn1.nextLine();
                                System.out.println();
                                /*
                                 * Quan hem introduit totes les dades mostrem missage i ens guardem nova localització.
                                 */
                                System.out.println("La informació s'ha registrat amb èxit!");
                                System.out.println();
                                Location localitzacio = new Location(nom, coordinates, descripcio);
                                usuari.getLocalitzacionsUsuari().add(localitzacio);
                                sortir4 = true;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method localitzacioPreferida() on decidim si volem guardar la localització com a preferida.
     * @param location la localització que podrà ser guardada com a preferida.
     * @see Usuari#getLocalitzacionsUsuari() Mira les localitzacions creades per l'usuari.
     * @see DataModel#getLocations() Mira les localitzacions del JSon.
     */
    private void localitzacioPreferida(Location location) {
        Scanner sn = new Scanner(System.in);
        boolean sortir = false;
        String opcio;
        String tipus;
        Date data = new Date();
        LocalitzacioPreferida locPreferida = null;
        try {
            System.out.println();
            System.out.println("Vols guardar la localització trobada com a preferida? (sí/no)");
            opcio = sn.nextLine();

            //Eliminem els accents i ho posem en minúscules
            opcio = Normalizer.normalize(opcio, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            String opcioLower = opcio.toLowerCase();
            switch (opcioLower) {
                case "si":
                    while (!sortir) {
                        System.out.println("Tipus(casa/feina/estudis/oci/cultura):");
                        tipus = sn.nextLine();
                        if (tipus.equals("casa") || tipus.equals("feina") || tipus.equals("estudis") || tipus.equals("oci") || tipus.equals("cultura")) {
                            locPreferida = new LocalitzacioPreferida(data, tipus, location);
                            usuari.getLocalitzacionsPreferidas().add(locPreferida);
                            sortir = true;
                        } else {
                            System.out.println();
                            System.out.println("Error! S'ha d'introduir casa, feina, estudis, oci o cultura");
                        }
                    }
                    System.out.println();
                    System.out.println(locPreferida.getName() + " s'ha assignat com a una nova localització preferida.");
                    System.out.println();
                    break;
                case "no":
                    sortir = true;
                    break;
                default:
                    System.out.println();
                    System.out.println("Error! S'ha d'introduir \"sí\" o \"no\"");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error! S'ha d'introduir \"sí\" o \"no\"");
        }
    }

    /**
     * Method historialLocalitzacions() on consultem el historial de localitzacions.
     * @see Usuari#getLocalitzacionsBuscades() retorna localitzacions buscades per l'usuari.
     */
    public void historialLocalitzacions() {
        for (int i = usuari.getLocalitzacionsBuscades().size() - 1; i >= 0; i--) {
            System.out.println("- " + usuari.getLocalitzacionsBuscades().get(i).getName());
        }
        /*
         * Si no ha buscat cap localització mostra missatge.
         */
        if (usuari.getLocalitzacionsBuscades().size() == 0) {
            System.out.println("Encara no has buscat cap localització!");
            System.out.println("Per buscar-ne una, accedeix a l'opció 2 del menú principal.");
            System.out.println();
        }
    }

    /**
     * Method buscaLocalitzacio() on busquem una localització i donem la seva informació.
     * @see Usuari#getLocalitzacionsUsuari() retorna localitzacions creades per l'usuari.
     * @see DataModel#getLocations() Mira les localitzacions del JSon.
     */
    public void buscarLocalitzacio() {
        Scanner sn = new Scanner(System.in);
        String nom;
        boolean sortir = false;
        while (!sortir) {
            int errorCheck = 0;
            int error1Check = 0;
            System.out.println("Introdueix el nom d'una localització:");
            nom = sn.nextLine();
            System.out.println();
            /*
             * Busquem les localitzacions per nom, si existeix mostrem informació, si no mostra missatge.
             */
            for (int i = 0; i < usuari.getLocalitzacionsUsuari().size(); i++) {
                if (usuari.getLocalitzacionsUsuari().get(i).getName().equals(nom)) {
                    errorCheck = 1;
                    System.out.println("Posició: " + usuari.getLocalitzacionsUsuari().get(i).getCoordinates()[0] + "," + usuari.getLocalitzacionsUsuari().get(i).getCoordinates()[1]);
                    System.out.println("Descripció: ");
                    System.out.println(usuari.getLocalitzacionsUsuari().get(i).getDescription());
                    usuari.localitzacionsBuscades.add(usuari.getLocalitzacionsUsuari().get(i));
                    localitzacioPreferida(usuari.getLocalitzacionsUsuari().get(i));
                }
            }
            for (int i = 0; i < dataModel.getLocations().size(); i++) {
                if (dataModel.getLocations().get(i).getName().equals(nom)) {
                    error1Check = 1;
                    System.out.println("Posició: " + dataModel.getLocations().get(i).getCoordinates()[0] + "," + dataModel.getLocations().get(i).getCoordinates()[1]);
                    System.out.println("Descripció: " + dataModel.getLocations().get(i).getDescription());
                    if (dataModel.getLocations().get(i) instanceof Monument) {
                        System.out.println("Inauguració: " + ((Monument) dataModel.getLocations().get(i)).getInauguration());
                        System.out.println("Arquitecte: " + ((Monument) dataModel.getLocations().get(i)).getArchitect());
                    } else if (dataModel.getLocations().get(i) instanceof Restaurant) {
                        System.out.println("Característiques: " + Arrays.toString(((Restaurant) dataModel.getLocations().get(i)).getCharacteristics()));
                    }
                    usuari.localitzacionsBuscades.add(dataModel.getLocations().get(i));
                    localitzacioPreferida(dataModel.getLocations().get(i));
                }
            }
            if (error1Check == 0 && errorCheck == 0) {
                System.out.println();
                System.out.println("Ho sentim, no hi ha cap localització amb aquest nom.");
            }
            sortir = true;
        }
    }

    /**
     * Method tempsEsperaBus() on mostrarem el temps d'espera dels busos d'una parada.
     */
    public void tempsEsperaBus() {
        Scanner sn = new Scanner(System.in);
        int parada;
        boolean sortir = false;
        while (!sortir) {
            System.out.println("Introdueix el codi de parada:");
            parada = sn.nextInt();
            ArrayList<ParadaBus> llistaBus = LecturaApi.llegirApiIbus(parada);
            if (llistaBus != null) {
                sortir = true;
                for (ParadaBus bus : llistaBus) {
                    for (int j = 0; j < usuari.getParadesPreferides().size(); j++) {
                        if (bus.getDestination().equals(usuari.paradesPreferides.get(j))) {
                            System.out.println("Parada preferida!");
                        }
                    }
                    System.out.println(bus.getIdBus() + " - " + bus.getDestination() + " - " + bus.getTemps());
                }
                System.out.println(System.lineSeparator());

            } else {
                System.out.println("Error, codi de parada no vàlid!");
                System.out.println(System.lineSeparator());
            }
        }
    }

    /**
     * Method buscaLocalitzacio() Printa les estacions de més properes a més llunyanes de cada localització preferida.
     */
    public void distanciaParadesEstacionsPreferides(){
        if (usuari.localitzacionsPreferidas.isEmpty()){
            System.out.println("Per tenir parades i estacions preferides es requereix haver creat una localització preferida anteriorment.");
            System.out.println();
        } else {
            ArrayList<ApiTransit> llistaBusos = LecturaApi.llegirApiTransitBus();
            ArrayList<ApiTransit> llistaMetros = LecturaApi.llegirApiTransitMetro();
            ArrayList<ApiTransit> llistat = new ArrayList<>();
            llistat.addAll(llistaBusos);
            llistat.addAll(llistaMetros);

            for (int i = 0; i < usuari.localitzacionsPreferidas.size(); i++) {
                for(ApiTransit llist: llistat)
                    llist.setDistancia(-1);
                System.out.println("- "+ usuari.getLocalitzacionsPreferidas().get(i).getName());
                for (ApiTransit apiTransit : llistat) {
                    //És la distància de cada parada de la Api amb la preferida.
                    int distancia = distanciaCoord(usuari.getLocalitzacionsPreferidas().get(i).getCoordinates()[0], usuari.getLocalitzacionsPreferidas().get(i).getCoordinates()[1]
                            , apiTransit.getCoordinates()[0], apiTransit.getCoordinates()[1]);

                    if (distancia <= 500) {
                        apiTransit.setDistancia(distancia);
                    }
                }
                ArrayList<ApiTransit> toSort = new ArrayList<>();
                toSort.clear();
                for (ApiTransit ap: llistat){
                    if(ap.getDistancia() != -1){
                        toSort.add(ap);
                    }
                }
                Collections.sort(toSort);
                if (toSort.isEmpty()) {
                    System.out.println("TMB està fent tot el possible perquè el bus i el metro arribin fins aquí.");
                } else {
                    //toSort està ordenat
                    for (int j = 0; j < toSort.size(); j++) {
                        System.out.println(j+1 + ") " + toSort.get(j).getName() + " (" + toSort.get(j).getCodiParada() + ") " + toSort.get(j).getTipus());
                    }
                    System.out.println();
                }
            }
        }
    }


    /**
     * Demanem localitzacions que poden ser passades pel seu nom o per les seves coordenades, demanem també una serie de paràmetres per poder passar-li al usuari la ruta més ràpida d'un punt a un altre.
     * @throws IOException error.
     */
    public void planRuta() throws IOException{
        Scanner sn = new Scanner(System.in);

        boolean error;
        String origen, desti, sA, date, hour;
        int caminar = 0;
        double latOrigen = 0, lonOrigen = 0, latDesti = 0, lonDesti = 0;
        //Origen
        do{
            error = false;
            System.out.println("Origen? (lat, lon / nom localització)");
            origen = sn.nextLine().toLowerCase();
            System.out.println();
            //Si no es coordenada ni existeix nom
            if(!esCoordenada(origen) && comprovaNom(origen) == null){
                error = true;
                System.out.println("Ho sentim, aquesta localització no és vàlida :(");
            } else{
                if(esCoordenada(origen)) {
                    String[] aux = origen.split(", ");
                    latOrigen = parseDouble(aux[0]);
                    lonOrigen = parseDouble(aux[1]);
                }else{
                    double[] aux = comprovaNom(origen);
                    latOrigen = aux[1];
                    lonOrigen = aux[0];
                }
            }
        }while (error); //Comprova si loc es creada o json / o es coordenada

        //Desti
        do{
            error = false;
            System.out.println("Destí? (lat, lon / nom localització)");
            desti = sn.nextLine().toLowerCase();
            System.out.println();
            //Si no es coordenada ni existeix nom
            if(!esCoordenada(desti) && comprovaNom(desti) == null){
                error = true;
                System.out.println("Ho sentim, aquesta localització no és vàlida :(");
            } else{
                if(esCoordenada(desti)) {
                    String[] aux = desti.split(", ");
                    latDesti = parseDouble(aux[0]);
                    lonDesti = parseDouble(aux[1]);
                }else{
                    latDesti = comprovaNom(desti)[1];
                    lonDesti = comprovaNom(desti)[0];
                }
            }
        }while (error); //Comprova si loc es creada o json / o es coordenada

        //Sortida o arribada
        do{
            error = false;
            System.out.println("Dia/hora seran de sortida o d'arribada? (s/a)");
            sA = sn.nextLine().toLowerCase();
            System.out.println();
            if (!sA.equals("s") && !sA.equals("a")) {
                error = true;
                System.out.println("Error! S'ha d'introduir \"s\" o \"a\"!");
            }else{
                switch(sA){
                    case "s":
                        sA = "false";
                        break;
                    case "a":
                        sA = "true";
                        break;
                }
            }
        }while(error);

        //Data
        do{
            error = false;
            System.out.println("Dia? MM-DD-YYYY");
            date = sn.nextLine().toLowerCase();
            System.out.println();
            if(!esData(date)){
                error = true;
                System.out.println("Error, hi ha algun paràmetre erroni :(");
            }
        }while (error);

        //Hora
        do{
            error = false;
            System.out.println("Hora? (HH:MMam/HH:MMpm)");
            hour = sn.nextLine().toLowerCase();
            System.out.println();
            if(!esHora(hour)){
                error = true;
                System.out.println("Error, hi ha algun paràmetre erroni :(");
            }
        } while (error);

        //Caminar
        do{
            error = false;
            System.out.println("Màxima distància caminant en metres?");
            String aux = sn.nextLine().toLowerCase();
            System.out.println();
            if(!aux.matches("[1-9][0-9]*")){
                error = true;
                System.out.println("Error, hi ha algun paràmetre erroni :(");
            } else{
                caminar = parseInt(aux);
            }
        } while (error);


        String coordsOrigen = latOrigen + "," + lonOrigen;
        String coordsDesti = latDesti + "," + lonDesti;
        char sortidaarribada = sA.charAt(0);
        Ruta ruta = new Ruta(origen, desti, coordsOrigen, coordsDesti, date, hour, sortidaarribada, caminar);
        usuari.getRutaUsuari().add(ruta);

        String rutes = LecturaApi.llegirApiPlanner(coordsOrigen, coordsDesti, date, hour, sA, caminar);


        if (rutes.equals("")){
            System.out.println("TMB està fent tot el possible perquè el bus i el metro facin aquesta ruta en un futur.\n");
            usuari.getRutaUsuari().remove(ruta);
        }
        else{
            combinacioRapida(rutes);
        }

    }

    /**
     * Comprova que l'entrada existeixi al json o siguin creades
     * @param input el nom de la localització
     * @return les coordenades de la localització introduida.
     */
    private double[] comprovaNom(String input){
        double[] aux = null;
        //Comprova que el nom estigui dintre del json o per l'usuari
        for (int i = 0; i < dataModel.getLocations().size(); i++){
            if(input.equals(dataModel.getLocations().get(i).getName().toLowerCase())){
                aux = dataModel.getLocations().get(i).getCoordinates();
            }
        }
        for (int i = 0; i < usuari.getLocalitzacionsUsuari().size(); i++){
            if(input.equals(usuari.getLocalitzacionsUsuari().get(i).getName().toLowerCase())){
                aux = usuari.getLocalitzacionsUsuari().get(i).getCoordinates();
                System.out.println(usuari.getLocalitzacionsUsuari().get(i));
            }
        }
        return aux;
    }

    /**
     * Method combinaciRapida() fa prints de la combinació més ràpida segons la ruta que li passem.
     * @param ruta hem de passar-li una ruta on li donarem la combinació més ràpida per arribar al destí.
     */
    private void combinacioRapida(String ruta) {
        /*
         * L'objecte que em retorna la API:
         */
        JsonParser Parser = new JsonParser();
        JsonObject jsonObject = Parser.parse(ruta).getAsJsonObject();
        JsonObject planes = jsonObject.get("plan").getAsJsonObject();
        JsonArray itineraries = planes.get("itineraries").getAsJsonArray();
        /*
         * El que ens interessa de aquest Array de itineraris es la primera posició (la més ràpida).
         */
        JsonObject itinerari = itineraries.get(0).getAsJsonObject();
        int temps = itinerari.get("duration").getAsInt();
        System.out.println("\t-Combinació més ràpida:");
        System.out.println("\t\tTemps del trajecte: " + (temps / 60) + " min");
        System.out.println("\t\tOrigen");
        System.out.println("\t\t|");
        JsonArray legs = itinerari.get("legs").getAsJsonArray();
        for (int j = 0; j < legs.size(); j++) {
            /*
             * Agafem el primer ja que será el més ràpid.
             */
            JsonObject legObject = legs.get(j).getAsJsonObject();
            long tempsI = legObject.get("startTime").getAsLong();
            long tempsF = legObject.get("endTime").getAsLong();
            String mode = legObject.get("mode").getAsString();
            int tempsT = Math.round((float)((tempsF - tempsI) / 60000));
                if (mode.equals("WALK")) {
                    if (!(temps == 0)) {
                        System.out.println("\t\tcaminar " + tempsT + " min");
                        System.out.println("\t\t|");
                    }
                } else if (mode.equals("BUS") || mode.equals("SUBWAY")) {
                    String route = legObject.get("route").getAsString();
                    JsonObject origen = legObject.get("from").getAsJsonObject();
                    JsonObject desti = legObject.get("to").getAsJsonObject();
                    String origenE = origen.get("name").getAsString();
                    String destiE = desti.get("name").getAsString();
                    String codiO = origen.get("stopCode").getAsString();
                    String codiD = desti.get("stopCode").getAsString();
                    System.out.println("\t\t" + route + " " + origenE + "(" + codiO + ")" + " -> " + destiE + "(" + codiD + ") " + tempsT + " min");
                    System.out.println("\t\t|");
                } else {
                    System.out.println("Les dades són correctes però el servei de metro/bus no arriba a aquesta localització!");
                }
            }
        System.out.println("\t\tDestí");
        System.out.println();
        }


    /**
     * Method mostrarRutas() que mostra la primera part de les rutes, abans de donar la combinació més ràpida.
     * @throws IOException necessaria per a la lectura del JSON.
     * @see Usuari#getRutaUsuari() obtenim les rutes que ha creat l'usuari.
     * @see Ruta#getSortidaArribada() posem true o false segons sortida o arribada.
     * @see LecturaApi#llegirApiPlanner(String, String, String, String, String, int) passem les dades de la ruta a la API.
     * @see Logica#combinacioRapida(String) retorna la combinació de bus/metro més ràpida per a aquesta ruta.
     */
    public void mostrarRutas() throws IOException {
        /*
         * Primer mirem que l'usuari tingui alguna ruta creada.
         */
        if (usuari.getRutaUsuari().isEmpty()) {
            System.out.println("Encara no has realitzat cap ruta :(");
            System.out.println("Per buscar-ne una, accedeix a l'opció 3 del menú principal.");
            System.out.println();
        } else {
            /*
             * Recorrem les rutes del usuari, que les tinc guardades en un arraylist amb tota la informació.
             */
            for (int i = 0; i < usuari.getRutaUsuari().size(); i++) {
                String sa;
                Ruta rutaActual = usuari.getRutaUsuari().get(i);
                if (rutaActual.getSortidaArribada() == 'a') {
                    sa = "true";
                } else {
                    sa = "false";
                }
                String rutas = LecturaApi.llegirApiPlanner(rutaActual.getOrigenF(), rutaActual.getDestiF(), rutaActual.getData(), rutaActual.getHora(), sa, rutaActual.getDist());
                int num = i + 1;
                System.out.println("->Ruta " + num + ":");
                System.out.println("\t-Origen: " + rutaActual.getOrigen());
                System.out.println("\t-Destí: " + rutaActual.getDesti());
                if (rutaActual.getSortidaArribada() == 's') {
                    System.out.println("\t-Dia de Sortida: " + rutaActual.getData() + " a les " + rutaActual.getHora());
                } else {
                    System.out.println("\t-Dia de Arribada: " + rutaActual.getData() + " a les " + rutaActual.getHora());
                }
                System.out.println("\t-Màxima distància caminant: " + rutaActual.getDist() + " metres");
                combinacioRapida(rutas);
            }
        }
    }

    /**
     * Method estacionsInauguradesAnyNaixaent() mostra les estacions inaugurades l'any de naixament del Usuari.
     * @param usuari passem l'usuari per saber el seu any de naixament.
     * @throws IOException necessaria per a la lectura del JSON.
     * @see Usuari#getRutaUsuari() obtenim les rutes que ha creat l'usuari.
     * @see Ruta#getSortidaArribada() posem true o false segons sortida o arribada.
     * @see LecturaApi#llegirApiPlanner(String, String, String, String, String, int) passem les dades de la ruta a la API.
     * @see Logica#combinacioRapida(String) retorna la combinació de bus/metro més ràpida per a aquesta ruta.
     */
    public void estacionsInauguradesAnyNaixament(Usuari usuari) throws IOException {
        String estacions = LecturaApi.paradaInaugurada();
        ArrayList<EstacioMetro> arrayEstacions = new ArrayList<>();
        /*
         * L'objecte que em retorna la API:
         */
        JsonParser Parser = new JsonParser();
        JsonObject jsonObject = Parser.parse(estacions).getAsJsonObject();
        JsonArray features = jsonObject.get("features").getAsJsonArray();

        for (int i = 0; i < features.size(); i++) {
            EstacioMetro estacio = new EstacioMetro();

            JsonObject jsonObject1 = (JsonObject) features.get(i);
            JsonObject propierties = (JsonObject) jsonObject1.get("properties");
            /*
             * Agafem la informació que necessitem:
             */
            JsonElement nomEstacio = propierties.get("NOM_ESTACIO");
            JsonElement nomLinia = propierties.get("NOM_LINIA");
            JsonElement dataInauguracio = propierties.get("DATA_INAUGURACIO");

            String nom = nomEstacio.getAsString();
            String linia = nomLinia.getAsString();
            String data = dataInauguracio.getAsString();
            /*
             * Per a tenir l'any de inauguració fem un split i ens quedem només amb l'any
             */
            String[] separar = data.split("-");
            int any = parseInt(separar[0]);

            estacio.setEstacio(nom);
            estacio.setLinia(linia);

            /*
             * Mirem que l'any de naixament del usuari sigui el mateix que l'any de inauguració de la estació.
             * L'afegim al nostre array de estacions que mostrarem.
             */
            if (usuari.getAnyNaixement() == any) {
                arrayEstacions.add(estacio);
            }
        }
        /*
         * Mirem que hi l'array de estacions no estigui buit
         */
        if (arrayEstacions.isEmpty()) {
            System.out.println("Cap estació de metro es va inaugurar el teu any de naixement :(");
            System.out.println();
        } else {
            System.out.println("Estacions inaugurades el " + usuari.getAnyNaixement() + ":");
            for (EstacioMetro arrayEstacion : arrayEstacions) {
                System.out.println("\t-" + arrayEstacion.getEstacio() + " " + arrayEstacion.getLinia());
            }
        }
    }
}