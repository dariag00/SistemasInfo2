/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SI2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mark
 */

public class Trabajador {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String DNI;
    private String nombreEmpresa;
    private String cifEmpresa;
    private String categoria;
    private String cuenta;
    private float salario;
    private Date fechaAltaEmpresa;
    private int horasExtrasForzadas;
    private int horasExtrasVoluntarias;
    private boolean prorrata;
    private String pais;
    private String correo;
    private String iban;
    private int id;
    private int trienios;
    
    public Trabajador(){
        
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
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

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getCifEmpresa() {
        return cifEmpresa;
    }

    public void setCifEmpresa(String cifEmpresa) {
        this.cifEmpresa = cifEmpresa;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
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

    public Date getFechaAltaEmpresa() {
        return fechaAltaEmpresa;
    }

    public void setFechaAltaEmpresa(Date fechaAltaEmpresa) {
        this.fechaAltaEmpresa = fechaAltaEmpresa;
        
        
    }

    public int getHorasExtrasForzadas() {
        return horasExtrasForzadas;
    }

    public void setHorasExtrasForzadas(int horasExtrasForzadas) {
        this.horasExtrasForzadas = horasExtrasForzadas;
    }

    public int getHorasExtrasVoluntarias() {
        return horasExtrasVoluntarias;
    }

    public void setHorasExtrasVoluntarias(int horasExtrasVoluntarias) {
        this.horasExtrasVoluntarias = horasExtrasVoluntarias;
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
    
    public int calculateSalary(){
        return 0;
    }
    
    public void calculateTrienios(String fecha) throws ParseException{
        
        Date today = new SimpleDateFormat("mm/yyyy").parse(fecha);
        //System.out.println(today.toString() + contratado.toString());
        long milliseconds = today.getTime() - fechaAltaEmpresa.getTime();
        double division =   milliseconds / 94608;
        this.trienios = (int) Math.floor(division/1000000);
    }
    
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("ID: " + id + " Nombre:" + nombre + " Apellidos: " + apellido1 + " " + apellido2);
        builder.append(" DNI: " + DNI);
        builder.append(" Categoria " + categoria);
        builder.append(" FA: " + fechaAltaEmpresa + " Prorrata " + prorrata);
        builder.append(" TR: " + this.trienios);
        
        return builder.toString();
    }
    
    
    


    
}
