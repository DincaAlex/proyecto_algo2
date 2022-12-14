package entities.Viajes;

public class Transporte {
    private String ID;
    private String tipo;
    private String empresa;
    private String calidad;
    private String horaPartida;
    private String horaDestino;
    private int cantDisponible;

    public Transporte(String ID, String tipo, String empresa, String calidad, String horaPartida, String horaDestino, int cantDisponible){
        this.ID= ID;
        this.tipo = tipo;
        this.empresa = empresa;
        this.calidad = calidad;
        this.horaPartida = horaPartida;
        this.horaDestino = horaDestino;
        this.cantDisponible= cantDisponible;
    }

    public String mostrarID(){
        return ID;
    }

    public String mostrarTipo() {
        return tipo;
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
