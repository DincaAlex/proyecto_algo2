package processes;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Persistence.JSONConfigFileReservas;
import entities.Reservas.ReservaTransporte;

public class ConfigReservaTransporte implements Config<ReservaTransporte>{
    private static HashMap<String, ReservaTransporte> reservasTransporte;
    private static final ConfigReservaHotel configReservaHotel= new ConfigReservaHotel();
    private static final ConfigReservaTransporte configReservaTransporte= new ConfigReservaTransporte();

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
    
    public void reservarTransporte(String UUID, String IDCliente, String IDRuta, String IDTransporte){
        actualizar();
        ReservaTransporte reservaT= new ReservaTransporte(UUID, IDRuta, IDTransporte);
        reservasTransporte.put(reservaT.mostrarIDReserva(), reservaT);
        guardar();
    }

    public void reservaTransporte() {
    }

}
