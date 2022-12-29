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
    private static HashMap<String, Cliente> clientes;
    private static final ConfigAdmins configAdmins = new ConfigAdmins();
    private static final ConfigClientes configClientes = new ConfigClientes();

    public ConfigClientes() {
        clientes = new HashMap<String, Cliente>();
    }

    public void registrar (Cliente args) {
        clientes.put(args.mostrarCorreo(), args);
    }

    //CREAMOS UNA FUNCION QUE RECUPERE LOS DATOS PERSONALES DEL CLIENTE
    public void recuperarCliente (String UUID) {
        actualizar();
        Enumeration<Cliente> enumH = Collections.enumeration(clientes.values());
        
        String nombreCliente="";
        String apellidoCliente="";
        
        boolean encontrado = false;
        while (enumH.hasMoreElements()) {
            Cliente cliente = enumH.nextElement();
            if (UUID.equals(cliente.mostrarUUID())) {
                System.out.println("DATOS DE CLIENTE: --------");
                nombreCliente = cliente.mostrarNombres();
                apellidoCliente = cliente.mostrarApellidos();
                System.out.println("NOMBRE: " + nombreCliente + "\nAPELLIDO :" + apellidoCliente);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontró datos personales del cliente");
        }
        
    }


    public JSONArray ToJSON () {
        JSONArray arrayCliente = new JSONArray();
        Enumeration<Cliente> cl = Collections.enumeration(clientes.values());
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

    private void agregar (Cliente cliente) {
        if(clientes.containsKey(cliente.mostrarCorreo())){
            System.out.println("Correo en uso actualmente, no se ha creado una nueva cuenta.");
            return;
        }
        clientes.put(cliente.mostrarCorreo(), cliente);
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
        Enumeration<Cliente> enumH = Collections.enumeration(clientes.values());

        while (enumH.hasMoreElements()) {
            Cliente cliente = enumH.nextElement();
            if (correo.equals(cliente.mostrarCorreo())) {
                clientes.remove(cliente.mostrarCorreo(), cliente);
                clientes.remove(cliente.mostrarNombres(), cliente);
                clientes.remove(cliente.mostrarApellidos(), cliente);
                clientes.remove(cliente.mostrarContrasena(), cliente);
                clientes.remove(cliente.mostrarUUID(), cliente);
                System.out.println("Cuenta de cliente eliminada exitosamente");
                break;
            }
        }
    }

    public boolean confirmarIngresoCliente (String correo, String contrasena) {
        Enumeration<Cliente> enumC = Collections.enumeration(clientes.values());
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
        Enumeration<Cliente> enumC = Collections.enumeration(clientes.values());
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
            if (configClientes.confirmarIngresoCliente(correo, contrasena)) {
                System.out.println("El correo ya esta registrado. Desea intentar de nuevo? [S/N]: ");
                retryAnswer = scan.next();
                if (retryAnswer.equalsIgnoreCase("n"))
                    break;
            }
            else {
                Cliente cliente = new Cliente(correo, nombres, apellidos, contrasena);
                agregar(cliente);
                guardar();
            }
        } while (retryAnswer.equalsIgnoreCase("s"));
    }

    public String ingresarCliente () {
        actualizar();
        Scanner scan = new Scanner(System.in);
        String copiaUUID = "error"; // en caso de fallar el login
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

    public void mostrarClientes () {
        actualizar();
        Enumeration<Cliente> enu = Collections.enumeration(clientes.values());

        int i = 0;
        while (enu.hasMoreElements()) {
            Cliente cliente = enu.nextElement();
            System.out.println(i + ". Correo: " + cliente.mostrarCorreo());
            System.out.println(i + ". Nombres: " + cliente.mostrarNombres());
            System.out.println(i + ". Apellidos: " + cliente.mostrarApellidos() + "\n");
            i++;
        }
    }

    public void eliminarCliente () {
        Scanner scan = new Scanner(System.in);
        actualizar();

        mostrarClientes();
        System.out.println("Ingrese el correo del cliente: ");
        String correo = scan.nextLine();
        eliminar(correo);
        guardar();
    }

    public void borrarCliente (String UUID) {
        actualizar();
        Scanner scan = new Scanner(System.in);
        Enumeration<Cliente> enu = Collections.enumeration(clientes.values());

        System.out.println("Escriba su correo: ");
        String correo = scan.nextLine();
        System.out.println("Escriba su contraseña: ");
        String password = scan.nextLine();
        System.out.println("Esta seguro que desea borrar su cuenta? [S/N]: ");
        String confirmacion = scan.nextLine();
        if (confirmacion.equals("s")) {
            while (enu.hasMoreElements()) {
                Cliente cliente = enu.nextElement();
                if (correo.equals(cliente.mostrarCorreo()) && password.equals(cliente.mostrarContrasena())) {
                    eliminar(correo);
                    guardar();
                }
            }
        }
    }
}