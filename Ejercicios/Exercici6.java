public class Exercici6 {

    public static void main(String[] args) {


        int [] a = {2, 3, 5};
        int [] b = {0,0};

        b=a;
        b[0] = 3;
        for (int i = 0; i < a.length; i++) System.out.print(a[i]);
    }
}
