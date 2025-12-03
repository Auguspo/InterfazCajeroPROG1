package InterfazCajeroAutomatico;
public class CuentaCorriente extends Cuenta {
    private double descubiertoPermitido;

    public CuentaCorriente(String numero, double saldoInicial, double descubiertoPermitido) {
        super(numero, saldoInicial);
        this.descubiertoPermitido = descubiertoPermitido;
    }

    @Override
    public boolean extraer(double monto) {
        if (monto <= (saldo + descubiertoPermitido)) {
            saldo -= monto;
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return super.toString() + " (CC - Descubierto: " + descubiertoPermitido + ")";
    }
}