import Persistance.JSONConfigFileHoteles;
import Persistance.JSONConfigFileUsuarios;
import Persistance.Persistance;
import entities.Admin;
import entities.Cliente;
import hotel.Cuarto;
import hotel.Hotel;
import processes.SistemaConfig;

import java.util.Scanner;

public class AgenciaViajes {
    public static void main (String []args){
        int opc;
        boolean salir = false;
        SistemaConfig config = new SistemaConfig();
        Scanner scan = new Scanner(System.in);

        System.out.println("Bienvenido al sistema de Agencia de viajes");
        System.out.println("Ingrese el tipo de usuario: Administrador (1) o Cliente (2)");
        int user = scan.nextInt();

        Persistance p = new JSONConfigFileUsuarios();
        p.leerConfig(config);

        Persistance p1 = new JSONConfigFileHoteles();
        p1.leerConfig(config);

        while (!salir) {
            if (user==1) {
                opc = menuAdmin();
                switch (opc) {
                    case 1 -> {
                        System.out.println("Ingrese el correo con el que se registrara: ");
                        String correo = scan.next();
                        System.out.println("Ingrese sus nombre: ");
                        String nombres = scan.next();
                        System.out.println("Ingrese sus apellido:");
                        String apellidos = scan.next();
                        System.out.println("Ingrese una contrasena:");
                        String contrasena = scan.next();
                        Admin admin = new Admin(correo, nombres, apellidos, contrasena);
                        config.registrarAdmin(admin);
                    }
                    case 2 -> {
                        System.out.println("Ingrese su correo:");
                        String correoA = scan.next();
                        System.out.println("Ingrese su contrasena: ");
                        String contrasenaA = scan.next();
                        if (config.confirmarIngresoAdmin(correoA, contrasenaA)) {
                            System.out.println("Bienvenido.");
                            opc = menuOpcAdmin();
                            switch (opc) {
                                case 1, 2 -> {
                                    break;
                                }
                                case 3 -> {
                                    System.out.println("Ingrese el nombre del hotel: ");
                                    String nombre = scan.next();
                                    System.out.println("Ingrese la ciudad: ");
                                    String ciudad = scan.next();
                                    System.out.println("Ingrese las estrellas:");
                                    int estrellas = Integer.parseInt(scan.next());
                                    Hotel hotel = new Hotel(nombre, ciudad, estrellas);
                                    config.registrarHotel(hotel);
                                }
                                case 4 -> {
                                    System.out.println("Ingrese el nombre del hotel: ");
                                    String nombre = scan.next();
                                    System.out.println("Ingrese la ciudad del hotel: ");
                                    String ciudad = scan.next();
                                    System.out.println("Ingrese las estrellas del hotel: ");
                                    int estrellas = Integer.parseInt(scan.next());
                                    System.out.println("Ingrese el numero del cuarto: ");
                                    int numero = Integer.parseInt(scan.next());
                                    System.out.println("Ingrese el piso del cuarto: ");
                                    int piso = Integer.parseInt(scan.next());
                                    System.out.println("Ingrese si el cuarto esta ocupado:");
                                    boolean ocupado = Boolean.parseBoolean(scan.next());
                                    Cuarto cuarto = new Cuarto(nombre, ciudad, estrellas, numero, piso, ocupado);
                                    config.registrarHotel(cuarto);
                                }
                                case 5 -> {
                                    salir = true;
                                    break;
                                }
                            }
                        }
                    }
                    default -> salir = true;
                }
                
            } else {
                opc = menuCliente();
                switch (opc) {
                    case 1:
                        System.out.println("Ingrese el correo con el que se registrara: ");
                        String correo = scan.next();
                        System.out.println("Ingrese sus nombre: ");
                        String nombres = scan.next();
                        System.out.println("Ingrese sus apellido:");
                        String apellidos = scan.next();
                        System.out.println("Ingrese una contrasena:");
                        String contrasena = scan.next();
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
        p1.guardarConfig(config);
        }
    }
    



    public static int menuAdmin() {
        System.out.println("1. Registrar administrador");
        System.out.println("2. Ingresar como administrador");
        System.out.println("3. Salir");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    public static int menuCliente() {
        System.out.println("1. Registrar cliente");
        System.out.println("2. Ingresar como cliente");
        System.out.println("3. Salir");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    public static int menuOpcAdmin() {
        System.out.println("1. Agregar Rutas. [En construcción]");
        System.out.println("2. Agregar Transporte. [En construcción]");
        System.out.println("3. Agregar Hotel. ");
        System.out.println("4. Agregar Cuarto. [En construcción]");
        System.out.println("5. Salir");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
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