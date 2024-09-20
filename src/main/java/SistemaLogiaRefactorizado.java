import java.util.*;

public class SistemaLogiaRefactorizado {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Integer> logias7Personas = new ArrayList<>();
    private static final List<Integer> logias5Personas = new ArrayList<>();
    private static final List<Integer> logias3Personas = new ArrayList<>();
    private static final List<String> usuarios = new ArrayList<>();
    private static final Map<String, String> reservas = new HashMap<>();  // Almacena "numeroLogia:capacidad"
    private static String matriculaActual = null;

    public static void main(String[] args) {
        iniciarSistema(); // Único método para iniciar todo el programa
    }

    public static void iniciarSistema() {
        inicializarLogias();
        inicializarUsuarios();
        ejecutarOpcionesSistema();
    }

    // Modularización de usuarios
    public static void inicializarUsuarios() {
        usuarios.add("21212121k21"); // Nikazo
        usuarios.add("21212121211"); // Maria
        usuarios.add("21212121212"); // Juan
    }

    public static boolean iniciarSesion() {
        System.out.println("\nIniciar Sesión");
        while (true) {
            System.out.print("Ingrese su matrícula (o ingrese 0 para volver al menú anterior): ");
            matriculaActual = limpiarMatricula(scanner.nextLine());

            if (matriculaActual.equals("0")) {
                return false;
            }

            if (usuarios.contains(matriculaActual)) {
                System.out.println("Bienvenido!");
                return true;
            } else {
                System.out.println("Usuario no encontrado. Por favor, intente nuevamente.");
            }
        }
    }

    public static boolean registrarUsuario() {
        System.out.println("\nRegistro de Usuario");
        String matricula;
        matricula = registrarUsuarioOpcion();

        if (matricula == null) {
            return false;  // Se cancela el registro
        }

        usuarios.add(matricula);
        matriculaActual = matricula;
        System.out.println("Usuario registrado exitosamente.");
        return true;
    }

    public static String registrarUsuarioOpcion() {
        while (true) {
            System.out.print("Ingrese su matrícula (o ingrese 0 para volver al menú anterior): ");
            String matricula = limpiarMatricula(scanner.nextLine());

            if (matricula.equals("0")) {
                System.out.println("Volviendo al menú principal...");
                return null;  // Se cancela el registro
            } else if (usuarios.contains(matricula)) {
                System.out.println("El usuario ya está registrado. Por favor, inicie sesión.");
                return null;  // Se cancela el registro
            } else if (!esMatriculaValida(matricula)) {
                System.out.println("Matrícula inválida. Intente nuevamente.");
            } else {
                return matricula;  // Retornamos la matrícula válida
            }
        }
    }

    // Modularización de logias
    public static void inicializarLogias() {
        inicializarLogiasPorCapacidad(logias7Personas, 3);
        inicializarLogiasPorCapacidad(logias5Personas, 4);
        inicializarLogiasPorCapacidad(logias3Personas, 3);
    }

    private static void inicializarLogiasPorCapacidad(List<Integer> logias, int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            logias.add(i);
        }
    }

    public static void mostrarDisponibilidadLogias(List<Integer> logias, String mensaje, int capacidadActual) {
        System.out.print(mensaje + " ");
        logias.stream()
                .filter(logia -> reservas.values().stream().noneMatch(reserva -> {
                    String[] partes = reserva.split(":");
                    int numeroLogia = Integer.parseInt(partes[0]);
                    int capacidad = Integer.parseInt(partes[1]);
                    return numeroLogia == logia && capacidad == capacidadActual;
                }))
                .forEach(logia -> System.out.print("Logia " + logia + " "));
        System.out.println();
    }

    // Modularización de reservas
    public static void reservarLogia() {
        if (reservas.containsKey(matriculaActual)) {
            System.out.println("Ya tienes una logia reservada.");
            return;
        }

        List<Integer> logiasSeleccionadas = seleccionarLogias();
        if (logiasSeleccionadas == null) return;

        int numeroLogia = seleccionarNumeroDeLogia(logiasSeleccionadas);
        if (numeroLogia == -1) return;

        int capacidad = obtenerCapacidadLogiaPorLogiaSeleccionada(logiasSeleccionadas);
        if (solicitarIntegrantes(numeroLogia, capacidad)) {
            completarReserva(numeroLogia, capacidad);
        } else {
            System.out.println("La reserva fue cancelada.");
        }
    }

    private static void cancelarReserva() {
        if (!reservas.containsKey(matriculaActual)) {
            System.out.println("No tienes reservas para cancelar.");
        } else {
            String reserva = reservas.get(matriculaActual);
            String[] partes = reserva.split(":");
            int numeroLogia = Integer.parseInt(partes[0]);

            reservas.remove(matriculaActual);
            System.out.println("Reserva de la logia " + numeroLogia + " cancelada con éxito.");
        }
    }

    // Menús
    public static void mostrarMenuInicial() {
        System.out.println("\nMenú Inicial\n");
        System.out.println("1.- Iniciar Sesión");
        System.out.println("2.- Registrarse");
        System.out.println("3.- Salir");
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("\nMenú Principal\n");
        System.out.println("1.- Reservar Logia");
        System.out.println("2.- Consultar Disponibilidad de una Logia");
        System.out.println("3.- Cancelar Reserva de una Logia");
        System.out.println("4.- Salir");
    }

    public static void procesarOpcionInicial(int opcion) {
        switch (opcion) {
            case 1:
                if (iniciarSesion()) {
                    ejecutarOpcionesSistema();
                }
                break;
            case 2:
                if (registrarUsuario()) {
                    System.out.println("Usuario registrado exitosamente, inicie sesión.");
                }
                break;
            case 3:
                System.out.println("Saliendo del programa...");
                break;
            default:
                mostrarMensajeOpcionInvalida();
        }
    }

    public static void procesarOpcionPrincipal(int opcion) {
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

    public static void ejecutarOpcionesSistema() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = solicitarOpcion("Ingrese una opción: ", 1, 4);
            procesarOpcionPrincipal(opcion);
        } while (opcion != 4);
    }

    private static String limpiarMatricula(String matricula) {
        return matricula.replaceAll("[^\\dk]", "");
    }

    private static boolean esMatriculaValida(String matricula) {
        return matricula.matches("\\d{8}[\\dk]\\d{2}");
    }

    private static void mostrarMensajeOpcionInvalida() {
        System.out.println("Opción inválida. Por favor, inténtelo de nuevo.");
    }

    public static void consultarDisponibilidad() {
        mostrarDisponibilidadLogias(logias7Personas, "Logias de 7 personas disponibles:", 7);
        mostrarDisponibilidadLogias(logias5Personas, "Logias de 5 personas disponibles:", 5);
        mostrarDisponibilidadLogias(logias3Personas, "Logias de 3 personas disponibles:", 3);
    }

    private static List<Integer> seleccionarLogias() {
        System.out.println("\nSeleccione el tamaño de la logia a reservar:");
        System.out.println("1.- Logia para 7 personas");
        System.out.println("2.- Logia para 5 personas");
        System.out.println("3.- Logia para 3 personas");
        System.out.println("4.- Cancelar");

        int opcion = solicitarOpcion("Ingrese una opción: ", 1, 4);
        if (opcion == 4) {
            System.out.println("Cancelando la reserva y volviendo al menú anterior...");
            return null;
        }

        return switch (opcion) {
            case 1 -> logias7Personas;
            case 2 -> logias5Personas;
            case 3 -> logias3Personas;
            default -> null;
        };
    }

    private static int seleccionarNumeroDeLogia(List<Integer> logiasSeleccionadas) {
        mostrarDisponibilidadLogias(logiasSeleccionadas, "\nLogias disponibles:", obtenerCapacidadLogiaPorLogiaSeleccionada(logiasSeleccionadas));
        int numeroLogia = solicitarNumeroLogia();

        if (!logiasSeleccionadas.contains(numeroLogia) || reservas.values().stream().anyMatch(reserva -> reserva.startsWith(numeroLogia + ":"))) {
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
            return 3;
        }
    }

    private static boolean solicitarIntegrantes(int numeroLogia, int capacidad) {
        List<String> integrantes = new ArrayList<>();
        int cantidadmiembros = capacidad - 1;

        for (int i = 1; i <= cantidadmiembros; i++) {
            String matricula;
            while (true) {
                System.out.print("Matrícula del compañero " + i + " (ingrese 0 para cancelar): ");
                matricula = limpiarMatricula(scanner.nextLine());

                if (matricula.equals("0")) {
                    System.out.println("Cancelando la reserva de la logia " + numeroLogia + ".");
                    return false;
                } else if (matricula.equals(matriculaActual)) {
                    System.out.println("No puedes ingresar tu propia matrícula.");
                } else if (!usuarios.contains(matricula)) {
                    System.out.println("La matrícula " + matricula + " no está registrada.");
                } else if (integrantes.contains(matricula)) {
                    System.out.println("La matrícula " + matricula + " ya fue ingresada.");
                } else {
                    break;
                }
            }
            integrantes.add(matricula);
        }

        return true;
    }

    private static void completarReserva(int numeroLogia, int capacidad) {
        reservas.put(matriculaActual, numeroLogia + ":" + capacidad);
        System.out.println("Logia " + numeroLogia + " reservada con éxito.");
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
