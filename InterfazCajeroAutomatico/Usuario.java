package InterfazCajeroAutomatico;

public class Usuario extends Persona { // Clase Usuario, hereda atributos y métodos de Persona (Herencia).

    private String nroCuenta;
    private int pin; // PIN secreto para validar el acceso al cajero.
    private String estado;
    private boolean admin; // Flag booleano para identificar si el usuario tiene privilegios de administrador.
    private Cuenta[] cuentas; 
    private int cantCuentas;

    // Constructor. Llama al constructor de la clase padre (super).
    public Usuario(String nombre, String apellido, String dni, String nroCuenta, int pin, String estado, boolean admin) {
        super(nombre, apellido, dni); // Llamada al constructor de Persona para inicializar sus atributos.
        this.nroCuenta = nroCuenta;
        this.pin = pin;
        this.estado = estado;
        this.admin = admin;
        this.cuentas = new Cuenta[5];
        this.cantCuentas = 0; // Método clave para la seguridad: verifica si el PIN ingresado es correcto.
    }

    public boolean validarPin(int pinIngresado) {
        return this.pin == pinIngresado;
    }

    public void agregarCuenta(Cuenta c) { // Añade una instancia de Cuenta (CajaAhorro o CuentaCorriente) al arreglo del usuario.
        if (this.cantCuentas < this.cuentas.length) {
            this.cuentas[this.cantCuentas] = c;
            this.cantCuentas++;
        } else {
            System.out.println("Error: No se pueden agregar más cuentas a este usuario.");
        }
    }

    public Cuenta getCuenta(String numero) { // Busca una cuenta específica por su número dentro del arreglo del usuario (Búsqueda Lineal).
        for (int i = 0; i < this.cantCuentas; i++) {
            if (this.cuentas[i].getNumero().equals(numero)) {
                return this.cuentas[i];
            }
        }
        return null;
    }

    public Cuenta[] getCuentas() {
        return this.cuentas;
    }

    public int getCantCuentas() {
        return this.cantCuentas;
    }

    public void cambiarPin(int nuevoPin) {
        this.pin = nuevoPin;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean esAdmin() {
        return this.admin;
    }

    public String getNroUsuario() { // Alias para obtener el nroCuenta, que actúa como ID de usuario en el sistema de login.
        return this.nroCuenta;
    }

    public String getNroCuenta() {
        return this.nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public int getPin() {
        return this.pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean isAdmin() {
        return this.admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override // Representación completa del usuario, incluyendo su estado y si es administrador.
    public String toString() {
        return super.toString() + " - UserID: " + this.nroCuenta + " [" + this.estado + "]";
    }
}
