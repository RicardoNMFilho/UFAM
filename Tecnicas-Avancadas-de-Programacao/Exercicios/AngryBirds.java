import java.util.Scanner;

public class AngryBirds {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double v0 = sc.nextDouble();

        double angulo = sc.nextDouble();

        double distancia = sc.nextDouble();

        if (atingePorco(v0, angulo, distancia)) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        sc.close();
    }

    public static boolean atingePorco(double v0, double angulo, double distancia) {
        double g = 9.8; 
        double rad = Math.toRadians(angulo); 
        double alcanceMaximo = Math.pow(v0, 2) * Math.sin(2 * rad) / g;

       
        if (Math.abs(distancia - alcanceMaximo) <= 1.0) {
            return true;
        } else {
            return false;
        }
    }
}
