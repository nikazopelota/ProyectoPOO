import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Avance01 {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Integer> logias7Personas = new ArrayList<>();
    private static final List<Integer> logias5Personas = new ArrayList<>();
    private static final List<Integer> logias3Personas = new ArrayList<>();
    private static final List<String> usuarios = new ArrayList<>();
    private static final List<String> usuariosConReserva = new ArrayList<>();
    private static String matriculaActual = null;
    private static int logiaReservadaActual = 0;

    public static void main(String[] args) {
        inicializarLogias();
        inicializarUsuarios();
        mostrarMenuInicial();
    }

    public static void inicializarLogias() {
        inicializarLogiasPorCapacidad(logias7Personas, 3);
        inicializarLogiasPorCapacidad(logias5Personas, 3);
        inicializarLogiasPorCapacidad(logias3Personas, 3);
    }

    private static void inicializarLogiasPorCapacidad(List<Integer> logias, int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            logias.add(i);
        }
    }

    public static void inicializarUsuarios() {
        usuarios.add("21212121k21"); // Nikazo
        usuarios.add("21212121211"); // Maria
        usuarios.add("21212121212"); // Juan
        usuarios.add("21212121213");
        usuarios.add("21212121214");
        usuarios.add("21212121215");
    }

    public static void mostrarMenuInicial() {
        int opcion;
        do {
            System.out.println("\nMenú Inicial\n");
            System.out.println("1.- Iniciar Sesión");
            System.out.println("2.- Registrarse");
            System.out.println("3.- Salir");

            opcion = solicitarOpcion("Seleccione una opción: ", 1, 3);
            procesarOpcionInicial(opcion);
        } while (opcion != 3);
    }

    public static void procesarOpcionInicial(int opcion) {
        switch (opcion) {
            case 1:
                if (iniciarSesion()) {
                    iniciarPrograma();
                }
                break;
            case 2:
                if (registrarUsuario()) {
                    iniciarPrograma(); // Solo si el registro es exitoso
                }
                break;
            case 3:
                System.out.println("Saliendo del programa...");
                break;
            default:
                mostrarMensajeOpcionInvalida();
        }
    }

    public static boolean iniciarSesion() {
        System.out.println("\nIniciar Sesión");
        System.out.print("Ingrese su matrícula: ");
        matriculaActual = limpiarMatricula(scanner.nextLine());

        if (usuarios.contains(matriculaActual)) {
            System.out.println("Bienvenido!");
            return true;
        } else {
            System.out.println("Usuario no encontrado. Por favor, regístrese primero.");
            return false;
        }
    }

    public static boolean registrarUsuario() {
        System.out.println("\nRegistro de Usuario");
        String matricula;

        while (true) {
            System.out.print("Ingrese su matrícula (o ingrese 0 para volver al menú anterior): ");
            matricula = limpiarMatricula(scanner.nextLine());

            if (matricula.equals("0")) {
                System.out.println("Volviendo al menú principal...");
                return false; // Indica que el usuario decidió volver
            } else if (usuarios.contains(matricula)) {
                System.out.println("El usuario ya está registrado. Por favor, inicie sesión.");
                return false; // No se completa el registro
            } else if (!esMatriculaValida(matricula)) {
                System.out.println("Matrícula inválida. Debe ser un número de 11 dígitos con un posible 'k' en la novena posición. Intente nuevamente.");
            } else {
                break; // Sale del bucle si la matrícula es válida y no está registrada
            }
        }

        usuarios.add(matricula);
        matriculaActual = matricula;
        System.out.println("Usuario registrado exitosamente.");
        return true; // Registro exitoso
    }

    private static String limpiarMatricula(String matricula) {
        return matricula.replaceAll("[^\\dk]", "");
    }

    private static boolean esMatriculaValida(String matricula) {
        return matricula.matches("\\d{8}[\\dk]\\d{2}");
    }

    public static void iniciarPrograma() {
        int opcion;
        do {
            mostrarMenu();
            opcion = solicitarOpcion("Ingrese una opción: ", 1, 4);
            procesarOpcion(opcion);
        } while (opcion != 4);
    }

    public static void mostrarMenu() {
        System.out.println("\nMenú\n");
        System.out.println("1.- Reservar Logia");
        System.out.println("2.- Consultar Disponibilidad de una Logia");
        System.out.println("3.- Cancelar Reserva de una Logia");
        System.out.println("4.- Salir \n");
    }

    public static int solicitarOpcion(String mensaje, int min, int max) {
        while (true) {
            try {
                System.out.print(mensaje);
                int numero = scanner.nextInt();
                scanner.nextLine();
                if (numero >= min && numero <= max) {
                    return numero;
                } else {
                    System.out.println("Por favor, ingrese un número entre " + min + " y " + max + ".");
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
                mostrarMensajeOpcionInvalida();
        }
    }

    public static void consultarDisponibilidad() {
        mostrarDisponibilidadLogias(logias7Personas, "Logias de 7 personas disponibles:");
        mostrarDisponibilidadLogias(logias5Personas, "Logias de 5 personas disponibles:");
        mostrarDisponibilidadLogias(logias3Personas, "Logias de 3 personas disponibles:");
    }

    private static void mostrarDisponibilidadLogias(List<Integer> logias, String mensaje) {
        System.out.print(" " + mensaje + " ");
        logias.stream()
                .filter(logia -> !usuariosConReserva.contains(logia.toString()))
                .forEach(logia -> System.out.print("Logia " + logia + " "));
        System.out.println(); // Añade un salto de línea después de mostrar todas las logias
    }

    public static void reservarLogia() {
        if (verificarReservaExistente()) return;

        List<Integer> logiasSeleccionadas = seleccionarLogias();

        if (logiasSeleccionadas == null) return;

        int numeroLogia = seleccionarNumeroDeLogia(logiasSeleccionadas);

        if (numeroLogia == -1) return;

        completarReserva(numeroLogia, obtenerCapacidadLogiaPorLogiaSeleccionada(logiasSeleccionadas));
    }

    private static boolean verificarReservaExistente() {
        if (logiaReservadaActual != 0) {
            System.out.println("Ya tienes una logia reservada: Logia " + logiaReservadaActual + ".");
            return true;
        }
        return false;
    }

    private static List<Integer> seleccionarLogias() {
        System.out.println("\nSeleccione el tamaño de la logia a reservar:");
        System.out.println("1.- Logia para 7 personas");
        System.out.println("2.- Logia para 5 personas");
        System.out.println("3.- Logia para 3 personas");
        System.out.println("4.- Salir");  // Añade la opción para salir

        int opcion = solicitarOpcion("Ingrese una opción: ", 1, 4);

        if (opcion == 4) {
            System.out.println("Cancelando la reserva y volviendo al menú anterior...");
            return null;  // Retorna null si el usuario elige salir
        }

        List<Integer> logiasSeleccionadas = seleccionarLogiasPorOpcion(opcion);

        if (logiasSeleccionadas == null) {
            mostrarMensajeOpcionInvalida();
        }

        return logiasSeleccionadas;
    }

    private static int seleccionarNumeroDeLogia(List<Integer> logiasSeleccionadas) {
        mostrarDisponibilidadLogias(logiasSeleccionadas, "\nLogias disponibles:");
        int numeroLogia = solicitarNumeroLogia();

        if (!logiasSeleccionadas.contains(numeroLogia) || usuariosConReserva.contains(String.valueOf(numeroLogia))) {
            System.out.println("La logia seleccionada no está disponible.");
            return -1;
        }

        return numeroLogia;
    }

    private static int obtenerCapacidadLogiaPorLogiaSeleccionada(List<Integer> logiasSeleccionadas) {
        if (logiasSeleccionadas == logias7Personas) {
            return 7;
        } else if (logiasSeleccionadas == logias5Personas) {
            return 5;
        } else {
            return 3; // Asumiendo que si no es ninguna de las anteriores, es una logia de 3 personas.
        }
    }


    private static void completarReserva(int numeroLogia, int capacidad) {
        // Cambia para que devuelva si la reserva fue cancelada
        boolean reservaCompletada = solicitarIntegrantes(numeroLogia, capacidad);
        if (reservaCompletada) {
            logiaReservadaActual = numeroLogia;
            System.out.println("Logia " + logiaReservadaActual + " reservada con éxito.");
        } else {
            System.out.println("La reserva fue cancelada.");
        }
    }

    private static List<Integer> seleccionarLogiasPorOpcion(int opcion) {
        return switch (opcion) {
            case 1 -> logias7Personas;
            case 2 -> logias5Personas;
            case 3 -> logias3Personas;
            default -> null;
        };
    }

    private static boolean solicitarIntegrantes(int numeroLogia, int capacidad) {
        List<String> integrantes = new ArrayList<>();

        // El número de compañeros a pedir es exactamente capacidad - 1, ya que el usuario actual ocupa un lugar
        int cantidadCompañeros = capacidad - 1;

        // Iterar exactamente cantidadCompañeros veces
        for (int i = 1; i <= cantidadCompañeros; i++) {
            String matricula;
            while (true) {
                System.out.print("Matrícula del compañero " + i + " (ingrese 0 para cancelar): ");
                matricula = limpiarMatricula(scanner.nextLine());

                if (matricula.equals("0")) {
                    System.out.println("Cancelando la reserva de la logia " + numeroLogia + ".");
                    return false;  // Retorna false para indicar que se canceló la operación
                } else if (matricula.equals(matriculaActual)) {
                    System.out.println("No puedes ingresar tu propia matrícula. Ingresa una matrícula distinta.");
                } else if (!usuarios.contains(matricula)) {
                    System.out.println("La matrícula " + matricula + " no está registrada. Por favor, ingrese una matrícula válida.");
                } else if (integrantes.contains(matricula)) {
                    System.out.println("La matrícula " + matricula + " ya fue ingresada. Por favor, ingrese una matrícula diferente.");
                } else {
                    break;
                }
            }
            integrantes.add(matricula);  // Agregar cada matrícula válida al listado de compañeros
        }

        // Agrega la matrícula del usuario actual a la lista de reservas
        usuariosConReserva.add(String.valueOf(numeroLogia));
        return true;  // Retorna true si todo fue completado exitosamente
    }





    public static void cancelarReserva() {
        if (logiaReservadaActual == 0) {
            System.out.println("No tienes reservas para cancelar.");
        } else {
            usuariosConReserva.remove(String.valueOf(logiaReservadaActual));
            System.out.println("Reserva de la logia " + logiaReservadaActual + " cancelada con éxito.");
            logiaReservadaActual = 0;
        }
    }

    public static int solicitarNumeroLogia() {
        while (true) {
            try {
                System.out.print("Ingrese el número de logia: ");
                int numero = scanner.nextInt();
                scanner.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
                scanner.nextLine();
            }
        }
    }

    private static void mostrarMensajeOpcionInvalida() {
        System.out.println("Opción inválida. Por favor, inténtelo de nuevo.");
    }
}
