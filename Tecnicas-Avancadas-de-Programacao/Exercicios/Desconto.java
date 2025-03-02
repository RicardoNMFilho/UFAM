import java.util.Scanner;

class Desconto {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    double x = scan.nextDouble();
    double z;
    z = x;
    if(x >= 200){
      z = x * 0.95;
    }
    System.out.printf("%.2f",z);
    scan.close();
  }
}