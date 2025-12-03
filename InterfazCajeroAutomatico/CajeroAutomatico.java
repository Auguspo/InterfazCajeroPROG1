package InterfazCajeroAutomatico;
public class CajeroAutomatico {
    private Banco banco;
    private double efectivoDisponible;

    public CajeroAutomatico(Banco banco, double efectivoInicial) {
        this.banco = banco;
        this.efectivoDisponible = efectivoInicial;
    }

    public Usuario iniciarSesion(String nroUsuario, int pin) {
        Usuario u = banco.buscarUsuarioPorNro(nroUsuario);
        if (u != null) {
            if (u.validarPin(pin)) {
                return u;
            }
        }
        return null;
    }

    public boolean extraer(Usuario u, Cuenta c, double monto) {
        if (monto > efectivoDisponible) {
            System.out.println("El cajero no tiene suficiente efectivo.");
            return false;
        }

        if (c.extraer(monto)) {
            efectivoDisponible -= monto;
            System.out.println("Extracción exitosa. Retire su dinero.");
            return true;
        } else {
            System.out.println("Saldo insuficiente o límite excedido.");
            return false;
        }
    }

    public void depositar(Usuario u, Cuenta c, double monto) {
        c.depositar(monto);
        System.out.println("Depósito exitoso. Nuevo saldo: " + c.getSaldo());
    }

    public double consultarSaldo(Cuenta c) {
        return c.getSaldo();
    }
    
    public void cargarEfectivo(double monto) {
        this.efectivoDisponible += monto;
        System.out.println("Cajero recargado. Total disponible: " + efectivoDisponible);
    }
}