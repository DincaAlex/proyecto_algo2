package processes;

import Persistence.JSONConfigFileHoteles;
import entities.Hotel.Cuarto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

public class ConfigCuartos implements Config<Cuarto> {
    private final HashMap<String, Cuarto> cuartos;

    public ConfigCuartos(){
        this.cuartos = new HashMap<String, Cuarto>();
    }

    public void registrar(Cuarto args) {
        if(this.cuartos.containsKey((Object)args.getID())){
            System.out.println("El cuarto ya existe.");
            return;
        }
        this.cuartos.put(args.getID(), args);
    }

    public JSONArray ToJSON() {
        JSONArray arrayCuartos = new JSONArray();
        Enumeration<Cuarto> enumC = Collections.enumeration(this.cuartos.values());
        while (enumC.hasMoreElements()) {
            Cuarto c = enumC.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("ID", c.getID());
            obj.put("nombre", c.mostrarNombre());
            obj.put("ciudad", c.mostrarCiudad());
            obj.put("estrellas", c.mostrarEstrellas());
            obj.put("numero", c.mostrarNumero());
            obj.put("piso", c.mostrarPiso());
            obj.put("ocupado", c.mostrarOcupado());
            obj.put("cReserva", c.mostrarClienteReserva());
            arrayCuartos.add(obj);
        }
        return arrayCuartos;
    }
    public void reservar (Cuarto cuarto, String uuid) {
        Enumeration<Cuarto> enumC= Collections.enumeration(this.cuartos.values());
        while(enumC.hasMoreElements()){
            Cuarto c= enumC.nextElement();
            while(cuarto.mostrarNombre().equals(c.mostrarNombre())){
                if(!c.mostrarOcupado()){
                    System.out.println("ID de la habitación: "+c.getID());
                    c.ocupar();
                    c.clienteReservar(uuid);
                    cuarto=c;
                    this.cuartos.remove(c.getID(), c);
                    this.cuartos.put(cuarto.getID(), cuarto);
                    System.out.println("Reserva realizada");
                    return;
                }
            }
        }
    }

    public void autoGenerarCuartos(String nombre, String ciudad, int estrellas, int numCuartos, int pisos) {
        boolean ocupado = false;
        ConfigCuartos configCuartos = new ConfigCuartos();
        ConfigHoteles configHoteles = new ConfigHoteles();
        JSONConfigFileHoteles p = new JSONConfigFileHoteles();
        p.leerConfig(configHoteles, configCuartos);
        for (int i=1; i<=pisos; i++) {
            for (int j=1; j<= numCuartos; j++){
                int idNum = i*100+j; // genera el ID
                String id = String.valueOf(idNum);
                Cuarto cuarto = new Cuarto(nombre, ciudad, estrellas, j, i, ocupado, "", id);
                this.cuartos.put(cuarto.getID(), cuarto);
                configCuartos.registrar(cuarto);
            }
        }
        p.guardarConfig(configHoteles, configCuartos);
    }

    public int contarCuartosHotel(String nombre) {
        int cant = 0;
        Enumeration<Cuarto> enumC = Collections.enumeration(this.cuartos.values());
        while (enumC.hasMoreElements()) {
            Cuarto cuarto = enumC.nextElement();
            if (nombre.equals(cuarto.mostrarNombre()))
                cant++;
        }
        return cant;
    }
}
