package processes;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Persistance.PersistanceHoteles;
import Persistance.JSONConfigFileHoteles;
import entities.Cuarto;
import entities.Hotel;

public class ConfigHoteles {
    private HashMap<String, Hotel> hoteles;
    private HashMap<String, Cuarto> cuartos;
    
    public ConfigHoteles(){
        this.hoteles = new HashMap<String, Hotel>();
        this.cuartos = new HashMap<String, Cuarto>();
    }
    
    public void registrarHotel (Hotel hotel) {
        if(this.hoteles.containsKey((Object)hotel.mostrarNombre())){
            System.out.println("El hotel ya esta registrado.");
            return;
        }
        this.hoteles.put(hotel.mostrarNombre(), hotel);
    }

    public void registrarCuarto(Cuarto cuarto) {
        if(this.cuartos.containsKey((Object)cuarto.getID())){
            System.out.println("El cuarto ya existe.");
            return;
        }
        this.cuartos.put(cuarto.getID(), cuarto);
    }

    public void reservarCuarto(Cuarto cuarto, String uuid){
        Cuarto c = this.cuartos.get(cuarto.getID());
        if (c==null) {
            System.out.println("El cuarto no existe");
            return;
        }
        c.ocupar();
        c.clienteReservar(uuid);
        System.out.println("Reserva realizada");
    }

    public JSONArray hotelesToJSON () {
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

    public JSONArray cuartosToJSON () {
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

    public void autoGenerarCuartos (String nombre, String ciudad, int estrellas, int numCuartos, int pisos) {
        boolean ocupado = false;
        ConfigHoteles config = new ConfigHoteles();
        PersistanceHoteles p = new JSONConfigFileHoteles();
        p.leerConfig(config);
        for (int i=1; i<=pisos; i++) {
            for (int j=1; j<= numCuartos; j++){
                int idNum = i*100+j; // genera el ID
                String id = String.valueOf(idNum);
                Cuarto cuarto = new Cuarto(nombre, ciudad, estrellas, j, i, ocupado, "", id);
                this.cuartos.put(cuarto.getID(), cuarto);
                config.registrarCuarto(cuarto);
            }
        }
        p.guardarConfig(config);
    }

    public boolean noHayHoteles () {
        return hoteles.isEmpty();
    }

    public void mostrarHoteles () {
        int i = 1;
        Enumeration<Hotel> enu = Collections.enumeration(this.hoteles.values());
        ConfigHoteles config = new ConfigHoteles();
        PersistanceHoteles p = new JSONConfigFileHoteles();
        p.leerConfig(config);
        while (enu.hasMoreElements()) {
            Hotel hotel = enu.nextElement();
            System.out.println(i + ". Nombre: " + hotel.mostrarNombre());
            System.out.println(i + ". Ciudad: " + hotel.mostrarCiudad());
            System.out.println(i + ". Estrellas: " + hotel.mostrarEstrellas());
            System.out.println(i + ". Numero de cuartos: " + config.contarCuartosHotel(hotel.mostrarNombre()) + "\n");
            i++;
        }
    }

    public Hotel buscarHotel (String nombre) {
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

    public int contarCuartosHotel (String nombre) {
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

