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
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String DNI;
    private String cuenta;
    private float salario;
    private float antiguedad;
    private float prorrateo;
    private float descuentos;
    private float contingencias;
    private float desempleo;
    private float formacion;
    private float irpf;
    private float pagos;
    private float retencion;
    private float pagosEmpresario;
    private float porcIRPF;
    private Date fechaAltaEmpresa;
    private boolean prorrata;
    private String pais;
    private String correo;
    private String iban;
    private int id;
    private Empresa empresa;
    private Categoria categoria;
    private int trienios;
    
    private static ArrayList<String> correos; 
    
    public Trabajador(){
        this.apellido2 = "";
        
         correos=new ArrayList<>();
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    public Empresa getEmpresa(){
        return empresa;
    }
    
    public void setEmpresa(Empresa empresa){
        this.empresa = empresa;
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
    public float getRetencion(){
        return retencion;
    }
    public void setRetencion(float retencion){
        this.retencion=retencion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }
   
    
    public float getProrrateo(){
        return prorrateo;
    }
    
    public void setProrrateo(float prorrateo){
        this.prorrateo=prorrateo;
        
    }
    public float getDescuentos(){
        return descuentos;
    }
    
    public void setDescuentos(float descuentos){
        this.descuentos=descuentos;
        
    }
    public float getPagos(){
        return pagos;
    }
    
    public void setPagos(float pagos){
        this.pagos=pagos;
        
    }

    public Date getFechaAltaEmpresa() {
        return fechaAltaEmpresa;
    }

    public void setFechaAltaEmpresa(Date fechaAltaEmpresa) {
        this.fechaAltaEmpresa = fechaAltaEmpresa;
        
        
    }

    public boolean isProrrata() {
        return prorrata;
    }

    public void setProrrata(boolean prorrata) {
        this.prorrata = prorrata;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
    public int getTrienios(){
        return this.trienios;
    }
    public void setAntiguedad(float ant){
        this.antiguedad=ant;
    }
    public float getAntiguedad(){
        return this.antiguedad;
    }
    public boolean hasProrrateo(){
        return this.prorrata;
    }
    
    public float getPagosEmpresario(){
        return this.pagosEmpresario;
    }
    public void setPagosEmpresario(float pagosEmpresario){
        this.pagosEmpresario=pagosEmpresario;
    }
    public void setContingencias(float contingencias){
        this.contingencias=contingencias;
    }
    public void setIRPF(float IRPF){
        this.irpf=IRPF;
    }
    public void setDesempleo(float desempleo){
        this.desempleo=desempleo;
    }
    public void setFormacion(float formacion){
        this.formacion=formacion;
    }
    public float getFormacion(){
        return formacion;
    }
    public float getDesempleo(){
        return desempleo;
    }
    public float getIRPF(){
        return irpf;
    }
    public float getContingencias(){
        return contingencias;
    }
    public float getPorcIRPF(){
        return porcIRPF;
    }
    public void setPorcIRPF(float porcIRPF){
        this.porcIRPF=porcIRPF;
    }
    
    
    public void calculateTrienios(String fecha) throws ParseException{
        
        Date today = new SimpleDateFormat("mm/yyyy").parse(fecha);
        //System.out.println(today.toString() + contratado.toString());
        long milliseconds = today.getTime() - fechaAltaEmpresa.getTime();
        double division =   milliseconds / 94608;
        int trie=(int) Math.floor(division/1000000);
        if(trie<=0)
            this.trienios=0;
        else
            this.trienios=trie;
    }   
    
    public String toString(){
        StringBuilder builder = new StringBuilder();
        
        
        builder.append("IBAN: " + getIban()+ "\n");
        builder.append("Categoria: " + getCategoria()+ "\n");
        builder.append("Fecha Alta: " + getFechaAltaEmpresa()+ "\n");
        builder.append("----------------------------------\n");
        
        builder.append(getDNI()+ "\n");
        builder.append(getNombre()+ " " + getApellido1() + " " + getApellido2() + "\n");            
        builder.append("\n");
        
        return builder.toString();
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
    
}


    
    
    


    

