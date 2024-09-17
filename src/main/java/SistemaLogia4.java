import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SistemaLogia4 {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Logia> logias7Personas = new ArrayList<>();
    private static final List<Logia> logias5Personas = new ArrayList<>();
    private static final List<Logia> logias3Personas = new ArrayList<>();
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final List<String> usuariosConReserva = new ArrayList<>();
    private static Usuario usuarioActual;
    private static int logiaReservadaActual = 0;

    public static void main(String[] args) {
        inicializarLogias();
        inicializarUsuarios();
        mostrarMenuInicial();
    }

    public static void inicializarLogias() {
        inicializarLogiasPorCapacidad(logias7Personas, 7, 3);
        inicializarLogiasPorCapacidad(logias5Personas, 5, 3);
        inicializarLogiasPorCapacidad(logias3Personas, 3, 4);
    }

    private static void inicializarLogiasPorCapacidad(List<Logia> logias, int capacidad, int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            logias.add(new Logia(i, capacidad));
        }
    }

    public static void inicializarUsuarios() {
        usuarios.add(new Usuario("Nikazo", "22184444320"));
        usuarios.add(new Usuario("Maria", "22184444321"));
        usuarios.add(new Usuario("Juan", "22184444322"));
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
                iniciarSesion();
                if (usuarioActual != null) {
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

    public static void iniciarSesion() {
        System.out.println("\nIniciar Sesión");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Ingrese su matrícula: ");
        String matricula = scanner.nextLine().trim();

        usuarioActual = buscarUsuario(nombre, matricula);
        if (usuarioActual == null) {
            System.out.println("Usuario no encontrado. Por favor, regístrese primero.");
        } else {
            System.out.println("Bienvenido, " + usuarioActual.getNombre() + "!");
        }
    }

    public static Usuario buscarUsuario(String nombre, String matricula) {
        return usuarios.stream()
                .filter(usuario -> usuario.getNombre().equalsIgnoreCase(nombre) && usuario.getMatricula().equals(matricula))
                .findFirst()
                .orElse(null);
    }

    public static void registrarUsuario() {
        System.out.println("\nRegistro de Usuario");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine().trim();

        String matricula;
        while (true) {
            System.out.print("Ingrese su matrícula: ");
            matricula = scanner.nextLine().trim();

            // Elimina puntos y guiones antes de la validación
            matricula = limpiarMatricula(matricula);

            // Validación de matrícula
            if (!esMatriculaValida(matricula)) {
                System.out.println("Matrícula inválida. Debe ser un número de 11 dígitos con un posible 'k' en la novena posición. Intente nuevamente.");
            } else if (buscarUsuario(nombre, matricula) != null) {
                System.out.println("El usuario ya está registrado. Por favor, inicie sesión.");
                return;
            } else {
                break;
            }
        }

        usuarioActual = new Usuario(nombre, matricula);
        usuarios.add(usuarioActual);
        System.out.println("Usuario registrado exitosamente. Bienvenido, " + usuarioActual.getNombre() + "!");
    }

    private static String limpiarMatricula(String matricula) {
        // Elimina puntos y guiones
        return matricula.replace(".", "").replace("-", "");
    }

    private static boolean esMatriculaValida(String matricula) {
        // Verifica que la matrícula tenga 11 caracteres y que el noveno sea un dígito o una 'k'
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
                scanner.nextLine();  // Limpia el buffer
                if (numero >= min && numero <= max) {
                    return numero;
                } else {
                    System.out.println("Por favor, ingrese un número entre " + min + " y " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
                scanner.nextLine();  // Limpia el buffer
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

    private static void mostrarDisponibilidadLogias(List<Logia> logias, String mensaje) {
        System.out.println("\n" + mensaje);
        logias.stream()
                .filter(logia -> !logia.isReservada())
                .forEach(logia -> System.out.println("Logia " + logia.getNumero()));
    }

    public static void reservarLogia() {
        if (logiaReservadaActual != 0) {
            System.out.println("Ya tienes una logia reservada: Logia " + logiaReservadaActual + ".");
            return;
        }

        System.out.println("\nSeleccione el tamaño de la logia a reservar:");
        System.out.println("1.- Logia para 7 personas");
        System.out.println("2.- Logia para 5 personas");
        System.out.println("3.- Logia para 3 personas");

        int opcion = solicitarOpcion("Ingrese una opción: ", 1, 3);
        List<Integer> logiasSeleccionadas = seleccionarLogiasPorOpcion(opcion);

        if (logiasSeleccionadas == null) {
            System.out.println("Opción inválida.");
            return;
        }

        mostrarDisponibilidadLogiasSimples(logiasSeleccionadas, "\nLogias disponibles:");
        int numeroLogia = solicitarNumeroLogia();

        if (!logiasSeleccionadas.contains(numeroLogia)) {
            System.out.println("La logia seleccionada no está disponible.");
            return;
        }

        solicitarIntegrantes(numeroLogia, opcion == 1 ? 7 : opcion == 2 ? 5 : 3);
        logiaReservadaActual = numeroLogia;
        System.out.println("Logia " + numeroLogia + " reservada con éxito.");
    }

    private static List<Integer> seleccionarLogiasPorOpcion(int opcion) {
        switch (opcion) {
            case 1:
                return List.of(1, 2, 3);
            case 2:
                return List.of(4, 5, 6);
            case 3:
                return List.of(7, 8, 9, 10);
            default:
                return null;
        }
    }

    private static void mostrarDisponibilidadLogiasSimples(List<Integer> logias, String mensaje) {
        System.out.println(mensaje);
        logias.stream()
                .filter(logia -> !usuariosConReserva.contains(String.valueOf(logia)))
                .forEach(logia -> System.out.println("Logia " + logia));
    }

    private static void solicitarIntegrantes(int numeroLogia, int capacidad) {
        List<String> integrantes = new ArrayList<>();
        integrantes.add(usuarioActual.getMatricula()); // Agrega la matrícula del usuario actual como primer integrante

        for (int i = 2; i <= capacidad; i++) { // Comienza en 2 porque el primer integrante es el usuario actual
            String matricula;
            while (true) {
                System.out.print("Matrícula del compañero " + (i - 1) + ": ");
                matricula = scanner.nextLine().trim();

                if (matricula.equals(usuarioActual.getMatricula())) {
                    System.out.println("No sabía que existías dos veces XD. Por favor, ingrese una matrícula diferente.");
                } else {
                    String finalMatricula = matricula;
                    if (usuarios.stream().noneMatch(u -> u.getMatricula().equals(finalMatricula))) {
                        System.out.println("La matrícula " + matricula + " no está registrada. Por favor, ingrese una matrícula válida.");
                    } else if (integrantes.contains(matricula)) {
                        System.out.println("La matrícula " + matricula + " ya fue ingresada. Por favor, ingrese una matrícula diferente.");
                    } else {
                        break;
                    }
                }
            }
            integrantes.add(matricula);
        }
        // Se asume que `usuariosConReserva` gestiona las reservas de logias correctamente.
        usuariosConReserva.add(String.valueOf(numeroLogia));
    }

    public static void cancelarReserva() {
        if (logiaReservadaActual == 0) {
            System.out.println("No tienes reservas para cancelar.");
        } else {
            usuariosConReserva.remove(String.valueOf(logiaReservadaActual));
            logiaReservadaActual = 0;
            System.out.println("Reserva de la logia cancelada con éxito.");
        }
    }

    public static int solicitarNumeroLogia() {
        while (true) {
            try {
                System.out.print("Ingrese el número de logia: ");
                int numero = scanner.nextInt();
                scanner.nextLine();  // Limpia el buffer
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
                scanner.nextLine();  // Limpia el buffer
            }
        }
    }

    // Clases internas
    public static class Logia {
        private final int numero;
        private final int capacidad;
        private boolean reservada;
        private List<String> integrantes;

        public Logia(int numero, int capacidad) {
            this.numero = numero;
            this.capacidad = capacidad;
            this.reservada = false;
            this.integrantes = null;
        }

        public int getNumero() {
            return numero;
        }

        public int getCapacidad() {
            return capacidad;
        }

        public boolean isReservada() {
            return reservada;
        }

        public void setReservada(boolean reservada) {
            this.reservada = reservada;
        }

        public List<String> getIntegrantes() {
            return integrantes;
        }

        public void setIntegrantes(List<String> integrantes) {
            this.integrantes = integrantes;
        }
    }

    public static class Usuario {
        private final String nombre;
        private final String matricula;
        private Logia logiaReservada;

        public Usuario(String nombre, String matricula) {
            this.nombre = nombre;
            this.matricula = matricula;
            this.logiaReservada = null;
        }

        public String getNombre() {
            return nombre;
        }

        public String getMatricula() {
            return matricula;
        }

        public Logia getLogiaReservada() {
            return logiaReservada;
        }

        public void setLogiaReservada(Logia logiaReservada) {
            this.logiaReservada = logiaReservada;
        }
    }
}
