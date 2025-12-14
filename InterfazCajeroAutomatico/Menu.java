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
    
    // Nuevo método para solicitar texto no vacío
    private String solicitarString(String mensaje, String titulo) {
        String input;
        do { 
            input = JOptionPane.showInputDialog(null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
            if (input == null) { // Cancelación
                return null;
            }
            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Este campo no puede estar vacío.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            }
        } while (input.isEmpty());
        return input;
    }
    
    // Lógica para añadir un nuevo usuario.
    private void añadirUsuarioCliente() {
        // 1. Solicitud de datos básicos
        String nombre = solicitarString("Ingrese Nombre:", "Nuevo Cliente");
        if (nombre == null) return;
        
        String apellido = solicitarString("Ingrese Apellido:", "Nuevo Cliente");
        if (apellido == null) return;
        
        String dni;
        boolean dniDuplicado;

        do {
            dniDuplicado = false;
            dni = solicitarString("Ingrese DNI:", "Nuevo Cliente");
            
            if (dni == null) return;

            if (banco.buscarUsuario(dni, "dni") != null) {
                JOptionPane.showMessageDialog(null, "Error: El DNI ya existe en el sistema.", "DNI Duplicado", JOptionPane.ERROR_MESSAGE);
                dniDuplicado = true;
            }
        } while (dniDuplicado);
        
        // 2. Generación automática de UserID
        String nroCuenta = "user" + (banco.getCantUsuarios() + 1);
        int intentos = 0;
        // Asegurar que el ID no exista (aunque con +1 no debería si la numeración es secuencial)
        while (banco.buscarUsuario(nroCuenta, "nroCuenta") != null && intentos < 100) {
            nroCuenta = "user" + (banco.getCantUsuarios() + 1 + intentos);
            intentos++;
        }
        
        // 3. Solicitud de PIN
        Integer pin = solicitarEntero("El UserID asignado es: " + nroCuenta + "\nIngrese PIN (4 dígitos numéricos):");
        if (pin == null) return;

        // 4. Creación del usuario (por defecto: Activo, no administrador)
        Usuario nuevoUsuario = new Usuario(nombre, apellido, dni, nroCuenta, pin, "Activo", false);
        
        // 5. Selección de cuentas a abrir
        boolean cuentaCorrienteAbierta = false;
        boolean cajaAhorroAbierta = false;
        
        // a) Cuenta Corriente
        String abrirCC = solicitarString("¿Desea abrir una Cuenta Corriente (S/N)?", "Selección de Cuentas");
        if (abrirCC != null && abrirCC.equalsIgnoreCase("S")) {
            Double descubierto = solicitarMonto("Ingrese el Descubierto Permitido para Cuenta Corriente (Monto Positivo):");
            if (descubierto != null) {
                nuevoUsuario.agregarCuenta(new CuentaCorriente("CC-" + nroCuenta, 0.0, descubierto));
                cuentaCorrienteAbierta = true;
            }
        }
        
        // b) Caja de Ahorro
        String abrirCA = solicitarString("¿Desea abrir una Caja de Ahorro (S/N)?", "Selección de Cuentas");
        if (abrirCA != null && abrirCA.equalsIgnoreCase("S")) {
            Double limite = solicitarMonto("Ingrese el Límite de Extracción para Caja de Ahorro (Monto Positivo):");
            if (limite != null) {
                nuevoUsuario.agregarCuenta(new CajaAhorro("CA-" + nroCuenta, 0.0, limite));
                cajaAhorroAbierta = true;
            }
        }

        // 6. Finalización y validación de cuentas
        if (banco.agregarUsuario(nuevoUsuario)) {
            String mensajeExito = "Usuario/Cliente agregado con éxito. UserID: " + nroCuenta + "\n";
            if (!cuentaCorrienteAbierta && !cajaAhorroAbierta) {
                mensajeExito += "ADVERTENCIA: No se asignó ninguna cuenta al usuario.";
            } else {
                mensajeExito += "Cuentas abiertas: " 
                              + (cuentaCorrienteAbierta ? "Cuenta Corriente | " : "") 
                              + (cajaAhorroAbierta ? "Caja de Ahorro" : "");
            }
            JOptionPane.showMessageDialog(null, mensajeExito, "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar usuario (límite alcanzado o error interno).", "Error de Creación", JOptionPane.ERROR_MESSAGE);
        }
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

        while (cambiarCuenta) { // Cambiar de cuenta
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
                        + "9. Cambiar de cuenta\n"
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
                        JOptionPane.showMessageDialog(null, "Saldo actual: $" + saldo);
                        break;
                    case 2: // Depositar
                        Double dep = solicitarMonto("Monto a depositar:");
                        if (dep != null) {
                            // Como solicitarMonto ya valida > 0, aquí solo ejecutamos
                            if (cajero.depositar(u, cuentaActual, dep)) {
                                JOptionPane.showMessageDialog(null, "Depósito realizado. Nuevo saldo: $" + cuentaActual.getSaldo());
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
                    case 9: // Cambiar de cuenta
                        break;
                    case 0: // Cerrar sesión
                        JOptionPane.showMessageDialog(null, "Cerrando sesión...");
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
                    + "2. Ordenar Usuarios (Burbujeo por DNI)\n"
                    + "3. Ordenar Usuarios (Selección por DNI)\n"
                    + "4. Cargar efectivo al Cajero\n"
                    + "5. Consultar Efectivo Cajero\n"
                    + "6. Activar/Bloquear Usuario\n"
                    + "7. Cambiar PIN de Usuario\n"
                    + "9. Agregar nuevo usuario/cliente\n" // Nueva opción
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
                    JOptionPane.showMessageDialog(null, "Efectivo disponible en el cajero: $" + efectivo);
                    break;
                case 6: // Activar/Bloquear usuario
                    String busquedaBloqueo = JOptionPane.showInputDialog(null, banco.listarUsuarios() + "\n\nIngrese el **UserID** o **DNI** del Usuario a modificar:");
                    if (busquedaBloqueo != null) { // Búsqueda
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
                            JOptionPane.showMessageDialog(null, "Usuario no encontrado por UserID ni DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case 7: // Cambiar PIN de usuario
                    String busquedaPin = JOptionPane.showInputDialog(null, banco.listarUsuarios() + "\n\nIngrese el **UserID** o **DNI** del Usuario para cambio de PIN:");
                    if (busquedaPin != null) { // Búsqueda
                        Usuario uTemp = banco.buscarUsuario(busquedaPin, "nroCuenta");
                        if (uTemp == null) {
                            uTemp = banco.buscarUsuario(busquedaPin, "dni");
                        }
                                        
                        if (uTemp != null) {

                            Integer nuevoPin = solicitarEntero("Ingrese el NUEVO PIN (Numérico) para " + uTemp.getApellido() + " " + uTemp.getNombre() + ":");

                            if (nuevoPin != null) {
                                uTemp.cambiarPin(nuevoPin);
                                JOptionPane.showMessageDialog(null, "PIN cambiado exitosamente para el usuario: " + uTemp.getApellido() + " " + uTemp.getNombre());
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuario no encontrado por UserID ni DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case 9: // Agregar nuevo usuario/cliente
                    añadirUsuarioCliente();
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