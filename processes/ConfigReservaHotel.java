package processes;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Persistence.JSONConfigFileReservas;
import entities.Reservas.ReservaHotel;

public class ConfigReservaHotel implements Config<ReservaHotel> {
    private static HashMap<String, ReservaHotel> reservasHotel;
    private static final ConfigReservaHotel configReservaHotel= new ConfigReservaHotel();
    private static final ConfigReservaTransporte configReservaTransporte= new ConfigReservaTransporte();
    //private static final ConfigReservaHotel configreservahotel = new ConfigReservaHotel();

    public ConfigReservaHotel() {
        reservasHotel = new HashMap<String, ReservaHotel>();
    }

    public void registrar(ReservaHotel args){
        reservasHotel.put(args.mostrarIDReserva(), args);
    }

    public JSONArray ToJSON () {
        JSONArray arrayReservaHotel = new JSONArray();
        Enumeration<ReservaHotel> rh = Collections.enumeration(reservasHotel.values());
        while(rh.hasMoreElements()) {
            ReservaHotel rHot = rh.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("IDReserva", rHot.mostrarIDReserva());
            obj.put("IDCliente", rHot.mostrarIDCliente());
            obj.put("IDHotel", rHot.mostrarIDHotel());
            obj.put("IDCuarto", rHot.mostrarIDCuarto());
            arrayReservaHotel.add(obj);
        }
        return arrayReservaHotel;
    }

    public void actualizar() {
        JSONConfigFileReservas p = new JSONConfigFileReservas();
        p.leerConfig(configReservaHotel, configReservaTransporte);
    }

    public void guardar() {
        JSONConfigFileReservas p = new JSONConfigFileReservas();
        p.guardarConfig(configReservaHotel, configReservaTransporte);
    }

    public void reservarHotel(String UUID, String IDHotel, String IDCuarto){
        actualizar();
        ReservaHotel reservaH= new ReservaHotel(UUID, IDHotel, IDCuarto);
        reservasHotel.put(reservaH.mostrarIDReserva(), reservaH);
        guardar();
    }

    private void eliminar (String ID) {
        actualizar();
        Enumeration<ReservaHotel> enumH = Collections.enumeration(reservasHotel.values());

        while (enumH.hasMoreElements()) {
            ReservaHotel reservaHotel = enumH.nextElement();
            if (ID.equals(reservaHotel.mostrarIDReserva())) {
                reservasHotel.remove(reservaHotel.mostrarIDReserva(), reservaHotel);
                reservasHotel.remove(reservaHotel.mostrarIDCliente(), reservaHotel);
                reservasHotel.remove(reservaHotel.mostrarIDHotel(), reservaHotel);
                reservasHotel.remove(reservaHotel.mostrarIDCuarto(), reservaHotel);
                System.out.println("Reserva de hotel eliminada exitosamente");
                break;
            }
        }
        guardar();
    }

    public boolean mostrarReservasHotel () {
        actualizar();
        Enumeration<ReservaHotel> enu = Collections.enumeration(reservasHotel.values());

        boolean vacio = false;
        int i = 1;
        if (reservasHotel.isEmpty()) {
            System.out.println("No hay reservas de hotel");
            vacio = true;
        }
        else {
            System.out.println("Reservas de hotel: ");
            while (enu.hasMoreElements()) {
                ReservaHotel reservaHotel = enu.nextElement();
                System.out.println(i + ". ID reserva: " + reservaHotel.mostrarIDReserva());
                System.out.println(i + ". ID cliente: " + reservaHotel.mostrarIDCliente());
                System.out.println(i + ". ID hotel: " + reservaHotel.mostrarIDHotel());
                System.out.println(i + ". ID cuarto: " + reservaHotel.mostrarIDCuarto() + "\n");
                i++;
            }
        }
        return vacio;
    }

    //IMPRESI??N DE LOS DATOS DEL HOTEL EN EL TICKET
    public void mostrarReservasHotelTicket (String UUID) {
        actualizar();
        Enumeration<ReservaHotel> enu = Collections.enumeration(reservasHotel.values());

        boolean vacio = false;
        int i = 1;
        if (reservasHotel.isEmpty()) {
            System.out.println("No hay reservas de hotel");
            vacio = true;
        }
        else {
            while (enu.hasMoreElements()) {
                ReservaHotel reservaHotel = enu.nextElement();
                if(UUID.equals(reservaHotel.mostrarIDCliente())){
                //System.out.println(i + ". ID hotel: " + reservaHotel.mostrarIDHotel());
                //System.out.println(i + ". ID cuarto: " + reservaHotel.mostrarIDCuarto() + "\n");
                configReservaHotel.recuperarClienteHotel(UUID);
                i++;
                }
            }
        }      
    }
//EMPIEZO C??DIGO DE PRUEBA

public void recuperarClienteHotel (String UUID) {
    actualizar();
    Enumeration<ReservaHotel> enumH = Collections.enumeration(reservasHotel.values());
    
    String nombreCliente="";
    String apellidoCliente="";
    
    boolean encontrado = false;
    while (enumH.hasMoreElements()) {
        ReservaHotel clienteHotel = enumH.nextElement();
        if (UUID.equals(clienteHotel.mostrarIDCliente())) {
            System.out.println("DATOS DE CLIENTE RESPECTO AL HOTEL:--------");
            nombreCliente = clienteHotel.mostrarIDCuarto();
            apellidoCliente = clienteHotel.mostrarIDHotel();
            System.out.println("NOMBRE DEL ID DEL CUARTO: " + nombreCliente + "\nNOMBRE DEL ID DEL HOTEL :" + apellidoCliente);
            encontrado = true;
            break;  
        }
    }
    if (!encontrado) {
        System.out.println("No se encontr?? datos personales del cliente");
    }   
}

public String recuperarIDHotel (String UUID) {
    actualizar();
    Enumeration<ReservaHotel> enumH = Collections.enumeration(reservasHotel.values());
    String apellidoCliente="";
    while (enumH.hasMoreElements()) {
        ReservaHotel clienteHotel = enumH.nextElement();
        if (UUID.equals(clienteHotel.mostrarIDCliente())) {
            apellidoCliente = clienteHotel.mostrarIDHotel();
            System.out.println(apellidoCliente);  
        }
    }
    return apellidoCliente;
}
//TERMINO DE PRUEBA DE C??DIGO   
    public boolean mostrarReservasHotelCliente (String UUID) {
        actualizar();
        Enumeration<ReservaHotel> enu = Collections.enumeration(reservasHotel.values());

        boolean vacio = false;
        int i = 1;
        if (reservasHotel.isEmpty()) {
            System.out.println("No hay reservas de hoteles");
            vacio = true;
        }
        else {
            System.out.println("Reservas de hotel: ");
            while (enu.hasMoreElements()) {
                ReservaHotel reservaHotel = enu.nextElement();
                if (UUID.equals(reservaHotel.mostrarIDCliente())) {
                    System.out.println(i + ". ID reserva: " + reservaHotel.mostrarIDReserva());
                    System.out.println(i + ". ID hotel: " + reservaHotel.mostrarIDHotel());
                    System.out.println(i + ". ID cuarto: " + reservaHotel.mostrarIDCuarto() + "\n");
                }
                i++;
            }
        }
        return vacio;
    }

    public void eliminarReservaHotel () {
        Scanner scan = new Scanner(System.in);
        actualizar();

        if (!mostrarReservasHotel()) {
            System.out.println("Ingrese el ID de la reserva: ");
            String ID = scan.nextLine();
            eliminar(ID);
            guardar();
        }
    }

    public void eliminarReservaHotelCliente (String UUID) {
        Scanner scan = new Scanner(System.in);
        actualizar();
        if (!mostrarReservasHotelCliente(UUID)) {
            System.out.println("Ingrese el ID de la reserva: ");
            String ID = scan.nextLine();
            eliminar(ID);
            guardar();
        }
    }
}