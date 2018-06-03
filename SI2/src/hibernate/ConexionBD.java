/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hibernate;

import java.util.Calendar;
import java.util.Date;
import model.Trabajador;
import model.Empresa;
import model.Categoria;
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
    
    public  void insertCategoria(Categoria cat){
        
        Query query = session.createQuery("from hibernate.Categorias cate where cate.nombreCategoria = '" + cat.getNombreCategoria() +"'" );
        
        List lista=query.list();
         
        if (lista.isEmpty()) {
            hibernate.Categorias hica = new hibernate.Categorias(cat.getNombreCategoria(), cat.getSalarioBase(), cat.getComplemento());
            hica.setIdCategoria(cat.getIdCategoria());
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
    public  void insertEmpresa(Empresa emp){
        Query query = session.createQuery("from hibernate.Empresas empr where empr.cif = '" + emp.getCIF() +"'" );
        List lista=query.list();
        if (lista.isEmpty()) {
            hibernate.Empresas empra = new hibernate.Empresas(emp.getNombre(), emp.getCIF());
            empra.setIdEmpresa(emp.getIdEmpresa());
            
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
        
        if(tra.getDNI() == null || tra.getDNI().isEmpty()){
            return;
        }
        
        Date date = tra.getFechaAltaEmpresa();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date); 
        int mes = cal.get(Calendar.MONTH) + 1;
        String fecha = cal.get(Calendar.YEAR) + "-" + mes + "-" + cal.get(Calendar.DAY_OF_MONTH);  
                        
        Query query = session.createQuery("from hibernate.Trabajadorbbdd trab where trab.nombre = '" + tra.getNombre() 
                +"' and trab.nifnie = '" + tra.getDNI()+"' and trab.fechaAlta = '" + fecha +"'" );
 
        List lista=query.list();
        
        
        Categoria catTrabajador = tra.getCategoria();
        //Categorias cat = new Categorias(catTrabajador.getNombreCategoria(), catTrabajador.getSalarioBase(), catTrabajador.getComplemento());
        
        Query getCagetoria = session.createQuery("from hibernate.Categorias cate where cate.nombreCategoria = '" + catTrabajador.getNombreCategoria() +"'" );
        Categorias cat = (Categorias) getCagetoria.list().get(0);
            
        Empresa emprTrabajador = tra.getEmpresa();
        Query getEmpresa = session.createQuery("from hibernate.Empresas empr where empr.cif = '" + emprTrabajador.getCIF() +"'" );
        //Empresas empr = new Empresas(emprTrabajador.getNombre(), emprTrabajador.getCIF());
        Empresas empr = (Empresas) getEmpresa.list().get(0);
         
        if (lista.isEmpty()) {
            
            hibernate.Trabajadorbbdd traba = new hibernate.Trabajadorbbdd(cat, empr, tra.getNombre(), tra.getApellido1(),
                    tra.getApellido2(), tra.getDNI(), tra.getCorreo(), tra.getFechaAltaEmpresa(), tra.getCuenta(), tra.getIban(), null);
            traba.setIdTrabajador(tra.getIdTrabajador());
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
            trabajador.setNifnie(tra.getDNI());
            trabajador.setNombre(tra.getNombre());
             
            session.update(trabajador);
            
            tx.commit();
        } 
    }
    
    
    public void insertNomina(model.Nomina nomina){
        
        
        Query getTrabajadores = session.createQuery("from hibernate.Trabajadorbbdd");
        List listaTrabajadores = getTrabajadores.list();
        
        int id = -1;
        for(int i = 0; i<listaTrabajadores.size(); i++){
            Trabajadorbbdd trabajador = (Trabajadorbbdd) listaTrabajadores.get(i);
            
            if(trabajador != null){
                if(nomina.getTrabajador().getDNI().equals(trabajador.getNifnie()))  
                    id = trabajador.getIdTrabajador();
            }
        }
        if(id == -1)
            return;
            //id = nomina.getTrabajador().getIdTrabajador();
        
        System.out.println("ID:" + id);
        //TODO Arreglar liquidoNomina
        Query query = session.createQuery("from hibernate.Nomina nom where nom.mes = '" + nomina.getMes()
                +"' and nom.anio = '" + nomina.getAnio()+"' and nom.trabajadorbbdd = '" + id
                +"' and nom.liquidoNomina = '" + nomina.getLiquidoNomina() +"' and nom.brutoNomina = '" + nomina.getBrutoNomina() + "'");
        
        System.out.println("xd " + query.getQueryString());
        //from hibernate.Nomina nom where nom.mes = '10' and nom.anio = '2010'  and nom.trabajadorbbdd = '2' and nom.liquidoNomina = '1232.6' and nom.brutoNomina = '1411.91'
        
        List lista = query.list();
        
        Trabajador tra = nomina.getTrabajador();
      

        Date date = tra.getFechaAltaEmpresa();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date); 
        int mes = cal.get(Calendar.MONTH) + 1;
        String fecha = cal.get(Calendar.YEAR) + "-" + mes + "-" + cal.get(Calendar.DAY_OF_MONTH); 

        Query getTrabajador = session.createQuery("from hibernate.Trabajadorbbdd trab where trab.nombre = '" + tra.getNombre() 
                +"' and trab.nifnie = '" + tra.getDNI()+"' and trab.fechaAlta = '" + fecha +"'" );
        Trabajadorbbdd trabajador = (Trabajadorbbdd) getTrabajador.list().get(0);
        
        System.out.println(lista.size());
        
        if(lista.isEmpty()){
        hibernate.Nomina hNomina = new hibernate.Nomina(trabajador, nomina.getMes(), nomina.getAnio(), nomina.getNumeroTrienios(), nomina.getImporteTrienios(), 
                nomina.getImporteSalarioMes(), nomina.getImporteComplementoMes(), nomina.getValorProrrateo(), nomina.getBrutoAnual(), nomina.getIRPF(), 
                nomina.getImporteIRPF(), nomina.getBaseEmpresario(), nomina.getSeguridadSocialEmpresario(), nomina.getImporteSeguridadSocialEmpresario(), 
                nomina.getDesempleoEmpresario(), nomina.getImporteDesempleoEmpresario(), nomina.getFormacionEmpresario(), nomina.getImporteFormacionEmpresario(), 
                nomina.getAccidentesTrabajoEmpresario(), nomina.getImporteAccidentesTrabajo(), nomina.getFOGASAEmpresario(), nomina.getImporteFOGASAEmpresario(), 
                nomina.getSeguridadSocialTrabajador(), nomina.getImporteSeguridadSocialTrabajador(), nomina.getDesempleoTrabajador(), nomina.getImporteDesempleoTrabajador(), 
                nomina.getFormacionTrabajador(), nomina.getImporteFormacionTrabajador(), nomina.getBrutoNomina(), nomina.getLiquidoNomina(), nomina.getCosteTotalEmpresario());
          
        session.save(hNomina);
        }else{
            Transaction tx = session.beginTransaction();
            hibernate.Nomina hNomina = (hibernate.Nomina) lista.get(0);
            
            hNomina.setTrabajadorbbdd(trabajador);
            hNomina.setNumeroTrienios(nomina.getNumeroTrienios());
            hNomina.setImporteTrienios(nomina.getImporteTrienios());
                
            hNomina.setImporteIrpf(nomina.getIRPF());
            hNomina.setBaseEmpresario(nomina.getBaseEmpresario());
            hNomina.setSeguridadSocialEmpresario(nomina.getSeguridadSocialEmpresario());
            hNomina.setImporteSeguridadSocialEmpresario(nomina.getImporteSeguridadSocialEmpresario());
            
            hNomina.setDesempleoEmpresario(nomina.getDesempleoEmpresario());
            hNomina.setImporteDesempleoEmpresario(nomina.getImporteDesempleoEmpresario());
            hNomina.setFormacionEmpresario(nomina.getFormacionEmpresario());
            hNomina.setImporteFormacionEmpresario(nomina.getImporteFormacionEmpresario());
            
            hNomina.setAccidentesTrabajoEmpresario(nomina.getAccidentesTrabajoEmpresario());
            hNomina.setImporteAccidentesTrabajoEmpresario(nomina.getImporteAccidentesTrabajo());
            hNomina.setFogasaempresario(nomina.getFOGASAEmpresario());
            hNomina.setImporteFogasaempresario(nomina.getImporteFOGASAEmpresario());
            
            hNomina.setSeguridadSocialTrabajador(nomina.getSeguridadSocialTrabajador());
            hNomina.setImporteSeguridadSocialTrabajador(nomina.getImporteSeguridadSocialTrabajador());
            hNomina.setDesempleoTrabajador(nomina.getDesempleoTrabajador());
            hNomina.setImporteDesempleoTrabajador(nomina.getImporteDesempleoTrabajador());
            
            hNomina.setFormacionTrabajador(nomina.getFormacionTrabajador());
            hNomina.setImporteFormacionTrabajador(nomina.getImporteFormacionTrabajador());
            hNomina.setBrutoNomina(nomina.getBrutoNomina());
            hNomina.setLiquidoNomina(nomina.getLiquidoNomina());
            hNomina.setCosteTotalEmpresario(nomina.getCosteTotalEmpresario());
            
            hNomina.setIrpf(nomina.getIRPF());
            hNomina.setBrutoAnual(nomina.getBrutoAnual());
            hNomina.setValorProrrateo(nomina.getValorProrrateo());
            hNomina.setImporteComplementoMes(nomina.getImporteComplementoMes());
            hNomina.setImporteSalarioMes(nomina.getImporteSalarioMes());
            //TODO comprobar
     
            session.update(hNomina);
            tx.commit();
            
        }
        
    }

    
    
    
}
