package InterfazCajeroAutomatico;

public class Cuenta { // Clase base para todas las cuentas (Caja de Ahorro, Cuenta Corriente, etc.).

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

    public void depositar(double monto) { // Lógica para incrementar el saldo (siempre que el monto sea positivo).
        if (monto > 0) {
            this.saldo += monto;
        }
    }

    public boolean extraer(double monto) { // Método fundamental, será sobrescrito por las clases derivadas (Polimorfismo).
        return false;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setSaldo(double saldo) { // Setter para modificar directamente el saldo (uso interno y por herederos).
        this.saldo = saldo;
    }

    @Override // Formato de salida de la cuenta base.
    public String toString() {
        return "Cuenta Nro: " + this.numero + " | Saldo: $" + this.saldo;
    }
}
