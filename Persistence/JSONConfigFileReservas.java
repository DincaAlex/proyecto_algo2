package Persistence;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import entities.Reservas.ReservaHotel;
import entities.Reservas.ReservaTransporte;
import processes.ConfigReservaHotel;
import processes.ConfigReservaTransporte;

public class JSONConfigFileReservas implements Persistence<ConfigReservaHotel, ConfigReservaTransporte> {
    String nombreArchivo;

    public JSONConfigFileReservas() {
        this.nombreArchivo = "Reservas.json";
    }

    public void guardarConfig (ConfigReservaHotel args1, ConfigReservaTransporte args2) {
        JSONObject JSONConfig = new JSONObject();
        JSONConfig.put("reservasHotel", args1.ToJSON());
        JSONConfig.put("reservasTransporte", args2.ToJSON());
        try {
            FileWriter fw = new FileWriter(this.nombreArchivo);
            fw.write(JSONConfig.toJSONString());
            fw.flush();
        } catch (IOException e) {
            System.out.println("Error "+ e);
            e.printStackTrace();
        }
    }

    public void leerConfig (ConfigReservaHotel args1, ConfigReservaTransporte args2) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(this.nombreArchivo); // lectura del archivo

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            LeerReservasHotel(jsonObject, args1);
            LeerReservasTransporte(jsonObject, args2); //Arreglar el mostrar
        } catch (ParseException | IOException e) {
            System.out.println("Exception" + e);
        }
    }

    private void LeerReservasHotel (JSONObject jsonObject, ConfigReservaHotel config) {
        JSONArray reservasHotelJSONArray = (JSONArray) jsonObject.get("reservasHotel");

        if (reservasHotelJSONArray == null )
            return;
        for (Object o : reservasHotelJSONArray) {
            JSONObject reservaHotel = (JSONObject) o;
            String IDCliente = (String) reservaHotel.get("IDCliente");
            String IDHotel = (String) reservaHotel.get("IDHotel");
            String IDCuarto = (String) reservaHotel.get("IDCuarto");
            String uuid=(String) reservaHotel.get("IDReserva");
            ReservaHotel resHot = new ReservaHotel(uuid, IDCliente, IDHotel, IDCuarto);
            config.registrar(resHot);
        }
    }

    private void LeerReservasTransporte (JSONObject jsonObject, ConfigReservaTransporte config) {
        JSONArray reservasTransporteJSONArray = (JSONArray) jsonObject.get("reservasTransporte");

        if (reservasTransporteJSONArray == null )
            return;
        for (Object o : reservasTransporteJSONArray) {
            JSONObject reservasTransporte = (JSONObject) o;
            String IDCliente = (String) reservasTransporte.get("IDCliente");
            String IDRuta = (String) reservasTransporte.get("IDRuta");
            String IDTransporte = (String) reservasTransporte.get("IDTransporte");
            String uuid=(String) reservasTransporte.get("IDReserva");
            ReservaTransporte reservaTransporte = new ReservaTransporte(uuid, IDCliente, IDRuta, IDTransporte);
            config.registrar(reservaTransporte);
        }
    }
}
