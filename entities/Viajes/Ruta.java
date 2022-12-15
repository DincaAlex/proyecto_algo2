package entities.Viajes;

import java.util.Scanner;

public class Ruta {
    private String IDRuta;
    private String ciudadPartida;
    private String ciudadDestino;
    private String tipoTransporte;

    public Ruta(String IDRuta, String ciudadPartida, String ciudadDestino, String tipoTransporte){
        this.IDRuta= IDRuta;
        this.ciudadPartida = ciudadPartida;
        this.ciudadDestino = ciudadDestino;
        this.tipoTransporte = tipoTransporte;
    }

    public String mostrarIDRuta() {
        return IDRuta;
    }

    public String mostrarCiudadPartida() {
        return ciudadPartida;
    }

    public String mostrarCiudadDestino() {
        return ciudadDestino;
    }

    public String mostrarTipoTransporte() {
        return tipoTransporte;
    }
}
