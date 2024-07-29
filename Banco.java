como se vio en el documento word cuenta, bancos y main estan separados aqui estaran juntos pero señalizare el comienzo y el fin de cada uno:

abstract class Cuenta {
    protected String nombre;
    protected String apellido;
    protected int edad;
    protected double saldo;
    protected String tipoCuenta;

    public Cuenta(String nombre, String apellido, int edad, double saldo, String tipoCuenta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.saldo = saldo;
        this.tipoCuenta = tipoCuenta;
    }

    public abstract void aplicarComisiones();

    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            aplicarComisionesDeposito(monto);
        } else {
            System.out.println("El monto de deposito debe ser positivo.");
        }
    }

    public void retirar(double monto, boolean esCajeroDelBanco) {
        if (monto > 0 && saldo >= monto) {
            saldo -= monto;
            if (!esCajeroDelBanco) {
                saldo -= 4500; 
            }
        } else {
            System.out.println("Saldo insuficiente o monto invalido.");
        }
    }

    protected abstract void aplicarComisionesDeposito(double monto);

    public double getSaldo() {
        return saldo;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + " " + apellido +
               "\nTipo de Cuenta: " + tipoCuenta +
               "\nSaldo: " + saldo;
    }
}

class CuentaAhorros extends Cuenta {

    public CuentaAhorros(String nombre, String apellido, int edad, double saldo) {
        super(nombre, apellido, edad, saldo, "Ahorros");
    }

    @Override
    public void aplicarComisiones() {
     
    }

    @Override
    protected void aplicarComisionesDeposito(double monto) {
        if (monto < 500000) {
            saldo -= 3000;
        } else if (monto < 2000000) {
            saldo -= 3000 + monto * 0.01;
        } else if (monto <= 10000000) {
            saldo -= 2000 + monto * 0.005;
        } else if (monto <= 100000000) {
            saldo -= monto * 0.018;
        } else {
            saldo -= monto * 0.02;
        }
    }
}

class CuentaCorriente extends Cuenta {

    public CuentaCorriente(String nombre, String apellido, int edad, double saldo) {
        super(nombre, apellido, edad, saldo, "Corriente");
    }

    @Override
    public void aplicarComisiones() {
        saldo -= saldo * 0.015;
    }

    @Override
    protected void aplicarComisionesDeposito(double monto) {
        if (monto < 500000) {
            saldo -= 7000;
        } else if (monto < 2000000) {
            saldo -= 5000 + monto * 0.02;
        } else if (monto <= 10000000) {
            saldo -= 4000 + monto * 0.02;
        } else {
            saldo -= monto * 0.033;
        }
    }
}

_______________________________________________________________________________________________________________________________________________________________________________________________________

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

___________________________________________________________________________________________________________________________________________________________________________________________________

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    private static Banco banco = new Banco();
    private static DecimalFormat df = new DecimalFormat("#,##0.00");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Menu:");
            System.out.println("1. Apertura de Cuentas");
            System.out.println("2. Transferencias");
            System.out.println("3. Cajero Automatico");
            System.out.println("4. Estado de Cuenta");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    abrirCuenta(scanner);
                    break;
                case 2:
                    transferencias(scanner);
                    break;
                case 3:
                    cajeroAutomatico(scanner);
                    break;
                case 4:
                    mostrarEstadoCuenta(scanner);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 5);
    }

    private static void abrirCuenta(Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); 

        double saldo = leerMonto(scanner, "Saldo inicial: ");
        System.out.print("Tipo de cuenta (Ahorros/Corriente): ");
        String tipoCuenta = scanner.nextLine();

        banco.abrirCuenta(nombre, apellido, edad, saldo, tipoCuenta);
    }

    private static void transferencias(Scanner scanner) {
        System.out.print("Nombre del remitente (Nombre Apellido): ");
        String deNombre = scanner.nextLine();
        System.out.print("Nombre del destinatario (Nombre Apellido): ");
        String aNombre = scanner.nextLine();

        double monto = leerMonto(scanner, "Monto a transferir: ");
        banco.transferir(deNombre, aNombre, monto);
    }

    private static void cajeroAutomatico(Scanner scanner) {
        System.out.print("Nombre (Nombre Apellido): ");
        String nombre = scanner.nextLine();

        double monto = leerMonto(scanner, "Monto a retirar: ");
        System.out.print("¿Es cajero del banco? (si/no): ");
        String respuesta = scanner.nextLine();
        boolean esCajeroDelBanco = respuesta.equalsIgnoreCase("si");

        banco.operarCajero(nombre, monto, esCajeroDelBanco);
    }

    private static double leerMonto(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine();
        
        input = input.replace(',', '.');
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Monto invalido, se usara 0.0.");
            return 0.0;
        }
    }

    private static void mostrarEstadoCuenta(Scanner scanner) {
        System.out.print("Nombre (Nombre Apellido): ");
        String nombre = scanner.nextLine();
        Cuenta cuenta = banco.getCuentas().get(nombre);
        if (cuenta != null) {
            System.out.println(cuenta);
        } else {
            System.out.println("Cuenta no encontrada.");
        }
    }
}
