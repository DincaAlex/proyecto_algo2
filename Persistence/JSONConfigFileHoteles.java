package Persistence;

import entities.Hotel.Cuarto;
import entities.Hotel.Hotel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processes.ConfigCuartos;
import processes.ConfigHoteles;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONConfigFileHoteles implements Persistence<ConfigHoteles, ConfigCuartos> {
    String nombreArchivo;

    public JSONConfigFileHoteles () {
        this.nombreArchivo = "Hoteles.json";
    }

    public void guardarConfig (ConfigHoteles args1, ConfigCuartos args2) {
        JSONObject JSONConfig = new JSONObject();
        JSONConfig.put("hoteles", args1.ToJSON());
        JSONConfig.put("cuartos", args2.ToJSON());
        try {
            FileWriter fw = new FileWriter(this.nombreArchivo);
            fw.write(JSONConfig.toJSONString());
            fw.flush();
        } catch (IOException e) {
            System.out.println("Error "+ e);
            e.printStackTrace();
        }
    }

    public void leerConfig (ConfigHoteles args1, ConfigCuartos args2) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(this.nombreArchivo); // lectura del archivo

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            LeerHoteles(jsonObject, args1);
            LeerCuartos(jsonObject, args2); //Arreglar el mostrar
        } catch (ParseException | IOException e) {
            System.out.println("Exception" + e);
        }
    }

    private void LeerHoteles (JSONObject jsonObject, ConfigHoteles config) {
        JSONArray hotelesJSONArray = (JSONArray) jsonObject.get("hoteles");

        if (hotelesJSONArray == null )
            return;
        for (Object o : hotelesJSONArray) {
            JSONObject hoteles = (JSONObject) o;
            String IDHotel = (String) hoteles.get("IDHotel");
            String nombre = (String) hoteles.get("nombre");
            String ciudad = (String) hoteles.get("ciudad");
            int estrellas = (int)(long) hoteles.get("estrellas");
            float precio = (float)(double) hoteles.get("precio");
            Hotel hot = new Hotel(IDHotel, nombre, ciudad, estrellas, precio);
            config.registrar(hot);
        }
    }

    private void LeerCuartos (JSONObject jsonObject, ConfigCuartos config) {
        JSONArray cuartosJSONArray = (JSONArray) jsonObject.get("cuartos");

        if (cuartosJSONArray == null )
            return;
        for (Object o : cuartosJSONArray) {
            JSONObject cuartos = (JSONObject) o;
            String IDHotel= (String) cuartos.get("IDHotel");
            String ID = (String) cuartos.get("ID");
            String nombre = (String) cuartos.get("nombre");
            String ciudad = (String) cuartos.get("ciudad");
            int estrellas = (int)(long) cuartos.get("estrellas");
            float precio = (float)(double) cuartos.get("precio");
            int numero = (int)(long) cuartos.get("numero");
            int piso = (int)(long) cuartos.get("piso");
            boolean ocupado = (boolean) cuartos.get("ocupado");
            Cuarto cuarto = new Cuarto(IDHotel, nombre, ciudad, estrellas, precio, numero, piso, ocupado, ID);
            config.registrar(cuarto);
        }
    }
}
