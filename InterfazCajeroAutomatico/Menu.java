package InterfazCajeroAutomatico;

import javax.swing.JOptionPane;

public class Menu {

    private Banco banco;
    private CajeroAutomatico cajero;

    public Menu() {
        banco = new Banco("Banco Nación", 100);
        cajero = new CajeroAutomatico(banco, 50000);
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() { // Crea y precarga usuarios y cuentas para inicializar el sistema.
        Usuario u1 = new Usuario("Juan", "Perez", "12345678", "user1", 1234, "Activo", false);
        u1.agregarCuenta(new CajaAhorro("CA-001", 5000, 2000));
        u1.agregarCuenta(new CuentaCorriente("CC-001", 1000, 5000));

        Usuario u2 = new Usuario("Ana", "Gomez", "87654321", "user2", 4321, "Activo", false); 
        u2.agregarCuenta(new CajaAhorro("CA-002", 7000, 3000));
        
        Usuario admin = new Usuario("Admin", "Sist", "0000", "admin", 9999, "Activo", true);

        banco.agregarUsuario(u1);
        banco.agregarUsuario(u2);
        banco.agregarUsuario(admin);
    }

    public void iniciar() { // Punto de entrada del flujo del programa (main loop del cajero).
        JOptionPane.showMessageDialog(null, "--- BIENVENIDO AL CAJERO AUTOMATICO ---");

        while (true) {
            try {

                String user = JOptionPane.showInputDialog(null, "Ingrese Usuario (o escriba 'salir' o Cancelar para cerrar):");
                // Solicita el ID de usuario a través de una ventana de diálogo.

                if (user == null || user.equalsIgnoreCase("salir")) {
                    break;
                }

                String pinStr = JOptionPane.showInputDialog(null, "Ingrese PIN para " + user + ":");
                if (pinStr == null) {
                    continue;
                }

                int pin = Integer.parseInt(pinStr);

                Usuario usuarioLogueado = cajero.iniciarSesion(user, pin);

                if (usuarioLogueado != null) {
                    JOptionPane.showMessageDialog(null, "Bienvenido, " + usuarioLogueado.getNombre());
                    if (usuarioLogueado.esAdmin()) {
                        menuAdmin(usuarioLogueado);
                    } else {
                        menuUsuario(usuarioLogueado);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: El PIN debe ser numérico.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void menuUsuario(Usuario u) { // Menú dedicado a clientes normales con opciones de transacción.

        Cuenta[] cuentasDisponibles = new Cuenta[u.getCantCuentas()]; // Obtiene las cuentas del usuario y las prepara para la selección.
        for (int i = 0; i < u.getCantCuentas(); i++) {
            cuentasDisponibles[i] = u.getCuentas()[i];
        }

        boolean cambiarCuenta = true;

        while (cambiarCuenta) {

            Cuenta cuentaActual = (Cuenta) JOptionPane.showInputDialog( // Permite al usuario seleccionar una de sus cuentas (CajaAhorro o CuentaCorriente).
                    null,
                    "Seleccione la cuenta con la que desea operar:",
                    "Selección de Cuenta",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    cuentasDisponibles,
                    cuentasDisponibles[0]
            );

            if (cuentaActual == null) {
                return;
            }

            int opcion = -1;
            while (opcion != 0 && opcion != 9) {
                try {
                    String menu = "--- Operando con: " + cuentaActual.getClass().getSimpleName() + " " + cuentaActual.getNumero() + " ---\n"
                            + "1. Consultar Saldo\n"
                            + "2. Depositar\n"
                            + "3. Extraer\n"
                            + "9. CAMBIAR DE CUENTA\n"
                            + "0. Cerrar Sesión\n\n"
                            + "Elija opción:";

                    String input = JOptionPane.showInputDialog(null, menu);

                    if (input == null) {
                        opcion = 0;
                        cambiarCuenta = false;
                        break;
                    }

                    opcion = Integer.parseInt(input);

                    switch (opcion) {
                        case 1:
                            double saldo = cajero.consultarSaldo(cuentaActual);
                            JOptionPane.showMessageDialog(null, "Saldo actual: $" + saldo);
                            break;
                        case 2:
                            String depStr = JOptionPane.showInputDialog("Monto a depositar:");
                            if (depStr != null) {
                                double dep = Double.parseDouble(depStr);
                                cajero.depositar(u, cuentaActual, dep);
                                JOptionPane.showMessageDialog(null, "Depósito realizado. Nuevo saldo: $" + cuentaActual.getSaldo());
                            }
                            break;
                        case 3:
                            String extStr = JOptionPane.showInputDialog("Monto a extraer:");
                            if (extStr != null) {
                                double ext = Double.parseDouble(extStr);
                                
                                int status = cajero.extraer(u, cuentaActual, ext); // Llama a la lógica de extracción y recibe el código de estado (0, 1, 2).

                                switch (status) { // Traduce el código de estado retornado por CajeroAutomatico a un mensaje de JOptionPane.
                                    case 0:
                                        JOptionPane.showMessageDialog(null, "Extracción exitosa. Retire su dinero.");
                                        break;
                                    case 1:
                                        JOptionPane.showMessageDialog(null, "El cajero no tiene suficiente efectivo.", "Error", JOptionPane.ERROR_MESSAGE);
                                        break;
                                    case 2:
                                        JOptionPane.showMessageDialog(null, "Saldo insuficiente o límite excedido.", "Error", JOptionPane.ERROR_MESSAGE);
                                        break;
                                }
                            }
                            break;
                        case 9:
                            break; 
                        case 0:
                            JOptionPane.showMessageDialog(null, "Cerrando sesión...");
                            cambiarCuenta = false;
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Opción inválida.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null, "Operación cancelada o valor no válido.");
                }
            }
        }
    }

    private void menuAdmin(Usuario u) { // Menú exclusivo con operaciones de mantenimiento y gestión.
        int opcion = -1;
        while (opcion != 0) {
            try {
                String menu = "    MENÚ ADMINISTRADOR    \n" // Define las opciones de administración, incluyendo los dos algoritmos de ordenamiento.
                        + "1. Listar Usuarios\n"
                        + "2. Ordenar Usuarios (Burbujeo)\n"
                        + "3. Ordenar Usuarios (Selección)\n" 
                        + "4. Cargar efectivo al Cajero\n"
                        + "5. Consultar Efectivo Cajero\n"
                        + "0. Salir\n\n"
                        + "Elija opción:";

                String input = JOptionPane.showInputDialog(null, menu);

                if (input == null) {
                    break;
                }

                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1:
                        String listado = banco.listarUsuarios();  // Llama a la función de listado del banco que devuelve el resultado como String.
                        JOptionPane.showMessageDialog(null, listado, "Listado de Usuarios", JOptionPane.INFORMATION_MESSAGE);
                        // Muestra el listado de usuarios en una ventana de diálogo, cumpliendo la consigna de no usar consola.
                        break;
                    case 2:
                        banco.ordenarUsuariosPorNumeroBurbujeo(); 
                        JOptionPane.showMessageDialog(null, "Usuarios ordenados correctamente por Burbujeo.");
                        break;
                    case 3:
                        banco.ordenarUsuariosPorNumeroSeleccion(); // Ejecuta el Ordenamiento por Selección, afectando el orden interno del arreglo de usuarios.
                        JOptionPane.showMessageDialog(null, "Usuarios ordenados correctamente por Selección.");
                        break;
                    case 4:
                        String montoStr = JOptionPane.showInputDialog("Monto a cargar:");
                        if (montoStr != null) {
                            double monto = Double.parseDouble(montoStr);
                            cajero.cargarEfectivo(monto);
                            JOptionPane.showMessageDialog(null, "Carga exitosa.");
                        }
                        break;
                    case 5:
                        double efectivo = cajero.getEfectivoDisponible(); // Consulta el efectivo total en el cajero.
                        JOptionPane.showMessageDialog(null, "Efectivo disponible en el cajero: $" + efectivo);
                        break;
                    case 0:
                        JOptionPane.showMessageDialog(null, "Saliendo del menú de administrador...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
            }
        }
    }

    public static void main(String[] args) {
        new Menu().iniciar();
    }

    public Banco getBanco() {
        return this.banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public CajeroAutomatico getCajero() {
        return this.cajero;
    }

    public void setCajero(CajeroAutomatico cajero) {
        this.cajero = cajero;
    }
}