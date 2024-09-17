import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SistemaLogiaMetodos5 {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Integer> logias7Personas = new ArrayList<>();
    private static final List<Integer> logias5Personas = new ArrayList<>();
    private static final List<Integer> logias3Personas = new ArrayList<>();
    private static final List<String> usuarios = new ArrayList<>();
    private static final List<String> usuariosConReserva = new ArrayList<>();
    private static int logiaReservadaActual = 0;

    public static void main(String[] args) {
        inicializarLogias();
        inicializarUsuarios();
        mostrarMenuInicial();
    }

    public static void inicializarLogias() {
        inicializarLogiasPorCapacidad(logias7Personas, 3);
        inicializarLogiasPorCapacidad(logias5Personas, 3);
        inicializarLogiasPorCapacidad(logias3Personas, 4);
    }

    private static void inicializarLogiasPorCapacidad(List<Integer> logias, int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            logias.add(i);
        }
    }

    public static void inicializarUsuarios() {
        usuarios.add("22184444320"); // Nikazo
        usuarios.add("22184444321"); // Maria
        usuarios.add("22184444322"); // Juan
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
                registrarUsuario();
                iniciarPrograma();
                break;
            case 3:
                System.out.println("Saliendo del programa...");
                break;
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
        }
    }

    public static boolean iniciarSesion() {
        System.out.println("\nIniciar Sesión");
        System.out.print("Ingrese su matrícula: ");
        String matricula = limpiarMatricula(scanner.nextLine());

        if (usuarios.contains(matricula)) {
            System.out.println("Bienvenido!");
            return true;
        } else {
            System.out.println("Usuario no encontrado. Por favor, regístrese primero.");
            return false;
        }
    }

    public static void registrarUsuario() {
        System.out.println("\nRegistro de Usuario");
        String matricula;

        while (true) {
            System.out.print("Ingrese su matrícula: ");
            matricula = limpiarMatricula(scanner.nextLine());

            if (!esMatriculaValida(matricula)) {
                System.out.println("Matrícula inválida. Debe ser un número de 11 dígitos con un posible 'k' en la novena posición. Intente nuevamente.");
            } else if (usuarios.contains(matricula)) {
                System.out.println("El usuario ya está registrado. Por favor, inicie sesión.");
                return;
            } else {
                break;
            }
        }

        usuarios.add(matricula);
        System.out.println("Usuario registrado exitosamente.");
    }

    private static String limpiarMatricula(String matricula) {
        // Elimina todos los caracteres que no sean dígitos o 'k'
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
                System.out.println("Opción inválida. Intente de nuevo.");
        }
    }

    public static void consultarDisponibilidad() {
        mostrarDisponibilidadLogias(logias7Personas, "Logias de 7 personas disponibles:");
        mostrarDisponibilidadLogias(logias5Personas, "Logias de 5 personas disponibles:");
        mostrarDisponibilidadLogias(logias3Personas, "Logias de 3 personas disponibles:");
    }

    private static void mostrarDisponibilidadLogias(List<Integer> logias, String mensaje) {
        System.out.println("\n" + mensaje);
        logias.stream()
                .filter(logia -> !usuariosConReserva.contains(logia.toString()))
                .forEach(logia -> System.out.println("Logia " + logia));
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

        int opcion = solicitarOpcion("Ingrese una opción: ", 1, 3);
        List<Integer> logiasSeleccionadas = seleccionarLogiasPorOpcion(opcion);

        if (logiasSeleccionadas == null) {
            System.out.println("Opción inválida.");
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
        if (logiasSeleccionadas.equals(logias7Personas)) return 7;
        if (logiasSeleccionadas.equals(logias5Personas)) return 5;
        return 3;  // Asumiendo que si no es ninguna de las anteriores, es una logia de 3 personas.
    }

    private static void completarReserva(int numeroLogia, int capacidad) {
        solicitarIntegrantes(numeroLogia, capacidad);
        logiaReservadaActual = numeroLogia;
        System.out.println("Logia " + logiaReservadaActual + " reservada con éxito.");
    }

    private static List<Integer> seleccionarLogiasPorOpcion(int opcion) {
        return switch (opcion) {
            case 1 -> logias7Personas;
            case 2 -> logias5Personas;
            case 3 -> logias3Personas;
            default -> null;
        };
    }

    private static void solicitarIntegrantes(int numeroLogia, int capacidad) {
        List<String> integrantes = new ArrayList<>();
        for (int i = 1; i < capacidad; i++) { // Comienza en 1 para omitir al usuario actual
            String matricula;
            while (true) {
                System.out.print("Matrícula del compañero " + i + ": ");
                matricula = limpiarMatricula(scanner.nextLine());

                if (!usuarios.contains(matricula)) {
                    System.out.println("La matrícula " + matricula + " no está registrada. Por favor, ingrese una matrícula válida.");
                } else if (integrantes.contains(matricula)) {
                    System.out.println("La matrícula " + matricula + " ya fue ingresada. Por favor, ingrese una matrícula diferente.");
                } else if (matricula.equals(limpiarMatricula(scanner.nextLine()))) {
                    System.out.println("No sabia que existías dos veces XD");
                } else {
                    break;
                }
            }
            integrantes.add(matricula);
        }
        // Agrega la matrícula del usuario actual
        usuariosConReserva.add(String.valueOf(numeroLogia));
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
}
