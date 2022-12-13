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

import java.util.Objects;
import java.util.Scanner;

public class AgenciaViajes {
    public static void main (String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean salir = false;

        System.out.println("Bienvenido al sistema de Agencia de viajes\n");
        System.out.println("Ingrese el tipo de usuario [Administrador/Cliente] [1/2]: ");
        int user = scan.nextInt();

        while (!salir) {
            if (user == 1)
                salir = menuAdmin();
            if (user == 2)
                salir = menuCliente();
        }
    }

    public static boolean menuAdmin() {
        System.out.println("1. Registrar administrador");
        System.out.println("2. Ingresar como administrador");
        System.out.println("3. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;

        if (opcion==1)
            registrarAdmin();
        if (opcion==2){
            if (ingresarAdmin()) {
                while(!salir) {
                    switch (menuOpcionesAdmin()) {
                        case 1, 2 -> System.out.println("Menu en construcción.");
                        case 3 -> agregarHotelAdmin();
                        case 4 -> agregarCuartoAdmin();
                        case 5 -> autogenerarCuartosAdmin();
                        default -> salir = true;
                    }
                }
            }
        }
        else
            salir = true;

        return salir;
    }

    public static void registrarAdmin() {
        Scanner scan = new Scanner(System.in);
        ConfigUsers config = new ConfigUsers();
        Persistance p = new JSONConfigFileUsuarios();
        p.leerConfig(config);
        String retry;

        do {
            retry = "N";
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
                retry = scan.next();
                if (Objects.equals(retry, "N") || Objects.equals(retry, "n"))
                    break;
            }
            else {
                Admin admin = new Admin(correo, nombres, apellidos, contrasena);
                config.registrarAdmin(admin);
                p.guardarConfig(config);
            }
        } while (retry.equalsIgnoreCase("s"));
    }

    public static boolean ingresarAdmin() {
        Scanner scan = new Scanner(System.in);
        ConfigUsers config = new ConfigUsers();
        Persistance p = new JSONConfigFileUsuarios();
        p.leerConfig(config);
        String retry;
        boolean entradaExitosa = false;

        do {
            retry = "N";
            System.out.println("Ingrese su correo:");
            String correoA = scan.next();
            System.out.println("Ingrese su contrasena: ");
            String contrasenaA = scan.next();
            if (!config.confirmarIngresoAdmin(correoA, contrasenaA)) {
                System.out.println("No se encontró el usuario. Desea intentar de nuevo? [S/N]: ");
                retry = scan.next();
                if (Objects.equals(retry, "N") || Objects.equals(retry, "n"))
                    break;
            }
            else {
                entradaExitosa = true;
                System.out.println("Bienvenido.\n");
            }
        } while (retry.equalsIgnoreCase("s"));
        return entradaExitosa;
    }

    public static int menuOpcionesAdmin() {
        System.out.println("1. Agregar Rutas [En construcción]");
        System.out.println("2. Agregar Transporte [En construcción]");
        System.out.println("3. Agregar Hotel");
        System.out.println("4. Agregar Cuarto");
        System.out.println("5. Autogenerar Cuartos");
        System.out.println("6. Salir");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    public static void agregarHotelAdmin() {
        Scanner scan = new Scanner(System.in);
        ConfigHoteles config = new ConfigHoteles();
        HotelPersistance p = new JSONConfigFileHoteles();
        p.leerConfig(config);

        System.out.println("Ingrese el nombre del hotel: ");
        String nombre = scan.nextLine();
        System.out.println("Ingrese la ciudad: ");
        String ciudad = scan.nextLine();
        System.out.println("Ingrese las estrellas:");
        int estrellas = Integer.parseInt(scan.next());
        Hotel hotel = new Hotel(nombre, ciudad, estrellas);
        config.registrarHotel(hotel);
        p.guardarConfig(config);
    }

    public static void agregarCuartoAdmin() {
        Scanner scan = new Scanner(System.in);
        ConfigHoteles config = new ConfigHoteles();
        HotelPersistance p = new JSONConfigFileHoteles();
        p.leerConfig(config);

        if (config.noHayHoteles()) {
            System.out.println("No hay hoteles registrados.");
        }
        else {
            config.mostrarHoteles();
            System.out.println("Escoge el nombre del hotel: ");
            String nombreH = scan.nextLine();
            String ciudad = config.buscarHotel(nombreH, 1);
            int estrellas = Integer.parseInt(config.buscarHotel(nombreH, 2));

            System.out.println("Ingrese el numero del cuarto: ");
            int numero = Integer.parseInt(scan.next());
            System.out.println("Ingrese el piso del cuarto: ");
            int piso = Integer.parseInt(scan.next());
            System.out.println("Ingrese si el cuarto esta ocupado:");
            boolean ocupado = Boolean.parseBoolean(scan.next());
            int idNum= piso*100+numero;
            String id= String.valueOf(idNum);
            Cuarto cuarto = new Cuarto(nombreH, ciudad, estrellas, numero, piso, ocupado, "", id);
            config.registrarCuarto(cuarto);
            p.guardarConfig(config);
        }
    }

    public static void autogenerarCuartosAdmin() {
        Scanner scan = new Scanner(System.in);
        ConfigHoteles config = new ConfigHoteles();
        HotelPersistance p = new JSONConfigFileHoteles();
        p.leerConfig(config);

        if (config.noHayHoteles()) {
            System.out.println("No hay hoteles registrados.");
        }
        else {
            System.out.println("Lista de hoteles registrados:");
            config.mostrarHoteles();
            System.out.println("Escoge el nombre del hotel: ");
            String nombreH = scan.nextLine();
            String ciudad = config.buscarHotel(nombreH, 1);
            int estrellas = Integer.parseInt(config.buscarHotel(nombreH, 2));

            System.out.println("Numero de pisos: ");
            int numP = scan.nextInt();
            System.out.println("Numero de cuartos por piso: ");
            int numC = scan.nextInt();
            config.autoGenerarCuartos(nombreH, ciudad, estrellas, numC, numP);
            p.guardarConfig(config);
        }
    }

    public static boolean menuCliente() {
        System.out.println("1. Registrar cliente");
        System.out.println("2. Ingresar como cliente");
        System.out.println("3. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;

        switch (opcion) {
            case 1 -> registrarCliente();
            case 2 -> {
                String UUID = ingresarCliente();
                while (!salir) {
                    switch (menuOpcionesCliente()) {
                        case 1,2,4,5 -> System.out.println("Menu en construcción");
                        case 3 -> reservarHotelCliente(UUID);
                        default -> salir = true;
                    }
                }
            }
            default -> salir = true;
        }
        return salir;
    }

    public static void registrarCliente() {
        Scanner scan = new Scanner(System.in);
        ConfigUsers config = new ConfigUsers();
        Persistance p = new JSONConfigFileUsuarios();
        p.leerConfig(config);

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

    public static String ingresarCliente() {
        Scanner scan = new Scanner(System.in);
        ConfigUsers config = new ConfigUsers();
        Persistance p = new JSONConfigFileUsuarios();
        p.leerConfig(config);
        String copiaUUID;

        System.out.println("Ingrese su correo:");
        String correoA = scan.next();
        System.out.println("Ingrese su contrasena: ");
        String contrasenaA = scan.next();
        if (config.confirmarIngresoCliente(correoA, contrasenaA)) {
            System.out.println("Bienvenido.");
            copiaUUID = config.copiarUUID(correoA);
        }
        else {
            copiaUUID = "null"; // en caso de no encontrar el usuario
        }
        return copiaUUID;
    }

    public static int menuOpcionesCliente() {
        System.out.println("1. Realizar reserva de transporte (en proceso)");
        System.out.println("2. Cancelar reserva de transporte (en proceso)");
        System.out.println("3. Realizar reserva de hotel");
        System.out.println("4. Cancelar reserva de hotel (en proceso)");
        System.out.println("5. Salir");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    public static void reservarHotelCliente(String UUID) {
        Scanner scan = new Scanner(System.in);
        ConfigHoteles config = new ConfigHoteles();
        HotelPersistance p = new JSONConfigFileHoteles();
        p.leerConfig(config);

        config.mostrarHoteles();
        System.out.println("Ingrese el nombre del hotel: ");
        String nHotel = scan.next();
        Cuarto cu = new Cuarto(nHotel, "", 0, 0, 0, false, "", "");
        config.reservarCuarto(cu, UUID);
        p.guardarConfig(config);
    }
}


