package Persistance;

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

public class JSONConfigFileHoteles implements Persistance {
    String nombreArchivo;

    public JSONConfigFileHoteles() {
        this.nombreArchivo = "Hoteles.json";
    }
    @Override
    public void guardarConfig(SistemaConfig config) {
        JSONObject JSONConfig = new JSONObject();
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

    @Override
    public void leerConfig(SistemaConfig config) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(this.nombreArchivo); // lectura del archivo

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            LeerHoteles(jsonObject, config);
            LeerCuartos(jsonObject, config);
        } catch (ParseException | IOException e){
            System.out.println("Exception" + e);
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
