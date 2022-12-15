package entities.Viajes;

public class Transporte extends Ruta {
    private String ID;
    private String empresa;
    private String calidad;
    private String horaPartida;
    private String horaDestino;
    private int cantDisponible;

    public Transporte(String IDRuta, String ciudadPartida, String ciudadDestino, String tipoTransporte, String ID, String empresa, String calidad, String horaPartida, String horaDestino, int cantDisponible){
        super(IDRuta, ciudadPartida, ciudadDestino, tipoTransporte);
        this.ID= ID;
        this.empresa = empresa;
        this.calidad = calidad;
        this.horaPartida = horaPartida;
        this.horaDestino = horaDestino;
        this.cantDisponible= cantDisponible;
    }

    public String mostrarID(){
        return ID;
    }

    public String mostrarEmpresa() {
        return empresa;
    }

    public String mostrarCalidad() {
        return calidad;
    }

    public String mostrarHoraPartida() {
        return horaPartida;
    }

    public String mostrarHoraDestino() {
        return horaDestino;
    }

    public int mostrarCantDisponible(){
        return cantDisponible;
    }
}
