import java.util.Scanner;
import java.util.ArrayList;

class PorcentagemAcerto {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    float cont = 0;
    float resultado;
    float acertos = 0;
    int resp;
    ArrayList<Integer> aluno = new ArrayList<Integer>();
    ArrayList<Integer> professor = new ArrayList<Integer>();

    while(cont < 2){
      resp = scan.nextInt();
      if(resp == -1){
        cont++;
      }else if(cont == 0){
        aluno.add(resp);
      }else if(cont == 1){
        professor.add(resp);
      }
    }

    for(int i = 0; i < aluno.size(); i++){
      if(aluno.get(i) == professor.get(i)){
        acertos++;
      }
    }

    resultado = (acertos / aluno.size()) * 100;

    System.out.printf("%.2f", resultado);
    
    scan.close();
  }
}