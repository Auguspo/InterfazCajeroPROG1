package InterfazCajeroAutomatico;

public class CuentaCorriente extends Cuenta { // Clase derivada. Representa una Cuenta Corriente.

    private double descubiertoPermitido; // Monto máximo que la cuenta puede quedar en negativo.

    public CuentaCorriente(String numero, double saldoInicial, double descubiertoPermitido) { // Constructor. Pasa datos a Cuenta y establece el descubierto.
        super(numero, saldoInicial);
        this.descubiertoPermitido = descubiertoPermitido;
    }

    @Override // Sobrescribe el método de extracción para incluir la lógica del descubierto.
    public boolean extraer(double monto) {
        if (monto <= (getSaldo() + this.descubiertoPermitido)) { // Lógica: el monto extraído no puede superar la suma del saldo y el descubierto.
            setSaldo(getSaldo() - monto); // Realiza la extracción, pudiendo generar un saldo negativo.
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

    @Override // Extiende la representación de la cuenta base para mostrar el descubierto.
    public String toString() {
        return super.toString() + " (CC - Descubierto: " + this.descubiertoPermitido + ")";
    }
}
