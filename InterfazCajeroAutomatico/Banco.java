package InterfazCajeroAutomatico;

import javax.swing.JOptionPane;

public class Banco { // Clase que representa un banco con multiples usuarios.
    private String nombre; // Nombre del banco.
    private Usuario[] usuarios; // Arreglo para almacenar todos los usuarios/clientes del banco.
    private int cantUsuarios; // Limite usuarios actual

    public Banco(String nombre, int capacidad) { // Inicializa el banco con un nombre y capacidad maxima de
                                                 // usuarios.
        this.nombre = nombre;
        this.usuarios = new Usuario[capacidad]; // Arreglo para almacenar usuarios
        this.cantUsuarios = 0;
    }

    // Añade un nuevo usuario verificando espacio y duplicados.
    public boolean agregarUsuario(Usuario u) {
        if (this.cantUsuarios >= this.usuarios.length) {
            return false; // Banco lleno
        }

        // Usa el metodo de bussqueda para chequear duplicados por nroCuenta
        // (UserID).
        if (buscarUsuario(u.getNroCuenta(), "nroCuenta") != null) {
            return false; // El usuario ya existe
        }

        this.usuarios[this.cantUsuarios] = u;
        this.cantUsuarios++;
        return true; // Agregado con exito
    }

    // Busqueda lineal: busca por numero de usuario (nroCuenta) o por DNI.
    public Usuario buscarUsuario(String valorBusqueda, String tipoBusqueda) {
        if (valorBusqueda == null || valorBusqueda.trim().isEmpty()) {
            return null;
        }

        String valorLimpio = valorBusqueda.trim();

        for (int i = 0; i < this.cantUsuarios; i++) {
            Usuario u = this.usuarios[i];

            if (tipoBusqueda.equalsIgnoreCase("nroCuenta") && u.getNroCuenta().equals(valorLimpio)) {
                return u;
            } else if (tipoBusqueda.equalsIgnoreCase("dni") && u.getDni().equals(valorLimpio)) {
                return u;
            }
        }
        return null;
    }

    // Implementacin del algoritmo de Ordenamiento de Burbuja (Bubble Sort)con mensajes de estado.
    public void ordenarUsuariosPorNumeroBurbujeo() {
        String listaAntes = listarUsuarios();

        int resultado = JOptionPane.showConfirmDialog(null,
                "ESTADO ACTUAL DE LOS USUARIOS:\n" + listaAntes
                        + "\n\n¿Desea continuar con el Ordenamiento por Burbujeo (por UserID)?",
                "Confirmar Ordenamiento por Burbujeo",
                JOptionPane.YES_NO_OPTION);

        if (resultado != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Ordenamiento cancelado.", "Cancelado",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (int i = 0; i < this.cantUsuarios - 1; i++) {
            for (int j = 0; j < this.cantUsuarios - i - 1; j++) {

                if (this.usuarios[j].getNroCuenta().compareTo(this.usuarios[j + 1].getNroCuenta()) > 0) { // Compara dos
                                                                                                          // IDs
                                                                                                          // consecutivos.

                    Usuario temp = this.usuarios[j];
                    this.usuarios[j] = this.usuarios[j + 1];
                    this.usuarios[j + 1] = temp;
                }
            }
        }

        String listaDespues = listarUsuarios();
        JOptionPane.showMessageDialog(null,
                "Ordenamiento realizado con exito.\n\nLISTA ORDENADA:\n" + listaDespues,
                "Ordenamiento por Burbujeo Exitoso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Implementacion del algoritmo de Ordenamiento por Seleccion
    public void ordenarUsuariosPorNumeroSeleccion() {
        String listaAntes = listarUsuarios();

        int resultado = JOptionPane.showConfirmDialog(null,
                "ESTADO ACTUAL DE LOS USUARIOS:\n" + listaAntes
                        + "\n\n¿Desea continuar con el Ordenamiento por Seleccion (por UserID)?",
                "Confirmar Ordenamiento por Seleccion",
                JOptionPane.YES_NO_OPTION);

        if (resultado != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Ordenamiento cancelado.", "Cancelado",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (int i = 0; i < this.cantUsuarios - 1; i++) {
            int indiceMinimo = i; // Asume el inicio de la sublista como el minimo.
            for (int j = i + 1; j < this.cantUsuarios; j++) {
                if (this.usuarios[j].getNroCuenta().compareTo(this.usuarios[indiceMinimo].getNroCuenta()) < 0) {
                    indiceMinimo = j;
                }
            }
            Usuario temp = this.usuarios[indiceMinimo];
            this.usuarios[indiceMinimo] = this.usuarios[i];
            this.usuarios[i] = temp;
        }

        String listaDespues = listarUsuarios();
        JOptionPane.showMessageDialog(null,
                "Ordenamiento realizado con exito.\n\nLISTA ORDENADA:\n" + listaDespues,
                "Ordenamiento por Seleccion Exitoso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Implementacion de la BBusqueda Binaria (Requiere arreglo ordenado).
    public Usuario buscarUsuarioBinaria(String nroCuenta) {
        int inicio = 0;
        int fin = this.cantUsuarios - 1;

        if (nroCuenta == null) {
            return null;
        }

        // Limpiamos espacios en blanco del input.
        nroCuenta = nroCuenta.trim();

        while (inicio <= fin) {
            // Forma segura de calcular el medio para evitar desbordamiento de enteros.
            int medio = inicio + (fin - inicio) / 2;

            int comparacion = this.usuarios[medio].getNroCuenta().compareTo(nroCuenta);

            if (comparacion == 0) {
                return this.usuarios[medio];
            } else if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null;
    }

    // Genera un String con el listado completo para mostrar en JOptionPane.
    public String listarUsuarios() {
        if (this.cantUsuarios == 0) {
            return "No hay usuarios en el banco.";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("<html><pre>");

        sb.append(String.format("%-12s %-25s %-12s %-10s\n", "ID USUARIO", "APELLIDO Y NOMBRE", "DNI", "ESTADO"));
        sb.append(String.format("%-12s %-25s %-12s %-10s\n", "----------", "-------------------------", "----------",
                "----------"));

        for (int i = 0; i < this.cantUsuarios; i++) {
            Usuario u = this.usuarios[i];

            // Concatenamos nombre y apellido para que entre en una columna
            String nombreCompleto = u.getApellido() + ", " + u.getNombre();

            if (nombreCompleto.length() > 24) {
                nombreCompleto = nombreCompleto.substring(0, 21) + "...";
            }

            sb.append(String.format("%-12s %-25s %-12s %-10s\n",
                    u.getNroCuenta(),
                    nombreCompleto,
                    u.getDni(),
                    u.getEstado()));
        }

        sb.append("</pre></html>");

        return sb.toString();
    }

    public int getCantUsuarios() {
        return this.cantUsuarios;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario[] getUsuarios() {
        return this.usuarios;
    }

    // Setter que actualiza el arreglo de usuarios y la cantidad.
    public void setUsuarios(Usuario[] usuarios) {
        this.usuarios = usuarios;
        this.cantUsuarios = (usuarios != null) ? usuarios.length : 0;
    }
}