package proyecto;

import java.util.Scanner;

public class AgenciaViajes{
    public static void main(String []args){
        System.out.println("BIENVENIDO AL SISTEMA DE AGENCIA DE VIAJES");
        System.out.println("Desea ingresar como usuario (1) o administrador (2): ");
        Scanner t= new Scanner(System.in);
        int tipoIngreso= t.nextInt();
        switch(tipoIngreso){
            case 1: Ruta a1= new Ruta("", "", "", ""); 
            a1.menuReserva(); break;
            case 2: ; break;
            default: System.out.println("Ingrese una opcion valida");
        }
    }
}

