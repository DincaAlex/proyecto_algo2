package entities;

public class Cuarto extends Hotel {
    private int numero;
    private int piso;
    private boolean ocupado;

    public Cuarto (String ID, String nombre, String ciudad, int estrellas, int numero, int piso, boolean ocupado) {
        super(ID, nombre, ciudad, estrellas);
        this.numero = numero;
        this.piso = piso;
        this.ocupado = ocupado;
    }

    public int mostrarNumero () {
        return numero;
    }

    public int mostrarPiso () {
        return piso;
    }

    public boolean mostrarOcupado () {
        return ocupado;
    }

}
