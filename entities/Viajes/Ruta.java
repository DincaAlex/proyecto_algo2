package entities.Viajes;

public class Ruta {
    private String IDRuta;
    private String ciudadPartida;
    private String ciudadDestino;

    public Ruta(String IDRuta, String ciudadPartida, String ciudadDestino){
        this.IDRuta= IDRuta;
        this.ciudadPartida = ciudadPartida;
        this.ciudadDestino = ciudadDestino;
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
}
