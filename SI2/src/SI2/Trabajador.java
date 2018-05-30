/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SI2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import java.util.ArrayList;

/**
 *
 * @author Mark
 */


public class Trabajador {
    
    private int idTrabajador;

    
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String DNI;
    private String correo;
    private Date fechaAltaEmpresa;
    private String cuenta;
    private String iban;
    private int idEmpresa;
    private int idCategoria;
    
    private boolean prorrateo;

    public boolean isProrrateo() {
        return prorrateo;
    }

    public void setProrrateo(boolean prorrateo) {
        this.prorrateo = prorrateo;
    }

    public int getnTrienios() {
        return nTrienios;
    }

    public void setnTrienios(int nTrienios) {
        this.nTrienios = nTrienios;
    }
    
    private int nTrienios;

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public static ArrayList<String> getCorreos() {
        return correos;
    }

    public static void setCorreos(ArrayList<String> correos) {
        Trabajador.correos = correos;
    }

    private static ArrayList<String> correos; 
    
    
    public Trabajador(){
        this.apellido2 = "";
        
         correos=new ArrayList<>();
    }
    
    public void setId(int id){
        this.idTrabajador = id;
    }
    
    public int getId(){
        return idTrabajador;
    }
    
    public int getIdEmpresa(){
        return idEmpresa;
    }
    
    public void setEmpresa(int idEmpresa){
        this.idEmpresa = idEmpresa;
    }
    
    public String getCorreo(){
        return correo;
    }
    
    public void setCorreo(String correo){
        this.correo = correo;
    }
    
    public void setIban(String iban){
        this.iban = iban;
    }
    
    public String getIban(){
        return iban;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Date getFechaAltaEmpresa() {
        return fechaAltaEmpresa;
    }

    public void setFechaAltaEmpresa(Date fechaAltaEmpresa) {
        this.fechaAltaEmpresa = fechaAltaEmpresa;
        
        
    }

    
    
    public void calculateTrienios(String fecha) throws ParseException{
        
        Date today = new SimpleDateFormat("mm/yyyy").parse(fecha);
        //System.out.println(today.toString() + contratado.toString());
        long milliseconds = today.getTime() - fechaAltaEmpresa.getTime();
        double division =   milliseconds / 94608;
        int trie=(int) Math.floor(division/1000000);
        if(trie<=0)
            this.nTrienios=0;
        else
            this.nTrienios=trie;
    }   
    
  
    
    
    
    public String calculateIBAN(String numeroCuenta, String pais){
        int num1 = calculateCountryNum(pais.charAt(0));
        int num2 = calculateCountryNum(pais.charAt(1));
        String aux = numeroCuenta;
        numeroCuenta = numeroCuenta + String.valueOf(num1) + String.valueOf(num2) + "0" + "0";
        double numTotal = Double.parseDouble(numeroCuenta);
        int fff =(int) numTotal;
        int division97 =fff%97;
        String iban = pais;
        if(division97 < 10){
            iban = iban + "0";
        }
        
        iban = iban + division97;
        return iban + aux;
    }
    private int calculateCountryNum(char letra){
        
        int num;
        switch(letra){
            case 'A':
                    num = 10;
                    break;
         
            case 'B':
                    num = 11;
                    break;
            
            case 'C':
                    num = 12;
                    break;
          
            case 'D':
                    num = 13;
                    break;
           
            case 'E':
                    num = 14;
                    break;
           
            case 'F':
                    num = 15;
                    break;
       
            case 'G':
                    num = 16;
                    break;
         
            case 'H':
                    num = 17;
                    break;
         
            case 'I':
                    num = 18;
                    break;
     
            case 'J':
                    num = 19;
                    break;
          
            case 'K':
                    num = 20;
                    break;
        
            case 'L':
                    num = 21;
                    break;
          
            case 'M':
                    num = 22;
                    break;
         
            case 'N':
                    num = 23;
                    break;
          
            case 'O':
                    num = 24;
                    break;
         
            case 'P':
                    num = 25;
                    break;
        
            case 'Q':
                    num = 26;
                    break;
     
            case 'R':
                    num = 27;
                    break;
           
            case 'S':
                    num = 28;
                    break;
     
            case 'T':
                    num = 29;
                    break;
            
            case 'U':
                    num = 30;
                    break;
          
            case 'V':
                    num = 31;
                    break;
           
            case 'W':
                    num = 32;
                    break;
        
            case 'X':
                    num = 33;
                    break;
         
            case 'Y':
                    num = 34;
                    break;
     
            case 'Z':
                    num = 35;
                    break;
                    
            default:
                    num = -1;
     
        }
        
        return num;
    }
    
    public String calculaCorreo(String nombre, String apellido1, String apellido2, String nombreEmpresa){
        String correo=nombre.substring(0,3)+apellido1.substring(0,2);
        if(apellido2!=""){
            correo=correo+apellido2.substring(0,2);
        }
        int i=0;
        for(String s: correos){
            if (s.equals(correo)){
                i++;
            }
        }
        correos.add(correo);
        if(i<10){
            correo=correo+0+i;
        }
        else{
            correo=correo+i;
        }
        
        return correo+"@"+nombreEmpresa+".es";
        

    }
    @Override
    public String toString() {
        return "Trabajador{" + "idTrabajador=" + idTrabajador + ", nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", DNI=" + DNI + ", correo=" + correo + ", fechaAltaEmpresa=" + fechaAltaEmpresa + ", cuenta=" + cuenta + ", iban=" + iban + ", idEmpresa=" + idEmpresa + ", idCategoria=" + idCategoria + ", prorrateo=" + prorrateo + ", nTrienios=" + nTrienios + '}';
    }
}


    
    
    


    

