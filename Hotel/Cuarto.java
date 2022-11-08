package Hotel;

public class Cuarto {
    private int numero;
    private int piso;
    private boolean ocupado;

    public Cuarto (int numero, int piso, boolean ocupado) {
        this.numero = numero;
        this.piso = piso;
        this.ocupado = ocupado;
    }

    public int mostrarNumero () {
        return numero;
    }

    public int mostrarPiso () {
        return piso;
    }

    public boolean mostarOcupado () {
        return ocupado;
    }
}
