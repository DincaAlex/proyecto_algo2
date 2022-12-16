package processes;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Persistence.JSONConfigFileReservas;
import entities.Reservas.ReservaHotel;

public class ConfigReservaHotel implements Config<ReservaHotel> {
    private static HashMap<String, ReservaHotel> reservasHotel;
    private static final ConfigReservaHotel configReservaHotel= new ConfigReservaHotel();
    private static final ConfigReservaTransporte configReservaTransporte= new ConfigReservaTransporte();

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

    public boolean mostrarReservasHotel () {
        actualizar();
        Enumeration<ReservaHotel> enu = Collections.enumeration(reservasHotel.values());

        boolean vacio = false;
        int i = 1;
        if (reservasHotel.isEmpty()) {
            System.out.println("No hay hoteles registrados");
            vacio = true;
        }
        else {
            while (enu.hasMoreElements()) {
                ReservaHotel reservaHotel = enu.nextElement();
                System.out.println(i + ". ID: " + reservaHotel.mostrarIDReserva());
                System.out.println(i + ". Cliente: " + reservaHotel.mostrarIDCliente());
                System.out.println(i + ". Hotel: " + reservaHotel.mostrarIDHotel());
                System.out.println(i + ". Cuarto: " + reservaHotel.mostrarIDCuarto() + "\n");
                i++;
            }
        }
        return vacio;
    }

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
            while (enu.hasMoreElements()) {
                ReservaHotel reservaHotel = enu.nextElement();
                if (UUID.equals(reservaHotel.mostrarIDCliente())) {
                    System.out.println(i + ". ID: " + reservaHotel.mostrarIDReserva());
                    System.out.println(i + ". Cliente: " + reservaHotel.mostrarIDCliente());
                    System.out.println(i + ". Hotel: " + reservaHotel.mostrarIDHotel());
                    System.out.println(i + ". Cuarto: " + reservaHotel.mostrarIDCuarto() + "\n");
                }
                i++;
            }
        }
        return vacio;
    }
}
