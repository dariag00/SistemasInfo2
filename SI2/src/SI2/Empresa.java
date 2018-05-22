package SI2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dariag00
 */
public class Empresa {
    
    String direccion;
    String CIF;
    String nombre;
    
    public Empresa(){
        
    }

    public Empresa(String CIF, String nombre) {
        this.CIF = CIF;
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCIF() {
        return CIF;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Empresa{" + "direccion=" + direccion + ", CIF=" + CIF + ", nombre=" + nombre + '}';
    }
    
    
    
}
