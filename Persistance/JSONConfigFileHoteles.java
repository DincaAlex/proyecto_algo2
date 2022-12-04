package Persistance;

import entities.Cuarto;
import entities.Hotel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import processes.ConfigHoteles;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONConfigFileHoteles implements HotelPersistance {
    String nombreArchivo;

    public JSONConfigFileHoteles() {
        this.nombreArchivo = "Hoteles.json";
    }

    public void guardarConfig(ConfigHoteles config) {
        JSONObject JSONConfig = new JSONObject();
        JSONConfig.put("hoteles", config.hotelesToJSON());
        JSONConfig.put("cuartos", config.cuartosToJSON());
        try {
            FileWriter fw = new FileWriter(this.nombreArchivo);
            fw.write(JSONConfig.toJSONString());
            fw.flush();
        } catch (IOException e) {
            System.out.println("Error "+ e);
            e.printStackTrace();
        }
    }

    public void leerConfig (ConfigHoteles config) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(this.nombreArchivo); // lectura del archivo

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            LeerHoteles(jsonObject, config);
            LeerCuartos(jsonObject, config);
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
            String nombre = (String) hoteles.get("nombre");
            String ciudad = (String) hoteles.get("ciudad");
            int estrellas = (int)(long) hoteles.get("estrellas");
            Hotel hot = new Hotel(nombre, ciudad, estrellas);
            config.registrarHotel(hot);
        }
    }

    private void LeerCuartos (JSONObject jsonObject, ConfigHoteles config) {
        JSONArray cuartosJSONArray = (JSONArray) jsonObject.get("cuartos");

        if (cuartosJSONArray == null )
            return;
        for (Object o : cuartosJSONArray) {
            JSONObject cuartos = (JSONObject) o;
            String nombre = (String) cuartos.get("nombre");
            String ciudad = (String) cuartos.get("ciudad");
            int estrellas = (int)(long) cuartos.get("estrellas");
            int numero = (int)(long) cuartos.get("numero");
            int piso = (int)(long) cuartos.get("piso");
            boolean ocupado = (boolean) cuartos.get("ocupado");
            String cReserva= (String) cuartos.get("cReserva");
            Cuarto cuarto = new Cuarto(nombre, ciudad, estrellas, numero, piso, ocupado, cReserva);
            config.registrarCuarto(cuarto);
        }
    }
}
