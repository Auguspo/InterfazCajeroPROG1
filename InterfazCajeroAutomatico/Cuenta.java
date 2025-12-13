package InterfazCajeroAutomatico;

// Clase base para todas las cuentas (Caja de Ahorro, Cuenta Corriente, etc.).
public class Cuenta { 

    private String numero;
    private double saldo; // Atributo principal: el saldo actual de la cuenta.

    public Cuenta(String numero, double saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    public double getSaldo() { // Getter para consultar el saldo.
        return this.saldo;
    }

    public String getNumero() {
        return this.numero;
    }

    // Lógica para incrementar el saldo (siempre que el monto sea positivo).
    public void depositar(double monto) { 
        if (monto > 0) {
            this.saldo += monto;
        }
    }

    // Método fundamental, será sobrescrito por las clases derivadas (Polimorfismo).
    public boolean extraer(double monto) { 
        return false;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    // Setter para modificar directamente el saldo (uso interno y por herederos).
    public void setSaldo(double saldo) { 
        this.saldo = saldo;
    }

    // Formato de salida de la cuenta base.
    @Override 
    public String toString() {
        return "Cuenta Nro: " + this.numero + " | Saldo: $" + this.saldo;
    }
}
