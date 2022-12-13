package entities.Usuario;

public class Cliente extends Usuario {
    public Cliente(String correo, String nombres, String apellidos, String contrasena){
        super(correo, nombres, apellidos, contrasena);
        this.tipoUsuario= "Cliente";
    }
    
    public Cliente(String correo, String nombres, String apellidos, String contrasena, String uuid){
        super(correo, nombres, apellidos, contrasena, uuid);
        this.tipoUsuario= "Cliente";
    }
}
