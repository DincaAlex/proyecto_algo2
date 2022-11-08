import Persistance.JSONConfigFile;
import Persistance.Persistance;
import entities.Admin;
import entities.Cliente;
import processes.SistemaConfig;
import java.util.Scanner;

public class AgenciaViajes {
    public static void main (String []args){
        SistemaConfig config = new SistemaConfig();
        System.out.println("Bienvenido al sistema de Agencia de viajes");
        int opc;
        boolean salir = false;
        System.out.println("Ingrese el tipo de usuario: Administrador (1) o Cliente (2)");
        Scanner scanner = new Scanner(System.in);
        int user = scanner.nextInt();
        Persistance p = new JSONConfigFile();
        p.leerConfig(config);
        while (!salir) {
            if (user==1) {
                opc = menuAdmin();
                switch (opc) {
                    case 1: 
                        System.out.println("Ingrese el correo con el que se registrara: ");
                        String correo = scanner.next();
                        System.out.println("Ingrese sus nombre: ");
                        String nombres = scanner.next();
                        System.out.println("Ingrese sus apellido:");
                        String apellidos = scanner.next();
                        System.out.println("Ingrese una contrasena:");
                        String contrasena = scanner.next();
                        Admin admin= new Admin(correo, nombres, apellidos, contrasena);
                        config.registrarAdmin(admin);
                        break;
                    case 2:
                        System.out.println("Ingrese su correo:");
                        String correoA = scanner.next();
                        System.out.println("Ingrese su contrasena: ");
                        String contrasenaA = scanner.next();
                        if(config.confirmarIngresoAdmin(correoA, contrasenaA))
                        {
                            System.out.println("Bienvenido.");
                            int menu;
                            menu = menuOpcAdm();
                        }
                        break;
                    case 3:
                        salir=true;
                        break;
                }
                
            }
            else {
                opc = menuCliente();
                switch (opc) {
                    case 1:
                        System.out.println("Ingrese el correo con el que se registrara: ");
                        String correo = scanner.next();
                        System.out.println("Ingrese sus nombre: ");
                        String nombres = scanner.next();
                        System.out.println("Ingrese sus apellido:");
                        String apellidos = scanner.next();
                        System.out.println("Ingrese una contrasena:");
                        String contrasena = scanner.next();
                        Cliente cliente = new Cliente(correo, nombres, apellidos, contrasena);
                        config.registrarCliente(cliente);
                        break;
                    case 2:
                        break;
                    case 3:
                        salir = true;
                        break;
                }
                //Menu para clientes
            }
        p.guardarConfig(config);
        }
    }
    



    public static int menuAdmin()
    {
        System.out.println("1. Registrar administrador");
        System.out.println("2. Ingresar como administrador");
        System.out.println("3. Salir");
        Scanner b = new Scanner(System.in);
        int opc = b.nextInt();
        return opc;
    }

    

    public static int menuCliente()
    {
        System.out.println("1. Registrar cliente");
        System.out.println("2. Ingresar como cliente");
        System.out.println("3. Salir");
        Scanner b = new Scanner(System.in);
        int opc = b.nextInt();
        return opc;
    }

    public static int menuOpcAdm()
    {
        System.out.println("1. Agregar Rutas");
        System.out.println("2. Agregar Transporte");
        System.out.println("3. Salir");
        Scanner c= new Scanner(System.in);
        int opc= c.nextInt();
        return opc;
    }

}



/*
Agregar funcionamiento Clientes
public class AgenciaViajes {

    public static void main(String[] args) {
       
           System.out.println("BIENVENIDO A NUESTRA AGENCIA DE VIAJE \nRESERVE SU VIAJE: ");
        System.out.println("PRESIONE EN TIPO DE TRANSPORTE QUE DESEA : \n1.Bus \n2.Avion \n3.Salir");
   
        Scanner t = new Scanner(System.in);
        int p = t.nextInt();
        
        switch(p){
            case 1:
              Bus b1 = new Bus(0,"","","");
            
              b1.menuReserva();
              b1.imprimirInfoReserva();
              break;
            case 2: 
              Avion a1 = new Avion(0,"","","");
              a1.menuReserva();
              a1.imprimirInfoReserva();
              break;
            case 3:
                System.out.println("GRACIAS POR SU VISITA");
                break;
          default:
              System.out.println("INGRESE UN NUMERO VALIDO, GRACIAS");
              break;
        }
    }
}
 */