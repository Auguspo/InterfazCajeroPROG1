package InterfazCajeroAutomatico;

// Clase derivada. Representa una Cuenta Corriente.
public class CuentaCorriente extends Cuenta {

    // Monto máximo que la cuenta puede quedar en negativo.
    private double descubiertoPermitido;

    // Constructor. Pasa datos a Cuenta y establece el descubierto.
    public CuentaCorriente(String numero, double saldoInicial, double descubiertoPermitido) {
        super(numero, saldoInicial);
        this.descubiertoPermitido = descubiertoPermitido;
    }

    // Sobrescribe el método de extracción para incluir la lógica del descubierto.
    @Override
    public boolean extraer(double monto) {
        // Lógica: el monto extraído no puede superar la suma del saldo y el descubierto.
        if (monto <= (getSaldo() + this.descubiertoPermitido)) {
            setSaldo(getSaldo() - monto);
            // Realiza la extracción, pudiendo generar un saldo negativo.
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

    // Extiende la representación de la cuenta base para mostrar el descubierto.
    @Override 
    public String toString() {
        return super.toString() + " (CC - Descubierto: " + this.descubiertoPermitido + ")";
    }
}
