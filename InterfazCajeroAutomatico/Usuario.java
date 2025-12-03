package InterfazCajeroAutomatico;

public class Usuario extends Persona {

    private String nroCuenta;
    private int pin;
    private String estado;
    private boolean admin;
    private Cuenta[] cuentas;
    private int cantCuentas;

    public Usuario(String nombre, String apellido, String dni, String nroCuenta, int pin, String estado, boolean admin) {
        super(nombre, apellido, dni);
        this.nroCuenta = nroCuenta;
        this.pin = pin;
        this.estado = estado;
        this.admin = admin;
        this.cuentas = new Cuenta[5];
        this.cantCuentas = 0;
    }

    public boolean validarPin(int pinIngresado) {
        return this.pin == pinIngresado;
    }

    public void agregarCuenta(Cuenta c) {
        if (this.cantCuentas < this.cuentas.length) {
            this.cuentas[this.cantCuentas] = c;
            this.cantCuentas++;
        } else {
            System.out.println("Error: No se pueden agregar más cuentas a este usuario.");
        }
    }

    public Cuenta getCuenta(String numero) {
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

    public String getNroUsuario() {
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

    @Override
    public String toString() {
        return super.toString() + " - UserID: " + this.nroCuenta + " [" + this.estado + "]";
    }
}
