package Persistance;

import entities.Admin;
import entities.Cliente;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processes.SistemaConfig;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONConfigFileUsuarios implements Persistance{
    private String nombreArchivo;

    public JSONConfigFileUsuarios() {
        this.nombreArchivo = "Usuarios.json";
    }

    public void guardarConfig (SistemaConfig config) {
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

    public void leerConfig (SistemaConfig config) {
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

    private void LeerAdmins (JSONObject jsonObject, SistemaConfig config) {
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

    private void LeerClientes (JSONObject jsonObject, SistemaConfig config){
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
            Cliente cl = new Cliente(correo, nombres, apellidos, contrasena, uuid);
            config.registrarCliente(cl);
        }
    }
}

