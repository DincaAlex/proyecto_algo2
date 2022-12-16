package processes;

import Persistence.JSONConfigFileViajes;
import entities.Reservas.ReservaTransporte;
import entities.Viajes.Ruta;
import entities.Viajes.Transporte;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigTransportes implements Config<Transporte> {
    private static HashMap<String, Transporte> transportes;
    private static final ConfigRutas configRutas = new ConfigRutas();
    private static final ConfigTransportes configTransportes = new ConfigTransportes();
    private static final ConfigReservaTransporte configReservaTransporte= new ConfigReservaTransporte();

    public ConfigTransportes(){
        transportes = new HashMap<String, Transporte>();
    }

    public void registrar (Transporte args) {
        transportes.put(args.mostrarID(), args);
    }

    public JSONArray ToJSON() {
        JSONArray arrayTransportes = new JSONArray();
        Enumeration<Transporte> enumT = Collections.enumeration(transportes.values());
        while (enumT.hasMoreElements()) {
            Transporte trans= enumT.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("IDRuta", trans.mostrarIDRuta());
            obj.put("ID", trans.mostrarID());
            obj.put("tipoTransporte", trans.mostrarTipoTransporte());
            obj.put("empresa", trans.mostrarEmpresa());
            obj.put("calidad", trans.mostrarCalidad());
            obj.put("hora-partida", trans.mostrarHoraPartida());
            obj.put("hora-llegada", trans.mostrarHoraDestino());
            obj.put("cantDisponible", trans.mostrarCantDisponible());
            arrayTransportes.add(obj);
        }
        return arrayTransportes;
    }

    private void actualizar () {
        JSONConfigFileViajes p = new JSONConfigFileViajes();
        p.leerConfig(configRutas, configTransportes);
    }

    private void guardar () {
        JSONConfigFileViajes p = new JSONConfigFileViajes();
        p.guardarConfig(configRutas, configTransportes);
    }

    public void agregar (Transporte transporte) {
        if(transportes.containsKey(transporte.mostrarID())) {
            System.out.println("El transporte ya existe.");
            return;
        }
        transportes.put(transporte.mostrarID(), transporte);
    }

    private void eliminar (String ID) {
        actualizar();
        Enumeration<Transporte> enu = Collections.enumeration(transportes.values());

        while (enu.hasMoreElements()) {
            Transporte transporte = enu.nextElement();
            if (ID.equals(transporte.mostrarID())) {
                transportes.remove(transporte.mostrarID(), transporte);
                transportes.remove(transporte.mostrarTipoTransporte(), transporte);
                transportes.remove(transporte.mostrarEmpresa(), transporte);
                transportes.remove(transporte.mostrarHoraPartida(), transporte);
                transportes.remove(transporte.mostrarHoraDestino(), transporte);
                transportes.remove(transporte.mostrarCantDisponible(), transporte);
                System.out.println("Transporte eliminado exitosamente");
                break;
            }
        }
    }

    public void agregarTransporte () {
        actualizar();
        Scanner scan= new Scanner(System.in);
        configRutas.mostrarRutas();
        System.out.println("Ingrese el ID de la ruta: ");
        String IDRuta= scan.nextLine();
        Ruta ruta = configRutas.conseguirInfoRuta(IDRuta);
        if (!ruta.mostrarCiudadPartida().equals("error") && !ruta.mostrarCiudadDestino().equals("error")) {
            String ciudadPartida = ruta.mostrarCiudadPartida();
            String ciudadDestino = ruta.mostrarCiudadDestino();
            System.out.println("Ingrese el tipo de transporte: ");
            String tipoTransporte = scan.nextLine();
            System.out.println("Ingrese la empresa:");
            String empresa = scan.nextLine();
            System.out.println("Ingrese la calidad del servicio: ");
            String calidad = scan.next();
            String ID = tipoTransporte+empresa+calidad;
            System.out.println("Ingrese el dia de partida (dd-mm-yyyy): ");
            int diaP = scan.nextInt();
            int mesP = scan.nextInt();
            int anioP = scan.nextInt();
            System.out.println("Ingrese la hora de partida (hh-mm-ss): ");
            int horasP = scan.nextInt();
            int minutosP = scan.nextInt();
            int segundosP = scan.nextInt();
            LocalDateTime hPartida = LocalDateTime.of(anioP, mesP, diaP, horasP, minutosP, segundosP);
            System.out.println("Ingrese el dia de destino (dd-mm-yyyy): ");
            int diaD = scan.nextInt();
            int mesD = scan.nextInt();
            int anioD = scan.nextInt();
            System.out.println("Ingrese la hora de destino (hh-mm-ss): ");
            int horasD = scan.nextInt();
            int minutosD = scan.nextInt();
            int segundosD = scan.nextInt();
            LocalDateTime hDestino = LocalDateTime.of(anioD, mesD, diaD, horasD, minutosD, segundosD);
            System.out.println("Ingrese la capacidad del transporte: ");
            int cantDisponible = scan.nextInt();
            Transporte transporte = new Transporte(IDRuta, ciudadPartida, ciudadDestino,tipoTransporte, ID, empresa, calidad, hPartida.toString(), hDestino.toString(), cantDisponible);
            agregar(transporte);
            guardar();
        }
    }

    public void mostrarTransportes () {
        actualizar();
        Enumeration<Transporte> enu = Collections.enumeration(transportes.values());
        int i = 1;
        if(transportes.isEmpty()){
            System.out.println("No hay transportes registrados");
        }
        else{
            while (enu.hasMoreElements()) {
                Transporte transporte = enu.nextElement();
                System.out.println(i+". ID de la Ruta: "+transporte.mostrarIDRuta());
                System.out.println(i + ". ID: " + transporte.mostrarID());
                System.out.println(i + ". Tipo: " + transporte.mostrarTipoTransporte());
                System.out.println(i + ". Empresa: " + transporte.mostrarEmpresa());
                System.out.println(i + ". Calidad: " + transporte.mostrarCalidad());
                System.out.println(i + ". Hora de partida: " + transporte.mostrarHoraPartida());
                System.out.println(i + ". Hora de llegada: " + transporte.mostrarHoraDestino());
                System.out.println(i + ". Cantidad disponible: " + transporte.mostrarCantDisponible() + "\n");
                i++;
            }
        }
    }

    public void eliminarRuta () {
        Scanner scan = new Scanner(System.in);
        actualizar();

        mostrarTransportes();
        System.out.println("Ingrese el ID del transporte: ");
        String ID = scan.nextLine();
        eliminar(ID);
        guardar();
    }
    public void reservarTransporte(String UUID){
        Scanner scan= new Scanner(System.in);
        actualizar();
        configRutas.mostrarRutas();
        mostrarTransportes();
        System.out.println("Ingrese el ID del transporte a elegir: ");
        String IDTransporte= scan.nextLine();
        Transporte trans= new Transporte("", "", "", "", IDTransporte, "", "", "", "", 0);
        reservar(trans, UUID);
        guardar();
    }

    public void reservar(Transporte trans, String UUID){
        Enumeration<Transporte> enumT= Collections.enumeration(transportes.values());
        while(enumT.hasMoreElements()){
            Transporte t= enumT.nextElement();
            while(trans.mostrarID().equals(t.mostrarID())){
                if(t.mostrarCantDisponible()>0){
                    t.ocuparLugar();
                    trans=t;
                    transportes.remove(t.mostrarID(), t);
                    transportes.put(trans.mostrarID(), trans);
                    configReservaTransporte.reservaTransporte();
                    System.out.println("Reserva realizada");
                    return;
                }
                else   
                    System.out.println("Transporte lleno. Seleccione otro.");
            }
        }
    }

}
