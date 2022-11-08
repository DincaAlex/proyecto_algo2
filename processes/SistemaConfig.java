package processes;

import java.util.Enumeration;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import entities.*;
public class SistemaConfig 
{
    private Vector<Admin> admins;
    private Vector<Cliente> clientes;

    public SistemaConfig()
    {
        this.admins= new Vector<Admin>();
        this.clientes= new Vector<Cliente>();
    }
    

    public void registrarAdmin(Admin admin)
    {
        Enumeration<Admin> adm= this.admins.elements();
        while(adm.hasMoreElements())
        {
            Admin a= adm.nextElement();
            if(admin.mostrarCorreo().equals(a.mostrarCorreo()))
            {
                System.out.println("Correo ya utilizado con anterioridad, no se ha creado una nueva cuenta de administrador.");
                return;
            }
        }
        this.admins.add((Admin) admin);
        System.out.println("Cuenta de administrador creada con exito.");   
    }

    public void registrarCliente(Cliente cliente)
    {
        Enumeration<Cliente> cl= this.clientes.elements();
        while(cl.hasMoreElements())
        {
            Cliente c= cl.nextElement();
            if(cliente.mostrarCorreo().equals(c.mostrarCorreo()))
            {
                System.out.println("Correo ya utilizado con anterioridad, no se ha creado una nueva cuenta.");
                return;
            }
        }
        this.clientes.add((Cliente) cliente);
        System.out.println("Cuenta de cliente creada con exito.");
        
    }

    public boolean confirmarIngresoAdmin(String correo,String contrasena)
    {
        Enumeration <Admin> adm= this.admins.elements();
        while(adm.hasMoreElements())
        {
            Admin a= adm.nextElement();
            if(correo.equals(a.mostrarCorreo()))
            {
                if(contrasena.equals(a.mostrarContrasena()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public JSONArray adminsToJSON(){
        JSONArray arrayAdmins = new JSONArray();
        Enumeration<Admin> adm = this.admins.elements();
        while(adm.hasMoreElements()) {
            Admin a = adm.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("correo", a.mostrarCorreo());
            obj.put("nombres", a.mostrarNombres());
            obj.put("apellidos", a.mostrarApellidos());
            obj.put("contrasena", a.mostrarContrasena());
            arrayAdmins.add(obj);
        }
        return  arrayAdmins;
    }

    public JSONArray clientesToJSON(){
        JSONArray arrayCliente = new JSONArray();
        Enumeration<Cliente> cl = this.clientes.elements();
        while(cl.hasMoreElements()) {
            Cliente c = cl.nextElement();
            JSONObject obj = new JSONObject();
            obj.put("correo", c.mostrarCorreo());
            obj.put("nombres", c.mostrarNombres());
            obj.put("apellidos", c.mostrarApellidos());
            obj.put("contrasena", c.mostrarContrasena());
            arrayCliente.add(obj);
        }
        return  arrayCliente;
    }
}
