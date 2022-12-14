package processes;

import Persistence.JSONConfigFileUsuarios;
import entities.Usuario.Cliente;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigClientes implements Config<Cliente> {
    private final HashMap<String, Cliente> clientes;

    public ConfigClientes() {
        this.clientes = new HashMap<String, Cliente>();
    }

    public void registrar (Cliente args) {
        if(this.clientes.containsKey(args.mostrarCorreo())){
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

    public void registrarCliente () {
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
            if (configClientes.confirmarIngresoCliente(correo, contrasena)) {
                System.out.println("El correo ya esta registrado. Desea intentar de nuevo? [S/N]: ");
                retryAnswer = scan.next();
                if (retryAnswer.equalsIgnoreCase("n"))
                    break;
            }
            else {
                Cliente cliente = new Cliente(correo, nombres, apellidos, contrasena);
                configClientes.registrar(cliente);
                persistenceUsuarios.guardarConfig(configAdmins, configClientes);
            }
        } while (retryAnswer.equalsIgnoreCase("s"));
    }

    public String ingresarCliente () {
        Scanner scan = new Scanner(System.in);
        ConfigAdmins configAdmins = new ConfigAdmins();
        ConfigClientes configClientes = new ConfigClientes();
        JSONConfigFileUsuarios persistenceUsuarios = new JSONConfigFileUsuarios();
        persistenceUsuarios.leerConfig(configAdmins, configClientes);
        String copiaUUID = "";
        String retryAnswer = "n";
        int retryTimes = 0;
        do {
            System.out.println("Ingrese su correo:");
            String correoA = scan.next();
            System.out.println("Ingrese su contrasena: ");
            String contrasenaA = scan.next();
            if (!configClientes.confirmarIngresoCliente(correoA, contrasenaA)) {
                System.out.println("No se encontró el usuario.\n");
                System.out.println("Desea intentar de nuevo? [S/N]: ");
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
                System.out.println("Bienvenido.");
                copiaUUID = configClientes.copiarUUID(correoA);
            }
        } while (retryAnswer.equalsIgnoreCase("s"));
        return copiaUUID;
    }
}
