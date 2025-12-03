package InterfazCajeroAutomatico;

public class CuentaCorriente extends Cuenta {

    private double descubiertoPermitido;

    public CuentaCorriente(String numero, double saldoInicial, double descubiertoPermitido) {
        super(numero, saldoInicial);
        this.descubiertoPermitido = descubiertoPermitido;
    }

    @Override
    public boolean extraer(double monto) {
        if (monto <= (getSaldo() + this.descubiertoPermitido)) {
            setSaldo(getSaldo() - monto);
            return true;
        }
        return false;
    }

    public double getDescubiertoPermitido() {
        return this.descubiertoPermitido;
    }

    public void setDescubiertoPermitido(double descubiertoPermitido) {
        this.descubiertoPermitido = descubiertoPermitido;
    }

    @Override
    public String toString() {
        return super.toString() + " (CC - Descubierto: " + this.descubiertoPermitido + ")";
    }
}
