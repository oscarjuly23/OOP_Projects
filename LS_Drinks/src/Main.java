import java.util.ArrayList;
import java.util.Scanner;
import java.io.Reader;
import java.io.FileReader;
import com.google.gson.Gson;
import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        int option;
        boolean out = false;
        Scanner sn = new Scanner(System.in);

        Gson gson = new Gson();

        // Llegim l'arxiu JSON proporcionat
        try (Reader reader = new FileReader(".\\archive.json")) {

            // Convert JSON File to Java Object
            Drink drinks = gson.fromJson(reader, Drink.class);

            while (!out) {
                System.out.println("1. Calcular quantes begudes alcholiques hi ha de cada tipus.");
                System.out.println("2. Mostrar una llista de les begudes destilades (25-60) que es poden barrejar amb cola.");
                System.out.println("3. Mostrar tota la informacio de la ginebra on la suma de la mida del nom dels seus fundadors sigui major.");
                System.out.println("4. Mostrar un top 3 de les combinacions (alcohol + mixer) mes repetires.");
                System.out.println("5. Mostrar un top 3 dels mixers on la mitjana dels graus de les begudes amb les que es barregen sigui major.");
                System.out.println("6. Sortir.");
                System.out.println();

                System.out.println("Selecciona una opcio:");
                option = sn.nextInt();
                switch (option) {
                    case 1:     //"1. Calcular quantes begudes alcholiques hi ha de cada tipus"
                        System.out.println("De cada tipus de beguda alcholica hi ha aquesta quantitat:");
                        // Creem una variable per a cada tipus de beguda alcoholica
                        int d1 = 0, d2 = 0, d3 = 0, d4 = 0, d5 = 0;
                        for (int i = 0; i < drinks.getAlcohols().size(); i++) {
                            if (drinks.getAlcohols().get(i).getType() == 1) {
                                d1++;
                            } else if (drinks.getAlcohols().get(i).getType() == 2) {
                                d2++;
                            } else if (drinks.getAlcohols().get(i).getType() == 3) {
                                d3++;
                            } else if (drinks.getAlcohols().get(i).getType() == 4) {
                                d4++;
                            } else if (drinks.getAlcohols().get(i).getType() == 5) {
                                d5++;
                            }
                        }
                        for (int i = 0; i < drinks.getTypes().size(); i++) {
                            // Treiem el nom de cada tipus de beguda alcoholica, aizí no cal mostrar tot l'array
                            if (drinks.getTypes().get(i).getId() == 1) {
                                System.out.println(drinks.getTypes().get(i).getName() + ": " + d1);
                            }
                            if (drinks.getTypes().get(i).getId() == 2) {
                                System.out.println(drinks.getTypes().get(i).getName() + ": " + d2);
                            }
                            if (drinks.getTypes().get(i).getId() == 3) {
                                System.out.println(drinks.getTypes().get(i).getName() + ": " + d3);
                            }
                            if (drinks.getTypes().get(i).getId() == 4) {
                                System.out.println(drinks.getTypes().get(i).getName() + ": " + d4);
                            }
                            if (drinks.getTypes().get(i).getId() == 5) {
                                System.out.println(drinks.getTypes().get(i).getName() + ": " + d5);
                            }
                        }
                        System.out.println();
                        break;

                    case 2:     //"2. Mostrar una llista de les begudes destilades (25-60) que es poden barrejar amb cola."
                        System.out.println(" Les begudes destilades que es poden barrejar amb cola són les següents:");
                        // Recorrem els alcohols
                        for (Alcohol alcohol : drinks.getAlcohols()) {
                            // Recorrem les combinacions que tenen cada alcohol
                            for (int combinacions : alcohol.getCombinations()) {
                                // En un mateix if comparem els graus del alchol que estiguin entre 25-60 i ademés comprovem que la combinació sigui la 'Cocacola' que és la 1
                                if (alcohol.getGraduation() >= 25 && alcohol.getGraduation() <= 60 && combinacions == 1) {
                                    System.out.println("- " + alcohol.getNom());
                                }
                            }
                        }
                        System.out.println();
                        break;

                    case 3: //"3. Mostrar tota la informacio de la ginebra on la suma de la mida del nom dels seus fundadors sigui major."
                        int llarg = 0, any = 0, k = 0;
                        float graduacio = 0;
                        String nom = " ", procedencia = " ", fundadors = " ", tipus = " ", combinacions = " ";
                        for (int i = 0; i < drinks.getAlcohols().size(); i++) {
                            // Recorrem el ArrayList de Founders
                            for (int n = 0; n < drinks.getAlcohols().get(i).getFounders().length; n++) {
                                // Mirem primer que sigui una Ginebra, també que la llargada del nom del fundador sigui major a la guardada anteriorment.
                                if (drinks.getAlcohols().get(i).getType() == 2 && drinks.getAlcohols().get(i).getFounders()[n].getName().length() > llarg) {
                                    // Reinicialitzem paràmetres per al foreach
                                    combinacions = " ";
                                    k = 0;
                                    // Agafo els valors demanats en l'enunciat per a cada variable
                                    nom = drinks.getAlcohols().get(i).getNom();
                                    graduacio = drinks.getAlcohols().get(i).getGraduation();
                                    procedencia = drinks.getAlcohols().get(i).getProcedence();
                                    any = drinks.getAlcohols().get(i).getYear();
                                    tipus = drinks.getTypes().get(n).getName();
                                    fundadors = drinks.getAlcohols().get(i).getFounders()[n].getName();
                                    llarg = drinks.getAlcohols().get(i).getFounders()[n].getName().length();
                                    // Fem un foreach per a aconseguir traduir el número de les combinacions amb el seu propi nom de la següent manera
                                    for(int combinacio: drinks.getAlcohols().get(i).getCombinations()){
                                        for(Mixer mixer:drinks.getMixers()){
                                            if(mixer.getId() == combinacio){
                                                if(k != 0){
                                                    combinacions += ", " + mixer.getName();
                                                } else{
                                                    combinacions += mixer.getName();
                                                    k++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                System.out.println("La ginebra on la suma de la mida del nom dels seus fundadors es major és la següent:");
                System.out.println("Nom: <" + nom + ">");
                System.out.println("Graduacio: <" + graduacio + ">");
                System.out.println("Procedencia: <" + procedencia + ">");
                System.out.println("Any: <" + any + ">");
                System.out.println("Tipus: <" + tipus + ">");
                System.out.println("Fundadors: <" + fundadors + ">");
                System.out.println("Combinacions: <" + combinacions + " >");
                System.out.println();
                break;

                case 4: //"4. Mostrar un top 3 de les combinacions (alcohol + mixer) mes repetides"
                    System.out.println("El TOP 3 combinacions més repetides son les següents:");
                    int increment = 0;
                    // Primer de tot recorrem els tipus alchols (size = 5)
                    for (int i = 0; i < drinks.getTypes().size(); i++) {
                        //Ara recorrem els mixers (size = 5)
                        for (int n = 0; n < drinks.getMixers().size(); n++) {
                            // Reinicialitzem la suma/increment
                            increment = 0;
                            // Recorrem els alchols en general (size = 28)
                            for (int j = 0; j < drinks.getAlcohols().size(); j++) {
                                // Finalment recorrem les combinacions de alchols (lenght = 2/3)
                                for (int m = 0; m < drinks.getAlcohols().get(j).getCombinations().length; m++){
                                    // Ara mirem si el ID del tipus d'alchol es el mateix que el del tipus d'alchol
                                    if (drinks.getTypes().get(i).getId() == drinks.getAlcohols().get(j).getType()) {
                                        // Posteriorment comprobem que el ID del Mixer sigui el mateix que el de la combinació de Alcohols, d'aquesta manera ja sabrem que la combinació coincideix
                                        if (drinks.getMixers().get(n).getId() == drinks.getAlcohols().get(j).getCombinations()[m]) {
                                            increment++; // Tindrem acumulades totes les combinacions que tinguem
                                        }
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(increment);

                    System.out.println();
                    break;

                case 5: //"5. Mostrar un top 3 dels mixers on la mitjana dels graus de les begudes amb les que es barregen sigui major"
                    for (int n = 0; n < drinks.getMixers().size(); n++) {
                        for (int j = 0; j < drinks.getAlcohols().size(); j++) {
                            for (int m = 0; m < drinks.getAlcohols().get(j).getCombinations().length; m++) {
                            }
                        }
                    }
                    System.out.println();
                    break;

                case 6: //"Salir"
                    out = true;
                    System.out.println();
                    break;
                default:
                    System.out.println("Has d'intoduir un número del 1-6! Torna a intentar-ho.");
                    System.out.println();
                    break;
            }
        }
//            System.out.println(drinks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
