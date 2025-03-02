import java.util.Scanner;

class Xadrez {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int x = scan.nextInt();
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < x; j++) {
        System.out.print("* ");
      }
      if (i % 2 == 0) {
        System.out.print("\n ");
      } else {
        System.out.print("\n");
      }
    }
    scan.close();
  }
}