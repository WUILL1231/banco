import java.util.Scanner;

public class MatriculaEstudiantes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Selecciona una carrera:");
        System.out.println("1. Ingenieria en Sistemas");
        System.out.println("2. Ingenieria Civil");
        System.out.println("3. Medicina");
        int carrera = scanner.nextInt();
        scanner.nextLine();  

        System.out.println("Ingrese los nombres del estudiante:");
        String nombres = scanner.nextLine();
        System.out.println("Ingrese los apellidos del estudiante:");
        String apellidos = scanner.nextLine();
        System.out.println("Ingrese el documento del estudiante:");
        String documento = scanner.nextLine();
        System.out.println("Ingrese la direccion del estudiante:");
        String direccion = scanner.nextLine();
        System.out.println("Ingrese el telefono del estudiante:");
        String telefono = scanner.nextLine();


        System.out.println("Realiza curso en linea? (si/no):");
        String enLinea = scanner.nextLine().toLowerCase();


        System.out.println("Ingrese el semestre del estudiante:");
        int semestre = scanner.nextInt();

        double costoMatricula = 1500.0;
        double aumentoPorSemestre = semestre > 5 ? 0.05 : 0.0;

        double montoMatricula = costoMatricula + (costoMatricula * aumentoPorSemestre);

        if (enLinea.equals("no")) {
            System.out.println("Ingrese el numero de materias aplazadas:");
            int materiasAplazadas = scanner.nextInt();
            System.out.println("Ingrese el numero de creditos por materia:");
            int creditosPorMateria = scanner.nextInt();
            scanner.nextLine();  
            
            double costoCredito = 0.0;
            if (semestre <= 3) {
                costoCredito = 20.0;
            } else if (semestre <= 6) {
                costoCredito = 25.0;
            } else {
                costoCredito = 30.0;
            }
            
            double costoMaterias = materiasAplazadas * creditosPorMateria * costoCredito;
            montoMatricula += costoMaterias;
        }

       
        System.out.println("\n--- Recibo de Inscripcion ---");
        System.out.println("Nombre del Estudiante: " + nombres + " " + apellidos);
        System.out.println("Documento: " + documento);
        System.out.println("Direccion: " + direccion);
        System.out.println("Telefono: " + telefono);
        System.out.println("Carrera: " + getCarrera(carrera));
        System.out.println("Semestre: " + semestre);
        System.out.println("Costo de la Matricula: $" + montoMatricula);
    }

    private static String getCarrera(int carrera) {
        switch (carrera) {
            case 1: return "Ingenieria en Sistemas";
            case 2: return "Ingenieria Civil";
            case 3: return "Medicina";
            default: return "Desconocida";
        }
    }
}
