package processes;

import Persistence.JSONConfigFileHoteles;
import entities.Hotel.Cuarto;
import entities.Hotel.Hotel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

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

    public void autoGenerarCuartos (String nombre, String ciudad, int estrellas, int numCuartos, int pisos) {
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

    public void agregarCuarto () {
        Scanner scan = new Scanner(System.in);
        ConfigHoteles configHoteles = new ConfigHoteles();
        ConfigCuartos configCuartos = new ConfigCuartos();
        JSONConfigFileHoteles persistenceHoteles = new JSONConfigFileHoteles();
        persistenceHoteles.leerConfig(configHoteles, configCuartos);
        if (configHoteles.noHayHoteles()) {
            System.out.println("No hay hoteles registrados.");
        }
        else {
            configHoteles.mostrarHoteles();
            System.out.println("Escoge el nombre del hotel: ");
            String nombreH = scan.nextLine();
            Hotel hotel = configHoteles.recuperarHotel(nombreH);
            String ciudad = hotel.mostrarCiudad();
            int estrellas = hotel.mostrarEstrellas();
            System.out.println("Ingrese el numero del cuarto: ");
            int numero = Integer.parseInt(scan.next());
            System.out.println("Ingrese el piso del cuarto: ");
            int piso = Integer.parseInt(scan.next());
            System.out.println("Ingrese si el cuarto esta ocupado:");
            boolean ocupado = Boolean.parseBoolean(scan.next());
            int idNum = piso*100+numero;
            String id = String.valueOf(idNum);
            Cuarto cuarto = new Cuarto(nombreH, ciudad, estrellas, numero, piso, ocupado, "", id);
            configCuartos.registrar(cuarto);
            persistenceHoteles.guardarConfig(configHoteles, configCuartos);
        }
    }

    public void generarCuartosEnMasse () {
        Scanner scan = new Scanner(System.in);
        ConfigHoteles configHoteles = new ConfigHoteles();
        ConfigCuartos configCuartos = new ConfigCuartos();
        JSONConfigFileHoteles persistenceHoteles = new JSONConfigFileHoteles();
        persistenceHoteles.leerConfig(configHoteles, configCuartos);
        if (configHoteles.noHayHoteles()) {
            System.out.println("No hay hoteles registrados.");
        }
        else {
            String ciudad;
            int estrellas;
            String nombreHotel;
            String retry;
            do {
                retry = "N";
                System.out.println("Lista de hoteles registrados:");
                configHoteles.mostrarHoteles();
                System.out.println("Elige el nombre del hotel: ");
                nombreHotel = scan.nextLine();
                Hotel hotel = configHoteles.recuperarHotel(nombreHotel);
                ciudad = hotel.mostrarCiudad();
                estrellas = hotel.mostrarEstrellas();

                if (estrellas==-1) { // error para el caso en que lo ingresado no se encuentra
                    System.out.println("Desea intentar de nuevo? [S/N]: ");
                    retry = scan.next();
                    if (retry.equalsIgnoreCase("n"))
                        break;
                }
                else {
                    System.out.println("Numero de pisos: ");
                    int numP = scan.nextInt();
                    System.out.println("Numero de cuartos por piso: ");
                    int numC = scan.nextInt();
                    configCuartos.autoGenerarCuartos(nombreHotel, ciudad, estrellas, numC, numP);
                    persistenceHoteles.guardarConfig(configHoteles, configCuartos);
                }
            } while (retry.equalsIgnoreCase("s"));
        }
    }
}
