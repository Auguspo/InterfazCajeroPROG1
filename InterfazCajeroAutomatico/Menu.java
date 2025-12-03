package InterfazCajeroAutomatico;
import java.util.Scanner;

public class Menu {
    private Banco banco;
    private CajeroAutomatico cajero;
    private Scanner sc;

    public Menu() {
        banco = new Banco("Banco Nación", 100);
        cajero = new CajeroAutomatico(banco, 50000); 
        sc = new Scanner(System.in);
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        Usuario u1 = new Usuario("Juan", "Perez", "12345678", "user1", 1234, "Activo", false);
        u1.agregarCuenta(new CajaAhorro("CA-001", 5000, 2000));
        u1.agregarCuenta(new CuentaCorriente("CC-001", 1000, 5000));
        
        Usuario admin = new Usuario("Admin", "Sist", "0000", "admin", 9999, "Activo", true);

        banco.agregarUsuario(u1);
        banco.agregarUsuario(admin);
    }

    public void iniciar() {
        System.out.println("--- BIENVENIDO AL CAJERO AUTOMATICO ---");
        while (true) {
            System.out.print("\nIngrese Usuario (o 'salir'): ");
            String user = sc.next();
            if (user.equalsIgnoreCase("salir")) break;

            System.out.print("Ingrese PIN: ");
            int pin = sc.nextInt();

            Usuario usuarioLogueado = cajero.iniciarSesion(user, pin);

            if (usuarioLogueado != null) {
                System.out.println("Bienvenido, " + usuarioLogueado.getNombre());
                if (usuarioLogueado.esAdmin()) {
                    menuAdmin(usuarioLogueado);
                } else {
                    menuUsuario(usuarioLogueado);
                }
            } else {
                System.out.println("Credenciales incorrectas.");
            }
        }
    }

    private void menuUsuario(Usuario u) {
        int opcion = -1;
        Cuenta cuentaActual = u.getCuentas()[0]; 
        
        while (opcion != 0) {
            System.out.println("\n--- Menú Usuario (" + cuentaActual.getNumero() + ") ---");
            System.out.println("1. Consultar Saldo");
            System.out.println("2. Depositar");
            System.out.println("3. Extraer");
            System.out.println("0. Cerrar Sesión");
            System.out.print("Elija opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Saldo: $" + cajero.consultarSaldo(cuentaActual));
                    break;
                case 2:
                    System.out.print("Monto a depositar: ");
                    double dep = sc.nextDouble();
                    cajero.depositar(u, cuentaActual, dep);
                    break;
                case 3:
                    System.out.print("Monto a extraer: ");
                    double ext = sc.nextDouble();
                    cajero.extraer(u, cuentaActual, ext);
                    break;
                case 0:
                    System.out.println("Cerrando sesión...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void menuAdmin(Usuario u) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- Menú Administrador ---");
            System.out.println("1. Listar Usuarios");
            System.out.println("2. Ordenar Usuarios (Burbujeo)");
            System.out.println("3. Cargar efectivo al Cajero");
            System.out.println("0. Salir");
            System.out.print("Elija opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    banco.listarUsuarios();
                    break;
                case 2:
                    banco.ordenarUsuariosPorNumero();
                    break;
                case 3:
                    System.out.print("Monto a cargar: ");
                    double monto = sc.nextDouble();
                    cajero.cargarEfectivo(monto);
                    break;
                case 0: break;
            }
        }
    }

    public static void main(String[] args) {
        new Menu().iniciar();
    }
}