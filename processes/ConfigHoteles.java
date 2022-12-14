package processes;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Persistence.JSONConfigFileHoteles;
import entities.Hotel.Hotel;

public class ConfigHoteles implements Config<Hotel> {
    private HashMap<String, Hotel> hoteles;

    public ConfigHoteles(){
        this.hoteles = new HashMap<String, Hotel>();
    }

    public void registrar(Hotel args) {
        if(this.hoteles.containsKey((Object)args.mostrarNombre())){
            System.out.println("El hotel ya esta registrado.");
            return;
        }
        this.hoteles.put(args.mostrarNombre(), args);
    }

    public JSONArray ToJSON() {
        JSONArray arrayHoteles = new JSONArray();
        Enumeration<Hotel> enumH = Collections.enumeration(this.hoteles.values());
        while (enumH.hasMoreElements()) {
            Hotel h = enumH.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("nombre", h.mostrarNombre());
            obj.put("ciudad", h.mostrarCiudad());
            obj.put("estrellas", h.mostrarEstrellas());
            arrayHoteles.add(obj);
        }
        return arrayHoteles;
    }

    public boolean noHayHoteles () {
        return hoteles.isEmpty();
    }

    public void mostrarHoteles () {
        int i = 1;
        Enumeration<Hotel> enu = Collections.enumeration(this.hoteles.values());
        ConfigHoteles configHoteles = new ConfigHoteles();
        ConfigCuartos configCuartos = new ConfigCuartos();
        JSONConfigFileHoteles p = new JSONConfigFileHoteles();
        p.leerConfig(configHoteles, configCuartos);
        while (enu.hasMoreElements()) {
            Hotel hotel = enu.nextElement();
            System.out.println(i + ". Nombre: " + hotel.mostrarNombre());
            System.out.println(i + ". Ciudad: " + hotel.mostrarCiudad());
            System.out.println(i + ". Estrellas: " + hotel.mostrarEstrellas());
            System.out.println(i + ". Numero de cuartos: " + configCuartos.contarCuartosHotel(hotel.mostrarNombre()) + "\n");
            i++;
        }
    }

    public Hotel recuperarHotel(String nombre) {
        Enumeration<Hotel> enumH = Collections.enumeration(this.hoteles.values());
        String ciudad = "";
        int estrellas = -1; // error en caso de no encontrar el hotel
        while (enumH.hasMoreElements()) {
            Hotel hotel = enumH.nextElement();
            if (nombre.equals(hotel.mostrarNombre())) {
                ciudad = hotel.mostrarCiudad();
                estrellas = Integer.parseInt(String.valueOf(hotel.mostrarEstrellas()));
            }
            else {
                System.out.println("No se encontró el hotel.");
            }
        }

        return new Hotel(nombre, ciudad, estrellas);
    }
}