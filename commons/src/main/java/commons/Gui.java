/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons;

import java.util.Scanner;

public class Gui {

    public static int menu(String titulo, String[] opciones) {
        System.out.println("\nMenu " + titulo);
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i+1) + ".- " + opciones[i]);
        }
        System.out.println(opciones.length + 1 + ".- Salir");
        System.out.println("Selecciona una opción: ");
        try {
            Scanner opcion = new Scanner(System.in);
            return opcion.nextInt();
        } catch (Exception e) {
            System.out.println("Error, la entrada esperada era un número");
        }
        return 0;
    }

    public static String entradaTexto(String texto) {
            System.out.println("\n" + texto + ": ");
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
    }
}
