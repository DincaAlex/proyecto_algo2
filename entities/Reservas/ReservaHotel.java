package entities.Reservas;

public class ReservaHotel {
    private String IDCliente;
    private String nombreHotel;
    private String ciudadHotel;
    private String IDCuarto;

    public ReservaHotel(String IDCliente, String nombreHotel, String ciudadHotel, String IDCuarto){
        this.IDCliente= IDCliente;
        this.nombreHotel= nombreHotel;
        this.ciudadHotel= ciudadHotel;
        this.IDCuarto= IDCuarto;
    }

    public String mostrarIDCliente(){
        return IDCliente;
    }

    public String mostrarnombreHotel(){
        return nombreHotel;
    }

    public String mostrarCiudadHotel(){
        return ciudadHotel;
    }

    public String mostrarIDCuarto(){
        return IDCuarto;
    }
}
