package processes;

import entities.Viajes.Ruta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

public class ConfigRutas implements Config<Ruta> {
    private HashMap<String, Ruta> rutas;

    public ConfigRutas() {
        this.rutas = new HashMap<String, Ruta>();
    }

    public void registrar(Ruta args) {
        if(this.rutas.containsKey((Object)args.mostrarID())) {
            System.out.println("La ruta ya esta registrada.");
            return;
        }
        this.rutas.put(args.mostrarID(), args);
    }

    public JSONArray ToJSON() {
        JSONArray arrayRutas = new JSONArray();
        Enumeration<Ruta> enumR = Collections.enumeration(this.rutas.values());
        while (enumR.hasMoreElements()) {
            Ruta ruta = enumR.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("ID", ruta.mostrarID());
            obj.put("ciudad-partida", ruta.mostrarCiudadPartida());
            obj.put("ciudad-destino", ruta.mostrarCiudadDestino());
            obj.put("transporte", ruta.mostrarTransporte());
            arrayRutas.add(obj);
        }
        return arrayRutas;
    }
}
