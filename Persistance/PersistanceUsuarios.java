package Persistance;
import processes.ConfigUsuarios;

public interface PersistanceUsuarios {
    void guardarConfig(ConfigUsuarios config);
    void leerConfig(ConfigUsuarios config);
}
