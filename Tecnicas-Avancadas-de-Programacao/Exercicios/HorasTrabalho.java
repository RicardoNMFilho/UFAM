import java.util.Scanner;

public class HorasTrabalho {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[][] horas = new int[4][7]; // matriz para armazenar as horas dos funcionários
        
        // leitura das horas de trabalho
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                horas[i][j] = scan.nextInt();
            }
        }
        
        // cálculo do total de horas por funcionário
        for (int i = 0; i < 4; i++) {
            int total = 0;
            for (int j = 0; j < 7; j++) {
                total += horas[i][j];
            }
            System.out.println(total);
        }
        
        scan.close();
    }
}
