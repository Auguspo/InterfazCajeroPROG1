package InterfazCajeroAutomatico;

public class CajaAhorro extends Cuenta { // Clase derivada de Cuenta. Representa una Caja de Ahorro.

    private double limiteExtraccion; // Restricción propia de este tipo de cuenta.

    public CajaAhorro(String numero, double saldoInicial, double limiteExtraccion) { // Constructor. Inicializa atributos propios y de la clase padre.
        super(numero, saldoInicial);
        this.limiteExtraccion = limiteExtraccion;
    }

    @Override // Sobrescribe el método de Cuenta (Polimorfismo).
    public boolean extraer(double monto) {
        if (getSaldo() >= monto && monto <= this.limiteExtraccion) { // Lógica: debe haber saldo Y no debe exceder el límite diario/por transacción.
            setSaldo(getSaldo() - monto); // Si es válido, actualiza el saldo (usa el setter de la clase base, Cuenta).
            return true;
        }
        return false;
    }

    public double getLimiteExtraccion() {
        return this.limiteExtraccion;
    }

    public void setLimiteExtraccion(double limiteExtraccion) { // Permite al sistema modificar el límite.
        this.limiteExtraccion = limiteExtraccion;
    }

    @Override // Extiende la representación de la cuenta base para incluir el límite.
    public String toString() {
        return super.toString() + " (CA - Límite: " + getLimiteExtraccion() + ")";
    }
}
