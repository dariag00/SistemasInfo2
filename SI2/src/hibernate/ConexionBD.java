/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import SI2.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Mark
 */


public class ConexionBD {
    
  
    private static HibernateUtil util;
    private static SessionFactory sessionFactory;
    private static Session session;
    
    public ConexionBD(){
        util = new HibernateUtil();
        sessionFactory = util.getSessionFactory();
        session = sessionFactory.openSession();
    }
    
    
    public static void closeSession(){
        session.close();
        sessionFactory.close();
        util.shutdown();
    }
    
    public  void insertCategorias(Categoria cat){
        
        Query query = session.createQuery("from hibernate.Categorias cate where cate.nombreCategoria = '" + cat.getNombreCategoria() +"'" );
        
        List lista=query.list();
         
        if (lista.size()==0) {
            hibernate.Categorias hica = new hibernate.Categorias(cat.getNombreCategoria(), cat.getSalarioBase(), cat.getComplemento());
            session.save(hica);
        }
        else{
            Transaction tx = session.beginTransaction();

            System.err.println("Hola he entrado en el else: "+cat.getSalarioBase());
            hibernate.Categorias hCateg=(hibernate.Categorias) lista.get(0);
            hCateg.setNombreCategoria(cat.getNombreCategoria());
            hCateg.setSalarioBaseCategoria(cat.getSalarioBase());
            hCateg.setComplementoCategoria(cat.getComplemento());
            session.saveOrUpdate(hCateg);
            
            tx.commit();
        } 
    }

    
    
    
}
