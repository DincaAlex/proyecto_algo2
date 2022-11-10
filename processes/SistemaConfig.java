package processes;

import entities.Admin;
import entities.Cliente;
import hotel.Cuarto;
import hotel.Hotel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

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
            if (cuarto.mostrarNumero()==(c.mostrarNumero())) {
                System.out.println("El cuarto ya existe.");
                return;
            }
        }
        this.cuartos.add((Cuarto) cuarto);
    }

    public JSONArray hotelesToJSON () {
        JSONArray arrayHoteles = new JSONArray();
        Enumeration<Hotel> enumH = this.hoteles.elements();
        while (enumH.hasMoreElements()) {
            Hotel h = enumH.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("ID", h.mostrarID());
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

    public void mostrarHoteles () {
        int i = 1;
        Enumeration<Hotel> enu = this.hoteles.elements();
        while (enu.hasMoreElements()) {
            Hotel hotel = enu.nextElement();
            System.out.println(i + ". ID: " + hotel.mostrarID());
            System.out.println(i + ". Nombre: " + hotel.mostrarNombre());
            System.out.println(i + ". Ciudad: " + hotel.mostrarCiudad());
            System.out.println(i + ". Estrellas: " + hotel.mostrarEstrellas() + "\n");
            i++;
        }
    }

    public String buscarHotel (String ID, int num) {
        Enumeration<Hotel> enumH = this.hoteles.elements();
        String valor = "";
        while (enumH.hasMoreElements()) {
            Hotel hotel = enumH.nextElement();
            if (ID.equals(hotel.mostrarID())) {
                switch (num) {
                    case 1 -> valor = hotel.mostrarNombre();
                    case 2 -> valor = hotel.mostrarCiudad();
                    case 3 -> {
                        valor = String.valueOf(hotel.mostrarEstrellas());
                        System.out.println(valor);
                    }
                }
            }
        }
        return valor;
    }

}
