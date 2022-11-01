package entities;

import java.util.Scanner;

public class Usuario {
    private String nombreUsuario;
    private String contrasena;
    private String nombres;
    private String apellidos;
    protected String tipoUsuario;

    public Usuario(String nombreUsuario, String contrasena, String nombres, String apellidos)
    {
        this.nombreUsuario= nombreUsuario;
        this.contrasena= contrasena;
        this.nombres= nombres;
        this.apellidos= apellidos;
    }

    public String nombreCompleto(){
        return nombres+" "+apellidos;
    }
}
