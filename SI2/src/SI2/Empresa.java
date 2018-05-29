package SI2;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dariag00
 */
public class Empresa {
    
    String direccion;
    String CIF;
    String nombre;
    
    
    private static ArrayList<String> correos; 
    
    
    
    
    public Empresa(){
         correos=new ArrayList<>();
    }

    public Empresa(String CIF, String nombre) {
        this.CIF = CIF;
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCIF() {
        return CIF;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCIF(String CIF) {
        this.CIF = CIF;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Empresa{" + "direccion=" + direccion + ", CIF=" + CIF + ", nombre=" + nombre + '}';
    }
    
    
    
    
    
    private static String calculateIBAN(String numeroCuenta, String pais){
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
    private static int calculateCountryNum(char letra){
        
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
    
    private static String calculaCorreo(String nombre, String apellido1, String apellido2, String nombreEmpresa){
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
