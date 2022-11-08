package entities;

public class Admin extends Usuario{
    public Admin(String correo, String nombres, String apellidos, String contrasena){
        super(correo, nombres, apellidos, contrasena);
        tipoUsuario= "Administrador";
    }

    
}
