package processes;

import Persistance.Persistance;
import Persistance.JSONConfigFileHoteles;
import entities.Admin;
import entities.Cliente;
import entities.Cuarto;
import entities.Hotel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

public class SistemaConfig
{
    private Vector<Admin> admins;
    private Vector<Cliente> clientes;
    private Vector<Hotel> hoteles;
    private Vector<Cuarto> cuartos;

    public SistemaConfig () {
        this.admins = new Vector<>();
        this.clientes = new Vector<>();
        this.hoteles = new Vector<>();
        this.cuartos = new Vector<>();
    }
    

    public void registrarAdmin(Admin admin) {
        Enumeration<Admin> adm= this.admins.elements();
        while(adm.hasMoreElements()) {
            Admin a= adm.nextElement();
            if(admin.mostrarCorreo().equals(a.mostrarCorreo())) {
                System.out.println("Correo ya utilizado con anterioridad,no se ha creado una nueva cuenta de administrador.");
                return;
            }
        }
        this.admins.add((Admin) admin);
    }

    public void registrarCliente (Cliente cliente) {
        Enumeration<Cliente> cl= this.clientes.elements();
        while (cl.hasMoreElements()) {
            Cliente c= cl.nextElement();
            if (cliente.mostrarCorreo().equals(c.mostrarCorreo())) {
                System.out.println("Correo ya utilizado con anterioridad, no se ha creado una nueva cuenta.");
                return;
            }
        }
        this.clientes.add((Cliente) cliente);
    }

    public boolean confirmarIngresoAdmin (String correo,String contrasena) {
        Enumeration<Admin> adm = this.admins.elements();
        while (adm.hasMoreElements()) {
            Admin a = adm.nextElement();
            if (correo.equals(a.mostrarCorreo())) {
                if (contrasena.equals(a.mostrarContrasena())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean confirmarIngresoCliente (String correo,String contrasena) {
        Enumeration<Cliente> enumC = this.clientes.elements();
        while (enumC.hasMoreElements()) {
            Cliente cl = enumC.nextElement();
            if (correo.equals(cl.mostrarCorreo())) {
                if (contrasena.equals(cl.mostrarContrasena())) {
                    return true;
                }
            }
        }
        return false;
    }


    public JSONArray adminsToJSON () {
        JSONArray arrayAdmins = new JSONArray();
        Enumeration<Admin> adm = this.admins.elements();
        while(adm.hasMoreElements()) {
            Admin a = adm.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("correo", a.mostrarCorreo());
            obj.put("nombres", a.mostrarNombres());
            obj.put("apellidos", a.mostrarApellidos());
            obj.put("contrasena", a.mostrarContrasena());
            obj.put("uuid", a.mostrarUUID());
            arrayAdmins.add(obj);
        }
        return arrayAdmins;
    }

    public JSONArray clientesToJSON () {
        JSONArray arrayCliente = new JSONArray();
        Enumeration<Cliente> cl = this.clientes.elements();
        while(cl.hasMoreElements()) {
            Cliente c = cl.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("correo", c.mostrarCorreo());
            obj.put("nombres", c.mostrarNombres());
            obj.put("apellidos", c.mostrarApellidos());
            obj.put("contrasena", c.mostrarContrasena());
            obj.put("uuid", c.mostrarUUID());
            arrayCliente.add(obj);
        }
        return arrayCliente;
    }

    public void registrarHotel (Hotel hotel) {
        Enumeration<Hotel> enumH = this.hoteles.elements();
        while (enumH.hasMoreElements()) {
            Hotel h = enumH.nextElement();
            if (hotel.mostrarNombre().equals(h.mostrarNombre())) {
                System.out.println("El hotel ya esta registrado.");
                return;
            }
        }
        this.hoteles.add((Hotel) hotel);
    }

    public void registrarCuarto(Cuarto cuarto) {
        Enumeration<Cuarto> enumC = this.cuartos.elements();
        while (enumC.hasMoreElements()) {
            Cuarto c = enumC.nextElement();
            if (Objects.equals(cuarto.mostrarNombre(), c.mostrarNombre())) {
                if (cuarto.mostrarPiso()==c.mostrarPiso() && cuarto.mostrarNumero()==c.mostrarNumero()){
                    System.out.println("El cuarto ya existe.");
                    return;
                }

            }
        }
        this.cuartos.add((Cuarto) cuarto);
    }

    public void reservarCuarto(Cuarto cuarto){
        Enumeration<Cuarto> enumC= this.cuartos.elements();
        while(enumC.hasMoreElements()){
            Cuarto c= enumC.nextElement();
            while(cuarto.mostrarNombre().equals(c.mostrarNombre())){
                if(!cuarto.mostrarOcupado()){
                    System.out.println("Piso: "+c.mostrarPiso()+"\nNumero: "+c.mostrarNumero());
                    c.ocupar();
                    if(c.mostrarOcupado()){
                        System.out.println("Reserva realizada");
                    }
                    return;
                }
            }
        }
    }

    public JSONArray hotelesToJSON () {
        JSONArray arrayHoteles = new JSONArray();
        Enumeration<Hotel> enumH = this.hoteles.elements();
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
        Enumeration<Cuarto> enumC = this.cuartos.elements();
        while (enumC.hasMoreElements()) {
            Cuarto c = enumC.nextElement();
            JSONObject obj = new JSONObject();
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

    public void autoGenerarCuartos (String nombre, String ciudad, int estrellas, int cuartos, int pisos ) {
        boolean ocupado = false;
        SistemaConfig config = new SistemaConfig();
        Persistance p = new JSONConfigFileHoteles();
        p.leerConfig(config);
        for (int i=1; i<=pisos; i++) {
            for (int j=1; j<= cuartos; j++){
                Cuarto cuarto = new Cuarto(nombre, ciudad, estrellas, j, i, ocupado);
                config.registrarCuarto(cuarto);
                p.guardarConfig(config);
            }
        }
    }

    public boolean noHayHoteles () {
        return hoteles.isEmpty();
    }

    public void mostrarHoteles () {
        int i = 1;
        Enumeration<Hotel> enu = this.hoteles.elements();
        SistemaConfig config = new SistemaConfig();
        Persistance p = new JSONConfigFileHoteles();
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

    public String buscarHotel (String nombre, int num) {
        Enumeration<Hotel> enumH = this.hoteles.elements();
        String valor = "";
        while (enumH.hasMoreElements()) {
            Hotel hotel = enumH.nextElement();
            if (nombre.equals(hotel.mostrarNombre())) {
                switch (num) {
                    case 1 -> valor = hotel.mostrarCiudad();
                    case 2 -> valor = String.valueOf(hotel.mostrarEstrellas());
                }
            }
        }
        return valor;
    }

    public int contarCuartosHotel (String nombre) {
        int cant = 0;
        Enumeration<Cuarto> enumC = this.cuartos.elements();
        while (enumC.hasMoreElements()) {
            Cuarto cuarto = enumC.nextElement();
            if (nombre.equals(cuarto.mostrarNombre()))
                cant++;
        }
        return cant;
    }
}
