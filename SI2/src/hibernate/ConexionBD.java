/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import SI2.*;
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
    
  
    private static SessionFactory sessionFactory;
    private static Session session;
    
    public ConexionBD(){
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
    }
    
    
    public static void closeSession(){
        session.close();
        sessionFactory.close();
        HibernateUtil.shutdown();
    }
    
    public  void insertCategorias(Categoria cat){
        
        Query query = session.createQuery("from hibernate.Categorias cate where cate.nombreCategoria = '" + cat.getNombreCategoria() +"'" );
        
        List lista=query.list();
         
        if (lista.isEmpty()) {
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
         
        if (lista.isEmpty()) {
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
        
        Query query = session.createQuery("from hibernate.Trabajador trab where trab.nombre = '" + tra.getNombre() 
                +"' and trab.NIFNIE = '" + tra.getDNI()+"' and trab.fechaAlta = '" + tra.getFechaAltaEmpresa()+"'" );
 
        List lista=query.list();
        
        
        Categoria catTrabajador = tra.getCategoria();
        Categorias cat = new Categorias(catTrabajador.getNombreCategoria(), catTrabajador.getSalarioBase(), catTrabajador.getComplemento());
            
        Empresa emprTrabajador = tra.getEmpresa();
        Empresas empr = new Empresas(emprTrabajador.getNombre(), emprTrabajador.getCIF());
         
        if (lista.isEmpty()) {
            
            hibernate.Trabajadorbbdd traba = new hibernate.Trabajadorbbdd(cat, empr, tra.getNombre(), tra.getApellido1(),
                    tra.getApellido2(), tra.getDNI(), tra.getCorreo(), tra.getFechaAltaEmpresa(), tra.getCorreo(), tra.getIban(), null);
            session.save(traba);
        }
        else{
            Transaction tx = session.beginTransaction();

            Trabajadorbbdd trabajador =(Trabajadorbbdd) lista.get(0);
            
            trabajador.setApellido1(tra.getApellido1());
            trabajador.setApellido2(tra.getApellido2());
            trabajador.setCategorias(cat);
            trabajador.setCodigoCuenta(tra.getCuenta());
            trabajador.setEmail(tra.getCorreo());
            trabajador.setEmpresas(empr);
            trabajador.setFechaAlta(tra.getFechaAltaEmpresa());
            trabajador.setIban(tra.getIban());
            trabajador.setIdTrabajador(tra.getId());
            trabajador.setNifnie(tra.getDNI());
            trabajador.setNombre(tra.getNombre());
             
            session.saveOrUpdate(trabajador);
            
            tx.commit();
        } 
    }

    
    
    
}
