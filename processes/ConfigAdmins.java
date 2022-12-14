package processes;

import entities.Usuario.Admin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

public class ConfigAdmins implements Config<Admin> {
    private HashMap<String, Admin> admins;

    public ConfigAdmins () {
        this.admins = new HashMap<String, Admin>();
    }

    public void registrar (Admin args) {
        if(this.admins.containsKey((Object)args.mostrarCorreo())){
            System.out.println("Correo en uso actualmente, no se ha creado una nueva cuenta.");
            return;
        }
        this.admins.put(args.mostrarCorreo(), args);
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

    public JSONArray ToJSON () {
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
}