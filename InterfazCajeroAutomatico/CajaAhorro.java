package InterfazCajeroAutomatico;
public class CajaAhorro extends Cuenta {
    private double limiteExtraccion;

    public CajaAhorro(String numero, double saldoInicial, double limiteExtraccion) {
        super(numero, saldoInicial);
        this.limiteExtraccion = limiteExtraccion;
    }

    @Override
    public boolean extraer(double monto) {
        if (saldo >= monto && monto <= limiteExtraccion) {
            saldo -= monto;
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return super.toString() + " (CA - Límite: " + limiteExtraccion + ")";
    }
}