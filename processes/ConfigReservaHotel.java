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
        reservasHotel.put(reservaH.mostrarIDCliente(), reservaH);
        guardar();
    }
}
