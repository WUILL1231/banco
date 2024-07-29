import java.util.HashMap;
import java.util.Map;

class Banco {
    private Map<String, Cuenta> cuentas;

    public Banco() {
        cuentas = new HashMap<>();
    }

    public void abrirCuenta(String nombre, String apellido, int edad, double saldoInicial, String tipoCuenta) {
        if (tipoCuenta.equalsIgnoreCase("Ahorros")) {
            cuentas.put(nombre + " " + apellido, new CuentaAhorros(nombre, apellido, edad, saldoInicial));
        } else if (tipoCuenta.equalsIgnoreCase("Corriente")) {
            if (saldoInicial >= 200000) {
                cuentas.put(nombre + " " + apellido, new CuentaCorriente(nombre, apellido, edad, saldoInicial));
            } else {
                System.out.println("El monto inicial para cuenta corriente debe ser al menos 200,000.");
            }
        } else {
            System.out.println("Tipo de cuenta no valido.");
        }
    }

    public void transferir(String deNombre, String aNombre, double monto) {
        Cuenta cuentaDe = cuentas.get(deNombre);
        Cuenta cuentaA = cuentas.get(aNombre);

        if (cuentaDe != null && cuentaA != null && cuentaDe.getSaldo() >= monto) {
            cuentaDe.retirar(monto, true); 
            cuentaA.depositar(monto);
            System.out.println("Transferencia exitosa.");
        } else {
            System.out.println("Transferencia fallida. Verifique las cuentas y el saldo.");
        }
    }

    public void operarCajero(String nombre, double monto, boolean esCajeroDelBanco) {
        Cuenta cuenta = cuentas.get(nombre);
        if (cuenta != null) {
            cuenta.retirar(monto, esCajeroDelBanco);
        } else {
            System.out.println("Cuenta no encontrada.");
        }
    }

    public void cierreDeMes() {
        for (Cuenta cuenta : cuentas.values()) {
            cuenta.aplicarComisiones();
        }
        System.out.println("Cierre de mes realizado.");
    }

    public void mostrarEstadoCuenta(String nombre) {
        Cuenta cuenta = cuentas.get(nombre);
        if (cuenta != null) {
            System.out.println(cuenta);
        } else {
            System.out.println("Cuenta no encontrada.");
        }
    }

    public Map<String, Cuenta> getCuentas() {
        return cuentas;
    }
}
