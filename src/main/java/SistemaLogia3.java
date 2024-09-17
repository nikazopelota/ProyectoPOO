import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SistemaLogia3 {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Logia> logias7Personas = new ArrayList<>();
    private static final List<Logia> logias5Personas = new ArrayList<>();
    private static final List<Logia> logias3Personas = new ArrayList<>();
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static Usuario usuarioActual;

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
        if (usuarioActual.getLogiaReservada() != null) {
            System.out.println("Ya tienes una logia reservada: Logia " + usuarioActual.getLogiaReservada().getNumero() + ".");
            return;
        }

        System.out.println("\nSeleccione el tamaño de la logia a reservar:");
        System.out.println("1.- Logia para 7 personas");
        System.out.println("2.- Logia para 5 personas");
        System.out.println("3.- Logia para 3 personas");

        int opcion = solicitarOpcion("Ingrese una opción: ", 1, 3);
        List<Logia> logiasSeleccionadas = seleccionarLogiasPorOpcion(opcion);

        if (logiasSeleccionadas == null) {
            System.out.println("Opción inválida.");
            return;
        }

        mostrarDisponibilidadLogias(logiasSeleccionadas, "\nLogias disponibles:");
        int numeroLogia = solicitarNumeroLogia();
        Logia logiaSeleccionada = buscarLogiaDisponible(logiasSeleccionadas, numeroLogia);

        if (logiaSeleccionada == null) {
            System.out.println("La logia seleccionada no está disponible.");
            return;
        }

        List<String> integrantes = solicitarIntegrantes(logiaSeleccionada.getCapacidad());
        logiaSeleccionada.setReservada(true);
        logiaSeleccionada.setIntegrantes(integrantes);
        usuarioActual.setLogiaReservada(logiaSeleccionada);
        System.out.println("Logia " + logiaSeleccionada.getNumero() + " reservada con éxito.");
    }

    private static List<Logia> seleccionarLogiasPorOpcion(int opcion) {
        switch (opcion) {
            case 1:
                return logias7Personas;
            case 2:
                return logias5Personas;
            case 3:
                return logias3Personas;
            default:
                return null;
        }
    }

    private static Logia buscarLogiaDisponible(List<Logia> logias, int numeroLogia) {
        return logias.stream()
                .filter(logia -> logia.getNumero() == numeroLogia && !logia.isReservada())
                .findFirst()
                .orElse(null);
    }

    private static List<String> solicitarIntegrantes(int capacidad) {
        List<String> integrantes = new ArrayList<>();
        for (int i = 0; i < capacidad; i++) {
            String matricula;
            while (true) {
                System.out.print("Matrícula del integrante " + (i + 1) + ": ");
                matricula = scanner.nextLine().trim();

                if (!esUsuarioRegistrado(matricula)) {
                    System.out.println("La matrícula " + matricula + " no está registrada. Por favor, ingrese una matrícula válida.");
                } else if (integrantes.contains(matricula)) {
                    System.out.println("La matrícula " + matricula + " ya fue ingresada. Por favor, ingrese una matrícula diferente.");
                } else {
                    break;
                }
            }
            integrantes.add(matricula);
        }
        return integrantes;
    }

    private static boolean esUsuarioRegistrado(String matricula) {
        return usuarios.stream()
                .anyMatch(usuario -> usuario.getMatricula().equals(matricula));
    }

    public static void cancelarReserva() {
        if (usuarioActual.getLogiaReservada() == null) {
            System.out.println("No tienes reservas para cancelar.");
        } else {
            Logia logiaReservada = usuarioActual.getLogiaReservada();
            logiaReservada.setReservada(false);
            logiaReservada.setIntegrantes(null);
            usuarioActual.setLogiaReservada(null);
            System.out.println("Reserva de la logia " + logiaReservada.getNumero() + " cancelada con éxito.");
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
