package processes;

import entities.Usuario.Cliente;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

public class ConfigClientes implements Config<Cliente> {
    private HashMap<String, Cliente> clientes;

    public ConfigClientes() {
        this.clientes = new HashMap<String, Cliente>();
    }

    public void registrar (Cliente args) {
        if(this.clientes.containsKey((Object)args.mostrarCorreo())){
            System.out.println("Correo en uso actualmente, no se ha creado una nueva cuenta.");
            return;
        }
        this.clientes.put(args.mostrarCorreo(), args);
    }

    public JSONArray ToJSON () {
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

    public String copiarUUID (String correo){
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
}
