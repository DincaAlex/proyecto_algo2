package Persistance;
import processes.SistemaConfig;

public interface Persistance {
    public void guardarConfig(SistemaConfig config);
    public void leerConfig(SistemaConfig config);
}
