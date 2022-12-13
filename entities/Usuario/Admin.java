package entities.Usuario;

public class Admin extends Usuario {
    public Admin(String correo, String nombres, String apellidos, String contrasena){
        super(correo, nombres, apellidos, contrasena);
        this.tipoUsuario= "Administrador";
    }
    
    public Admin(String correo, String nombres, String apellidos, String contrasena, String uuid){
        super(correo, nombres, apellidos, contrasena, uuid);
        this.tipoUsuario= "Administrador";
    }
}
