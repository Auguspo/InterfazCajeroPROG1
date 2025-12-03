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
        if (cantCuentas < cuentas.length) {
            cuentas[cantCuentas] = c;
            cantCuentas++;
        } else {
            System.out.println("Error: No se pueden agregar más cuentas a este usuario.");
        }
    }

    public Cuenta getCuenta(String numero) {
        for (int i = 0; i < cantCuentas; i++) {
            if (cuentas[i].getNumero().equals(numero)) {
                return cuentas[i];
            }
        }
        return null;
    }

    public Cuenta[] getCuentas() {
        return cuentas;
    }

    public int getCantCuentas() {
        return cantCuentas;
    }

    public void cambiarPin(int nuevoPin) {
        this.pin = nuevoPin;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public boolean esAdmin() { return admin; }
    public String getNroUsuario() { return nroCuenta; } 

    @Override
    public String toString() {
        return super.toString() + " - UserID: " + nroCuenta + " [" + estado + "]";
    }
}
