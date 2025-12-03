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
        if (cantUsuarios < usuarios.length) {
            usuarios[cantUsuarios] = u;
            cantUsuarios++;
        } else {
            System.out.println("El banco está lleno, no se admiten más clientes.");
        }
    }

    public Usuario buscarUsuarioPorNro(String nroCuenta) {
        for (int i = 0; i < cantUsuarios; i++) {
            if (usuarios[i].getNroUsuario().equals(nroCuenta)) {
                return usuarios[i];
            }
        }
        return null;
    }

    public void ordenarUsuariosPorNumeroBurbujeo() {
        for (int i = 0; i < cantUsuarios - 1; i++) {
            for (int j = 0; j < cantUsuarios - i - 1; j++) {

                if (usuarios[j].getNroUsuario().compareTo(usuarios[j + 1].getNroUsuario()) > 0) {

                    Usuario temp = usuarios[j];
                    usuarios[j] = usuarios[j + 1];
                    usuarios[j + 1] = temp;
                }
            }
        }
    }
    
    public void ordenarUsuariosPorNumeroSeleccion() {
        for (int i = 0; i < cantUsuarios - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < cantUsuarios; j++) {
                if (usuarios[j].getNroUsuario().compareTo(usuarios[indiceMinimo].getNroUsuario()) < 0) {
                    indiceMinimo = j;
                }
            }
            Usuario temp = usuarios[indiceMinimo];
            usuarios[indiceMinimo] = usuarios[i];
            usuarios[i] = temp;
        }
    }

    public Usuario buscarUsuarioBinaria(String nroCuenta) {
        int inicio = 0;
        int fin = cantUsuarios - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            int comparacion = usuarios[medio].getNroUsuario().compareTo(nroCuenta);

            if (comparacion == 0) {
                return usuarios[medio];
            } else if (comparacion < 0) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return null;
    }

    public String listarUsuarios() {
        if (cantUsuarios == 0) {
            return "No hay usuarios en el banco.";
        }
        StringBuilder sb = new StringBuilder("--- Listado de Usuarios ---\n");
        for (int i = 0; i < cantUsuarios; i++) {
            sb.append(usuarios[i].toString()).append("\n");
        }
        return sb.toString();
    }

    public int getCantUsuarios() {
        return cantUsuarios;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario[] getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario... usuarios) {
    }
}