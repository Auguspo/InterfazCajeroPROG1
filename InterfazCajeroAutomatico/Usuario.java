package InterfazCajeroAutomatico;

import javax.swing.JOptionPane;

public class Usuario extends Persona {
    // Clase Usuario, hereda atributos y métodos de Persona (Herencia).

    private String nroCuenta;
    private int pin;
    private String estado;
    private boolean admin; // Admin Y/N 
    private Cuenta[] cuentas;
    private int cantCuentas;

    // Constructor. Llama al constructor de la clase padre (super).
    public Usuario(String nombre, String apellido, String dni, String nroCuenta, int pin, String estado, boolean admin) {
        // Llamada al constructor de Persona para inicializar sus atributos.
        super(nombre, apellido, dni);
        this.nroCuenta = nroCuenta;
        this.pin = pin;
        this.estado = estado;
        this.admin = admin;
        this.cuentas = new Cuenta[5];
        this.cantCuentas = 0;
    }

    // Método clave para la seguridad: verifica si el PIN ingresado es correcto.
    public boolean validarPin(int pinIngresado) {
        return this.pin == pinIngresado;
    }

    public void agregarCuenta(Cuenta c) {
        // Añade una instancia de Cuenta (CajaAhorro o CuentaCorriente) al arreglo del usuario.
        if (this.cantCuentas < this.cuentas.length) {
            this.cuentas[this.cantCuentas] = c;
            this.cantCuentas++;
        } else {
            JOptionPane.showMessageDialog(null,
                    "Error: No se pueden agregar más cuentas a este usuario.",
                    "Límite de Cuentas Alcanzado",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Busca una cuenta específica por su número dentro del arreglo del usuario (Búsqueda Lineal).
    public Cuenta getCuenta(String numero) {
        for (int i = 0; i < this.cantCuentas; i++) {
            if (this.cuentas[i].getNumero().equals(numero)) {
                return this.cuentas[i];
            }
        }
        return null;
    }

    public Cuenta[] getCuentas() { // Devuelve todas las cuentas del usuario
        return this.cuentas;
    }

    public int getCantCuentas() { // Devuelve la cantidad de cuentas del usuario
        return this.cantCuentas;
    }

    public void cambiarPin(int nuevoPin) { // Permite cambiar el PIN del usuario
        this.pin = nuevoPin;
    }

    public String getEstado() { // Devuelve el estado del usuario
        return this.estado;
    }

    public void setEstado(String estado) { // Modifica el estado del usuario
        this.estado = estado;
    }

    public boolean esAdmin() { // Indica si el usuario es administrador
        return this.admin;
    }

    public String getNroCuenta() { // Devuelve el número de cuenta del usuario
        return this.nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) { // Modifica el número de cuenta del usuario
        this.nroCuenta = nroCuenta;
    }

    public int getPin() { // Devuelve el PIN del usuario
        return this.pin;
    }

    public void setPin(int pin) { // Modifica el PIN del usuario
        this.pin = pin;
    }

    public boolean isAdmin() { // Getter para el atributo admin
        return this.admin;
    }

    public void setAdmin(boolean admin) { // Setter para el atributo admin
        this.admin = admin;
    }

    @Override
    // Representación completa del usuario, incluyendo su estado y si es administrador.
    public String toString() {
        return super.toString() + " - UserID: " + this.nroCuenta + " [" + this.estado + "]";
    }
}
