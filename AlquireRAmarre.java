package actividad.semana.pkg4.poo;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Barco {
    String matricula;
    double eslora;
    int anoFabricacion;

    Barco(String matricula, double eslora, int anoFabricacion) {
        this.matricula = matricula;
        this.eslora = eslora;
        this.anoFabricacion = anoFabricacion;
    }
}

class Cliente {
    String nombre;
    int numClientes;

    Cliente(String nombre, int numClientes) {
        this.nombre = nombre;
        this.numClientes = numClientes;
    }
}

class Alquiler {
    Cliente cliente;
    Barco barco;
    LocalDate fechaInicio;
    LocalDate fechaFin;
    String posicionAmarre;

    Alquiler(Cliente cliente, Barco barco, LocalDate fechaInicio, LocalDate fechaFin, String posicionAmarre) {
        this.cliente = cliente;
        this.barco = barco;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.posicionAmarre = posicionAmarre;
    }

    double calcularCosto() {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return dias * 25000;  
    }

    void imprimirRecibo() {
        System.out.println("\n--- Recibo de Alquiler de Amarre ---");
        System.out.println("Nombre del Cliente: " + cliente.nombre);
        System.out.println("Numero de Clientes: " + cliente.numClientes);
        System.out.println("Matricula del Barco: " + barco.matricula);
        System.out.println("Eslora: " + barco.eslora + " metros");
        System.out.println("Ano de Fabricacion: " + barco.anoFabricacion);
        System.out.println("Posicion del Amarre: " + posicionAmarre);
        System.out.println("Fecha de Inicio: " + fechaInicio);
        System.out.println("Fecha de Fin: " + fechaFin);
        System.out.println("Costo Total: " + calcularCosto() + " pesos");
    }
}

public class AlquilerAmarre {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre del cliente:");
        String nombreCliente = scanner.nextLine();
        System.out.println("Ingrese el numero de clientes:");
        int numClientes = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = new Cliente(nombreCliente, numClientes);

        System.out.println("Ingrese la matricula del barco:");
        String matriculaBarco = scanner.nextLine();
        System.out.println("Ingrese la eslora del barco en metros:");
        double esloraBarco = scanner.nextDouble();
        System.out.println("Ingrese el ano de fabricacion del barco:");
        int anoFabricacionBarco = scanner.nextInt();
        scanner.nextLine();  

        Barco barco = new Barco(matriculaBarco, esloraBarco, anoFabricacionBarco);

        System.out.println("Ingrese la fecha de inicio (yyyy-mm-dd):");
        LocalDate fechaInicio = LocalDate.parse(scanner.nextLine());
        System.out.println("Ingrese la fecha de fin (yyyy-mm-dd):");
        LocalDate fechaFin = LocalDate.parse(scanner.nextLine());
        System.out.println("Ingrese la posicion del amarre:");
        String posicionAmarre = scanner.nextLine();

        Alquiler alquiler = new Alquiler(cliente, barco, fechaInicio, fechaFin, posicionAmarre);

        alquiler.imprimirRecibo();
    }
}
