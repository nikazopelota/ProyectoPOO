import java.util.InputMismatchException;
import java.util.Scanner;

public class SistemaLogia {

    private static final Scanner scanner = new Scanner(System.in);
    private static boolean logiaReservada = false;  // Simula si la logia está reservada o no

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
        System.out.println("1.- Reservar Logia");
        System.out.println("2.- Consultar Disponibilidad");
        System.out.println("3.- Cancelar Reserva");
        System.out.println("4.- Salir \n");
    }

    public static int solicitarOpcion(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                int numero = scanner.nextInt();
                scanner.nextLine();
                if (numero >= 1 && numero <= 4) {
                    return numero;
                } else {
                    System.out.println("Por favor, ingrese un número entre 1 y 4.");
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
                reservarLogia();
                break;
            case 2:
                consultarDisponibilidad();
                break;
            case 3:
                cancelarReserva();
                break;
            case 4:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
        }
    }

    public static void reservarLogia() {
        if (logiaReservada) {
            System.out.println("La logia ya está reservada.");
        } else {
            logiaReservada = true;
            System.out.println("Logia reservada con éxito.");
        }
    }

    public static void consultarDisponibilidad() {
        if (logiaReservada) {
            System.out.println("La logia no está disponible.");
        } else {
            System.out.println("La logia está disponible.");
        }
    }

    public static void cancelarReserva() {
        if (logiaReservada) {
            logiaReservada = false;
            System.out.println("Reserva cancelada con éxito.");
        } else {
            System.out.println("No hay reservas para cancelar.");
        }
    }
}
