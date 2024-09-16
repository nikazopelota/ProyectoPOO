import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SistemaLogia3 {

    private static final Scanner scanner = new Scanner(System.in);
    private static final boolean[] logias = new boolean[10];  // Array para 10 logias (true = reservada, false = disponible)
    private static final List<Usuario> usuarios = new ArrayList<>(); // Lista de usuarios registrados
    private static Usuario usuarioActual; // Usuario que ha iniciado sesión

    public static void main(String[] args) {
        inicializarUsuarios();
        iniciarSesion();
        iniciarPrograma();
    }

    public static void inicializarUsuarios() {
        usuarios.add(new Usuario("Nikazo", "22184444320"));
        usuarios.add(new Usuario("Maria", "22184444321"));
        usuarios.add(new Usuario("Juan", "22184444322"));
    }

    public static void iniciarSesion() {
        System.out.println("Iniciar Sesión");
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su matrícula: ");
        String matricula = scanner.nextLine();

        usuarioActual = buscarUsuario(nombre, matricula);
        if (usuarioActual == null) {
            System.out.println("Usuario no registrado. Proceda a registrarse.");
            registrarUsuario(nombre, matricula);
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

    public static void registrarUsuario(String nombre, String matricula) {
        usuarioActual = new Usuario(nombre, matricula);
        usuarios.add(usuarioActual);
        System.out.println("Usuario registrado exitosamente. Bienvenido, " + usuarioActual.getNombre() + "!");
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

    public static void reservarLogia() {
        int numeroLogia = solicitarNumeroLogia();
        if (logias[numeroLogia - 1]) {
            System.out.println("La logia " + numeroLogia + " ya está reservada.");
        } else {
            logias[numeroLogia - 1] = true;
            System.out.println("Logia " + numeroLogia + " reservada con éxito.");
        }
    }

    public static void consultarDisponibilidad() {
        int numeroLogia = solicitarNumeroLogia();
        if (logias[numeroLogia - 1]) {
            System.out.println("La logia " + numeroLogia + " no está disponible.");
        } else {
            System.out.println("La logia " + numeroLogia + " está disponible.");
        }
    }

    public static void cancelarReserva() {
        int numeroLogia = solicitarNumeroLogia();
        if (logias[numeroLogia - 1]) {
            logias[numeroLogia - 1] = false;
            System.out.println("Reserva de la logia " + numeroLogia + " cancelada con éxito.");
        } else {
            System.out.println("No hay reservas en la logia " + numeroLogia + " para cancelar.");
        }
    }

    public static int solicitarNumeroLogia() {
        while (true) {
            try {
                System.out.print("Ingrese el número de logia (1-10): ");
                int numero = scanner.nextInt();
                scanner.nextLine();
                if (numero >= 1 && numero <= 10) {
                    return numero;
                } else {
                    System.out.println("Por favor, ingrese un número entre 1 y 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número entero válido.");
                scanner.nextLine();
            }
        }
    }

    // Clase interna para representar un usuario
    public static class Usuario {
        private String nombre;
        private String matricula;

        public Usuario(String nombre, String matricula) {
            this.nombre = nombre;
            this.matricula = matricula;
        }

        public String getNombre() {
            return nombre;
        }

        public String getMatricula() {
            return matricula;
        }
    }
}

