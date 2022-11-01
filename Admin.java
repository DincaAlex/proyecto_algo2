package entities;

public class Admin extends Usuario{
    public Admin(String nombreUsuario, String contrasena, String nombres, String apellidos){
        super(nombreUsuario, contrasena, nombres, apellidos);
        tipoUsuario= "Administrador";
    }
}
