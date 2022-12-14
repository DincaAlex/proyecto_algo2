import Persistence.*;
import entities.Hotel.Cuarto;
import entities.Hotel.Hotel;
import entities.Usuario.Admin;
import entities.Usuario.Cliente;
import entities.Viajes.Ruta;
import entities.Viajes.Transporte;
import processes.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

public class AgenciaViajes {
    static ConfigAdmins configAdmins = new ConfigAdmins();
    static ConfigClientes configClientes = new ConfigClientes();
    static JSONConfigFileUsuarios persistenceUsuarios = new JSONConfigFileUsuarios();
    static ConfigRutas configRutas = new ConfigRutas();
    static ConfigTransportes configTransportes = new ConfigTransportes();
    static JSONConfigFileViajes persistenceViajes = new JSONConfigFileViajes();
    static ConfigHoteles configHoteles = new ConfigHoteles();
    static ConfigCuartos configCuartos = new ConfigCuartos();
    static JSONConfigFileHoteles persistenceHoteles = new JSONConfigFileHoteles();

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
        Scanner scan = new Scanner(System.in);
        ConfigHoteles configHoteles = new ConfigHoteles();
        ConfigCuartos configCuartos = new ConfigCuartos();
        JSONConfigFileHoteles p = new JSONConfigFileHoteles();
        p.leerConfig(configHoteles, configCuartos);

        System.out.println("1. Registrar administrador");
        System.out.println("2. Ingresar como administrador");
        System.out.println("3. Salir");
        int opcion = scan.nextInt();
        boolean salir = false;
        if (opcion==1)
            registrarAdmin();
        if (opcion==2) {
            boolean entradaExitosa = ingresarAdmin();
            while(!salir && entradaExitosa) {
                switch (menuOpcionesAdmin()) {
                    case 1 -> agregarRutaAdmin();
                    case 2 -> agregarTransporteAdmin();
                    case 3 -> agregarHotelAdmin();
                    case 4 -> {
                        configHoteles.mostrarHoteles();
                        System.out.println("Presione ENTER para continuar...");
                        scan.nextLine(); // creo que el scan.nextInt anterior está malogrando algo
                        scan.nextLine(); // detiene el código
                    }
                    case 5 -> agregarCuartoAdmin();
                    case 6 -> autogenerarCuartosAdmin();
                    default -> salir = true;
                }
            }
        }
        else
            salir = true;

        return salir;
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
                while (!salir && !Objects.equals(UUID, "null")) {
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

    public static void registrarAdmin() {
        Scanner scan = new Scanner(System.in);
        persistenceUsuarios.leerConfig(configAdmins, configClientes);
        String retryAnswer;
        do {
            retryAnswer = "n";
            System.out.println("Ingrese el correo con el que se registrara: ");
            String correo = scan.next();
            System.out.println("Ingrese su nombre: ");
            String nombres = scan.next();
            System.out.println("Ingrese su apellido:");
            String apellidos = scan.next();
            System.out.println("Ingrese una contrasena:");
            String contrasena = scan.next();
            if (configAdmins.confirmarIngresoAdmin(correo, contrasena)) {
                System.out.println("El correo ya esta registrado. Desea intentar de nuevo? [S/N]: ");
                retryAnswer = scan.next();
                if (retryAnswer.equalsIgnoreCase("n"))
                    break;
            }
            else {
                Admin admin = new Admin(correo, nombres, apellidos, contrasena);
                configAdmins.registrar(admin);
                persistenceUsuarios.guardarConfig(configAdmins, configClientes);
            }
        } while (retryAnswer.equalsIgnoreCase("s"));
    }

    public static boolean ingresarAdmin() {
        Scanner scan = new Scanner(System.in);
        persistenceUsuarios.leerConfig(configAdmins, configClientes);
        String retryAnswer;
        int retryTimes = 0;
        boolean entradaExitosa = false;
        do {
            retryAnswer = "n";
            System.out.println("Ingrese su correo:");
            String correoA = scan.next();
            System.out.println("Ingrese su contrasena: ");
            String contrasenaA = scan.next();
            if (!configAdmins.confirmarIngresoAdmin(correoA, contrasenaA)) {
                System.out.println("No se encontró el usuario. Desea intentar de nuevo? [S/N]: ");
                retryAnswer = scan.next();
                if (retryAnswer.equalsIgnoreCase("n"))
                    break;
                if (retryTimes>=3) {
                    System.out.println("Excedió el número máximo de intentos.");
                    retryAnswer = "n";
                    break;
                }
                if (retryAnswer.equalsIgnoreCase("s"))
                    retryTimes++;
            }
            else {
                entradaExitosa = true;
                System.out.println("Bienvenido.\n");
            }
        } while (retryAnswer.equalsIgnoreCase("s"));
        return entradaExitosa;
    }

    public static int menuOpcionesAdmin () {
        System.out.println("Elija el menu que desa acceder:");
        System.out.println("1. Rutas");
        System.out.println("2. Transporte");
        System.out.println("3. Hoteles");
        System.out.println("4. Cuartos");
        System.out.println("5. Salir");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();

        // switch (opcion) {
          //  case 1 -> menuRutasAdmin();
          //  case 2 -> menuTransporteAdmin();
          //  case 3 -> menuHotelesAdmin();
          //  case 4 -> menuCuartosAdmin();
        //}
    }

    public static void menuRutasAdmin () {
        System.out.println("Menu de las rutas");
        System.out.println("1. Mostrar");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();

        switch (opcion) {
            case 1, 3, 2 -> {
            }
        }
    }

    public static void menuTransporteAdmin () {
        System.out.println("Menu de los transportes");
        System.out.println("1. Mostrar");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();

        switch (opcion) {
            case 1, 3, 2 -> {
            }
        }
    }

    public static void menuHotelesAdmin () {
        System.out.println("Menu de los hoteles");
        System.out.println("1. Mostrar");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();

        switch (opcion) {
            case 1, 3, 2 -> {
            }
        }
    }

    public static void menuCuartosAdmin () {
        System.out.println("Menu de los cuartos");
        System.out.println("1. Mostrar");
        System.out.println("2. Agregar");
        System.out.println("3. Agregar en masse");
        System.out.println("3. Eliminar");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();

        switch (opcion) {
            case 1, 3, 2 -> {
            }
        }
    }

    public static void agregarRutaAdmin() {
        Scanner scan = new Scanner(System.in);
        persistenceViajes.leerConfig(configRutas, configTransportes);

        System.out.println("Ingrese el ID de la ruta: ");
        String ID = scan.next();
        System.out.println("Ingrese la ciudad de partida: ");
        String ciudadPartida = scan.next();
        System.out.println("Ingrese la ciudad de destino:");
        String ciudadDestino = scan.next();
        System.out.println("Ingrese el tipo de transporte:");
        String transporte = scan.next();
        Ruta ruta = new Ruta(ID, ciudadPartida, ciudadDestino, transporte);
        configRutas.registrar(ruta);
        persistenceViajes.guardarConfig(configRutas, configTransportes);
    }

    public static void agregarHotelAdmin () {
        Scanner scan = new Scanner(System.in);
        persistenceHoteles.leerConfig(configHoteles, configCuartos);
        System.out.println("Ingrese el nombre del hotel: ");
        String nombre = scan.nextLine();
        System.out.println("Ingrese la ciudad: ");
        String ciudad = scan.nextLine();
        System.out.println("Ingrese las estrellas:");
        int estrellas = Integer.parseInt(scan.next());
        Hotel hotel = new Hotel(nombre, ciudad, estrellas);
        configHoteles.registrar(hotel);
        persistenceHoteles.guardarConfig(configHoteles, configCuartos);
    }

    public static void agregarCuartoAdmin () {
        Scanner scan = new Scanner(System.in);
        persistenceHoteles.leerConfig(configHoteles, configCuartos);
        if (configHoteles.noHayHoteles()) {
            System.out.println("No hay hoteles registrados.");
        }
        else {
            configHoteles.mostrarHoteles();
            System.out.println("Escoge el nombre del hotel: ");
            String nombreH = scan.nextLine();
            Hotel hotel = configHoteles.recuperarHotel(nombreH);
            String ciudad = hotel.mostrarCiudad();
            int estrellas = hotel.mostrarEstrellas();
            System.out.println("Ingrese el numero del cuarto: ");
            int numero = Integer.parseInt(scan.next());
            System.out.println("Ingrese el piso del cuarto: ");
            int piso = Integer.parseInt(scan.next());
            System.out.println("Ingrese si el cuarto esta ocupado:");
            boolean ocupado = Boolean.parseBoolean(scan.next());
            int idNum = piso*100+numero;
            String id = String.valueOf(idNum);
            Cuarto cuarto = new Cuarto(nombreH, ciudad, estrellas, numero, piso, ocupado, "", id);
            configCuartos.registrar(cuarto);
            persistenceHoteles.guardarConfig(configHoteles, configCuartos);
        }
    }

    public static void autogenerarCuartosAdmin() {
        Scanner scan = new Scanner(System.in);
        persistenceHoteles.leerConfig(configHoteles, configCuartos);
        if (configHoteles.noHayHoteles()) {
            System.out.println("No hay hoteles registrados.");
        }
        else {
            String ciudad;
            int estrellas;
            String nombreHotel;
            String retry;
            do {
                retry = "N";
                System.out.println("Lista de hoteles registrados:");
                configHoteles.mostrarHoteles();
                System.out.println("Elige el nombre del hotel: ");
                nombreHotel = scan.nextLine();
                Hotel hotel = configHoteles.recuperarHotel(nombreHotel);
                ciudad = hotel.mostrarCiudad();
                estrellas = hotel.mostrarEstrellas();

                if (estrellas==-1) { // error para el caso en que lo ingresado no se encuentra
                    System.out.println("Desea intentar de nuevo? [S/N]: ");
                    retry = scan.next();
                    if (retry.equalsIgnoreCase("n"))
                        break;
                }
                else {
                    System.out.println("Numero de pisos: ");
                    int numP = scan.nextInt();
                    System.out.println("Numero de cuartos por piso: ");
                    int numC = scan.nextInt();
                    configCuartos.autoGenerarCuartos(nombreHotel, ciudad, estrellas, numC, numP);
                    persistenceHoteles.guardarConfig(configHoteles, configCuartos);
                }
            } while (retry.equalsIgnoreCase("s"));
        }
    }



    public static void agregarTransporteAdmin () {
        Scanner scan= new Scanner(System.in);
        persistenceViajes.leerConfig(configRutas, configTransportes);
        System.out.println("Ingrese el tipo de transporte: ");
        String tipoTransporte= scan.next();
        System.out.println("Ingrese la empresa:");
        String empresa= scan.next();
        System.out.println("Ingrese la calidad del servicio: ");
        String calidad= scan.next();
        System.out.println("Ingrese el dia de partida (dd-mm-yyyy): ");
        int diaP= scan.nextInt();
        int mesP= scan.nextInt();
        int anioP= scan.nextInt();
        System.out.println("Ingrese la hora de partida (hh-mm-ss): ");
        int horasP= scan.nextInt();
        int minutosP= scan.nextInt();
        int segundosP= scan.nextInt();
        LocalDateTime hPartida= LocalDateTime.of(anioP, mesP, diaP, horasP, minutosP, segundosP);
        System.out.println("Ingrese el dia de destino (dd-mm-yyyy): ");
        int diaD= scan.nextInt();
        int mesD= scan.nextInt();
        int anioD= scan.nextInt();
        System.out.println("Ingrese la hora de destino (hh-mm-ss): ");
        int horasD= scan.nextInt();
        int minutosD= scan.nextInt();
        int segundosD= scan.nextInt();
        LocalDateTime hDestino= LocalDateTime.of(anioD, mesD, diaD, horasD, minutosD, segundosD);
        Transporte transporte= new Transporte(tipoTransporte, empresa, calidad, hPartida.toString(), hDestino.toString());
        configTransportes.registrar(transporte);
        persistenceViajes.guardarConfig(configRutas, configTransportes);
    }


    public static void registrarCliente () {
        Scanner scan = new Scanner(System.in);
        persistenceUsuarios.leerConfig(configAdmins, configClientes);
        String retryAnswer;
        do {
            retryAnswer = "n";
            System.out.println("Ingrese el correo con el que se registrara: ");
            String correo = scan.next();
            System.out.println("Ingrese su nombre: ");
            String nombres = scan.next();
            System.out.println("Ingrese su apellido:");
            String apellidos = scan.next();
            System.out.println("Ingrese una contrasena:");
            String contrasena = scan.next();
            if (configClientes.confirmarIngresoCliente(correo, contrasena)) {
                System.out.println("El correo ya esta registrado. Desea intentar de nuevo? [S/N]: ");
                retryAnswer = scan.next();
                if (retryAnswer.equalsIgnoreCase("n"))
                    break;
            }
            else {
                Cliente cliente = new Cliente(correo, nombres, apellidos, contrasena);
                configClientes.registrar(cliente);
                persistenceUsuarios.guardarConfig(configAdmins, configClientes);
            }
        } while (retryAnswer.equalsIgnoreCase("s"));
    }

    public static String ingresarCliente () {
        Scanner scan = new Scanner(System.in);
        persistenceUsuarios.leerConfig(configAdmins, configClientes);
        String copiaUUID = "";
        String retryAnswer = "n";
        int retryTimes = 0;
        do {
            System.out.println("Ingrese su correo:");
            String correoA = scan.next();
            System.out.println("Ingrese su contrasena: ");
            String contrasenaA = scan.next();
            if (!configClientes.confirmarIngresoCliente(correoA, contrasenaA)) {
                System.out.println("No se encontró el usuario.\n");
                System.out.println("Desea intentar de nuevo? [S/N]: ");
                retryAnswer = scan.next();
                if (retryAnswer.equalsIgnoreCase("n"))
                    break;
                if (retryTimes>=3) {
                    System.out.println("Excedió el número máximo de intentos.");
                    retryAnswer = "n";
                    break;
                }
                if (retryAnswer.equalsIgnoreCase("s"))
                    retryTimes++;
            }
            else {
                System.out.println("Bienvenido.");
                copiaUUID = configClientes.copiarUUID(correoA);
            }
        } while (retryAnswer.equalsIgnoreCase("s"));
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
        persistenceHoteles.leerConfig(configHoteles, configCuartos);
        configHoteles.mostrarHoteles();
        System.out.println("Ingrese el nombre del hotel: ");
        String nHotel = scan.nextLine();
        Cuarto cu = new Cuarto(nHotel, "", 0, 0, 0, false, "", "");
        configCuartos.reservar(cu, UUID);
        persistenceHoteles.guardarConfig(configHoteles, configCuartos);
    }
}