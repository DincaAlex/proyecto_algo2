package entities;

public class Cliente extends Usuario{
    public Cliente(String nombreUsuario, String contrasena, String nombres, String apellidos){
        super(nombreUsuario, contrasena, nombres, apellidos);
        tipoUsuario= "Cliente";
    }
}
