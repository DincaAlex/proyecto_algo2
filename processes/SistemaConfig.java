package processes;

import entities.Admin;
import entities.Cliente;
import hotel.Cuarto;
import hotel.Hotel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Enumeration;
import java.util.Vector;
public class SistemaConfig 
{
    private Vector<Admin> admins;
    private Vector<Cliente> clientes;
    private Vector<Hotel> hoteles;
    private Vector<Cuarto> cuartos;

    public SistemaConfig () {
        this.admins = new Vector<Admin>();
        this.clientes = new Vector<Cliente>();
        this.hoteles = new Vector<Hotel>();
        this.cuartos = new Vector<Cuarto>();
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
        System.out.println("Cuenta de administrador creada con exito.");   
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
        System.out.println("Cuenta de cliente creada con exito.");
        
    }

    public boolean confirmarIngresoAdmin (String correo,String contrasena) {
        Enumeration <Admin> adm= this.admins.elements();
        while(adm.hasMoreElements()) {
            Admin a= adm.nextElement();
            if(correo.equals(a.mostrarCorreo())) {
                if(contrasena.equals(a.mostrarContrasena())) {
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
        return  arrayAdmins;
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
        return  arrayCliente;
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
        System.out.println("Hotel registrado exitosamente.");
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
        System.out.println("Cuarto agregado exitosamente.");
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
            obj.put("numero", c.mostrarNumero());
            obj.put("piso", c.mostrarPiso());
            obj.put("ocupado", c.mostrarOcupado());
            arrayCuartos.add(obj);
        }
        return arrayCuartos;
    }
}
