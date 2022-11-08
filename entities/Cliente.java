package entities;

public class Cliente extends Usuario{
    public Cliente(String correo, String nombres, String apellidos, String contrasena){
        super(correo, nombres, apellidos, contrasena);
        this.tipoUsuario= "Cliente";
    }
}
