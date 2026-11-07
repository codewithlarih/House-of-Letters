package Aplicacao; 

import java.util.Scanner;
public class Programa{
    public static void main (String [] args){

    Scanner sc = new Scanner(System.in); 

System.out.println("╔══════════════════════════════════════╗");
System.out.print("║               BOOKFLOW               ║");
System.out.print("║       SISTEMA DE BIBLIOTECA           ║");
System.out.print("╠══════════════════════════════════════╣");
System.out.print("║                                      ║");
System.out.print("║  1. Gerenciar livros                 ║");
System.out.print("║  2. Gerenciar leitores               ║");
System.out.print("║  3. Gerenciar funcionários            ║");
System.out.print("║  4. Gerenciar empréstimos             ║");
System.out.print("║  5. Gerenciar devoluções               ║");
System.out.print("║  6. Consultar relatórios               ║");
System.out.print("║                                      ║");
System.out.print("║  0. Sair                              ║");
System.out.print("║                                      ║");
System.out.print("╚══════════════════════════════════════╝");

System.out.println("Escolha uma opção: ");    
    
    
    sc.close();
    
    }
}