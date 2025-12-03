package InterfazCajeroAutomatico;

public class Cuenta {

    private String numero;
    private double saldo;

    public Cuenta(String numero, double saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public String getNumero() {
        return this.numero;
    }

    public void depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
        }
    }

    public boolean extraer(double monto) {
        return false;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cuenta Nro: " + this.numero + " | Saldo: $" + this.saldo;
    }
}
