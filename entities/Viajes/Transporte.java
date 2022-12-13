package entities.Viajes;

public class Transporte {
    public String tipo;
    public String empresa;
    public String calidad;
    public String horaPartida;
    public String horaDestino;

    public Transporte(String tipo, String empresa, String calidad, String horaPartida, String horaDestino){
        this.tipo = tipo;
        this.empresa = empresa;
        this.calidad = calidad;
        this.horaPartida = horaPartida;
        this.horaDestino = horaDestino;
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
}
