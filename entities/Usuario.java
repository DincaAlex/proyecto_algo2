package entities;

import java.util.UUID;

public abstract class Usuario {
    private String correo;
    private String nombres;
    private String apellidos;
    private String contrasena;
    private UUID uuid;
    protected String tipoUsuario;

    public Usuario(String correo, String nombres, String apellidos, String contrasena)
    {
        this.correo= correo;
        this.nombres= nombres;
        this.apellidos= apellidos;
        this.contrasena= contrasena;
        this.uuid= UUID.randomUUID();
    }

    public Usuario(String correo, String nombres, String apellidos, String contrasena, String uuid)
    {
        this.correo= correo;
        this.nombres= nombres;
        this.apellidos= apellidos;
        this.contrasena= contrasena;
        this.uuid= UUID.fromString(uuid);
    }

    public String mostrarTipoUsuario() {
        return tipoUsuario;
    }

    public String mostrarCorreo() {
        return correo;
    }
    public String mostrarNombres() {
        return nombres;
    }
    public String mostrarApellidos() {
        return apellidos;
    }
    public String mostrarContrasena() {
        return contrasena;
    }
    public String mostrarUUID() {
        return uuid.toString();
    }
}
