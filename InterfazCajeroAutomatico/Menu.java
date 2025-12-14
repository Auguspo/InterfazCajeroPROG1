package InterfazCajeroAutomatico;

import javax.swing.JOptionPane;

public class Menu { // Clase principal que maneja la interfaz del cajero automático

    private Banco banco; // Instancia del banco que contiene usuarios y cuentas
    private CajeroAutomatico cajero; // Instancia del cajero automático

    public Menu() { // Inicializa el banco y el cajero
        banco = new Banco("Banco Nación", 100);
        cajero = new CajeroAutomatico(banco, 50000);
        cargarDatosPrueba();
    }

    // Método auxiliar para validar inputs numéricos decimales positivos
    private Double solicitarMonto(String mensaje) {
        boolean IBool;
        Double valor = null;

        do { // Bucle hasta obtener un monto válido
            IBool = false;
            try { // Captura errores de formato
                String input = JOptionPane.showInputDialog(null, mensaje);

                if (input == null) {
                    return null; // Si cancela, retorna null
                }
                valor = Double.parseDouble(input);

                if (valor > 0) {
                    IBool = true;
                } else { // Monto no positivo
                    JOptionPane.showMessageDialog(null, "El monto debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) { // Error de formato
                JOptionPane.showMessageDialog(null, "Error: Ingrese un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        } while (!IBool); // Repite hasta obtener un monto válido

        return valor;
    }

    // Método auxiliar para validar inputs de opciones enteras
    private Integer solicitarEntero(String mensaje) {
        boolean IBool;
        Integer valor = null;

        do { // Bucle hasta obtener un entero válido
            IBool = false;
            try {
                String input = JOptionPane.showInputDialog(null, mensaje);
                if (input == null) {
                    return null; // Si cancela, retorna null
                }
                valor = Integer.parseInt(input);
                IBool = true;

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!IBool);

        return valor;
    }

    private void cargarDatosPrueba() { // Carga de usuarios y cuentas de prueba
        Usuario u1 = new Usuario("Juan", "Perez", "12345678", "user1", 1234, "Activo", false);
        u1.agregarCuenta(new CajaAhorro("CA-001", 5000, 2000));
        u1.agregarCuenta(new CuentaCorriente("CC-001", 1000, 5000));

        Usuario u2 = new Usuario("Ana", "Gomez", "87654321", "user2", 4321, "Activo", false);
        u2.agregarCuenta(new CajaAhorro("CA-002", 7000, 3000));

        Usuario u3 = new Usuario("Carlos", "García", "23456789", "user3", 5678, "Activo", false);
        u3.agregarCuenta(new CajaAhorro("CA-003", 2000, 1000));

        Usuario admin = new Usuario("Admin", "Sist", "0000", "admin", 9999, "Activo", true); // Usuario administrador

        // Agrega usuarios al banco
        banco.agregarUsuario(u1);
        banco.agregarUsuario(u2);
        banco.agregarUsuario(u3);
        banco.agregarUsuario(admin);
    }

    public void iniciar() { // Método principal para iniciar la interfaz
        JOptionPane.showMessageDialog(null, "BIENVENIDO AL CAJERO AUTOMÁTICO");

        while (true) { // Bucle principal de inicio de sesión
            String user = JOptionPane.showInputDialog(null, "Ingrese Usuario (o escriba 'salir' o Cancelar para cerrar):");

            if (user == null || user.equalsIgnoreCase("salir")) { // Sale del sistema
                break;
            }

            Integer pin = solicitarEntero("Ingrese PIN para " + user + ":");

            if (pin == null) {
                continue; // Si cancela el PIN, vuelve a pedir usuario
            }

            Usuario usuarioLogueado = cajero.iniciarSesion(user, pin); // Lógica de autenticación

            if (usuarioLogueado != null) {

                // Verifica si el usuario está bloqueado
                if (usuarioLogueado.getEstado().equalsIgnoreCase("Bloqueado")) {
                    JOptionPane.showMessageDialog(null,
                            "ACCESO DENEGADO.\nSu usuario se encuentra BLOQUEADO.\nContacte con la administración.",
                            "Cuenta Bloqueada",
                            JOptionPane.ERROR_MESSAGE);
                    continue; // Vuelve al inicio de sesión
                }

                JOptionPane.showMessageDialog(null, "Bienvenido, " + usuarioLogueado.getApellido() + " " + usuarioLogueado.getNombre());
                if (usuarioLogueado.esAdmin()) { // Menú administrador
                    menuAdmin(usuarioLogueado);
                } else {
                    menuUsuario(usuarioLogueado);
                }
            } else { // Falló autenticación
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void menuUsuario(Usuario u) { // Menú para usuarios normales
        Cuenta[] cuentasDisponibles = new Cuenta[u.getCantCuentas()];
        for (int i = 0; i < u.getCantCuentas(); i++) {
            cuentasDisponibles[i] = u.getCuentas()[i];
        }

        boolean cambiarCuenta = true; // Controla si el usuario quiere cambiar de cuenta

        while (cambiarCuenta) { // Bucle para cambiar de cuenta
            Cuenta cuentaActual = (Cuenta) JOptionPane.showInputDialog(
                    null,
                    "Seleccione la cuenta con la que desea operar:",
                    "Selección de Cuenta",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    cuentasDisponibles,
                    cuentasDisponibles[0]
            );

            if (cuentaActual == null) { // Si se cancela la selección, sale del menú de usuario
                return;
            }

            int opcion = -1; // Opción del menú de cuenta
            while (opcion != 0 && opcion != 9) {

                String menuText = " Operando con: " + cuentaActual.getClass().getSimpleName() + " " + cuentaActual.getNumero() + " \n"
                        + "1. Consultar Saldo\n"
                        + "2. Depositar\n"
                        + "3. Extraer\n"
                        + "9. CAMBIAR DE CUENTA\n"
                        + "0. Cerrar Sesión\n\n"
                        + "Elija opción:";

                Integer inputOpcion = solicitarEntero(menuText); // Solicita opción al usuario 

                if (inputOpcion == null) {
                    opcion = 0;
                    cambiarCuenta = false;
                    break;
                }
                opcion = inputOpcion;

                switch (opcion) { // Manejo de opciones
                    case 1: // Consultar saldo
                        double saldo = cajero.consultarSaldo(cuentaActual);
                        JOptionPane.showMessageDialog(null, "Saldo actual: $" + saldo, "Saldo de Cuenta", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 2: // Depositar
                        Double dep = solicitarMonto("Monto a depositar:");
                        if (dep != null) {
                            // Como solicitarMonto ya valida > 0, aquí solo ejecutamos
                            if (cajero.depositar(u, cuentaActual, dep)) {
                                JOptionPane.showMessageDialog(null, "Depósito realizado. Nuevo saldo: $" + cuentaActual.getSaldo(), "Deposito Exitoso", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Error desconocido al depositar.");
                            }
                        }
                        break;
                    case 3: // Extraer
                        Double ext = solicitarMonto("Monto a extraer:");
                        if (ext != null) {
                            int status = cajero.extraer(u, cuentaActual, ext);
                            switch (status) {
                                case 0:
                                    JOptionPane.showMessageDialog(null, "Extracción exitosa. Retire su dinero.", "Extracción Exitosa", JOptionPane.INFORMATION_MESSAGE);
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
                    case 9: // Cambiar de cuenta
                        break;
                    case 0: // Cerrar sesión
                        JOptionPane.showMessageDialog(null, "Cerrando sesión, retire la tarjeta", "Salir", JOptionPane.INFORMATION_MESSAGE);
                        cambiarCuenta = false;
                        break;
                    default: // Opción inválida
                        JOptionPane.showMessageDialog(null, "Opción inválida.");
                }
            }
        }
    }

    private void menuAdmin(Usuario u) { // Menú para administradores
        int opcion = -1;
        while (opcion != 0) {
            String menuText = "MENÚ ADMINISTRADOR\n"
                    + "1. Listar Usuarios\n"
                    + "2. Ordenar Usuarios (Burbujeo)\n"
                    + "3. Ordenar Usuarios (Selección)\n"
                    + "4. Cargar efectivo al Cajero\n"
                    + "5. Consultar Efectivo Cajero\n"
                    + "6. Activar/Bloquear Usuario\n"
                    + "7. Cambiar PIN de Usuario\n"
                    + "8. Buscar Usuario\n"
                    + "0. Salir\n\n"
                    + "Elija opción:";

            Integer inputOpcion = solicitarEntero(menuText); // Solicita opción al administrador

            if (inputOpcion == null) {
                break;
            }
            opcion = inputOpcion; // Asigna la opción ingresada

            switch (opcion) {
                case 1: // Listar usuarios
                    String listado = banco.listarUsuarios();
                    JOptionPane.showMessageDialog(null, listado, "Listado de Usuarios", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2: // Ordenar por burbujeo
                    banco.ordenarUsuariosPorNumeroBurbujeo();
                    break;
                case 3: // Ordenar por selección
                    banco.ordenarUsuariosPorNumeroSeleccion();
                    break;
                case 4: // Cargar efectivo al cajero
                    Double carga = solicitarMonto("Monto a cargar:");
                    if (carga != null) {
                        if (cajero.cargarEfectivo(carga)) {
                            JOptionPane.showMessageDialog(null, "Carga exitosa. Nuevo total: $" + cajero.getEfectivoDisponible());
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: El monto a cargar debe ser positivo.", "Error de Carga", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case 5: // Consultar efectivo del cajero
                    double efectivo = cajero.getEfectivoDisponible();
                    JOptionPane.showMessageDialog(null, "Efectivo disponible en el cajero: $" + efectivo, "Efectivo Cajero", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 6: // Activar/Bloquear usuario
                    String busquedaBloqueo = JOptionPane.showInputDialog(null, banco.listarUsuarios() + "\n\nIngrese el **UserID** o **DNI** del Usuario a modificar:");
                    if (busquedaBloqueo != null) {
                        Usuario uTemp = banco.buscarUsuario(busquedaBloqueo, "nroCuenta");
                        if (uTemp == null) {
                            uTemp = banco.buscarUsuario(busquedaBloqueo, "dni");
                        }

                        if (uTemp != null) {
                            if (uTemp.esAdmin()) {
                                JOptionPane.showMessageDialog(null, "Error: No se puede bloquear a un Administrador.", "Acción Denegada", JOptionPane.WARNING_MESSAGE);
                            } else {
                                if (uTemp.getEstado().equalsIgnoreCase("Activo")) {
                                    uTemp.setEstado("Bloqueado");
                                    JOptionPane.showMessageDialog(null, "Usuario " + uTemp.getNombre() + " ha sido BLOQUEADO.");
                                } else {
                                    uTemp.setEstado("Activo");
                                    JOptionPane.showMessageDialog(null, "Usuario " + uTemp.getNombre() + " ha sido ACTIVADO.");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case 7: // Cambiar PIN de usuario
                    String busquedaPin = JOptionPane.showInputDialog(null, banco.listarUsuarios() + "\n\nIngrese el **UserID** o **DNI** del Usuario para cambio de PIN:");
                    if (busquedaPin != null) {
                        Usuario uTemp = banco.buscarUsuario(busquedaPin, "nroCuenta");
                        if (uTemp == null) {
                            uTemp = banco.buscarUsuario(busquedaPin, "dni");
                        }

                        if (uTemp != null) {

                            Integer nuevoPin = solicitarEntero("Ingrese el NUEVO PIN (Numérico):");

                            if (nuevoPin != null) {
                                uTemp.cambiarPin(nuevoPin);
                                JOptionPane.showMessageDialog(null, "PIN cambiado exitosamente para el usuario: " + uTemp.getApellido() + " " + uTemp.getNombre());
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuario no encontrado por UserID ni DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case 8: 
                    //  Busqueda de usuario
                    String datoBusqueda = JOptionPane.showInputDialog("Ingrese UserID o DNI a buscar:");

                    if (datoBusqueda != null && !datoBusqueda.trim().isEmpty()) {
                        // 1. Buscamos por ID
                        Usuario uEnc = banco.buscarUsuario(datoBusqueda, "nroCuenta");

                        // 2. Si no está, buscamos por DNI
                        if (uEnc == null) {
                            uEnc = banco.buscarUsuario(datoBusqueda, "dni");
                        }

                        if (uEnc != null) {
                            String info = "<html><pre>"
                                    + "-----------------------------------\n"
                                    + "       DETALLE DE USUARIO          \n"
                                    + "-----------------------------------\n"
                                    + "Nombre:    " + uEnc.getApellido() + ", " + uEnc.getNombre() + "\n"
                                    + "DNI:       " + uEnc.getDni() + "\n"
                                    + "Usuario:   " + uEnc.getNroCuenta() + "\n"
                                    + "Estado:    " + uEnc.getEstado() + "\n"
                                    + "Cuentas:   " + uEnc.getCantCuentas() + "\n"
                                    + "Permisos:  " + (uEnc.esAdmin() ? "ADMINISTRADOR" : "CLIENTE") + "\n"
                                    + "-----------------------------------</pre></html>";

                            JOptionPane.showMessageDialog(null, info, "Usuario Encontrado", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró ningún usuario con ese dato.", "Sin resultados", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    break;
                case 0: // Salir
                    JOptionPane.showMessageDialog(null, "Saliendo del menú de administrador...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }
        }
    }

    public static void main(String[] args) { // Iniciar la aplicación
        new Menu().iniciar();
    }

    public Banco getBanco() { // Getter para el banco
        return this.banco;
    }

    public void setBanco(Banco banco) { // Setter para el banco
        this.banco = banco;
    }

    public CajeroAutomatico getCajero() { // Getter para el cajero
        return this.cajero;
    }

    public void setCajero(CajeroAutomatico cajero) { // Setter para el cajero
        this.cajero = cajero;
    }
}
