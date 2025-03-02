import java.util.Scanner;

public class CifraCesar {
  public static void main(String args[]) {
    Scanner input = new Scanner(System.in);
    int shift = input.nextInt();
    String text = input.nextLine().trim();
    text = text.toUpperCase();
    
    String result = "";
    for(int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (Character.isLetter(c)) {
        char shifted = (char) ((c - 'A' + shift) % 26 + 'A');
        result += shifted;
      } else {
        result += c;
      }
    }
    
    System.out.print(result);
      
    input.close();
  }
}
