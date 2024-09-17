import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SistemaLogia2 {

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
        // Inicializar logias de 7 personas
        for (int i = 1; i <= 3; i++) {
            logias7Personas.add(new Logia(i, 7));
        }
        // Inicializar logias de 5 personas
        for (int i = 1; i <= 3; i++) {
            logias5Personas.add(new Logia(i, 5));
        }
        // Inicializar logias de 3 personas
        for (int i = 1; i <= 4; i++) {
            logias3Personas.add(new Logia(i, 3));
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

            opcion = solicitarOpcion("Seleccione una opción: ");
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
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su matrícula: ");
        String matricula = scanner.nextLine();

        usuarioActual = buscarUsuario(nombre, matricula);
        if (usuarioActual == null) {
            System.out.println("Usuario no encontrado. Por favor, regístrese primero.");
        } else {
            System.out.println("Bienvenido, " + usuarioActual.getNombre() + "!");
        }
    }

    public static Usuario buscarUsuario(String nombre, String matricula) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre) && usuario.getMatricula().equals(matricula)) {
                return usuario;
            }
        }
        return null;
    }

    public static void registrarUsuario() {
        System.out.println("\nRegistro de Usuario");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su matrícula: ");
        String matricula = scanner.nextLine();

        if (buscarUsuario(nombre, matricula) == null) {
            usuarioActual = new Usuario(nombre, matricula);
            usuarios.add(usuarioActual);
            System.out.println("Usuario registrado exitosamente. Bienvenido, " + usuarioActual.getNombre() + "!");
        } else {
            System.out.println("El usuario ya está registrado. Por favor, inicie sesión.");
        }
    }

    public static void iniciarPrograma() {
        int opcion;
        do {
            mostrarMenu();
            opcion = solicitarOpcion("Ingrese una opción: ");
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

    public static void consultarDisponibilidad() {
        System.out.println("\nLogias de 7 personas disponibles:");
        for (Logia logia : logias7Personas) {
            if (!logia.isReservada()) {
                System.out.println("Logia " + logia.getNumero());
            }
        }

        System.out.println("\nLogias de 5 personas disponibles:");
        for (Logia logia : logias5Personas) {
            if (!logia.isReservada()) {
                System.out.println("Logia " + logia.getNumero());
            }
        }

        System.out.println("\nLogias de 3 personas disponibles:");
        for (Logia logia : logias3Personas) {
            if (!logia.isReservada()) {
                System.out.println("Logia " + logia.getNumero());
            }
        }
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

        int opcion = solicitarOpcion("Ingrese una opción: ");
        List<Logia> logiasSeleccionadas = null;

        switch (opcion) {
            case 1:
                logiasSeleccionadas = logias7Personas;
                break;
            case 2:
                logiasSeleccionadas = logias5Personas;
                break;
            case 3:
                logiasSeleccionadas = logias3Personas;
                break;
            default:
                System.out.println("Opción inválida.");
                return;
        }

        System.out.println("\nLogias disponibles:");
        for (Logia logia : logiasSeleccionadas) {
            if (!logia.isReservada()) {
                System.out.println("Logia " + logia.getNumero());
            }
        }

        int numeroLogia = solicitarNumeroLogia();
        Logia logiaSeleccionada = null;

        for (Logia logia : logiasSeleccionadas) {
            if (logia.getNumero() == numeroLogia && !logia.isReservada()) {
                logiaSeleccionada = logia;
                break;
            }
        }

        if (logiaSeleccionada == null) {
            System.out.println("La logia seleccionada no está disponible.");
            return;
        }

        System.out.println("Ingrese las matrículas de los integrantes:");
        List<String> integrantes = new ArrayList<>();
        for (int i = 0; i < logiaSeleccionada.getCapacidad(); i++) {
            System.out.print("Matrícula del integrante " + (i + 1) + ": ");
            integrantes.add(scanner.nextLine());
        }

        logiaSeleccionada.setReservada(true);
        logiaSeleccionada.setIntegrantes(integrantes);
        usuarioActual.setLogiaReservada(logiaSeleccionada);
        System.out.println("Logia " + logiaSeleccionada.getNumero() + " reservada con éxito.");
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
                scanner.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
                scanner.nextLine();
            }
        }
    }

    // Clase interna para representar una logia
    public static class Logia {
        private int numero;
        private int capacidad;
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

    // Clase interna para representar un usuario
    public static class Usuario {
        private String nombre;
        private String matricula;
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
