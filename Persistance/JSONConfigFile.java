package Persistance;

import entities.Admin;
import entities.Cliente;
import hotel.Cuarto;
import hotel.Hotel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processes.SistemaConfig;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONConfigFile implements Persistance{
    private String nombreArchivo;
    public JSONConfigFile () {
        this.nombreArchivo = "Usuarios.json";
    }

    public void guardarConfig (SistemaConfig config) {
        JSONObject JSONConfig = new JSONObject();
        JSONConfig.put("admins", config.adminsToJSON());
        JSONConfig.put("clientes", config.clientesToJSON());
        JSONConfig.put("hoteles", config.hotelesToJSON());
        JSONConfig.put("cuartos", config.cuartosToJSON());
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
            LeerHoteles(jsonObject, config);
            LeerCuartos(jsonObject, config);
        } catch (ParseException | IOException e){
            System.out.println("Exception" + e);
        }
    }

    private void LeerAdmins(JSONObject jsonObject, SistemaConfig config){
        JSONArray adminsJSONArray = (JSONArray) jsonObject.get("admins");

        if ( adminsJSONArray == null )
            return;
        for (Object o : adminsJSONArray) {
            JSONObject admin = (JSONObject) o;
            String correo = (String) admin.get("correo");
            String nombres = (String) admin.get("nombres");
            String apellidos = (String) admin.get("apellidos");
            String contrasena = (String) admin.get("contrasena");
            Admin adm = new Admin(correo, nombres, apellidos, contrasena);
            config.registrarAdmin(adm);
            System.out.println(adm.mostrarNombres());
        }
    }

    private void LeerClientes(JSONObject jsonObject, SistemaConfig config){
        JSONArray clientesJSONArray = (JSONArray) jsonObject.get("clientes");

        if ( clientesJSONArray == null )
            return;
        for (Object o : clientesJSONArray) {
            JSONObject cliente = (JSONObject) o;
            String correo = (String) cliente.get("correo");
            String nombres = (String) cliente.get("nombres");
            String apellidos = (String) cliente.get("apellidos");
            String contrasena = (String) cliente.get("contrasena");
            Cliente cl = new Cliente(correo, nombres, apellidos, contrasena);
            config.registrarCliente(cl);
            System.out.println(cl.mostrarNombres());
        }
    }
    private void LeerHoteles(JSONObject jsonObject, SistemaConfig config){
        JSONArray hotelesJSONArray = (JSONArray) jsonObject.get("hoteles");

        if (hotelesJSONArray == null )
            return;
        for (Object o : hotelesJSONArray) {
            JSONObject hoteles = (JSONObject) o;
            String nombre = (String) hoteles.get("nombre");
            String ciudad = (String) hoteles.get("ciudad");
            int estrellas = (int) hoteles.get("estrelles");
            Hotel hot = new Hotel(nombre, ciudad, estrellas);
            config.registrarHotel(hot);
            System.out.println(hot.mostrarNombre());
        }
    }

    private void LeerCuartos(JSONObject jsonObject, SistemaConfig config){
        JSONArray cuartosJSONArray = (JSONArray) jsonObject.get("cuartos");

        if (cuartosJSONArray == null )
            return;
        for (Object o : cuartosJSONArray) {
            JSONObject cuartos = (JSONObject) o;
            String nombre = (String) cuartos.get("nombre");
            String ciudad = (String) cuartos.get("ciudad");
            int estrellas = (int) cuartos.get("estrelles");
            int numero = (int) cuartos.get("numero");
            int piso = (int) cuartos.get("piso");
            boolean ocupado = (boolean) cuartos.get("ocupado");
            Cuarto cuarto = new Cuarto(nombre, ciudad, estrellas, numero, piso, ocupado);
            config.registrarCuarto(cuarto);
            System.out.println(cuarto.mostrarNumero());
        }
    }
}

