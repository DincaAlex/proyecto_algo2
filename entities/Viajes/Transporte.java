package entities.Viajes;

import java.util.Date;

public class Transporte {
    public String tipo;
    public String empresa;
    public String calidad;
    public Date horaPartida;
    public Date horaDestino;

    public Transporte(String tipo, String empresa, String calidad, Date horaPartida, Date horaDestino){
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

    public Date mostrarHoraPartida() {
        return horaPartida;
    }

    public Date mostrarHoraDestino() {
        return horaDestino;
    }
}
