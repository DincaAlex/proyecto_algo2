package Hotel;

public class Hotel {
    private String nombre;
    private String ciudad;
    private int estrellas;

    Hotel (String nombre, String ciudad, int estrellas) {
        this.nombre = nombre;
        this.ciudad = nombre;
        this.estrellas = estrellas;
    }

    public String mostrarNombre () {
        return nombre;
    }

    public String mostarCiudad () {
        return ciudad;
    }

    public int mostrarEstrellas () {
        return estrellas;
    }
}
