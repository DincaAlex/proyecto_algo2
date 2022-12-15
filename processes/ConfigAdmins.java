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
    private static HashMap<String, Admin> admins;
    private static final ConfigAdmins configAdmins = new ConfigAdmins();
    private static final ConfigClientes configClientes = new ConfigClientes();

    public ConfigAdmins () {
        admins = new HashMap<String, Admin>();
    }

    public void registrar (Admin args) {
        admins.put(args.mostrarCorreo(), args);
    }

    public JSONArray ToJSON () {
        JSONArray arrayAdmins = new JSONArray();
        Enumeration<Admin> adm = Collections.enumeration(admins.values());
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

    private void agregar (Admin admin) {
        if(admins.containsKey(admin.mostrarCorreo())){
            System.out.println("Correo en uso actualmente, no se ha creado una nueva cuenta.");
            return;
        }
        admins.put(admin.mostrarCorreo(), admin);
    }

    private void actualizar () {
        JSONConfigFileUsuarios p = new JSONConfigFileUsuarios();
        p.leerConfig(configAdmins, configClientes);
    }

    private void guardar () {
        JSONConfigFileUsuarios p = new JSONConfigFileUsuarios();
        p.guardarConfig(configAdmins, configClientes);
    }

    private void eliminar (String correo) {
        actualizar();
        Enumeration<Admin> enumH = Collections.enumeration(admins.values());

        while (enumH.hasMoreElements()) {
            Admin admin = enumH.nextElement();
            if (correo.equals(admin.mostrarCorreo())) {
                admins.remove(admin.mostrarCorreo(), admin);
                admins.remove(admin.mostrarNombres(), admin);
                admins.remove(admin.mostrarApellidos(), admin);
                admins.remove(admin.mostrarContrasena(), admin);
                admins.remove(admin.mostrarUUID(), admin);
                System.out.println("Cuenta de administrador eliminada exitosamente");
                break;
            }
        }
    }

    public boolean confirmarIngresoAdmin (String correo,String contrasena) {
        Enumeration<Admin> adm = Collections.enumeration(admins.values());
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

    public void registrarAdmin () {
        actualizar();
        Scanner scan = new Scanner(System.in);

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
                agregar(admin);
                guardar();
            }
        } while (retryAnswer.equalsIgnoreCase("s"));
    }

    public boolean ingresarAdmin() {
        actualizar();
        Scanner scan = new Scanner(System.in);
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

    public void mostrarAdmins () {
        actualizar();
        Enumeration<Admin> enu = Collections.enumeration(admins.values());

        int i = 0;
        while (enu.hasMoreElements()) {
            Admin admin = enu.nextElement();
            System.out.println(i + ". Correo: " + admin.mostrarCorreo());
            System.out.println(i + ". Nombres: " + admin.mostrarNombres());
            System.out.println(i + ". Apellidos: " + admin.mostrarApellidos() + "\n");
            i++;
        }
    }

    public void eliminarAdmin () {
        Scanner scan = new Scanner(System.in);
        actualizar();

        mostrarAdmins();
        System.out.println("Ingrese el correo del administrador: ");
        String correo = scan.nextLine();
        eliminar(correo);
        guardar();
    }
}