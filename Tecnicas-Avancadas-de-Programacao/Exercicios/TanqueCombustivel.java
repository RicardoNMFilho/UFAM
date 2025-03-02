import java.util.Scanner;

public class TanqueCombustivel {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double raio = sc.nextDouble();
        double altura = sc.nextDouble();
        int opcao = sc.nextInt();

        double volumeTotal = (4.0 / 3.0) * Math.PI * Math.pow(raio, 3);
        double volumeAr = calcularVolumeCalota(raio, altura);
        double volumeCombustivel = volumeTotal - volumeAr;

        double volume = 0;
        if (opcao == 1) {
            volume = volumeAr;
        } else if (opcao == 2) {
            volume = volumeCombustivel;
        }

        System.out.printf("%.4f", volume);

        sc.close();
    }

    public static double calcularVolumeCalota(double raio, double altura) {
        double volumeCalota = (Math.PI / 3.0) * Math.pow(altura, 2) * (3 * raio - altura);
        return volumeCalota;
    }

}
