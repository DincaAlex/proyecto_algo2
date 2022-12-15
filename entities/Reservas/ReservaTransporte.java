package entities.Reservas;

public class ReservaTransporte {
    private String IDCliente;
    private String IDRuta;
    private String IDTransporte;

    public ReservaTransporte(String IDCliente, String IDRuta, String IDTransporte){
        this.IDCliente= IDCliente;
        this.IDRuta= IDRuta;
        this.IDTransporte= IDTransporte;
    }

    public String mostrarIDCliente(){
        return IDCliente;
    }

    public String mostrarIDRuta(){
        return IDRuta;
    }

    public String mostrarIDTransporte(){
        return IDTransporte;
    }
}
