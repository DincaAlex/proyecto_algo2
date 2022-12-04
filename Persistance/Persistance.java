package Persistance;
import processes.ConfigUsers;

public interface Persistance {
    void guardarConfig(ConfigUsers config);
    void leerConfig(ConfigUsers config);
}
