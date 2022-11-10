package hotel;

public class Hotel {
    private String ID;
    private String nombre;
    private String ciudad;
    private int estrellas;

    public Hotel (String ID, String nombre, String ciudad, int estrellas) {
        this.ID = ID;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estrellas = estrellas;
    }
    public String mostrarID () {
        return ID;
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
