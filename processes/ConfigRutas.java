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
    private static HashMap<String, Ruta> rutas;
    private static final ConfigRutas configRutas = new ConfigRutas();
    private static final ConfigTransportes configTransportes = new ConfigTransportes();

    public ConfigRutas() {
        rutas = new HashMap<String, Ruta>();
    }

    public void registrar (Ruta args) {
        rutas.put(args.mostrarID(), args);
    }

    public JSONArray ToJSON () {
        JSONArray arrayRutas = new JSONArray();
        Enumeration<Ruta> enumR = Collections.enumeration(rutas.values());
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

    private void actualizar () {
        JSONConfigFileViajes p = new JSONConfigFileViajes();
        p.leerConfig(configRutas, configTransportes);
    }

    private void guardar () {
        JSONConfigFileViajes p = new JSONConfigFileViajes();
        p.guardarConfig(configRutas, configTransportes);
    }

    private void agregar (Ruta ruta) {
        if(rutas.containsKey(ruta.mostrarID())) {
            System.out.println("La ruta ya esta registrada.");
            return;
        }
        rutas.put(ruta.mostrarID(), ruta);
    }

    private void eliminar (String ID) {
        actualizar();
        Enumeration<Ruta> enu = Collections.enumeration(rutas.values());

        while (enu.hasMoreElements()) {
            Ruta ruta = enu.nextElement();
            if (ID.equals(ruta.mostrarID())) {
                rutas.remove(ruta.mostrarID(), ruta);
                rutas.remove(ruta.mostrarCiudadPartida(), ruta);
                rutas.remove(ruta.mostrarCiudadDestino(), ruta);
                rutas.remove(ruta.mostrarTransporte(), ruta);
                System.out.println("Hotel eliminado exitosamente");
                break;
            }
        }
    }

    public void agregarRuta () {
        actualizar();
        Scanner scan = new Scanner(System.in);

        System.out.println("Ingrese la ciudad de partida: ");
        String ciudadPartida = scan.next();
        System.out.println("Ingrese la ciudad de destino:");
        String ciudadDestino = scan.next();
        String ID = ciudadPartida+ciudadDestino;
        System.out.println("Ingrese el tipo de transporte:");
        String transporte = scan.next();
        Ruta ruta = new Ruta(ID, ciudadPartida, ciudadDestino, transporte);
        agregar(ruta);
        guardar();
    }

    public boolean mostrarRutas () {
        actualizar();
        Enumeration<Ruta> enu = Collections.enumeration(rutas.values());

        boolean vacio = false;
        int i = 1;
        if (rutas.isEmpty()) {
            System.out.println("No hay hoteles registrados");
            vacio = true;
        }
        else {
            while (enu.hasMoreElements()) {
                Ruta ruta = enu.nextElement();
                System.out.println(i + ". ID: " + ruta.mostrarID());
                System.out.println(i + ". Ciudad de partida: " + ruta.mostrarCiudadPartida());
                System.out.println(i + ". Ciudad de llegada: " + ruta.mostrarCiudadDestino());
                System.out.println(i + ". Transporte: " + ruta.mostrarTransporte());
                i++;
            }
        }
        return vacio;
    }

    public void eliminarRuta () {
        Scanner scan = new Scanner(System.in);
        actualizar();

        if (!mostrarRutas()) {
            System.out.println("Ingrese el ID de la ruta: ");
            String ID = scan.nextLine();
            eliminar(ID);
            guardar();
        }
    }
}
