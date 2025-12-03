package InterfazCajeroAutomatico;

public class Banco {

    private String nombre;
    private Usuario[] usuarios;
    private int cantUsuarios;

    public Banco(String nombre, int capacidad) {
        this.nombre = nombre;
        this.usuarios = new Usuario[capacidad];
        this.cantUsuarios = 0;
    }

    public void agregarUsuario(Usuario u) {
        if (this.cantUsuarios < this.usuarios.length) {
            this.usuarios[this.cantUsuarios] = u;
            this.cantUsuarios++;
        } else {
            System.out.println("El banco está lleno, no se admiten más clientes.");
        }
    }

    public Usuario buscarUsuarioPorNro(String nroCuenta) {
        for (int i = 0; i < this.cantUsuarios; i++) {
            if (this.usuarios[i].getNroUsuario().equals(nroCuenta)) {
                return this.usuarios[i];
            }
        }
        return null;
    }

    public void ordenarUsuariosPorNumeroBurbujeo() {
        for (int i = 0; i < this.cantUsuarios - 1; i++) {
            for (int j = 0; j < this.cantUsuarios - i - 1; j++) {

                if (this.usuarios[j].getNroUsuario().compareTo(this.usuarios[j + 1].getNroUsuario()) > 0) {

                    Usuario temp = this.usuarios[j];
                    this.usuarios[j] = this.usuarios[j + 1];
                    this.usuarios[j + 1] = temp;
                }
            }
        }
    }

    public void ordenarUsuariosPorNumeroSeleccion() {
        for (int i = 0; i < this.cantUsuarios - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < this.cantUsuarios; j++) {
                if (this.usuarios[j].getNroUsuario().compareTo(this.usuarios[indiceMinimo].getNroUsuario()) < 0) {
                    indiceMinimo = j;
                }
            }
            Usuario temp = this.usuarios[indiceMinimo];
            this.usuarios[indiceMinimo] = this.usuarios[i];
            this.usuarios[i] = temp;
        }
    }

    public Usuario buscarUsuarioBinaria(String nroCuenta) {
        int inicio = 0;
        int fin = this.cantUsuarios - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            int comparacion = this.usuarios[medio].getNroUsuario().compareTo(nroCuenta);
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

    public String listarUsuarios() {
        if (this.cantUsuarios == 0) {
            return "No hay usuarios en el banco.";
        }
        StringBuilder sb = new StringBuilder("--- Listado de Usuarios ---\n");
        for (int i = 0; i < this.cantUsuarios; i++) {
            sb.append(this.usuarios[i].toString()).append("\n");
        }
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

    public void setUsuarios(Usuario... usuarios) {
    }
}
