package entities.Viajes;

import java.util.Scanner;

public class Ruta {
    public String ID;
    public String ciudadPartida;
    public String ciudadDestino;
    public String transporte;

    public Ruta(String ID, String ciudadPartida, String ciudadDestino, String transporte){
        this.ID= ID;
        this.ciudadPartida = ciudadPartida;
        this.ciudadDestino = ciudadDestino;
        this.transporte = transporte;
    }

    public String mostrarID() {
        return ID;
    }

    public String mostrarCiudadPartida() {
        return ciudadPartida;
    }

    public String mostrarCiudadDestino() {
        return ciudadDestino;
    }

    public String mostrarTransporte() {
        return transporte;
    }

    public void menuReserva(){
        System.out.println("Bienvenido a la reserva de viajes");
        System.out.println("Seleccione una opción: ");
        System.out.println("1. Reservar hotel\t 2. Reservar transporte\t 3. Salir");
        Scanner s= new Scanner(System.in);
        int opc= s.nextInt();
        switch(opc){
            case 1:; break;
            case 2:; break;
            case 3:; break;
        }
    }
}
