package InterfazCajeroAutomatico;

public class CajaAhorro extends Cuenta {

    private double limiteExtraccion;

    public CajaAhorro(String numero, double saldoInicial, double limiteExtraccion) {
        super(numero, saldoInicial);
        this.limiteExtraccion = limiteExtraccion;
    }

    @Override
    public boolean extraer(double monto) {
        if (getSaldo() >= monto && monto <= limiteExtraccion) {
            setSaldo(getSaldo() - monto);
            return true;
        }
        return false;
    }

    public double getLimiteExtraccion() {
        return limiteExtraccion;
    }

    public void setLimiteExtraccion(double limiteExtraccion) {
        this.limiteExtraccion = limiteExtraccion;
    }

    @Override
    public String toString() {
        return super.toString() + " (CA - Límite: " + getLimiteExtraccion() + ")";
    }
}
