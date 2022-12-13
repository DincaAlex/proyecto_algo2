package Persistence;
import processes.ConfigUsuarios;

public interface PersistenceUsuarios {
    void guardarConfig(ConfigUsuarios config);
    void leerConfig(ConfigUsuarios config);
}
