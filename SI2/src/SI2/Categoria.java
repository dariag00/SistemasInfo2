package SI2;

import hibernate.HibernateUtil;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sun.security.pkcs11.wrapper.Functions;

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
    int idCategoria;

  

    public Categoria() {
        //
    }

    public Categoria(int idCategoria, String nombreCategoria, double salarioBase, double complemento) {
        this.nombreCategoria = nombreCategoria;
        this.salarioBase = salarioBase;
        this.complemento = complemento;
        this.idCategoria= idCategoria;
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
      public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "Categoria{" + "nombreCategoria=" + nombreCategoria + ", salarioBase=" + salarioBase + ", complemento=" + complemento + ", idCategoria=" + idCategoria + '}';
    }
    
    public void toHibernate(){
        //QUERY
        //
        SessionFactory factory1=HibernateUtil.getSessionFactory();
        Session session = factory1.openSession();
        Query query = session.createQuery("from Categorias cat where cat.nombreCategoria = :name");
        query.setString("name",this.nombreCategoria );
        List lista=query.list();
        if(lista.isEmpty()){
            hibernate.Categorias hica = new hibernate.Categorias(this.nombreCategoria, this.salarioBase, this.complemento);
            session.save(hica);
        }
       
        
    }
    
}
