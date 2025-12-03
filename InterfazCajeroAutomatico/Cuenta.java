package InterfazCajeroAutomatico;

public abstract class Cuenta {
    protected String numero;
    protected double saldo;

    public Cuenta(String numero, double saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNumero() {
        return numero;
    }

    public void depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
        }
    }

    public boolean extraer(double monto){return false; } 

    @Override
    public String toString() {
        return "Cuenta Nro: " + numero + " | Saldo: $" + saldo;
    }
}