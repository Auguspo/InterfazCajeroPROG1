package InterfazCajeroAutomatico;

public class CajeroAutomatico {

    private Banco banco; // Banco asociado al cajero.
    private double efectivoDisponible; // Dinero físico actual en el cajero (restricción de hardware).

    public CajeroAutomatico(Banco banco, double efectivoInicial) { // Inicializa el banco y el efectivo.
        this.banco = banco;
        this.efectivoDisponible = efectivoInicial;
    }

    // Lógica de autenticación: busca usuario y valida el PIN.
    public Usuario iniciarSesion(String nroUsuario, int pin) {
        // Usa el método de búsqueda genérico por UserID ("nroCuenta").
        Usuario u = banco.buscarUsuario(nroUsuario, "nroCuenta"); 
        
        if (u != null) {
            // Usuario encontrado, ahora se valida el PIN.
            if (u.validarPin(pin)) {
                return u; // Autenticación exitosa
            }
        }
        // Si no se encuentra el usuario O el PIN es incorrecto, retorna null.
        return null; 
    }

    // Ejecuta la extracción, devolviendo un código de estado (Polimorfismo en c.extraer).
    public int extraer(Usuario u, Cuenta c, double monto) {
        // Validación 1: ¿Hay suficiente efectivo en el cajero?
        if (monto > this.efectivoDisponible) {
            return 1; // Código 1: Cajero sin suficiente efectivo.
        }

        // Llama al método polimórfico (depende si es CajaAhorro o CuentaCorriente).
        if (c.extraer(monto)) {
            this.efectivoDisponible -= monto;
            // Código 0: Éxito. El saldo se reduce y el efectivo del cajero también.
            return 0;
        } else {
            // Código 2: Fallo de la cuenta (saldo o límite insuficiente).
            return 2;
        }
    }

    public boolean depositar(Usuario u, Cuenta c, double monto) { // Realiza un depósito en la cuenta.
        if (monto > 0) {
            c.depositar(monto);
            this.efectivoDisponible += monto;
            return true;
        } else {
            // Falló, el monto no es válido
            return false;
        }
    }

    public double consultarSaldo(Cuenta c) { // Devuelve el saldo actual de la cuenta.
        return c.getSaldo();
    }

    // Función de mantenimiento: carga más dinero al cajero.
    public boolean cargarEfectivo(double monto) {
        if (monto > 0) {
            this.efectivoDisponible += monto;
            return true;
        }
        return false;
    }

    public Banco getBanco() { // Devuelve el banco asociado al cajero.
        return this.banco;
    }

    public void setBanco(Banco banco) { // Permite cambiar el banco asociado al cajero.
        this.banco = banco;
    }

    // Devuelve el estado actual del efectivo (usado por el menú de admin).
    public double getEfectivoDisponible() {
        return this.efectivoDisponible;
    }

    public void setEfectivoDisponible(double efectivoDisponible) { // Permite modificar el efectivo disponible.
        this.efectivoDisponible = efectivoDisponible;
    }
}