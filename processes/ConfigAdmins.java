package processes;

import Persistence.JSONConfigFileUsuarios;
import entities.Usuario.Admin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigAdmins implements Config<Admin> {
    private final HashMap<String, Admin> admins;

    public ConfigAdmins () {
        this.admins = new HashMap<String, Admin>();
    }

    public void registrar (Admin args) {
        if(this.admins.containsKey(args.mostrarCorreo())){
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

    public void registrarAdmin () {
        Scanner scan = new Scanner(System.in);
        ConfigAdmins configAdmins = new ConfigAdmins();
        ConfigClientes configClientes = new ConfigClientes();
        JSONConfigFileUsuarios persistenceUsuarios = new JSONConfigFileUsuarios();
        persistenceUsuarios.leerConfig(configAdmins, configClientes);
        String retryAnswer;
        do {
            retryAnswer = "n";
            System.out.println("Ingrese el correo con el que se registrara: ");
            String correo = scan.next();
            System.out.println("Ingrese su nombre: ");
            String nombres = scan.next();
            System.out.println("Ingrese su apellido:");
            String apellidos = scan.next();
            System.out.println("Ingrese una contrasena:");
            String contrasena = scan.next();
            if (configAdmins.confirmarIngresoAdmin(correo, contrasena)) {
                System.out.println("El correo ya esta registrado. Desea intentar de nuevo? [S/N]: ");
                retryAnswer = scan.next();
                if (retryAnswer.equalsIgnoreCase("n"))
                    break;
            }
            else {
                Admin admin = new Admin(correo, nombres, apellidos, contrasena);
                configAdmins.registrar(admin);
                persistenceUsuarios.guardarConfig(configAdmins, configClientes);
            }
        } while (retryAnswer.equalsIgnoreCase("s"));
    }

    public boolean ingresarAdmin() {
        Scanner scan = new Scanner(System.in);
        ConfigAdmins configAdmins = new ConfigAdmins();
        ConfigClientes configClientes = new ConfigClientes();
        JSONConfigFileUsuarios persistenceUsuarios = new JSONConfigFileUsuarios();
        persistenceUsuarios.leerConfig(configAdmins, configClientes);
        String retryAnswer;
        int retryTimes = 0;
        boolean entradaExitosa = false;
        do {
            retryAnswer = "n";
            System.out.println("Ingrese su correo:");
            String correoA = scan.next();
            System.out.println("Ingrese su contrasena: ");
            String contrasenaA = scan.next();
            if (!configAdmins.confirmarIngresoAdmin(correoA, contrasenaA)) {
                System.out.println("No se encontró el usuario. Desea intentar de nuevo? [S/N]: ");
                retryAnswer = scan.next();
                if (retryAnswer.equalsIgnoreCase("n"))
                    break;
                if (retryTimes>=3) {
                    System.out.println("Excedió el número máximo de intentos.");
                    retryAnswer = "n";
                    break;
                }
                if (retryAnswer.equalsIgnoreCase("s"))
                    retryTimes++;
            }
            else {
                entradaExitosa = true;
                System.out.println("Bienvenido.\n");
            }
        } while (retryAnswer.equalsIgnoreCase("s"));
        return entradaExitosa;
    }
}