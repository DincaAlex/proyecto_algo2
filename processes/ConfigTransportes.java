package processes;

import Persistence.JSONConfigFileViajes;
import entities.Viajes.Transporte;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigTransportes implements Config<Transporte> {
    private HashMap<String, Transporte> transportes;

    public ConfigTransportes(){
        this.transportes = new HashMap<String, Transporte>();
    }

    public void registrar(Transporte args) {
        if(this.transportes.containsKey((Object)args.mostrarTipo())) {
            System.out.println("El transporte ya existe.");
            return;
        }
        this.transportes.put(args.mostrarTipo(), args);
    }

    public JSONArray ToJSON() {
        JSONArray arrayTransportes = new JSONArray();
        Enumeration<Transporte> enumT = Collections.enumeration(this.transportes.values());
        while (enumT.hasMoreElements()) {
            Transporte trans= enumT.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("tipo", trans.mostrarTipo());
            obj.put("empresa", trans.mostrarEmpresa());
            obj.put("calidad", trans.mostrarCalidad());
            obj.put("hora-partida", trans.mostrarHoraPartida());
            obj.put("hora-llegada", trans.mostrarHoraDestino());
            arrayTransportes.add(obj);
        }
        return arrayTransportes;
    }

    public void agregarTransporte () {
        Scanner scan= new Scanner(System.in);
        ConfigRutas configRutas = new ConfigRutas();
        ConfigTransportes configTransportes = new ConfigTransportes();
        JSONConfigFileViajes persistenceViajes = new JSONConfigFileViajes();
        persistenceViajes.leerConfig(configRutas, configTransportes);
        System.out.println("Ingrese el tipo de transporte: ");
        String tipoTransporte= scan.next();
        System.out.println("Ingrese la empresa:");
        String empresa= scan.next();
        System.out.println("Ingrese la calidad del servicio: ");
        String calidad= scan.next();
        System.out.println("Ingrese el dia de partida (dd-mm-yyyy): ");
        int diaP= scan.nextInt();
        int mesP= scan.nextInt();
        int anioP= scan.nextInt();
        System.out.println("Ingrese la hora de partida (hh-mm-ss): ");
        int horasP= scan.nextInt();
        int minutosP= scan.nextInt();
        int segundosP= scan.nextInt();
        LocalDateTime hPartida= LocalDateTime.of(anioP, mesP, diaP, horasP, minutosP, segundosP);
        System.out.println("Ingrese el dia de destino (dd-mm-yyyy): ");
        int diaD= scan.nextInt();
        int mesD= scan.nextInt();
        int anioD= scan.nextInt();
        System.out.println("Ingrese la hora de destino (hh-mm-ss): ");
        int horasD= scan.nextInt();
        int minutosD= scan.nextInt();
        int segundosD= scan.nextInt();
        LocalDateTime hDestino= LocalDateTime.of(anioD, mesD, diaD, horasD, minutosD, segundosD);
        Transporte transporte= new Transporte(tipoTransporte, empresa, calidad, hPartida.toString(), hDestino.toString());
        configTransportes.registrar(transporte);
        persistenceViajes.guardarConfig(configRutas, configTransportes);
    }
}
