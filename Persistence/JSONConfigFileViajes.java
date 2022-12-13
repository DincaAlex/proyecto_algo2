package Persistence;

import entities.Viajes.Ruta;
import entities.Viajes.Transporte;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processes.ConfigViajes;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class JSONConfigFileViajes {
    private String nombreArchivo;

    public JSONConfigFileViajes() {
        this.nombreArchivo = "Viajes.json";
    }

    public void guardarConfig (ConfigViajes config) {
        JSONObject JSONConfig = new JSONObject();
        JSONConfig.put("rutas", config.rutasToJSON());
        JSONConfig.put("transportes", config.transporteToJSON());
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

    public void leerConfig (ConfigViajes config) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(this.nombreArchivo); // lectura del archivo

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            LeerRutas(jsonObject, config);
            LeerTransporte(jsonObject, config);
        } catch (ParseException | IOException e){
            System.out.println("Exception" + e);
        }
    }

    private void LeerRutas (JSONObject jsonObject, ConfigViajes config) {
        JSONArray rutasJSONArray = (JSONArray) jsonObject.get("rutas");

        if (rutasJSONArray==null)
            return;
        for (Object o : rutasJSONArray) {
            JSONObject ruta = (JSONObject) o;
            String ID = (String) ruta.get("ID");
            String ciudadPartida = (String) ruta.get("ciudad-partida");
            String ciudadDestino = (String) ruta.get("ciudad-destino");
            String transporte = (String) ruta.get("transporte");
            Ruta rut = new Ruta(ID, ciudadPartida, ciudadDestino, transporte);
            config.registrarRuta(rut);
        }
    }

    private void LeerTransporte (JSONObject jsonObject, ConfigViajes config){
        JSONArray transportesJSONArray = (JSONArray) jsonObject.get("transporte");

        if (transportesJSONArray==null)
            return;
        for (Object o : transportesJSONArray) {
            JSONObject transporte = (JSONObject) o;
            String tipo = (String) transporte.get("tipo");
            String empresa = (String) transporte.get("empresa");
            String calidad = (String) transporte.get("calidad");
            Date horaPartida = (Date) transporte.get("hora-partida");
            Date horaDestino = (Date) transporte.get("hora-llegada");
            Transporte trans = new Transporte(tipo, empresa, calidad, horaPartida, horaDestino);
            config.registrarTransporte(trans);
        }
    }
}
