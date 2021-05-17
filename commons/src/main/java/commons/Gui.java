package commons;

import java.util.Scanner;

public class Gui {

    public static int menu(String titulo,String[] opciones){
        System.out.println("\nMenu " + titulo);
        System.out.println("-----------------");
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i+1) + ".- " + opciones[i]);
        }
        System.out.println(opciones.length + 1 + ".- Salir");
        System.out.println("Selecione una opcion > ");
        Scanner opcion = new Scanner(System.in);
        return opcion.nextInt();
    }

    public static String entradaTexto(String titulo){
        System.out.println("\n " + titulo + " > ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
