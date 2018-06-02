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

            hibernate.Categorias hCateg=(hibernate.Categorias) lista.get(0);
            hCateg.setNombreCategoria(cat.getNombreCategoria());
            hCateg.setSalarioBaseCategoria(cat.getSalarioBase());
            hCateg.setComplementoCategoria(cat.getComplemento());
            session.saveOrUpdate(hCateg);
            
            tx.commit();
        } 
    }
    public  void insertEmpresas(Empresa emp){
        
        Query query = session.createQuery("from hibernate.Empresas empr where empr.CIF = '" + emp.getCIF() +"'" );
        
        List lista=query.list();
         
        if (lista.size()==0) {
            hibernate.Empresas empra = new hibernate.Empresas(emp.getNombre(), emp.getCIF());
            session.save(empra);
        }
        else{
            Transaction tx = session.beginTransaction();

            hibernate.Empresas hEmp=(hibernate.Empresas) lista.get(0);
            hEmp.setCif(emp.getCIF());
            hEmp.setNombre(emp.getNombre());
            session.saveOrUpdate(hEmp);
            
            tx.commit();
        } 
    }
    public  void insertTrabajador(Trabajador tra){
        
        if(tra.getDNI().equals("")||tra.getDNI().isEmpty()){
            return;
        }
        
        Query query = session.createQuery("from hibernate.Trabajador trab where trab.nombre = '" + tra.getNombre() +"'" );
        Query query2 = session.createQuery("from hibernate.Trabajador trab where trab.NIFNIE = '" + tra.getDNI()+"'" );
        Query query3 = session.createQuery("from hibernate.Trabajador trab where trab.fechaAlta = '" + tra.getFechaAltaEmpresa()+"'" );
        
        List lista=query.list();
        List lista2=query2.list();
        List lista3=query3.list();
         
        if (lista.size()==0 && lista2.size()==0 && lista3.size()==0) {
            
            hibernate.Trabajadorbbdd traba = new hibernate.Trabajadorbbdd(tra.getCategoria(), tra.getEmpresa(), tra.getNombre(), tra.getApellido1(),
                    tra.getApellido2(), tra.getDNI(), tra.getCorreo(), tra.getFechaAltaEmpresa(), tra.getCorreo(), tra.getIban(), "");
            session.save(traba);
        }
        else{
            Transaction tx = session.beginTransaction();

            hibernate.Empresas hEmp=(hibernate.Empresas) lista.get(0);
            hEmp.setCif(emp.getCIF());
            hEmp.setNombre(emp.getNombre());
            session.saveOrUpdate(hEmp);
            
            tx.commit();
        } 
    }

    
    
    
}
