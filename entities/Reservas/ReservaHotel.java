package entities.Reservas;

import java.util.UUID;

public class ReservaHotel {
    private UUID IDReserva;
    private String IDCliente;
    private String IDHotel;
    private String IDCuarto;

    public ReservaHotel(String IDCliente, String IDHotel, String IDCuarto){
        this.IDReserva= UUID.randomUUID();
        this.IDCliente= IDCliente;
        this.IDHotel= IDHotel;
        this.IDCuarto= IDCuarto;
    }

    public ReservaHotel(String UUID, String IDCliente, String IDHotel, String IDCuarto){
        this.IDCliente= IDCliente;
        this.IDHotel= IDHotel;
        this.IDCuarto= IDCuarto;
    }

    public String mostrarIDReserva(){
        return IDReserva.toString();
    }

    public String mostrarIDCliente(){
        return IDCliente;
    }

    public String mostrarIDHotel(){
        return IDHotel;
    }

    public String mostrarIDCuarto(){
        return IDCuarto;
    }
}
