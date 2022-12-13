package Persistence;

import processes.ConfigViajes;

public interface PersistenceViajes {
    void guardarConfig(ConfigViajes config);
    void leerConfig(ConfigViajes config);
}
