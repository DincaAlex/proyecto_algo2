import Persistance.HotelPersistance;
import Persistance.JSONConfigFileHoteles;
import Persistance.JSONConfigFileUsuarios;
import Persistance.Persistance;
import entities.Admin;
import entities.Cliente;
import entities.Cuarto;
import entities.Hotel;
import processes.ConfigHoteles;
import processes.ConfigUsers;

import java.util.Scanner;

public class AgenciaViajes {
    public static void main(String[] args) {
        int opc;
        boolean salir = false;
        String continuar;
        ConfigUsers config = new ConfigUsers();
        ConfigHoteles config2= new ConfigHoteles();
        Scanner scan = new Scanner(System.in);

        System.out.println("Bienvenido al sistema de Agencia de viajes\n");
        System.out.println("Ingrese el tipo de usuario [Administrador/Cliente] [1/2]: ");
        int user = scan.nextInt();

        Persistance p = new JSONConfigFileUsuarios();
        p.leerConfig(config);
        HotelPersistance p1 = new JSONConfigFileHoteles();
        p1.leerConfig(config2);

        while (!salir) {
            if (user == 1) {
                opc = menuAdmin();
                switch (opc) {
                    case 1 -> {
                        do {
                            continuar = "N";
                            System.out.println("Ingrese el correo con el que se registrara: ");
                            String correo = scan.next();
                            System.out.println("Ingrese su nombre: ");
                            String nombres = scan.next();
                            System.out.println("Ingrese su apellido:");
                            String apellidos = scan.next();
                            System.out.println("Ingrese una contrasena:");
                            String contrasena = scan.next();
                            if (config.confirmarIngresoAdmin(correo, contrasena)) {
                                System.out.println("El correo ya esta registrado. Desea intentar de nuevo? [S/N]: ");
                                continuar = scan.next();
                            }
                            else {
                                Admin admin = new Admin(correo, nombres, apellidos, contrasena);
                                config.registrarAdmin(admin);
                                p.guardarConfig(config);
                            }
                        } while (continuar.equalsIgnoreCase("s"));
                    }
                    case 2 -> {
                        do {
                            continuar = "N";
                            System.out.println("Ingrese su correo:");
                            String correoA = scan.next();
                            System.out.println("Ingrese su contrasena: ");
                            String contrasenaA = scan.next();
                            if (!config.confirmarIngresoAdmin(correoA, contrasenaA)) {
                                System.out.println("No se encontró el usuario. Desea intentar de nuevo? [S/N]: ");
                                continuar = scan.next();
                                if(continuar=="N"|| continuar== "n"){
                                    break;
                                }
                            }
                        } while (continuar.equalsIgnoreCase("s"));
                        System.out.println("Bienvenido.\n");
                        while (!salir) {
                            opc = menuOpcAdmin();
                            switch (opc) {
                                case 1, 2 -> {
                                    System.out.println("Menu en construcción.");
                                }
                                case 3 -> {
                                    scan.nextLine();
                                    System.out.println("Ingrese el nombre del hotel: ");
                                    String nombre = scan.nextLine();
                                    System.out.println("Ingrese la ciudad: ");
                                    String ciudad = scan.nextLine();
                                    System.out.println("Ingrese las estrellas:");
                                    int estrellas = Integer.parseInt(scan.next());
                                        Hotel hotel = new Hotel(nombre, ciudad, estrellas);
                                        config2.registrarHotel(hotel);
                                        p1.guardarConfig(config2);
                                    }
                                    case 4 -> {
                                        if (config2.noHayHoteles()) {
                                            System.out.println("No hay hoteles registrados.");
                                        }
                                        else {
                                            config2.mostrarHoteles();
                                            scan.nextLine();
                                            System.out.println("Escoge el nombre del hotel: ");
                                            String nombreH = scan.nextLine();
                                            String ciudad = config2.buscarHotel(nombreH, 1);
                                            int estrellas = Integer.parseInt(config2.buscarHotel(nombreH, 2));

                                            System.out.println("Ingrese el numero del cuarto: ");
                                            int numero = Integer.parseInt(scan.next());
                                            System.out.println("Ingrese el piso del cuarto: ");
                                            int piso = Integer.parseInt(scan.next());
                                            System.out.println("Ingrese si el cuarto esta ocupado:");
                                            boolean ocupado = Boolean.parseBoolean(scan.next());

                                            Cuarto cuarto = new Cuarto(nombreH, ciudad, estrellas, numero, piso, ocupado, "");
                                            config2.registrarCuarto(cuarto);
                                            p1.guardarConfig(config2);
                                        }
                                    }
                                    case 5 -> {
                                        if (config2.noHayHoteles()) {
                                            System.out.println("No hay hoteles registrados.");
                                        }
                                        else {
                                            System.out.println("Lista de hoteles registrados:");
                                            config2.mostrarHoteles();
                                            scan.nextLine();
                                            System.out.println("Escoge el nombre del hotel: ");
                                            String nombreH = scan.nextLine();
                                            String ciudad = config2.buscarHotel(nombreH, 1);
                                            int estrellas = Integer.parseInt(config2.buscarHotel(nombreH, 2));

                                            System.out.println("Numero de pisos: ");
                                            int numP = scan.nextInt();
                                            System.out.println("Numero de cuartos por piso: ");
                                            int numC = scan.nextInt();
                                            config2.autoGenerarCuartos(nombreH, ciudad, estrellas, numC, numP);
                                            p1.guardarConfig(config2);
                                        }
                                    }
                                    default -> salir = true;
                            }
                        }
                    }
                }
            }
            else{
                opc = menuCliente();
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
                        Cliente cliente = new Cliente(correo, nombres, apellidos, contrasena);
                        config.registrarCliente(cliente);
                        p.guardarConfig(config);
                    }
                    case 2 -> {
                        System.out.println("Ingrese su correo:");
                        String correoA = scan.next();
                        System.out.println("Ingrese su contrasena: ");
                        String contrasenaA = scan.next();
                        if (config.confirmarIngresoCliente(correoA, contrasenaA)) {
                            System.out.println("Bienvenido.");
                            String copiaUUID= config.copiarUUID(correoA);
                            while(!salir){
                                opc= menuOpcCliente();
                                switch(opc){
                                    case 1: 
                                    break;
                                    case 2: 
                                    break;
                                    case 3:
                                        config2.mostrarHoteles();
                                        System.out.println("Ingrese el nombre del hotel: ");
                                        String nHotel= scan.next();
                                        Cuarto cu= new Cuarto(nHotel, "", 0, 0, 0, false, "");
                                        config2.reservarCuarto(cu, copiaUUID);
                                        p1.guardarConfig(config2);
                                    break;
                                    case 4:
                                    break;
                                    case 5: salir=true;
                                    break;
                                }
                            }
                        }
                    }
                    case 3 -> salir = true;
                }
            } //Menu para clientes
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
        System.out.println("1. Agregar Rutas [En construcción]");
        System.out.println("2. Agregar Transporte [En construcción]");
        System.out.println("3. Agregar Hotel");
        System.out.println("4. Agregar Cuarto");
        System.out.println("5. Autogenerar Cuartos");
        System.out.println("6. Salir");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    public static int menuOpcCliente() {
        System.out.println("1. Realizar reserva de transporte (en proceso)");
        System.out.println("2. Cancelar reserva de transporte (en proceso)");
        System.out.println("3. Realizar reserva de hotel");
        System.out.println("4. Cancelar reserva de hotel (en proceso)");
        System.out.println("5. Salir");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }
}


