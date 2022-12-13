package entities.Viajes;

import java.time.LocalDateTime;

public class Transporte {
    public String tipo;
    public String empresa;
    public String calidad;
    public LocalDateTime horaPartida;
    public LocalDateTime horaDestino;

    public Transporte(String tipo, String empresa, String calidad, LocalDateTime horaPartida, LocalDateTime horaDestino){
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

    public LocalDateTime mostrarHoraPartida() {
        return horaPartida;
    }

    public LocalDateTime mostrarHoraDestino() {
        return horaDestino;
    }
}
