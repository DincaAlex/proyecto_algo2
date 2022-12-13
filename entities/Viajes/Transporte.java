package entities.Viajes;

import java.util.Scanner;

public class Transporte {
    public String tipo;
    public String empresa;
    public String calidad;
    public int horaPartida;
    public int horaDestino;

    public Transporte(String tipo, String empresa, String calidad, int horaPartida, int horaDestino){
        this.tipo = tipo;
        this.empresa = empresa;
        this.calidad = calidad;
        this.horaPartida = horaPartida;
        this.horaDestino = horaDestino;
    }

    public String mostrarTipo() {
        return tipo;
    }

    public String mostrarEmpresa() {
        return empresa;
    }

    public String mostrarCalidad() {
        return calidad;
    }

    public int mostrarHoraPartida() {
        return horaPartida;
    }

    public int mostrarHoraDestino() {
        return horaDestino;
    }

    public void reservaTransporte() {
        System.out.println("Elegir la calidad del bus: \n1.PRIMERA_CLASE  \n2.SEGUNDA_CLASE  \n3.ECONOMICO\n");
        Scanner c = new Scanner(System.in);
        int cal = c.nextInt();
        switch (cal) {
            case 1 -> calidad = "PRIMERA CLASE";
            case 2 -> calidad = "SEGUNDA CLASE";
            case 3 -> calidad = "ECONOMICO";
            default -> {
                System.out.println("INGRESE UN NUMERO VALIDO, GRACIAS");
                System.exit(0);
            }
        }
    }
}
