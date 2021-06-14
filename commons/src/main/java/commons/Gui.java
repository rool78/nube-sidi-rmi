/*
 * Autor: Raúl Maza Sampériz
 * Email: rmaza14@alumno.uned.es
 */

package commons;

import java.util.List;
import java.util.Scanner;

public class Gui {

    public static int menu(String cabeceraMenu, List<String> opciones) {
        System.out.println("\nMenu " + cabeceraMenu);
//        for (int i = 0; i < opciones.length; i++) {
//            System.out.println((i+1) + ".- " + opciones[i]);
//        }
        int posicion = 1;
        for (String opcion: opciones) {
            System.out.println(posicion + ".- " + opcion);
            posicion++;
        }
        System.out.println(posicion + ".- Salir");
        System.out.println("Por favor, selecciona una opción: ");
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
