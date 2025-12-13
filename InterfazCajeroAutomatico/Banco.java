package InterfazCajeroAutomatico;

public class Banco {

    private String nombre;
    private Usuario[] usuarios; // Arreglo para almacenar todos los usuarios/clientes del banco.
    private int cantUsuarios; // Limite usuarios actual

    public Banco(String nombre, int capacidad) {
        this.nombre = nombre;
        this.usuarios = new Usuario[capacidad]; // Arreglo para almacenar usuarios
        this.cantUsuarios = 0;
    }

    // Añade un nuevo usuario verificando espacio y duplicados.
    public boolean agregarUsuario(Usuario u) {
        if (this.cantUsuarios >= this.usuarios.length) {
            return false; // Banco lleno
        }

        if (buscarUsuarioPorNro(u.getNroCuenta()) != null) {
            return false; // El usuario ya existe
        }

        this.usuarios[this.cantUsuarios] = u;
        this.cantUsuarios++;
        return true; // Agregado con éxito
    }

    // Búsqueda lineal simple por el número de usuario/cuenta.
    public Usuario buscarUsuarioPorNro(String nroCuenta) {
        for (int i = 0; i < this.cantUsuarios; i++) {
            if (this.usuarios[i].getNroCuenta().equals(nroCuenta)) {
                return this.usuarios[i];
            }
        }
        return null;
    }

    // Implementación del algoritmo de Ordenamiento de Burbuja (Bubble Sort).
    public void ordenarUsuariosPorNumeroBurbujeo() {
        for (int i = 0; i < this.cantUsuarios - 1; i++) {
            for (int j = 0; j < this.cantUsuarios - i - 1; j++) {

                if (this.usuarios[j].getNroCuenta().compareTo(this.usuarios[j + 1].getNroCuenta()) > 0) { // Compara dos IDs consecutivos.

                    Usuario temp = this.usuarios[j];
                    this.usuarios[j] = this.usuarios[j + 1];
                    this.usuarios[j + 1] = temp;
                }
            }
        }
    }

    // Implementación del algoritmo de Ordenamiento por Selección (Selection Sort).
    public void ordenarUsuariosPorNumeroSeleccion() {
        for (int i = 0; i < this.cantUsuarios - 1; i++) {
            int indiceMinimo = i; // Asume el inicio de la sublista como el mínimo.
            for (int j = i + 1; j < this.cantUsuarios; j++) {
                if (this.usuarios[j].getNroCuenta().compareTo(this.usuarios[indiceMinimo].getNroCuenta()) < 0) {
                    indiceMinimo = j;
                }
            }
            Usuario temp = this.usuarios[indiceMinimo];
            this.usuarios[indiceMinimo] = this.usuarios[i];
            this.usuarios[i] = temp;
        }
    }

    // Implementación de la Búsqueda Binaria (Requiere arreglo ordenado).
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
        sb.append(String.format("%-12s %-25s %-12s %-10s\n", "----------", "-------------------------", "----------", "----------"));

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
                    u.getEstado()
            ));
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

    // Reemplaza el arreglo actual (útil para cargas masivas).
    public void setUsuarios(Usuario[] usuarios) {
        this.usuarios = usuarios;
        this.cantUsuarios = (usuarios != null) ? usuarios.length : 0;
    }
}
