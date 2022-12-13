package Persistance;
import processes.ConfigHoteles;

public interface PersistanceHoteles {
    void guardarConfig(ConfigHoteles config);
    void leerConfig(ConfigHoteles config);
}
