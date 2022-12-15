package Persistence;

import entities.Viajes.Ruta;
import entities.Viajes.Transporte;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import processes.ConfigRutas;
import processes.ConfigTransportes;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONConfigFileViajes implements Persistence<ConfigRutas, ConfigTransportes> {
    private String nombreArchivo;

    public JSONConfigFileViajes() {
        this.nombreArchivo = "Viajes.json";
    }

    public void guardarConfig (ConfigRutas args1, ConfigTransportes args2) {
        JSONObject JSONConfig = new JSONObject();
        JSONConfig.put("rutas", args1.ToJSON());
        JSONConfig.put("transportes", args2.ToJSON());
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

    public void leerConfig (ConfigRutas args1, ConfigTransportes args2) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(this.nombreArchivo); // lectura del archivo

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            LeerRutas(jsonObject, args1);
            LeerTransporte(jsonObject, args2);
        } catch (ParseException | IOException e){
            System.out.println("Exception" + e);
        }
    }

    private void LeerRutas (JSONObject jsonObject, ConfigRutas config) {
        JSONArray rutasJSONArray = (JSONArray) jsonObject.get("rutas");

        if (rutasJSONArray==null)
            return;
        for (Object o : rutasJSONArray) {
            JSONObject ruta = (JSONObject) o;
            String ID = (String) ruta.get("IDRuta");
            String ciudadPartida = (String) ruta.get("ciudad-partida");
            String ciudadDestino = (String) ruta.get("ciudad-destino");
            String transporte = (String) ruta.get("tipoTransporte");
            Ruta rut = new Ruta(ID, ciudadPartida, ciudadDestino, transporte);
            config.registrar(rut);
        }
    }

    private void LeerTransporte (JSONObject jsonObject, ConfigTransportes config){
        JSONArray transportesJSONArray = (JSONArray) jsonObject.get("transporte");

        if (transportesJSONArray==null)
            return;
        for (Object o : transportesJSONArray) {
            JSONObject transporte = (JSONObject) o;
            String IDRuta= (String) transporte.get("IDRuta");
            String ciudadPartida= (String) transporte.get("ciudadPartida");
            String ciudadDestino= (String) transporte.get("ciudadDestino");
            String tipoTransporte = (String) transporte.get("tipoTransporte");
            String ID= (String) transporte.get("ID");
            String empresa = (String) transporte.get("empresa");
            String calidad = (String) transporte.get("calidad");
            String horaPartida = (String) transporte.get("hora-partida");
            String horaDestino = (String) transporte.get("hora-llegada");
            int cantDisponible = (int) transporte.get("cantDisponible");
            Transporte trans = new Transporte(IDRuta, ciudadPartida, ciudadDestino, tipoTransporte, ID, empresa, calidad, horaPartida, horaDestino, cantDisponible);
            config.registrar(trans);
        }
    }
}
