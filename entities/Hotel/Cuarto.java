package entities.Hotel;

public class Cuarto extends Hotel {
    private int numero;
    private int piso;
    private boolean ocupado;
    private String cReserva;
    private String ID;

    public Cuarto (String IDHotel, String nombre, String ciudad, int estrellas, int numero, int piso, boolean ocupado, String cReserva, String ID) {
        super(IDHotel, nombre, ciudad, estrellas);
        this.numero = numero;
        this.piso = piso;
        this.ocupado = ocupado;
        this.cReserva= cReserva;
        this.ID= ID;
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

    public void ocupar(){
        this.ocupado=true;
    }

    public String mostrarClienteReserva(){
        return cReserva;
    }

    public void clienteReservar(String a){
        cReserva=a;
    }

    public String mostrarID(){
        return this.ID;
    }
}
