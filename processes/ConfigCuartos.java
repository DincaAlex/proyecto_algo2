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
    private static HashMap<String, Cuarto> cuartos;
    private static final ConfigHoteles configHoteles = new ConfigHoteles();
    private static final ConfigCuartos configCuartos = new ConfigCuartos();
    private static final ConfigReservaHotel configReservaHotel = new ConfigReservaHotel();

    public ConfigCuartos(){
        cuartos = new HashMap<String, Cuarto>();
    }

    public void registrar (Cuarto args) {
        cuartos.put(args.mostrarID(), args);
    }

    public JSONArray ToJSON () {
        JSONArray arrayCuartos = new JSONArray();
        Enumeration<Cuarto> enumC = Collections.enumeration(cuartos.values());
        while (enumC.hasMoreElements()) {
            Cuarto c = enumC.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("ID", c.mostrarID());
            obj.put("nombre", c.mostrarNombre());
            obj.put("ciudad", c.mostrarCiudad());
            obj.put("estrellas", c.mostrarEstrellas());
            obj.put("numero", c.mostrarNumero());
            obj.put("piso", c.mostrarPiso());
            obj.put("ocupado", c.mostrarOcupado());
            arrayCuartos.add(obj);
        }
        return arrayCuartos;
    }

    private void actualizar () {
        JSONConfigFileHoteles p = new JSONConfigFileHoteles();
        p.leerConfig(configHoteles, configCuartos);
    }

    private void guardar () {
        JSONConfigFileHoteles p = new JSONConfigFileHoteles();
        p.guardarConfig(configHoteles, configCuartos);
    }

    private void agregar (Cuarto cuarto) {
        if(cuartos.containsKey(cuarto.mostrarID())){
            System.out.println("El cuarto" + cuarto.mostrarID() + "ya existe.");
            return;
        }
        cuartos.put(cuarto.mostrarID(), cuarto);
    }

    private void eliminar (String nombreHotel, String ID) {
        actualizar();
        Enumeration<Cuarto> enumH = Collections.enumeration(cuartos.values());

        while (enumH.hasMoreElements()) {
            Cuarto cuarto = enumH.nextElement();
            if (nombreHotel.equals(cuarto.mostrarNombre()) && ID.equals(cuarto.mostrarID())) {
                cuartos.remove(cuarto.mostrarID(), cuarto);
                cuartos.remove(cuarto.mostrarNombre(), cuarto);
                cuartos.remove(cuarto.mostrarCiudad(), cuarto);
                cuartos.remove(cuarto.mostrarEstrellas(), cuarto);
                cuartos.remove(cuarto.mostrarNumero(), cuarto);
                cuartos.remove(cuarto.mostrarPiso(), cuarto);
                cuartos.remove(cuarto.mostrarOcupado(), cuarto);
                System.out.println("Cuarto" +cuarto.mostrarID() + "eliminado exitosamente");
                break;
            }
        }
    }

    public void reservar (Cuarto cuarto, String uuid) {
        Enumeration<Cuarto> enumC = Collections.enumeration(cuartos.values());
        while(enumC.hasMoreElements()){
            Cuarto c = enumC.nextElement();
            while(cuarto.mostrarNombre().equals(c.mostrarNombre())){
                if(!c.mostrarOcupado()){
                    System.out.println("ID de la habitación: "+c.mostrarID());
                    c.ocupar();
                    cuarto=c;
                    cuartos.remove(c.mostrarID(), c);
                    cuartos.put(cuarto.mostrarID(), cuarto);
                    String IDHotel= (cuarto.mostrarNombre()+cuarto.mostrarCiudad()).replaceAll("\\s+","");
                    // ^borra todos los espacio en blanco para mejorar el formato
                    configReservaHotel.reservarHotel(uuid, IDHotel, cuarto.mostrarID());
                    System.out.println("Reserva realizada");
                    return;
                }
                c= enumC.nextElement();
            }
        }
    }

    public void autoGenerarCuartos (String nombre, String ciudad, int estrellas, int numCuartos, int pisos) {
        actualizar();

        boolean ocupado = false;
        for (int i=1; i<=pisos; i++) {
            for (int j=1; j<= numCuartos; j++){
                int idNum = i*100+j; // genera el ID
                String id = String.valueOf(idNum);
                String IDHotel= nombre+ciudad;
                id= id+IDHotel;
                Cuarto cuarto = new Cuarto(IDHotel, nombre, ciudad, estrellas, j, i, ocupado, id);
                cuartos.put(cuarto.mostrarID(), cuarto);
                agregar(cuarto);
            }
        }
        guardar();
    }

    public int contarCuartosHotel (String nombre) {
        actualizar();
        Enumeration<Cuarto> enumC = Collections.enumeration(cuartos.values());

        int cant = 0;
        while (enumC.hasMoreElements()) {
            Cuarto cuarto = enumC.nextElement();
            if (nombre.equals(cuarto.mostrarNombre()))
                cant++;
        }
        return cant;
    }

    public void agregarCuarto () {
        actualizar();
        Scanner scan = new Scanner(System.in);

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
            String IDHotel= nombreH+hotel.mostrarCiudad();
            System.out.println("Ingrese el numero del cuarto: ");
            int numero = Integer.parseInt(scan.next());
            System.out.println("Ingrese el piso del cuarto: ");
            int piso = Integer.parseInt(scan.next());
            System.out.println("Ingrese si el cuarto esta ocupado:");
            boolean ocupado = Boolean.parseBoolean(scan.next());
            int idNum = piso*100+numero;
            String id = String.valueOf(idNum);
            id= id+IDHotel;
            Cuarto cuarto = new Cuarto(IDHotel, nombreH, ciudad, estrellas, numero, piso, ocupado, id);
            agregar(cuarto);
            guardar();
        }
    }

    public void generarCuartosEnMasse () {
        Scanner scan = new Scanner(System.in);

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
                    autoGenerarCuartos(nombreHotel, ciudad, estrellas, numC, numP);
                }
            } while (retry.equalsIgnoreCase("s"));
        }
    }

    public void mostrarCuartosHotel (String nombreHotel) {
        actualizar();
        Enumeration<Cuarto> enumeration = Collections.enumeration(cuartos.values());

        while (enumeration.hasMoreElements()) {
            Cuarto cuarto = enumeration.nextElement();
            if (nombreHotel.equals(cuarto.mostrarNombre())) {
                System.out.println(cuarto.mostrarID() + ". Numero: " + cuarto.mostrarNumero());
                System.out.println(cuarto.mostrarID() + ". Piso: " + cuarto.mostrarPiso());
                System.out.println(cuarto.mostrarID() + ". Ocupado: " + cuarto.mostrarOcupado());
            }
        }
    }

    public void eliminarCuarto () {
        Scanner scan = new Scanner(System.in);
        actualizar();

        if (!configHoteles.mostrarHoteles()) {
            System.out.println("Ingrese el nombre del hotel: ");
            String nombreHotel = scan.nextLine();
            if (contarCuartosHotel(nombreHotel)>0) {
                mostrarCuartosHotel(nombreHotel);
                System.out.println("Ingrese el ID del cuarto: ");
                String ID = scan.nextLine();
                eliminar(nombreHotel, ID);
            }
            else
                System.out.println("No hay cuartos en el hotel.");
            guardar();
        }
    }
}