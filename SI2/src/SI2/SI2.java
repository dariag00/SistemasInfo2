/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SI2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author aluinge
 */

public class SI2 {
    
    private static Document doc, doc2;
    private static XSSFWorkbook workbook;
    private static String nombreArchivo;
    private static XSSFSheet sheet;
    private static ArrayList<String> correos; 
    private static ArrayList<Trabajador> listaTrabajadores;
    private static int nCu;
    private static int nCuMal;
    private static String fecha;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {
        
        System.out.println("Introduce mes y año: (MM/AAAA)");
        Scanner scan = new Scanner(System.in);
        fecha = scan.next();
        
        nombreArchivo="SistemasInformacionII.xlsx";
        nCu=0;
        nCuMal=0;
        listaTrabajadores = new ArrayList<>();
        
        String fileName = "empleados.xml";
        String fileName2 = "cuentas.xml";
        ArrayList sheetData = new ArrayList();
        doc = new Document();
        doc.setRootElement(new Element("Empleados",  Namespace.getNamespace("https://www.journaldev.com/employees")));
        doc2 = new Document();
        doc2.setRootElement(new Element("Cuentas",  Namespace.getNamespace("https://www.journaldev.com/employees")));
        correos=new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(nombreArchivo))){
            workbook = new XSSFWorkbook(file);
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            sheet = workbook.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            while(rows.hasNext()){
                XSSFRow row = (XSSFRow) rows.next();
                ArrayList data = new ArrayList();
                
                for(int i=0; i<row.getLastCellNum(); i++){
                    XSSFCell cell = row.getCell(i);
                    if(cell == null)
                        cell = row.createCell(i);
                    data.add(cell);
                }
                sheetData.add(data);
            }
            file.close(); //Cerramos el archivo
        }catch(IOException e){
            e.printStackTrace();
        }
        showData(sheetData);
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        try{
            xmlOutputter.output(doc, new FileOutputStream(fileName));
            xmlOutputter.output(doc2, new FileOutputStream(fileName2));
        }catch(IOException e) {
            System.err.println("Capturada excepcion!");
        }
        
        for(Trabajador s:listaTrabajadores){
            System.out.println(s.toString());
        }
        //TODO PEDIR POR CONSOLA EL AÃ‘O Y MES DEL QUE SE DESEAN GENERAR LAS NÃ“MINAS
        // DE ESTA FORMA: mm/aaaa
        
        
        //TODO USAR ITEXT 5 PARA HACER PDF DESDE JAVA(ITEXTPDF)
        
        //TODO LAS PAGAS EXTRAS SON "UN MES ANTES DE LO QUE PARECERIA LOGICO"
        // Si entramos el 1 de febrero, generamos 
        
        
        
        
        
        
        
        
    }
    
    private static void showData(List sheetData) throws IOException, ParseException{
        ArrayList<String> listaNifs = new ArrayList<String>();
        for(int i = 1; i< sheetData.size(); i++){
            List list = (List) sheetData.get(i);
            Cell cell = (Cell) list.get(0);      
            //Creamos el objeto trabajador. Lo seteamos en cada if
            Trabajador trab=new Trabajador();
            trab.setId(i);
            
            if(cell.getCellTypeEnum() == CellType.STRING){ //El DNI tiene una letra al final
                String analizedNif = analisamosNif(cell.toString());
                trab.setDNI(analizedNif);

                if(!analizedNif.equals(cell.toString())){ //El DNI tiene la letra que no es
                    write(i,0,analizedNif);
                }
                if(listaNifs.contains(analizedNif)){ //El DNI ya existe
                    addToXML(doc, list, i); //Lo aÃ±adimos al XML
                }
                else{//El DNI estÃ¡ bien, lo aÃ±adimos a la lista
                    listaNifs.add(analizedNif);
                }

            }
            else if(cell.getCellTypeEnum() == CellType.BLANK){ //No hay DNI en el campo
                addToXML(doc, list, i);//Lo aÃ±adimos al XML
            }
            String nombre, apellido1, apellido2, nombreEmpresa;
            Cell cellApe1= (Cell) list.get(1);
            Cell cellApe2= (Cell) list.get(2);
            Cell cellNom= (Cell) list.get(3);
            Cell cellEmpr= (Cell) list.get(6);
            Cell cuenta = (Cell) list.get(14);
            Cell pais = (Cell) list.get(15);
            Cell categoria = (Cell) list.get(5);
            Cell prorrata = (Cell) list.get(13);
            Cell fechaAlta = (Cell) list.get(8);
            
            
            if(cuenta.getCellTypeEnum()==CellType.STRING && pais.getCellTypeEnum()==CellType.STRING){
                //Comprobacion de cuenta
                String cuentaRecalculada=compruebaCuenta(cuenta.toString(),list,i, pais.toString());
                trab.setPais(pais.toString());
                trab.setCuenta(cuentaRecalculada);
                write(i,14,cuentaRecalculada);
                //Calculo de iban
                String ibanCalculado=calculateIBAN(cuentaRecalculada, pais.toString());
                trab.setIban(ibanCalculado);
                write(i,16,ibanCalculado);
            }

            if((cellApe1.getCellTypeEnum()==CellType.STRING)&&(cellNom.getCellTypeEnum()==CellType.STRING)
                    &&(cellEmpr.getCellTypeEnum()==CellType.STRING) && (categoria.getCellTypeEnum()==CellType.STRING)
                    && (prorrata.getCellTypeEnum()==CellType.STRING)){
                if(cellApe2.getCellTypeEnum()!=CellType.STRING){
                    apellido2="";
                }
                else{
                    apellido2=cellApe2.toString();
                }
                apellido1=cellApe1.toString();
                trab.setApellido1(apellido1);
                nombre=cellNom.toString();
                trab.setNombre(nombre);
                nombreEmpresa=cellEmpr.toString();
                trab.setNombreEmpresa(nombreEmpresa);
                trab.setCategoria(categoria.toString());
                String correo=calculaCorreo(nombre, apellido1.toLowerCase(), apellido2.toLowerCase(), nombreEmpresa.toLowerCase());
                trab.setCorreo(correo);
                String prort = prorrata.toString();
                if(prort.equals("NO")){
                    trab.setProrrata(false);
                }else{
                    trab.setProrrata(true);
                }
                trab.setFechaAltaEmpresa(fechaAlta.getDateCellValue());
                trab.calculateTrienios(fecha);
                listaTrabajadores.add(trab);

                write(i, 4, correo);
            }
            
        }
    }
    //FunciÃ³n que comprueba que el nif estÃ© bien
   
    private static String analisamosNif(String nif){
        char letraFinal = 'x';
        if(!Character.isDigit(nif.charAt(0))){
            if(nif.charAt(0) == 'X'){
               nif = "0" + nif.substring(1);
            }
            else if(nif.charAt(0) == 'Y'){
               nif = "1" + nif.substring(1);
            }
            else if(nif.charAt(0) == 'Z'){
               nif = "2" + nif.substring(1);
            }
        }
       
        int dniSinLetra = Integer.valueOf(nif.substring(0, nif.length()-1));
        char letra = nif.charAt(nif.length()-1);
        int resto = dniSinLetra%23;
        switch(resto){
            case 0:
                   letraFinal = 'T';
                   break;
            case 1:
                   letraFinal = 'R';
                   break;
            case 2:
                   letraFinal = 'W';
                   break;  
            case 3:
                   letraFinal = 'A';
                   break;   
            case 4:
                   letraFinal = 'G';
                   break;    
            case 5:
                   letraFinal = 'M';
                   break;    
            case 6:
                   letraFinal = 'Y';
                   break;    
            case 7:
                   letraFinal = 'F';
                   break;    
            case 8:
                   letraFinal = 'E';
                   break;    
            case 9:
                   letraFinal = 'D';
                   break;    
            case 10:
                   letraFinal = 'X';
                   break;    
            case 11:
                   letraFinal = 'B';
                   break;   
            case 12:
                   letraFinal = 'N';
                   break;    
            case 13:
                   letraFinal = 'J';
                   break;    
            case 14:
                   letraFinal = 'Z';
                   break;
            case 15:
                   letraFinal = 'S';
                   break;    
            case 16:
                   letraFinal = 'Q';
                   break;  
            case 17:
                   letraFinal = 'V';
                   break;    
            case 18:
                   letraFinal = 'H';
                   break;    
            case 19:
                   letraFinal = 'L';
                   break;
            case 20:
                   letraFinal = 'C';
                   break;    
            case 21:
                   letraFinal = 'K';
                   break;    
            case 22:
                   letraFinal = 'E';
                   break;    

        }

        return String.valueOf(dniSinLetra) + letraFinal;
       
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
    
    private static String compruebaCuenta(String numeroCuenta, List list, int fila, String pais){
        nCu++;
        
        String cuentaOriginal = numeroCuenta;
        //Comprobamos que la cuenta tenga la longitud que debe. De no ser asÃ­, es errÃ³nea. 
        if(cuentaOriginal.length()!=20){
            System.err.println("La cuenta de la fila "+nCu+"es errÃ³nea, no tiene 20 carÃ¡cteres");
            return cuentaOriginal;
        }
        
        String digitoControl1=String.valueOf(numeroCuenta.charAt(8));
        String digitoControl2=String.valueOf(numeroCuenta.charAt(9));
        double calcD1;
        double calcD2;
        String oficina = numeroCuenta.substring(4,8);
        String entidad = numeroCuenta.substring(0,4);
        String cuenta = numeroCuenta.substring(10,20);
        
        String numeroTotal = numeroCuenta.substring(0,8);
        ArrayList<Integer> numeros = new ArrayList<Integer>();
        numeros.add(0);
        numeros.add(0);
        for(int i = 0; i< 8; i++){
            //Corregimos el error de Integer.valueOf restando 48 al numero obtenido
            numeros.add(Integer.valueOf(numeroTotal.charAt(i))-48);
        }
        
        double x = 0;
        double sumatorio = 0;
        ArrayList<Double> numerosPow2 = new ArrayList<Double>();
        for(int i : numeros){
            x = (Math.pow(2, i))%11;
            numerosPow2.add(x);
        }
        for(double i: numerosPow2){
            sumatorio=sumatorio+i;
        }
        calcD1 = sumatorio % 11;
        //Solo queremos un nÃºmero, en caso de que salga resto 10 o 11 hacemos lo siguiente
        if(calcD1 == 10){
            calcD1 = 1;
        }else if(calcD1 == 11){
            calcD1 = 0;
        }
        
        //calculo del 2do digito
        numeros.clear();
        for(int i = 10; i<numeroCuenta.length(); i++){
            numeros.add(Integer.valueOf(numeroCuenta.charAt(i)-48));
        }
        x = 0;
        sumatorio = 0;
        ArrayList<Double> numerosPow2v2 = new ArrayList<Double>();
        for(int i : numeros){
            x = (Math.pow(2, i)%11);
            numerosPow2v2.add(x);
        }
        for(double i : numerosPow2v2){
            sumatorio=sumatorio+i;
        }
        calcD2 = sumatorio % 11;
        //Solo queremos un nÃºmero, en caso de que salga resto 10 o 11 hacemos lo siguiente
        if(calcD2 == 10){
            calcD2 = 1;
        }else if(calcD1 == 11){
            calcD2 = 0;
        }
        //Ahora, comprobamos si el numero de cuenta es errÃ³neo
        int digito1Recalculado=(int)calcD1;
        int digito2Recalculado=(int)calcD2;
        
        if(digito1Recalculado!=Integer.parseInt(digitoControl1)||digito2Recalculado!=Integer.parseInt(digitoControl2)){
            //Si estÃ¡ mal, devuelve el nÃºmero corregido
            String cuentaCorregida=entidad+oficina+String.valueOf(digito1Recalculado)+String.valueOf(digito2Recalculado)+cuenta;
            nCuMal++;
            //Escribe el XML
            addToXMLCuentas(doc2, list, fila, calculateIBAN(cuentaCorregida, pais), cuentaOriginal);
            return cuentaCorregida;
        }
        else{ //Si estÃ¡ bien, devuelve la original
            
            return cuentaOriginal;
        }
        
       
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
    
    
    //FunciÃ³n que aÃ±ade errores al XML 'Trabajadores'
    private static void addToXML(Document doc, List info, int id){
        Element employee = new Element("Empleados");
        employee.setAttribute("id",""+id);
        employee.addContent(new Element("NIF").setText(""+info.get(0)));
        employee.addContent(new Element("Apellidos").setText(info.get(1) + " " + info.get(2)));
        employee.addContent(new Element("Nombre").setText("" + info.get(3)));
        doc.getRootElement().addContent(employee);
    }
    //FunciÃ³n que aÃ±ade errores al XML 'Cuentas'
    private static void addToXMLCuentas(Document doc, List info, int id, String iban, String cuenta){
        Element employee = new Element("Trabajadores");
        employee.setAttribute("id",""+id);
        employee.addContent(new Element("NIF").setText(""+info.get(0)));
        employee.addContent(new Element("Apellidos").setText(info.get(1) + " " + info.get(2)));
        employee.addContent(new Element("Nombre").setText("" + info.get(3)));
        employee.addContent(new Element("Empresa").setText("" + info.get(6)));
        employee.addContent(new Element("Cuenta-erronea").setText(cuenta));
        employee.addContent(new Element("IBAN").setText(iban));
        doc.getRootElement().addContent(employee);
    }
    //FunciÃ³n que escribe en el excel (Fila, columna, texto)
    private static void write(int fila, int columna, String text){
        if(fila<0 || columna<0){
           System.err.println("Columna o fila incorrecta");
           return ;
        }
        
        Cell celda = sheet.getRow(fila).getCell(columna);
        celda.setCellValue(text);
        FileOutputStream output_file;
        try {
            output_file = new FileOutputStream(new File(nombreArchivo)); //Abrimos el archivo
            workbook.write(output_file);
            output_file.close();//Cerramos el archivo 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SI2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            System.err.println("Error");
        } 
    }
}
