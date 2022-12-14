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
    private static HashMap<String, Hotel> hoteles;
    private static final ConfigHoteles configHoteles = new ConfigHoteles();
    private static final ConfigCuartos configCuartos = new ConfigCuartos();

    public ConfigHoteles(){
        hoteles = new HashMap<String, Hotel>();
    }

    public void registrar (Hotel args) {
        hoteles.put(args.mostrarNombre(), args);
    }

    public JSONArray ToJSON () {
        JSONArray arrayHoteles = new JSONArray();
        Enumeration<Hotel> enumH = Collections.enumeration(hoteles.values());
        while (enumH.hasMoreElements()) {
            Hotel h = enumH.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("IDHotel", h.mostrarIDHotel());
            obj.put("nombre", h.mostrarNombre());
            obj.put("ciudad", h.mostrarCiudad());
            obj.put("estrellas", h.mostrarEstrellas());
            obj.put("precio", h.mostrarPrecio());
            arrayHoteles.add(obj);
        }
        return arrayHoteles;
    }

    private void actualizar () {
        JSONConfigFileHoteles p = new JSONConfigFileHoteles();
        p.leerConfig(configHoteles, configCuartos);
    }

    private void guardar () {
        JSONConfigFileHoteles p = new JSONConfigFileHoteles();
        p.guardarConfig(configHoteles, configCuartos);
    }
    
    private void agregar (Hotel hotel) {
        if(hoteles.containsKey(hotel.mostrarNombre())){
            System.out.println("El hotel ya esta registrado.");
            return;
        }
        hoteles.put(hotel.mostrarNombre(), hotel);
    }

    private void eliminar (String nombreHotel) {
        actualizar();
        Enumeration<Hotel> enumH = Collections.enumeration(hoteles.values());

        while (enumH.hasMoreElements()) {
            Hotel hotel = enumH.nextElement();
            if (nombreHotel.equals(hotel.mostrarNombre())) {
                hoteles.remove(hotel.mostrarIDHotel(), hotel);
                hoteles.remove(hotel.mostrarNombre(), hotel);
                hoteles.remove(hotel.mostrarCiudad(), hotel);
                hoteles.remove(hotel.mostrarEstrellas(), hotel);
                hoteles.remove(enumH, hotel);
                System.out.println("Hotel eliminado exitosamente");
                break;
            }
        }
    }

    private void modificar(String nombreHotel) {
        actualizar();
        Enumeration<Hotel> enumH = Collections.enumeration(hoteles.values());
        while (enumH.hasMoreElements()) {
            Hotel hotel = enumH.nextElement();
            if (nombreHotel.equals(hotel.mostrarNombre())) {
                Scanner scan = new Scanner(System.in);
                System.out.println("Ingrese el nuevo precio: ");
                float precioNuevo = Float.parseFloat(scan.next());
                Hotel hotelRee = new Hotel(hotel.mostrarIDHotel(), hotel.mostrarNombre(), hotel.mostrarCiudad(), hotel.mostrarEstrellas(), precioNuevo);
                hoteles.remove(hotel.mostrarIDHotel(), hotel);
                hoteles.remove(hotel.mostrarNombre(), hotel);
                hoteles.remove(hotel.mostrarCiudad(), hotel);
                hoteles.remove(hotel.mostrarEstrellas(), hotel);
                hoteles.remove(enumH, hotel);
                agregar(hotelRee);
                guardar();
                break;
            }
        }
    }
    
    public boolean noHayHoteles () {
        return hoteles.isEmpty();
    }

    public boolean mostrarHoteles () {
        actualizar();
        Enumeration<Hotel> enu = Collections.enumeration(hoteles.values());

        boolean vacio = false;
        int i = 1;
        if (hoteles.isEmpty()) {
            System.out.println("No hay hoteles registrados");
            vacio = true;
        }
        else {
            while (enu.hasMoreElements()) {
                Hotel hotel = enu.nextElement();
                System.out.println(i + ". ID: " + hotel.mostrarIDHotel());
                System.out.println(i + ". Nombre: " + hotel.mostrarNombre());
                System.out.println(i + ". Ciudad: " + hotel.mostrarCiudad());
                System.out.println(i + ". Estrellas: " + hotel.mostrarEstrellas());
                System.out.println(i + ". Precio por cuarto: " + hotel.mostrarPrecio());
                System.out.println(i + ". Numero de cuartos: " + configCuartos.contarCuartosHotel(hotel.mostrarNombre()) + "\n");
                i++;
            }
        }
        return vacio;
    }

    public float mostrarPrecioHoteles (String nombreHotel) {
        actualizar();
        Enumeration<Hotel> enu = Collections.enumeration(hoteles.values());
        Hotel hotel = enu.nextElement();
        if (nombreHotel.equals(hotel.mostrarIDHotel())) {
            return hotel.mostrarPrecio();
        }
        return hotel.mostrarPrecio();
    }

    public Hotel recuperarHotel (String nombre) {
        actualizar();
        Enumeration<Hotel> enumH = Collections.enumeration(hoteles.values());
        String IDHotel= "";
        String ciudad = "";
        int estrellas = -1; // error en caso de no encontrar el hotel
        float precio = -1; // error en caso de no encontrar el hotel
        boolean encontrado = false;
        while (enumH.hasMoreElements()) {
            Hotel hotel = enumH.nextElement();
            if (nombre.equals(hotel.mostrarNombre())) {
                IDHotel = hotel.mostrarIDHotel();
                ciudad = hotel.mostrarCiudad();
                estrellas = Integer.parseInt(String.valueOf(hotel.mostrarEstrellas()));
                precio = Float.parseFloat(String.valueOf(hotel.mostrarPrecio()));
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontr?? el hotel");
        }
        return new Hotel(IDHotel, nombre, ciudad, estrellas, precio);
    }

    public void agregarHotel () {
        Scanner scan = new Scanner(System.in);
        actualizar();

        System.out.println("Ingrese el nombre del hotel: ");
        String nombre = scan.nextLine();
        System.out.println("Ingrese la ciudad: ");
        String ciudad = scan.nextLine();
        String IDHotel= (nombre+ciudad).replaceAll("\\s+","");
        System.out.println("Ingrese las estrellas:");
        int estrellas = Integer.parseInt(scan.next());
        System.out.println("Ingrese el precio por cuarto:");
        float precio = Float.parseFloat(scan.next());
        Hotel hotel = new Hotel(IDHotel, nombre, ciudad, estrellas, precio);
        agregar(hotel);
        guardar();
    }

    public void reservarHotel (String UUID) {
        Scanner scan = new Scanner(System.in);
        actualizar();
        
        if (!configHoteles.mostrarHoteles()) {
            System.out.println("Ingrese el nombre del hotel: ");
            String nHotel = scan.nextLine();
            Cuarto cu = new Cuarto("", nHotel, "", 0, 0, 0,0, false,"");
            configCuartos.reservar(cu, UUID);
            guardar();
        }
    }

    public void eliminarHotel () {
        Scanner scan = new Scanner(System.in);
        actualizar();

        if (!mostrarHoteles()) {
            System.out.println("Ingrese el nombre del hotel: ");
            String nombreHotel = scan.nextLine();
            eliminar(nombreHotel);
            guardar();
        }
    }

    public void modificarPrecioHotel() {
        Scanner scan = new Scanner(System.in);
        actualizar();
        if (!mostrarHoteles()) {
            System.out.println("Ingrese el nombre del hotel: ");
            String nombreHotel = scan.nextLine();
            modificar(nombreHotel);
            guardar();
        }
        
    }

    public void mostrarPrecioHotel(String nombreHotel) {
        actualizar();
        if (!mostrarHoteles()) {
            mostrarPrecioHoteles(nombreHotel);
        }
        
    }
}