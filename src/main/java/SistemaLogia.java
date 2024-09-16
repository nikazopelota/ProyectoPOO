import java.util.InputMismatchException;
import java.util.Scanner;

public class SistemaLogia {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        iniciarPrograma();
    }

    public static void iniciarPrograma() {
        int opcion;
        do {
            mostrarMenu();
            opcion = solicitarOpcion("Ingrese una opción: ");
            procesarOpcion(opcion);
        } while (opcion != 4);
        scanner.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n Menú \n");
        System.out.println("1.- ........................");
        System.out.println("2.- ........................");
        System.out.println("3.- ........................");
        System.out.println("4.- Salir \n");
    }

    public static int solicitarOpcion(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int numero = scanner.nextInt();
                scanner.nextLine();
                if (numero >= 0) {
                    return numero;
                } else {
                    System.out.println("Por favor, ingrese un número entero positivo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
                scanner.nextLine();
            }
        }
    }

    public static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                opcion1();
                break;
            case 2:
                opcion2();
                break;
            case 3:
                opcion3();
                break;
            case 4:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
        }
    }

    public static void opcion1() {
        System.out.println("Opción 1 seleccionada. Implementar lógica aquí.");
        // Implementar la lógica correspondiente a la opción 1
    }

    public static void opcion2() {
        System.out.println("Opción 2 seleccionada. Implementar lógica aquí.");
        // Implementar la lógica correspondiente a la opción 2
    }

    public static void opcion3() {
        System.out.println("Opción 3 seleccionada. Implementar lógica aquí.");
        // Implementar la lógica correspondiente a la opción 3
    }
}
