package InterfazCajeroAutomatico;

import javax.swing.JOptionPane;


//opcion de revisar cajeros automáticos


public class Menu {

    private Banco banco;
    private CajeroAutomatico cajero;

    public Menu() {
        banco = new Banco("Banco Nación", 100);
        cajero = new CajeroAutomatico(banco, 50000);
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
        JOptionPane.showMessageDialog(null, "--- BIENVENIDO AL CAJERO AUTOMATICO ---");

        while (true) {
            try {

                String user = JOptionPane.showInputDialog(null, "Ingrese Usuario (o escriba 'salir' o Cancelar para cerrar):");

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

    private void menuUsuario(Usuario u) {

        Cuenta[] cuentasDisponibles = new Cuenta[u.getCantCuentas()];
        for (int i = 0; i < u.getCantCuentas(); i++) {
            cuentasDisponibles[i] = u.getCuentas()[i];
        }

        boolean cambiarCuenta = true;

        while (cambiarCuenta) {

            Cuenta cuentaActual = (Cuenta) JOptionPane.showInputDialog(
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
                                boolean exito = cajero.extraer(u, cuentaActual, ext);
                                if (exito) {
                                    JOptionPane.showMessageDialog(null, "Extracción exitosa. Retire su dinero.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Saldo insuficiente o límite excedido.", "Error", JOptionPane.ERROR_MESSAGE);
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
                }
            }
        }
    }

    private void menuAdmin(Usuario u) {
        int opcion = -1;
        while (opcion != 0) {
            try {
                String menu = "--- Menú Administrador ---\n"
                        + "1. Listar Usuarios (Ver Consola)\n"
                        + "2. Ordenar Usuarios (Burbujeo)\n"
                        + "3. Cargar efectivo al Cajero\n"
                        + "0. Salir\n\n"
                        + "Elija opción:";

                String input = JOptionPane.showInputDialog(null, menu);

                if (input == null) {
                    break;
                }

                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1:

                        banco.listarUsuarios();
                        JOptionPane.showMessageDialog(null, "El listado se ha generado en la CONSOLA de salida.");
                        break;
                    case 2:
                        banco.ordenarUsuariosPorNumero();
                        JOptionPane.showMessageDialog(null, "Usuarios ordenados correctamente.");
                        break;
                    case 3:
                        String montoStr = JOptionPane.showInputDialog("Monto a cargar:");
                        if (montoStr != null) {
                            double monto = Double.parseDouble(montoStr);
                            cajero.cargarEfectivo(monto);
                            JOptionPane.showMessageDialog(null, "Carga exitosa.");
                        }
                        break;
                    case 0:
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
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public CajeroAutomatico getCajero() {
        return cajero;
    }

    public void setCajero(CajeroAutomatico cajero) {
        this.cajero = cajero;
    }
}
