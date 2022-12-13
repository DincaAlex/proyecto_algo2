package processes;

import entities.Cuarto;
import entities.Hotel;
import entities.Ruta;
import entities.Transporte;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

public class ConfigViajes {
    private HashMap<String, Ruta> rutas;
    private HashMap<String, Transporte> transportes;

    public ConfigViajes(){
        this.rutas = new HashMap<String, Ruta>();
        this.transportes = new HashMap<String, Transporte>();
    }
    public void registrarRuta(Ruta ruta) {
        if(this.rutas.containsKey((Object)ruta.mostrarID())) {
            System.out.println("La ruta ya esta registrada.");
            return;
        }
        this.rutas.put(ruta.mostrarID(), ruta);
    }

    public void registrarTransporte(Transporte transporte) {
        if(this.transportes.containsKey((Object)transporte.mostrarTipo())) {
            System.out.println("El transporte ya existe.");
            return;
        }
        this.transportes.put(transporte.mostrarTipo(), transporte);
    }

    public JSONArray rutasToJSON () {
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

    public JSONArray transporteToJSON () {
        JSONArray arrayTransportes = new JSONArray();
        Enumeration<Transporte> enumT = Collections.enumeration(this.transportes.values());
        while (enumT.hasMoreElements()) {
            Transporte trans= enumT.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("tipo", trans.mostrarTipo());
            obj.put("empresa", trans.mostrarEmpresa());
            obj.put("calidad", trans.mostrarCalidad());
            obj.put("hora-partida", trans.mostrarHoraPartida());
            obj.put("hora-llegada", trans.mostrarHoraDestino());
            arrayTransportes.add(obj);
        }
        return arrayTransportes;
    }
}
