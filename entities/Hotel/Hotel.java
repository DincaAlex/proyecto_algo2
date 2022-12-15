package entities.Hotel;

public class Hotel {
    private String IDHotel;
    private String nombre;
    private String ciudad;
    private int estrellas;

    public Hotel (String IDHotel, String nombre, String ciudad, int estrellas) {
        this.IDHotel= IDHotel;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estrellas = estrellas;
    }

    public String mostrarIDHotel (){
        return IDHotel;
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
