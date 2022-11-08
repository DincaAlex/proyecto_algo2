package ruta;

import java.util.Scanner;

public class Transporte {
    public String tipo;
    public String empresa;
    public String calidad;
    public int horaP;
    public int horaL;

    public Transporte(String tipo, String empresa, String calidad, int horaP, int horaL){
        this.tipo=tipo;
        this.empresa=empresa;
        this.calidad=calidad;
        this.horaP=horaP;
        this.horaL=horaL;
    }

    public void reservaTransporte(){
        System.out.println("Eliga la calidad del bus: \n1.PRIMERA_CLASE  \n2.SEGUNDA_CLASE  \n3.ECONOMICO\n");
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
