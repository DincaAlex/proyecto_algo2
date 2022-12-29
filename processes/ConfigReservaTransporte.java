package processes;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Persistence.JSONConfigFileReservas;
import entities.Reservas.ReservaTransporte;

public class ConfigReservaTransporte implements Config<ReservaTransporte>{
    private static HashMap<String, ReservaTransporte> reservasTransporte;
    private static final ConfigReservaHotel configReservaHotel= new ConfigReservaHotel();
    private static final ConfigReservaTransporte configReservaTransporte= new ConfigReservaTransporte();
    private static final ConfigClientes configclientes = new ConfigClientes();


    

    public ConfigReservaTransporte () {
        reservasTransporte = new HashMap<String, ReservaTransporte>();
    }

    public void registrar(ReservaTransporte args){
        reservasTransporte.put(args.mostrarIDCliente(), args);
    }

    public JSONArray ToJSON () {
        JSONArray arrayReservaTransporte = new JSONArray();
        Enumeration<ReservaTransporte> rt = Collections.enumeration(reservasTransporte.values());
        while(rt.hasMoreElements()) {
            ReservaTransporte rTrans = rt.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("IDReserva", rTrans.mostrarIDReserva());
            obj.put("IDCliente", rTrans.mostrarIDCliente());
            obj.put("IDRuta", rTrans.mostrarIDRuta());
            obj.put("IDTransporte", rTrans.mostrarIDTransporte());
            arrayReservaTransporte.add(obj);
        }
        return arrayReservaTransporte;
    }
    private void actualizar() {
        JSONConfigFileReservas p = new JSONConfigFileReservas();
        p.leerConfig(configReservaHotel, configReservaTransporte);
    }

    private void guardar() {
        JSONConfigFileReservas p = new JSONConfigFileReservas();
        p.guardarConfig(configReservaHotel, configReservaTransporte);
    }
    
    public void reservarTransporte (String IDCliente, String IDRuta, String IDTransporte) {
        actualizar();
        ReservaTransporte reservaT = new ReservaTransporte(IDCliente, IDRuta, IDTransporte);
        reservasTransporte.put(reservaT.mostrarIDReserva(), reservaT);
        guardar();
    }

    public void reservaTransporte() {
    }

    private void eliminar (String ID) {
        actualizar();
        Enumeration<ReservaTransporte> enumH = Collections.enumeration(reservasTransporte.values());

        while (enumH.hasMoreElements()) {
            ReservaTransporte reservaTransporte = enumH.nextElement();
            if (ID.equals(reservaTransporte.mostrarIDReserva())) {
                reservasTransporte.remove(reservaTransporte.mostrarIDRuta(), reservaTransporte);
                reservasTransporte.remove(reservaTransporte.mostrarIDCliente(), reservaTransporte);
                reservasTransporte.remove(reservaTransporte.mostrarIDTransporte(), reservaTransporte);
                reservasTransporte.remove(reservaTransporte.mostrarIDReserva(), reservaTransporte);
                System.out.println("Reserva de hotel eliminada exitosamente");
                break;
            }
        }
        guardar();
    }

    public boolean mostrarReservasTransporteCliente (String UUID) {
        actualizar();
        Enumeration<ReservaTransporte> enu = Collections.enumeration(reservasTransporte.values());

        boolean vacio = false;
        int i = 1;
        if (reservasTransporte.isEmpty()) {
            System.out.println("No hay reservas de transportes");
            vacio = true;
        }
        else {
            System.out.println("Reservas de transporte: ");
            while (enu.hasMoreElements()) {
                ReservaTransporte reservaTransporte = enu.nextElement();
                if (UUID.equals(reservaTransporte.mostrarIDCliente())) {
                    System.out.println(i + ". ID reserva: " + reservaTransporte.mostrarIDReserva());
                    System.out.println(i + ". ID ruta: " + reservaTransporte.mostrarIDRuta());
                    System.out.println(i + ". ID transporte: " + reservaTransporte.mostrarIDTransporte() + "\n");
                }
                i++;
            }
        }
        return vacio;
    }

    public boolean mostrarReservasTransporte () {
        actualizar();
        Enumeration<ReservaTransporte> enu = Collections.enumeration(reservasTransporte.values());

        boolean vacio = false;
        int i = 1;
        if (reservasTransporte.isEmpty()) {
            System.out.println("No hay reservas de transportes");
            vacio = true;
        }
        else {
            System.out.println("Reservas de transporte: ");
            while (enu.hasMoreElements()) {
                ReservaTransporte reservaTransporte = enu.nextElement();
                System.out.println(i + ". ID reserva: " + reservaTransporte.mostrarIDReserva());
                System.out.println(i + ". ID cliente: " + reservaTransporte.mostrarIDCliente());
                System.out.println(i + ". ID ruta: " + reservaTransporte.mostrarIDRuta());
                System.out.println(i + ". ID transporte: " + reservaTransporte.mostrarIDTransporte() + "\n");
                }
                i++;
            }
        return vacio;
    }

    //IMPRESIÓN DE LOS DATOS DEL TRANSPORTE EN EL TICKET
    public void mostrarReservaTransporteTicket(String UUID){
        actualizar();
        Enumeration<ReservaTransporte> enu = Collections.enumeration(reservasTransporte.values());

        boolean vacio = false;
        int i = 1;
        if (reservasTransporte.isEmpty()) {
            System.out.println("No hay reservas de transportes");
            vacio = true;
        }
        else {
            
            while (enu.hasMoreElements()) {
                ReservaTransporte reservaTransporte = enu.nextElement();
                if(UUID.equals(reservaTransporte.mostrarIDCliente())){
                System.out.println("NOMBRE DEL DEL ID TRANSPORTE: " + reservaTransporte.mostrarIDTransporte());
                System.out.println("NOMBRE DEL DEL ID DE LA RUTA : " + reservaTransporte.mostrarIDRuta());
                configclientes.recuperarCliente(UUID);
                }
                
            }
                i++;
            }
    }


    public void eliminarReservaTransporte () {
        Scanner scan = new Scanner(System.in);
        actualizar();

        if (!mostrarReservasTransporte()) {
            System.out.println("Ingrese el ID de la reserva: ");
            String ID = scan.nextLine();
            eliminar(ID);
            guardar();
        }
    }

    public void eliminarReservaTransporteCliente (String UUID) {
        Scanner scan = new Scanner(System.in);
        actualizar();

        if (!mostrarReservasTransporteCliente(UUID)) {
            System.out.println("Ingrese el ID de la reserva: ");
            String ID = scan.nextLine();
            eliminar(ID);
            guardar();
        }
    }
}
