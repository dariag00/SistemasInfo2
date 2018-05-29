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
public class Categoria {
    
    String nombreCategoria;
    double salarioBase;
    double complemento;
    

    public Categoria() {
        //
    }

    public Categoria(String nombreCategoria, double salarioBase, double complemento) {
        this.nombreCategoria = nombreCategoria;
        this.salarioBase = salarioBase;
        this.complemento = complemento;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public double getComplemento() {
        return complemento;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public void setComplemento(double complemento) {
        this.complemento = complemento;
    }
    
}
