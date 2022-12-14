package Persistence;

public interface Persistence<T,V> {
    void guardarConfig(T args1, V args2);
    void leerConfig(T args1, V args2);
}
