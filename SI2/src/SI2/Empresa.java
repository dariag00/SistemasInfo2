package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Empresa {
    
    String CIF;
    String nombre;
    int idEmpresa;
   
    
    
    
    public Empresa(){
    }

    public Empresa(String CIF, String nombre) {
        this.CIF = CIF;
        this.nombre = nombre;
    }

    

    public String getCIF() {
        return CIF;
    }

    public String getNombre() {
        return nombre;
    }


    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
     public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public String toString() {
        return "Empresa{" +"CIF=" + CIF + ", nombre=" + nombre + ", idEmpresa=" + idEmpresa + '}';
    }
    
    
    
}
    
    
    
    