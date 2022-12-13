package processes;

import entities.Usuario.Admin;
import entities.Usuario.Cliente;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

public class ConfigUsuarios
{
    private HashMap<String, Admin> admins;
    private HashMap<String, Cliente> clientes;

    public ConfigUsuarios() {
        this.admins = new HashMap<String, Admin>();
        this.clientes = new HashMap<String, Cliente>();
    }

    public void registrarAdmin(Admin admin) {
        if(this.admins.containsKey((Object)admin.mostrarCorreo())){
            System.out.println("Correo en uso actualmente, no se ha creado una nueva cuenta.");
            return;
        }
        this.admins.put(admin.mostrarCorreo(), admin);
    }

    public void registrarCliente (Cliente cliente) {
        if(this.clientes.containsKey((Object)cliente.mostrarCorreo())){
            System.out.println("Correo en uso actualmente, no se ha creado una nueva cuenta.");
            return;
        }
        this.clientes.put(cliente.mostrarCorreo(), cliente);
    }

    public boolean confirmarIngresoAdmin (String correo,String contrasena) {
        Enumeration<Admin> adm = Collections.enumeration(this.admins.values());
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
        Enumeration<Cliente> enumC = Collections.enumeration(this.clientes.values());
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
        Enumeration<Cliente> enumC = Collections.enumeration(this.clientes.values());
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
        Enumeration<Admin> adm = Collections.enumeration(this.admins.values());
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
        Enumeration<Cliente> cl = Collections.enumeration(this.clientes.values());
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