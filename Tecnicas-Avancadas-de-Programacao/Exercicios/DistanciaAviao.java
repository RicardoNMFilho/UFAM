import java.util.Scanner;

public class DistanciaAviao {
    public static void main(String[] args) {
        // Tabela de tempos
        int[][] tempos = {{0, 2, 11, 6, 15, 11, 1},
                          {2, 0, 7, 12, 4, 2, 15},
                          {11, 7, 0, 11, 8, 3, 13},
                          {6, 12, 11, 0, 10, 2, 1},
                          {15, 4, 8, 10, 0, 5, 13},
                          {11, 2, 3, 2, 5, 0, 14},
                          {1, 15, 13, 1, 13, 14, 0}};

        Scanner sc = new Scanner(System.in);

        int cidadeAtual = sc.nextInt();
        int cidadeAnterior = cidadeAtual;
        int tempoTotal = 0;

        while (cidadeAtual != -1) {
            cidadeAtual--;
            tempoTotal += tempos[cidadeAnterior - 111][cidadeAtual];
            cidadeAnterior = cidadeAtual + 111;
            cidadeAtual = sc.nextInt();
        }

        System.out.println(tempoTotal);

        sc.close();
    }
}
