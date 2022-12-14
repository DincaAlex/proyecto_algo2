import Persistence.*;
import processes.*;

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
        persistenceHoteles.leerConfig(configHoteles, configCuartos);

        System.out.println("1. Registrar administrador");
        System.out.println("2. Ingresar como administrador");
        System.out.println("3. Salir");
        int opcion = scan.nextInt();
        boolean salir = false;

        if (opcion==1)
            configAdmins.registrarAdmin();
        if (opcion==2) {
            boolean entradaExitosa = configAdmins.ingresarAdmin();
            if (entradaExitosa) {
                menuOpcionesAdmin();
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
            case 1 -> configClientes.registrarCliente();
            case 2 -> {
                String UUID = configClientes.ingresarCliente();
                while (!salir && !Objects.equals(UUID, "null")) {
                    switch (menuOpcionesCliente()) {
                        case 1,2,4 -> System.out.println("Menu en construcción");
                        case 3 -> configHoteles.reservarHotel(UUID);
                        default -> salir = true;
                    }
                }
            }
            default -> salir = true;
        }
        return salir;
    }

    public static void menuOpcionesAdmin () {
        System.out.println("Elija el menu que desa acceder:");
        System.out.println("1. Rutas");
        System.out.println("2. Transporte");
        System.out.println("3. Hoteles");
        System.out.println("4. Cuartos");
        System.out.println("5. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;

        while (!salir) {
            switch (opcion) {
                case 1 -> salir = menuRutasAdmin();
                case 2 -> salir = menuTransporteAdmin();
                case 3 -> salir = menuHotelesAdmin();
                case 4 -> salir = menuCuartosAdmin();
            }
        }
    }

    public static boolean menuRutasAdmin () {
        System.out.println("Menu de las rutas");
        System.out.println("1. Mostrar [En construcción]");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar [En construcción]");
        System.out.println("4. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;

        switch (opcion) {
            case 1, 3 -> {
            }
            case 2 -> configRutas.agregarRuta();
            default -> salir = true;
        }
        return salir;
    }

    public static boolean menuTransporteAdmin () {
        System.out.println("Menu de los transportes");
        System.out.println("1. Mostrar [En construcción]");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar [En construcción]");
        System.out.println("4. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;

        switch (opcion) {
            case 1, 3 -> {
            }
            case 2 -> configTransportes.agregarTransporte();
            default -> salir = true;
        }
        return salir;
    }

    public static boolean menuHotelesAdmin () {
        System.out.println("Menu de los hoteles");
        System.out.println("1. Mostrar");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar");
        System.out.println("4. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;

        switch (opcion) {
            case 1 -> {
                if (!configHoteles.mostrarHoteles()){
                    System.out.println("Presione ENTER para continuar...");
                    scan.nextLine();
                    scan.nextLine(); // detiene el programa
                }
                // bug para investigar:
                // si llamas mostrar por si solo no se actualiza la lista si hay algún cambio
                // funciona en el caso 3 que hace una llamada por separado
            }
            case 2 -> configHoteles.agregarHotel();
            case 3 -> configHoteles.eliminarHotel();
            default -> salir = true;
        }
        return salir;
    }

    public static boolean menuCuartosAdmin () {
        System.out.println("Menu de los cuartos");
        System.out.println("1. Mostrar [En construcción]");
        System.out.println("2. Agregar");
        System.out.println("3. Agregar en masse");
        System.out.println("4. Eliminar [En construcción]");
        System.out.println("5. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;

        switch (opcion) {
            case 1, 4 -> {
            }
            case 2 -> configCuartos.agregarCuarto();
            case 3 -> configCuartos.generarCuartosEnMasse();
            default -> salir = true;
        }
        return salir;
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
}