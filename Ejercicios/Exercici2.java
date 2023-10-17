public class Exercici2 {

    public static void main(String[] args) {

        int [][] matriu1 = new int [10][];


        for (int i=0; i < 10; i++) {
            matriu1[i] = new int[i + 1];
        }
        for (int i = 0; i < matriu1.length; i++){
            for (int j = 0; j < matriu1[i].length; j++){
                matriu1[i][j] = j;
            }
        }

        for (int i = 0; i < matriu1.length; i++){
            for (int j = 0; j < matriu1[i].length; j++){
                System.out.print(matriu1[i][j]+" "); }
            System.out.println();
        }
    }
}
