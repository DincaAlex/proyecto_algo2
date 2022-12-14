package processes;

import Persistence.JSONConfigFileViajes;
import entities.Viajes.Ruta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigRutas implements Config<Ruta> {
    private HashMap<String, Ruta> rutas;

    public ConfigRutas() {
        this.rutas = new HashMap<String, Ruta>();
    }

    public void registrar (Ruta args) {
        if(this.rutas.containsKey((Object)args.mostrarID())) {
            System.out.println("La ruta ya esta registrada.");
            return;
        }
        this.rutas.put(args.mostrarID(), args);
    }

    public JSONArray ToJSON () {
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

    public void agregarRuta () {
        Scanner scan = new Scanner(System.in);
        ConfigRutas configRutas = new ConfigRutas();
        ConfigTransportes configTransportes = new ConfigTransportes();
        JSONConfigFileViajes persistenceViajes = new JSONConfigFileViajes();
        persistenceViajes.leerConfig(configRutas, configTransportes);

        System.out.println("Ingrese el ID de la ruta: ");
        String ID = scan.next();
        System.out.println("Ingrese la ciudad de partida: ");
        String ciudadPartida = scan.next();
        System.out.println("Ingrese la ciudad de destino:");
        String ciudadDestino = scan.next();
        System.out.println("Ingrese el tipo de transporte:");
        String transporte = scan.next();
        Ruta ruta = new Ruta(ID, ciudadPartida, ciudadDestino, transporte);
        configRutas.registrar(ruta);
        persistenceViajes.guardarConfig(configRutas, configTransportes);
    }
}
