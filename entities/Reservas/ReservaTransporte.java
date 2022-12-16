package entities.Reservas;

import java.util.UUID;

public class ReservaTransporte {
    private UUID IDReserva;
    private String IDCliente;
    private String IDRuta;
    private String IDTransporte;

    public ReservaTransporte(String IDCliente, String IDRuta, String IDTransporte){
        this.IDReserva= UUID.randomUUID();
        this.IDCliente= IDCliente;
        this.IDRuta= IDRuta;
        this.IDTransporte= IDTransporte;
    }

    public ReservaTransporte(String IDReserva, String IDCliente, String IDRuta, String IDTransporte){
        this.IDReserva= UUID.fromString(IDReserva);
        this.IDCliente= IDCliente;
        this.IDRuta= IDRuta;
        this.IDTransporte= IDTransporte;
    }

    public String mostrarIDReserva(){
        return IDReserva.toString();
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
