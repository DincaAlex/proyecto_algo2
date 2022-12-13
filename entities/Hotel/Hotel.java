package entities.Hotel;

public class Hotel {
    private String nombre;
    private String ciudad;
    private int estrellas;

    public Hotel (String nombre, String ciudad, int estrellas) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estrellas = estrellas;
    }
    public String mostrarNombre () {
        return nombre;
    }

    public String mostrarCiudad () {
        return ciudad;
    }

    public int mostrarEstrellas () {
        return estrellas;
    }
}
