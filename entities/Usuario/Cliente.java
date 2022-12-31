package entities.Usuario;

public class Cliente extends Usuario {
    public Cliente(String correo, String nombres, String apellidos, String contrasena,String saldo){
        super(correo, nombres, apellidos, contrasena, saldo);
        this.tipoUsuario= "Cliente";
    }
    
    public Cliente(String correo, String nombres, String apellidos, String contrasena, String saldo,String uuid){
        super(correo, nombres, apellidos, contrasena, saldo, uuid);
        this.tipoUsuario= "Cliente";
    }
}
