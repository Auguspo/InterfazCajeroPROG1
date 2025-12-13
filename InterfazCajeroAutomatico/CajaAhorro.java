package InterfazCajeroAutomatico;

// Clase derivada de Cuenta. Representa una Caja de Ahorro.
public class CajaAhorro extends Cuenta {

    private double limiteExtraccion; // Restricción propia de este tipo de cuenta.

    // Constructor. Inicializa atributos propios y de la clase padre.
    public CajaAhorro(String numero, double saldoInicial, double limiteExtraccion) {
        super(numero, saldoInicial);
        this.limiteExtraccion = limiteExtraccion;
    }

    // Sobrescribe el método de Cuenta (Polimorfismo).
    @Override
    public boolean extraer(double monto) {
        // Lógica: debe haber saldo Y no debe exceder el límite diario/por transacción.
        if (getSaldo() >= monto && monto <= this.limiteExtraccion) {
            setSaldo(getSaldo() - monto);
            // Si es válido, actualiza el saldo (usa el setter de la clase base, Cuenta).
            return true;
        }
        return false;
    }

    public double getLimiteExtraccion() {
        return this.limiteExtraccion;
    }

    // Permite al sistema modificar el límite.
    public void setLimiteExtraccion(double limiteExtraccion) {
        this.limiteExtraccion = limiteExtraccion;
    }

    // Extiende la representación de la cuenta base para incluir el límite.
    @Override
    public String toString() {
        return super.toString() + " (CA - Límite: " + getLimiteExtraccion() + ")";
    }
}
