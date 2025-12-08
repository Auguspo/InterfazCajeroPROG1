package InterfazCajeroAutomatico;

public class CajeroAutomatico {

    private Banco banco;
    private double efectivoDisponible; // Dinero físico actual en el cajero (restricción de hardware).

    public CajeroAutomatico(Banco banco, double efectivoInicial) {
        this.banco = banco;
        this.efectivoDisponible = efectivoInicial;
    }

    public Usuario iniciarSesion(String nroUsuario, int pin) { // Lógica de autenticación: busca usuario y valida el PIN.
        Usuario u = banco.buscarUsuarioPorNro(nroUsuario);
        if (u != null) {
            if (u.validarPin(pin)) {
                return u;
            }
        }
        return null;
    }

    public int extraer(Usuario u, Cuenta c, double monto) { // Ejecuta la extracción, devolviendo un código de estado (Polimorfismo en c.extraer).
        if (monto > this.efectivoDisponible) { // Validación 1: ¿Hay suficiente efectivo en el cajero?
            return 1; // Código 1: Cajero sin suficiente efectivo.
        }

        if (c.extraer(monto)) { // Llama al método polimórfico (depende si es CajaAhorro o CuentaCorriente).
            this.efectivoDisponible -= monto;
            return 0; // Código 0: Éxito. El saldo se reduce y el efectivo del cajero también.
        } else {
            return 2; // Código 2: Fallo de la cuenta (saldo o límite insuficiente).
        }
    }

    public void depositar(Usuario u, Cuenta c, double monto) { // Simula un depósito, incrementando el saldo de la cuenta.
        c.depositar(monto);
        this.efectivoDisponible += monto;
    }

    public double consultarSaldo(Cuenta c) {
        return c.getSaldo();
    }

    public void cargarEfectivo(double monto) { // Función de mantenimiento: inyecta más dinero al cajero.
        this.efectivoDisponible += monto;
    }

    public Banco getBanco() {
        return this.banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public double getEfectivoDisponible() { // Devuelve el estado actual del efectivo (usado por el menú de admin).
        return this.efectivoDisponible;
    }

    public void setEfectivoDisponible(double efectivoDisponible) {
        this.efectivoDisponible = efectivoDisponible;
    }
}