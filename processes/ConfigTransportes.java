package processes;

import entities.Viajes.Transporte;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

public class ConfigTransportes implements Config<Transporte> {
    private HashMap<String, Transporte> transportes;

    public ConfigTransportes(){
        this.transportes = new HashMap<String, Transporte>();
    }

    public void registrar(Transporte args) {
        if(this.transportes.containsKey((Object)args.mostrarTipo())) {
            System.out.println("El transporte ya existe.");
            return;
        }
        this.transportes.put(args.mostrarTipo(), args);
    }

    public JSONArray ToJSON() {
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
