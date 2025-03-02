import java.util.Scanner;

class ContaEnergia {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    double valor = scan.nextDouble();
    String tipo = scan.next();
    double valorF;
    
    if(tipo.equals("R")){
      if(valor <= 500){valorF = valor * 0.4;}
      else{valorF = valor * 0.65;}
    }else if(tipo.equals("C")){
      if(valor <= 1000){valorF = valor * 0.55;}
      else{valorF = valor * 0.60;}
    }else if(tipo.equals("I")){
      if(valor <= 5000){valorF = valor * 0.55;}
      else{valorF = valor * 0.60;}
    }else{
      valorF = -1;
    }

if(valor < 0){
      valorF = -1;
    }

    System.out.printf("%.2f",valorF);
    
    scan.close();
  }
}