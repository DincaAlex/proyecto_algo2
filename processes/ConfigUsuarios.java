package processes;

import entities.Usuario.Admin;
import entities.Usuario.Cliente;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Enumeration;
import java.util.Vector;

public class ConfigUsuarios
{
    private Vector<Admin> admins;
    private Vector<Cliente> clientes;

    public ConfigUsuarios() {
        this.admins = new Vector<>();
        this.clientes = new Vector<>();
    }

    public void registrarAdmin(Admin admin) {
        Enumeration<Admin> adm= this.admins.elements();
        while(adm.hasMoreElements()) {
            Admin a= adm.nextElement();
            if(admin.mostrarCorreo().equals(a.mostrarCorreo())) {
                System.out.println("Correo ya utilizado con anterioridad, no se ha creado una nueva cuenta.");
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

    public String copiarUUID(String correo){
        Enumeration<Cliente> enumC = this.clientes.elements();
        String uuidCopia="";
        while (enumC.hasMoreElements()) {
            Cliente cl = enumC.nextElement();
            if (correo.equals(cl.mostrarCorreo())) {
                uuidCopia= cl.mostrarUUID();
            }
        }
        return uuidCopia;
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
}