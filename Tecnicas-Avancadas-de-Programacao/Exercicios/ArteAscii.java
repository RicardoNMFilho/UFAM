import java.util.Scanner;

public class ArteAscii {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		int value = scan.nextInt();
		
		for(int i = 0; i < value; i++){
			for(int j = 0; j < value - i; j++){
				System.out.print("*");
			}
			System.out.print("\n");
		}

		for(int i = value; i > 0; i--){
			for(int j = 0; j < value - i + 1; j++){
				System.out.print("*");
			}
			System.out.print("\n");
		}
	}
}