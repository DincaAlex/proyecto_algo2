package processes;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

import entities.Hotel.Cuarto;
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

    public boolean mostrarHoteles () {
        ConfigHoteles configHoteles = new ConfigHoteles();
        ConfigCuartos configCuartos = new ConfigCuartos();
        JSONConfigFileHoteles p = new JSONConfigFileHoteles();
        p.leerConfig(configHoteles, configCuartos);
        Enumeration<Hotel> enu = Collections.enumeration(this.hoteles.values());

        boolean vacio = false;
        int i = 1;
        if (this.hoteles.isEmpty()) {
            System.out.println("No hay hoteles registrados");
            vacio = true;
        }
        else {
            while (enu.hasMoreElements()) {
                Hotel hotel = enu.nextElement();
                System.out.println(i + ". Nombre: " + hotel.mostrarNombre());
                System.out.println(i + ". Ciudad: " + hotel.mostrarCiudad());
                System.out.println(i + ". Estrellas: " + hotel.mostrarEstrellas());
                System.out.println(i + ". Numero de cuartos: " + configCuartos.contarCuartosHotel(hotel.mostrarNombre()) + "\n");
                i++;
            }
        }
        return vacio;
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

    public void agregarHotel () {
        Scanner scan = new Scanner(System.in);
        ConfigHoteles configHoteles = new ConfigHoteles();
        ConfigCuartos configCuartos = new ConfigCuartos();
        JSONConfigFileHoteles persistenceHoteles = new JSONConfigFileHoteles();
        persistenceHoteles.leerConfig(configHoteles, configCuartos);
        System.out.println("Ingrese el nombre del hotel: ");
        String nombre = scan.nextLine();
        System.out.println("Ingrese la ciudad: ");
        String ciudad = scan.nextLine();
        System.out.println("Ingrese las estrellas:");
        int estrellas = Integer.parseInt(scan.next());
        Hotel hotel = new Hotel(nombre, ciudad, estrellas);
        configHoteles.registrar(hotel);
        persistenceHoteles.guardarConfig(configHoteles, configCuartos);
    }

    public void reservarHotel (String UUID) {
        Scanner scan = new Scanner(System.in);
        ConfigHoteles configHoteles = new ConfigHoteles();
        ConfigCuartos configCuartos = new ConfigCuartos();
        JSONConfigFileHoteles persistenceHoteles = new JSONConfigFileHoteles();
        persistenceHoteles.leerConfig(configHoteles, configCuartos);
        configHoteles.mostrarHoteles();
        System.out.println("Ingrese el nombre del hotel: ");
        String nHotel = scan.nextLine();
        Cuarto cu = new Cuarto(nHotel, "", 0, 0, 0, false, "", "");
        configCuartos.reservar(cu, UUID);
        persistenceHoteles.guardarConfig(configHoteles, configCuartos);
    }

    public void eliminar (String nombreHotel) {
        Enumeration<Hotel> enumH = Collections.enumeration(this.hoteles.values());
        while (enumH.hasMoreElements()) {
            Hotel hotel = enumH.nextElement();
            if (nombreHotel.equals(hotel.mostrarNombre())) {
                this.hoteles.remove(hotel.mostrarNombre(), hotel);
                this.hoteles.remove(hotel.mostrarCiudad(), hotel);
                this.hoteles.remove(hotel.mostrarEstrellas(), hotel);
                System.out.println("Hotel eliminado exitosamente.");
                break;
            }
        }
    }

    public void eliminarHotel () {
        Scanner scan = new Scanner(System.in);
        ConfigHoteles configHoteles = new ConfigHoteles();
        ConfigCuartos configCuartos = new ConfigCuartos();
        JSONConfigFileHoteles persistenceHoteles = new JSONConfigFileHoteles();
        persistenceHoteles.leerConfig(configHoteles, configCuartos);
        if (!configHoteles.mostrarHoteles()) {
            System.out.println("Ingrese el nombre del hotel: ");
            String nombreHotel = scan.nextLine();
            configHoteles.eliminar(nombreHotel);
            persistenceHoteles.guardarConfig(configHoteles, configCuartos);
        }
    }
}