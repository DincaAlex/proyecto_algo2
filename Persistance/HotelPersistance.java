package Persistance;
import processes.ConfigHoteles;

public interface HotelPersistance {
    void guardarConfig(ConfigHoteles config);
    void leerConfig(ConfigHoteles config);
}
