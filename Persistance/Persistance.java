package Persistance;
import processes.SistemaConfig;

public interface Persistance {
    void guardarConfig(SistemaConfig config);
    void leerConfig(SistemaConfig config);
}
