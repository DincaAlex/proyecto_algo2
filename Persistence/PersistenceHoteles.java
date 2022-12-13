package Persistence;
import processes.ConfigHoteles;

public interface PersistenceHoteles {
    void guardarConfig(ConfigHoteles config);
    void leerConfig(ConfigHoteles config);
}
