import processes.*;

import java.util.Scanner;

import entities.Reservas.ReservaTransporte;

public class AgenciaViajes {
    static ConfigAdmins configAdmins = new ConfigAdmins();
    static ConfigClientes configClientes = new ConfigClientes();
    static ConfigRutas configRutas = new ConfigRutas();
    static ConfigTransportes configTransportes = new ConfigTransportes();
    static ConfigHoteles configHoteles = new ConfigHoteles();
    static ConfigCuartos configCuartos = new ConfigCuartos();
    static  ConfigReservaHotel configReservaHotel= new ConfigReservaHotel();
    static ConfigReservaTransporte configReservaTransporte= new ConfigReservaTransporte();
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("Bienvenido al sistema de Agencia de viajes\n");
            System.out.println("Ingrese el tipo de usuario [Administrador/Cliente] [1/2]: ");
            int user = scan.nextInt();
            scan.nextLine();
            if (user == 1) {
                while (!salir) {
                    System.out.println("Ingrese el código de seguridad para ingresar como administrador: ");
                    String cod = scan.nextLine();
                    if (cod.equals("12345"))
                        salir = menuAdmin();
                    else{
                        System.out.println("Código invalido");
                        salir = true;
                    }
                }
            }
            if (user == 2)
                salir = menuCliente();
        }
    }
    public static boolean menuAdmin() {
        Scanner scan = new Scanner(System.in);
        System.out.println("1. Registrar administrador");
        System.out.println("2. Ingresar como administrador");
        System.out.println("3. Salir");
        int opcion = scan.nextInt();
        boolean salir = false;
        if (opcion == 1)
            configAdmins.registrarAdmin();
        if (opcion == 2) {
            boolean entradaExitosa = configAdmins.ingresarAdmin();
            if (entradaExitosa) {
                boolean exit = false;
                while (!exit) {
                    exit = menuOpcionesAdmin();
                }
            }
        } else
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
        if (opcion == 1)
            configClientes.registrarCliente();
        if (opcion == 2) {
            String UUID = configClientes.ingresarCliente();
            if (!UUID.equals("null")) {
                boolean exit = false;
                while (!exit) {
                    exit = menuOpcionesCliente(UUID);
                }
            }
        } else
            salir = true;
        return salir;
    }
    public static boolean menuOpcionesAdmin() {
        System.out.println("Elija el menu que desa acceder:");
        System.out.println("1. Rutas");
        System.out.println("2. Transporte");
        System.out.println("3. Hoteles");
        System.out.println("4. Cuartos");
        System.out.println("5. Administradores");
        System.out.println("6. Clientes");
        System.out.println("7. Imprimir ticket");
        System.out.println("8. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;
        boolean exit = false;
        while (!exit) {
            switch (opcion) {
                case 1 -> exit = menuRutasAdmin();
                case 2 -> exit = menuTransporteAdmin();
                case 3 -> exit = menuHotelesAdmin();
                case 4 -> exit = menuCuartosAdmin();
                case 5 -> exit = menuAdminsAdmin();
                case 6 -> exit = menuClientesAdmin();
                default -> {
                    salir = true;
                    exit = true;
                }
            }
        }
        return salir;
    }
    public static boolean menuRutasAdmin() {
        System.out.println("Menu de las rutas");
        System.out.println("1. Mostrar");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar");
        System.out.println("4. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;
        switch (opcion) {
            case 1 -> configRutas.mostrarRutas();
            case 2 -> configRutas.agregarRuta();
            case 3 -> configRutas.eliminarRuta();
            default -> salir = true;
        }
        return salir;
    }
    public static boolean menuTransporteAdmin() {
        System.out.println("Menu de los transportes");
        System.out.println("1. Mostrar");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar");
        System.out.println("4. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;
        switch (opcion) {
            case 1 -> configTransportes.mostrarTransportes();
            case 2 -> configTransportes.agregarTransporte();
            case 3 -> configTransportes.eliminarTransporte();
            default -> salir = true;
        }
        return salir;
    }
    public static boolean menuHotelesAdmin() {
        System.out.println("Menu de los hoteles");
        System.out.println("1. Mostrar");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar");
        System.out.println("4. Modificar precios");
        System.out.println("5. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;
        switch (opcion) {
            case 1 -> configHoteles.mostrarHoteles();
            case 2 -> configHoteles.agregarHotel();
            case 3 -> configHoteles.eliminarHotel();
            case 4 -> configHoteles.modificarPrecioHotel();
            default -> salir = true;
        }
        return salir;
    }
    public static boolean menuCuartosAdmin() {
        System.out.println("Menu de los cuartos");
        System.out.println("1. Mostrar ");
        System.out.println("2. Agregar");
        System.out.println("3. Agregar en masa");
        System.out.println("4. Eliminar ");
        System.out.println("5. Modificar precios de cuartos");
        System.out.println("6. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;
        switch (opcion) {
            case 1 -> {
                configHoteles.mostrarHoteles();
                System.out.println("Elija el nombre del hotel: ");
                scan.nextLine();
                String nombreHotel = scan.nextLine();
                configCuartos.mostrarCuartosHotel(nombreHotel);
            }
            case 2 -> configCuartos.agregarCuarto();
            case 3 -> configCuartos.generarCuartosEnMasse();
            case 4 -> configCuartos.eliminarCuarto();
            case 5 -> configCuartos.modificarPrecioCuarto();
            default -> salir = true;
        }
        return salir;
    }
    public static boolean menuAdminsAdmin() {
        System.out.println("Menu de los administradores");
        System.out.println("1. Mostrar ");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar ");
        System.out.println("4. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;
        switch (opcion) {
            case 1 -> configAdmins.mostrarAdmins();
            case 2 -> configAdmins.registrarAdmin();
            case 3 -> configAdmins.eliminarAdmin();
            default -> salir = true;
        }
        return salir;
    }
    public static boolean menuClientesAdmin() {
        System.out.println("Menu de los clientes");
        System.out.println("1. Mostrar");
        System.out.println("2. Agregar");
        System.out.println("3. Eliminar");
        System.out.println("4. Imprimir ticket");
        System.out.println("5. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;
        switch (opcion) {
            case 1 -> configClientes.mostrarClientes();
            case 2 -> configClientes.registrarCliente();
            case 3 -> configClientes.eliminarCliente();

            default -> salir = true;
        }
        return salir;
    }
    public static boolean menuOpcionesCliente(String UUID) {
        System.out.println("1. Realizar reserva de transporte");
        System.out.println("2. Cancelar reserva de transporte");
        System.out.println("3. Realizar reserva de hotel");
        System.out.println("4. Cancelar reserva de hotel");
        System.out.println("5. Ver reservas realizadas");
        System.out.println("6. Borrar cuenta");
        System.out.println("7. Imprimir ticket");
        System.out.println("8. Pagar");
        System.out.println("9. Salir");
        Scanner scan = new Scanner(System.in);
        int opcion = scan.nextInt();
        boolean salir = false;
        switch (opcion) {
            case 1 -> configTransportes.reservarTransporte(UUID);
            case 2 -> configReservaTransporte.eliminarReservaTransporteCliente(UUID);
            case 3 -> configHoteles.reservarHotel(UUID);
            case 4 -> configReservaHotel.eliminarReservaHotelCliente(UUID);
            case 5 -> {
                configReservaHotel.mostrarReservasHotelCliente(UUID);
                configReservaTransporte.mostrarReservasTransporteCliente(UUID);
            }
            case 6 -> {
                if (!configReservaHotel.mostrarReservasHotelCliente(UUID) && configReservaTransporte.mostrarReservasTransporteCliente(UUID)) {
                    configClientes.borrarCliente(UUID);
                    salir = true;
                }
                else {
                    System.out.println("No se puede borrar la cuanta si existen reservas realizadas\n");
                    System.out.println("Cancela la reserva o espere hasta después de la compleción de la reserva");
                }
            }
            case 7 -> {
                System.out.println("TICKET DEL CLIENTE : " + UUID + "\n");
                System.out.println("Reserva en el HOTEL: ");
                configReservaHotel.mostrarReservasHotelTicket(UUID);
                System.out.println("Reserva en el TRANSPORTE: ");
                configReservaTransporte.mostrarReservaTransporteTicket(UUID);
                System.out.println("Pulse enter para continuar");
                scan.nextLine();
                scan.nextLine();
            }
            case 8 ->{
                String saldito = configClientes.mostrarSaldoCliente(UUID);
                int saldoA = Integer.parseInt(saldito);
                String idHotel = configReservaHotel.recuperarIDHotel(UUID);
                Float descontar = configHoteles.mostrarPrecioHoteles(idHotel);
                int descuentoA = Math.round(descontar);
                int nuevoSaldo = saldoA-descuentoA;
                String nuevoSaldoA = String.valueOf(nuevoSaldo);
                System.out.println("--------------------PROCESANDO--------------------");
                System.out.println("Su saldo actual es de: " + saldito );
                System.out.println("------------------PROCESANDO-PAGO------------------");
                System.out.println("Saldo restante: "+ nuevoSaldoA);
                configClientes.modificarSaldoAutomatico(UUID,nuevoSaldoA);
            }
            default -> salir = true;
        }
        return salir;
    }
}