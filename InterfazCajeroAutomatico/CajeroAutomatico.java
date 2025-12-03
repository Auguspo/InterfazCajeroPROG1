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

    public int extraer(Usuario u, Cuenta c, double monto) {
        if (monto > this.efectivoDisponible) {
            return 1;
        }

        if (c.extraer(monto)) {
            this.efectivoDisponible -= monto;
            return 0;
        } else {
            return 2;
        }
    }

    public void depositar(Usuario u, Cuenta c, double monto) {
        c.depositar(monto);
        this.efectivoDisponible += monto;
    }

    public double consultarSaldo(Cuenta c) {
        return c.getSaldo();
    }

    public void cargarEfectivo(double monto) {
        this.efectivoDisponible += monto;
    }

    public Banco getBanco() {
        return this.banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public double getEfectivoDisponible() {
        return this.efectivoDisponible;
    }

    public void setEfectivoDisponible(double efectivoDisponible) {
        this.efectivoDisponible = efectivoDisponible;
    }
}