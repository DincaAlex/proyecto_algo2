package Persistence;

import entities.Usuario.Admin;
import entities.Usuario.Cliente;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processes.ConfigUsuarios;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONConfigFileUsuarios implements PersistenceUsuarios {
    private String nombreArchivo;

    public JSONConfigFileUsuarios() {
        this.nombreArchivo = "Usuarios.json";
    }

    public void guardarConfig (ConfigUsuarios config) {
        JSONObject JSONConfig = new JSONObject();
        JSONConfig.put("admins", config.adminsToJSON());
        JSONConfig.put("clientes", config.clientesToJSON());
        try {
            FileWriter fw = new FileWriter(this.nombreArchivo);
            fw.write(JSONConfig.toJSONString());
            fw.flush();
        }
        catch (IOException ex) {
            System.out.println("Error "+ ex);
            ex.printStackTrace();
        }
    }

    public void leerConfig (ConfigUsuarios config) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(this.nombreArchivo); // lectura del archivo

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            LeerAdmins(jsonObject, config);
            LeerClientes(jsonObject, config);
        } catch (ParseException | IOException e){
            System.out.println("Exception" + e);
        }
    }

    private void LeerAdmins (JSONObject jsonObject, ConfigUsuarios config) {
        JSONArray adminsJSONArray = (JSONArray) jsonObject.get("admins");

        if ( adminsJSONArray == null )
            return;
        for (Object o : adminsJSONArray) {
            JSONObject admin = (JSONObject) o;
            String correo = (String) admin.get("correo");
            String nombres = (String) admin.get("nombres");
            String apellidos = (String) admin.get("apellidos");
            String contrasena = (String) admin.get("contrasena");
            String uuid= (String) admin.get("uuid");
            Admin adm = new Admin(correo, nombres, apellidos, contrasena, uuid);
            config.registrarAdmin(adm);
        }
    }

    private void LeerClientes (JSONObject jsonObject, ConfigUsuarios config){
        JSONArray clientesJSONArray = (JSONArray) jsonObject.get("clientes");

        if ( clientesJSONArray == null )
            return;
        for (Object o : clientesJSONArray) {
            JSONObject cliente = (JSONObject) o;
            String correo = (String) cliente.get("correo");
            String nombres = (String) cliente.get("nombres");
            String apellidos = (String) cliente.get("apellidos");
            String contrasena = (String) cliente.get("contrasena");
            String uuid=(String) cliente.get("uuid");
            Cliente cli = new Cliente(correo, nombres, apellidos, contrasena, uuid);
            config.registrarCliente(cli);
        }
    }
}
