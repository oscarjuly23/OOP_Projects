package UI;

import Logica.Logica;
import Model.JsonUsuariWrite;
import Model.Usuari;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static Logica.LogicaAux.validarEmail;

/**
 * Class Logica on tenim totes les funcionalitats que pot fer l'Usuari en la App
 * @author Josep Montava
 * @author Oscar Julian
 */
public class Menu {

    private static Scanner sn = new Scanner(System.in);
    /**
     * Method registreUsuari() on demanem les dades per al registre del Usuari
     * @return Usuari ja creat correctament
     * @throws IOException necessaria per a la lectura del JSON.
     */
    public static Usuari registreUsuari() throws IOException {
        JsonUsuariWrite jsonUsuariWrite = new JsonUsuariWrite();
        String nom;
        String email;
        boolean valid = false;
        int anyNaixement;

        System.out.println("Benvingut a l'aplicació de TMBJson! Si us plau, introdueix les dades que se't demanen.");
        System.out.println();
        System.out.println("Nom d'usuari:");
        nom = sn.nextLine();
        System.out.println();

        do {
            System.out.println("Correu electrònic:");
            email = sn.nextLine();
            System.out.println();
            /*
             * El email que introdueix l'usuari ha de tenir un format vàlid.
             */
            if (!validarEmail(email)) {
                System.out.println("Has de introduir un email vàlid.");
                System.out.println();
            }
        } while (!validarEmail(email));
        /*
         * També comrpovem que l'any de naixament sigui coherent.
         */
        do {
            System.out.println("Any de naixement:");
            anyNaixement = sn.nextInt();
            System.out.println();
            if (anyNaixement < 2020 && anyNaixement > 1900)
                valid = true;
            if (!valid) {
                System.out.println("Aquest any no és vàlid.");
                System.out.println();
            }
        } while (!valid);
        /*
         * Si tot és correcte ja tenim la informació guardada, la escribim en el Json i la guardem a Usuari.
         */
        System.out.println("La informació s'ha registrat amb èxit!");
        System.out.println();
        // Escribim el nostre Model.Usuari en el JSON
        Usuari usuari = new Usuari(nom, email, anyNaixement);
        jsonUsuariWrite.writeUser(usuari);
        return usuari;
    }

    /**
     * Method menuPrincipal() on mostrem el menú principal amb les funcionalitats.
     * @param logica Classe on tenim la lògica i funcionalitat del menú
     * @see Menu#menuGestioUsuari(Logica) Anem a la funció del menú de Gestió de Usuari
     * @see Logica#buscarLocalitzacio() Busquem la informació de la localització que busquem.
     * @see Logica#planRuta() Amb un origen i un destí mostrarem la ruta més ràpida per arribar-hi.
     * @see Logica#tempsEsperaBus() Amb el codi de parada mostrem el temps d'espera i destinació de la parada.
     */
    public static void menuPrincipal(Logica logica) {
        boolean sortir = false;
        int opcio;

        while (!sortir) {
            System.out.println("1. Gestió d'usuari");
            System.out.println("2. Buscar localitzacions");
            System.out.println("3. Planejar ruta");
            System.out.println("4. Temps d'espera del bus");
            System.out.println("5. Sortir");
            try {
                System.out.println();
                System.out.println("Selecciona una opció:");
                opcio = sn.nextInt();
                switch (opcio) {
                    case 1:
                        menuGestioUsuari(logica);
                        break;
                    case 2:
                        logica.buscarLocalitzacio();
                        break;
                    case 3:
                        logica.planRuta();
                        break;
                    case 4:
                        logica.tempsEsperaBus();
                        break;
                    case 5:
                        sortir = true;
                        break;
                    default:
                        System.out.println("Només números entre l'1 i el 5");
                }
            } catch (InputMismatchException | IOException e) {
                System.out.println("Has d'insertar un número entre l'1 i el 5");
                sn.next();
            }
        }
    }

    /**
     * Method menuGestioUsuari() on mostrem el menú de Gestió del Usuari ja registrat.
     * @param logica Classe on tenim la lògica i funcionalitat del menú
     * @see Logica#mostraLocalitzacions() Mostrarem localitzacions creades per l'usuari o crearem una de nova.
     * @see Logica#historialLocalitzacions() Mostrarem totes les localitzacions que s'hagin buscar en l'opcio 2 del menú.
     * @see Logica#mostrarRutas() Mostrem totes les rutes que hem creat en la opció 3 del menú.
     * @see Logica#estacionsInauguradesAnyNaixament(Usuari) Mostrem estacions inaugurades l'anu de naixament del Usuari.
     */
    private static void menuGestioUsuari(Logica logica) {
        boolean sortir = false;
        char opcio;

        while (!sortir) {
            System.out.println("a)Les meves localitzacions");
            System.out.println("b)Historial de localitzacions");
            System.out.println("c)Les meves rutes");
            System.out.println("d)Parades i estacions preferides");
            System.out.println("e)Estacions inaugurades el meu any de naixement");
            System.out.println("f)Tornar al menú principal");

            try {
                System.out.println();
                System.out.println("Selecciona una opció:");
                opcio = sn.next().charAt(0);
                opcio = Character.toLowerCase(opcio);

                switch (opcio) {
                    case 'a':
                        logica.mostraLocalitzacions();
                        break;
                    case 'b':
                        logica.historialLocalitzacions();
                        break;
                    case 'c':
                        logica.mostrarRutas();
                        break;
                    case 'd':
                        logica.distanciaParadesEstacionsPreferides();
                        break;
                    case 'e':
                        logica.estacionsInauguradesAnyNaixament(logica.getUsuari());
                        break;
                    case 'f':
                        sortir = true;
                        break;
                    default:
                        System.out.println("Només caràcters entre la 'a' i la 'f'.");
                }
            } catch (InputMismatchException | IOException e) {
                System.out.println("Has d'introduir un caràcter entre la 'a' i la 'f'.");
                sn.next();
            }
        }
    }
}