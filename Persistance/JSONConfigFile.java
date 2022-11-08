package Persistance;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import entities.Admin;
import entities.Cliente;
import processes.SistemaConfig;

public class JSONConfigFile implements Persistance{
    private String nombreArchivo;
    public JSONConfigFile()
    {
        this.nombreArchivo= "Usuarios.json";
    }

    public void guardarConfig(SistemaConfig config)
    {
        JSONObject JSONConfig= new JSONObject();
        JSONConfig.put("admins", config.adminsToJSON());
        JSONConfig.put("clientes", config.clientesToJSON());
        try
        {
            FileWriter fw= new FileWriter(this.nombreArchivo);
            fw.write(JSONConfig.toJSONString());
            fw.flush();
        }
        catch(IOException ex)
        {
            System.out.println("Error "+ ex);
            ex.printStackTrace();
        }
    }

    public void leerConfig(SistemaConfig config) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(this.nombreArchivo); // lectura del archivo

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            LeerAdmins(jsonObject, config);
            LeerClientes(jsonObject, config);
        } catch (ParseException e){
            System.out.println("Exception" + e);
        } catch (FileNotFoundException e){
            System.out.println("Exception" + e);
        } catch (IOException e){
            System.out.println("Exception" + e);
        }
    }

    private void LeerAdmins(JSONObject jsonObject, SistemaConfig config){
        JSONArray adminsJSONArray = (JSONArray) jsonObject.get("admins");

        if ( adminsJSONArray == null )
            return;
        Iterator it = adminsJSONArray.iterator();
        while (it.hasNext()) {
            JSONObject admin = (JSONObject) it.next();
            String correo = (String)admin.get("correo");
            String nombres = (String)admin.get("nombres");
            String apellidos = (String)admin.get("apellidos");
            String contrasena = (String)admin.get("contrasena");
            Admin adm = new Admin(correo, nombres, apellidos, contrasena);
            config.registrarAdmin(adm);
            System.out.println(adm.mostrarNombres());
        }
    }

    private void LeerClientes(JSONObject jsonObject, SistemaConfig config){
        JSONArray clientesJSONArray = (JSONArray) jsonObject.get("clientes");

        if ( clientesJSONArray == null )
            return;
        Iterator it = clientesJSONArray.iterator();
        while (it.hasNext()) {
            JSONObject cliente = (JSONObject) it.next();
            String correo = (String)cliente.get("correo");
            String nombres = (String)cliente.get("nombres");
            String apellidos = (String)cliente.get("apellidos");
            String contrasena = (String)cliente.get("contrasena");
            Cliente cl = new Cliente(correo, nombres, apellidos, contrasena);
            config.registrarCliente(cl);
            System.out.println(cl.mostrarNombres());
        }
    }
}

