package InterfazCajeroAutomatico;

// Clase base para todas las cuentas (Caja de Ahorro, Cuenta Corriente, etc.).
public class Cuenta { 

    private String numero; // Número único que identifica la cuenta.
    private double saldo; // Atributo principal: el saldo actual de la cuenta.

    public Cuenta(String numero, double saldoInicial) {  // Inicializa número y saldo inicial.
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    public double getSaldo() { // Getter para consultar el saldo.
        return this.saldo;
    }

    public String getNumero() { // Getter para consultar el número de cuenta.
        return this.numero;
    }

    // Lógica para incrementar el saldo (siempre que el monto sea positivo).
    public void depositar(double monto) { 
        if (monto > 0) {
            this.saldo += monto;
        }
    }

    public boolean extraer(double monto) { // Lógica base: solo permite extracción si hay saldo suficiente.
        return false;
    }

    public void setNumero(String numero) { // Setter para modificar el número de cuenta.
        this.numero = numero;
    }

    public void setSaldo(double saldo) { // Setter para modificar el saldo.
        this.saldo = saldo;
    }

    // Formato de salida de la cuenta base.
    @Override 
    public String toString() {
        return "Cuenta Nro: " + this.numero + " | Saldo: $" + this.saldo;
    }
}
