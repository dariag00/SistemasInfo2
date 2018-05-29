/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SI2;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Float.parseFloat;
import java.text.ParseException;
import java.util.ArrayList;
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
    private static XSSFSheet sheet2;
    
    private static ArrayList<String> correos; 
    private static ArrayList<Trabajador> listaTrabajadores;
    private static int nCu;
    private static int nCuMal;
    private static String fecha;
    private static ArrayList<Empresa> listaEmpresas;
    private static ArrayList<Categoria> listaCategorias;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException, DocumentException {
        
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
        ArrayList sheetData2 = new ArrayList();
        doc = new Document();
        doc.setRootElement(new Element("Empleados",  Namespace.getNamespace("https://www.journaldev.com/employees")));
        doc2 = new Document();
        doc2.setRootElement(new Element("Cuentas",  Namespace.getNamespace("https://www.journaldev.com/employees")));
        correos=new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(nombreArchivo))){
            workbook = new XSSFWorkbook(file);
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            sheet = workbook.getSheetAt(0);
            sheet2 = workbook.getSheetAt(1);
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
            Iterator rows2=sheet2.rowIterator();
            while(rows2.hasNext()){
                XSSFRow row2 = (XSSFRow) rows2.next();
                ArrayList data2 = new ArrayList();
                
                for(int i =0;i<row2.getLastCellNum();i++){
                    XSSFCell cell2 = row2.getCell(i);
                    if(cell2==null)
                        cell2=row2.createCell(i);
                    data2.add(cell2);
                }
                sheetData2.add(data2);
            }
            file.close(); //Cerramos el archivo
        }catch(IOException e){
            e.printStackTrace();
        }
        showData(sheetData,sheetData2);
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        try{
            xmlOutputter.output(doc, new FileOutputStream(fileName));
            xmlOutputter.output(doc2, new FileOutputStream(fileName2));
        }catch(IOException e) {
            System.err.println("Capturada excepcion!");
        }
        
        createPdf("pruebaProrrata.pdf", listaTrabajadores.get(2));
        createPdf("pruebaSinProra.pdf", listaTrabajadores.get(6));
        //TODO PEDIR POR CONSOLA EL AÃ‘O Y MES DEL QUE SE DESEAN GENERAR LAS NÃ“MINAS
        // DE ESTA FORMA: mm/aaaa
        
        
        //TODO USAR ITEXT 5 PARA HACER PDF DESDE JAVA(ITEXTPDF)
        
        //TODO LAS PAGAS EXTRAS SON "UN MES ANTES DE LO QUE PARECERIA LOGICO"
        // Si entramos el 1 de febrero, generamos 
        
        
        
        
        
        
        
        
    }
    
    private static void showData(List sheetData, List sheetData2) throws IOException, ParseException{
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
            String nombre, apellido1, apellido2, nombreEmpresa, cifEmpresa;
            Cell cellApe1= (Cell) list.get(1);
            Cell cellApe2= (Cell) list.get(2);
            Cell cellNom= (Cell) list.get(3);
            Cell cellEmpr= (Cell) list.get(6);
            Cell cuenta = (Cell) list.get(14);
            Cell pais = (Cell) list.get(15);
            Cell cellCategoria = (Cell) list.get(5);
            Cell prorrata = (Cell) list.get(13);
            Cell fechaAlta = (Cell) list.get(8);
            Cell cellCifEmpr = (Cell) list.get(7);
            
            
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
                    &&(cellEmpr.getCellTypeEnum()==CellType.STRING) && (cellCategoria.getCellTypeEnum()==CellType.STRING)
                    && (prorrata.getCellTypeEnum()==CellType.STRING)){
                if(cellApe2.getCellTypeEnum()!=CellType.STRING){
                    apellido2="";
                }
                else{
                    apellido2=cellApe2.toString();
                }
                                
                nombreEmpresa=cellEmpr.toString();
                cifEmpresa = cellCifEmpr.toString();
                Empresa empr = new Empresa(nombreEmpresa, cifEmpresa);
                //TODO añadir direccion
                for(Empresa empresa:listaEmpresas){
                    if(!empresa.getNombre().equals(nombreEmpresa)){
                        listaEmpresas.add(new Empresa(nombreEmpresa, cifEmpresa));
                    }
                }
                trab.setEmpresa(empr);
                
                Categoria categoria = new Categoria(cellCategoria.toString(), calculaBrutoAnual(categoria.toString(), sheetData2), 
                        calculaComplementos(categoria.toString(), sheetData2));
                for(Categoria cat : listaCategorias){
                    if(!cat.getNombreCategoria().equals(categoria)){
                        listaCategorias.add(categoria);
                    }
                }
                trab.setCategoria(categoria);
                
                apellido1=cellApe1.toString();
                trab.setApellido1(apellido1);
                nombre=cellNom.toString();
                trab.setNombre(nombre);
                
  
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
                //TODO Aquí empece a poner cosas y crear métodos
                trab.setSalario(calculaSalario(categoria.toString(), sheetData2));
              
                trab.setAntiguedad(calculaCompTrienio(trab.getTrienios(), sheetData2));
                float prorrat=calcularProrrateo(trab.hasProrrateo(), trab.getSalario(), trab.getComplementos(), trab.getAntiguedad());
                trab.setProrrateo(prorrat);
                /*
                trab.setDescuentos(calcularDescuentos(trab.getBrutoAnual()));
                trab.setContingencias(calcularContingencias(trab.getBrutoAnual()));
                trab.setDesempleo(calcularDesempleo(trab.getBrutoAnual()));
                trab.setIRPF(calcularIRPF(trab.getBrutoAnual()));
                trab.setPorcIRPF(calcularRetencion(trab.getBrutoAnual(),sheetData2));
                trab.setFormacion(calcularFormacion(trab.getBrutoAnual()));
                trab.setPagosEmpresario(calcularPagosEmpresario(trab.getBrutoAnual()));
                */
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

    private static float calculaSalario(String categoria, List sheetData2) {
        for(int i = 1; i< sheetData2.size(); i++){
            List list = (List) sheetData2.get(i);
            Cell salarCell = (Cell) list.get(1);
            Cell categCell = (Cell) list.get(0);
            if(categoria.equals(categCell.toString())){
                return parseFloat(salarCell.toString())/14;
            }
        }
        return 0;
    }

    private static double calculaComplementos(String categoria, List sheetData2) {
        for(int i = 1; i< sheetData2.size(); i++){
            List list = (List) sheetData2.get(i);
            Cell salarCell = (Cell) list.get(2);
            Cell categCell = (Cell) list.get(0);
            if(categoria.equals(categCell.toString())){
                return (double) parseFloat(salarCell.toString())/14;
            }
        }
        return 0;
    }
    private static float calculaCompTrienio(int trienios, List sheetData2) {
        if(trienios<=0)
            return 0;
        if(trienios==1) //TODO: BUG: no se por que, con 1 trienio pone el maximo importe... de momento lo hardcodeamos así
            return 15;
        float compT=0;
        for(int i = 18; i< 36; i++){
            List list = (List) sheetData2.get(i);
            Cell trieCell = (Cell) list.get(3);
            Cell pagoCell = (Cell) list.get(4);
            if(Integer.parseInt(trieCell.toString().substring(0,1))==trienios){
                compT=(float) pagoCell.getNumericCellValue();
            }
        }
        return compT;
    }
    private static float calcularProrrateo(boolean prorrata, float salario, float complemento, float antiguedad) {
        if(!prorrata)         
            return 0;
        return (salario)/6+(complemento)/6+(antiguedad)/6;
    }
    private static float calcularRetencion(float brutoAnual, List sheetData2) {
        
        //Calculamos la retención
        String brutoAnualStr=String.valueOf(brutoAnual);
        float total=0;
        for(int i = 1; i< sheetData2.size(); i++){
            List list = (List) sheetData2.get(i);
            Cell salarCell = (Cell) list.get(6);
            Cell categCell = (Cell) list.get(5);
            
            if(brutoAnualStr.equals(categCell.toString())){
                total=total+Float.parseFloat(salarCell.toString());
                return total;
            }
        }
        
        return total;
    }
    private static float calcularDescuentos(float brutoAnual) {
        float descuentoTotal=0;
        //IRPF 12.08%
        descuentoTotal+=(brutoAnual/12)*(12.08)/100;
        //Seguridad Social 4.7%
        descuentoTotal+=(brutoAnual/12)*(4.7)/100;
        //Desempleo 1.6%
        descuentoTotal+=(brutoAnual/12)*(1.6)/100;
        //Formacion 0.1
        descuentoTotal+=(brutoAnual/12)*(0.1)/100;
        
        return descuentoTotal;
    }

    private static double calculaBrutoAnual(String categoria, List sheetData2) {
        for(int i = 1; i< sheetData2.size(); i++){
            List list = (List) sheetData2.get(i);
            Cell salarCell = (Cell) list.get(1);
            Cell categCell = (Cell) list.get(0);
            if(categoria.equals(categCell.toString())){
                return (double) parseFloat(salarCell.toString());
            }
        }
        return 0;
    }

    private static float calcularPagosEmpresario(float brutoAnual) {
        float total=0;
        //Salario bruto anual
        total+=brutoAnual;
        //Seguridad Social 4.7%
        total+=(brutoAnual/12)*(4.7)/100;
        //FOGASA
        total+=(brutoAnual/12)*(0.2)/100;
        //Desempleo 1.6%
        total+=(brutoAnual/12)*(1.6)/100;
        //Formacion 0.1
        total+=(brutoAnual/12)*(0.1)/100;
        
        
        return total;
    }

    private static float calcularContingencias(float brutoAnual) {
        return (float) ((brutoAnual/12)*(4.7)/100);
    }

    private static float calcularIRPF(float brutoAnual) {
        return (float) ((brutoAnual/12)*(12.08)/100);
    }

    private static float calcularFormacion(float brutoAnual) {
        return (float) ((brutoAnual/12)*(0.1)/100);
        
       
    }
    private static float calcularDesempleo(float brutoAnual) {
        return (float) ((brutoAnual/12)*(1.6)/100);
        
    }
    
    public static PdfPTable createFirstTable(Trabajador trabajador) {
    	// a table with three columns
        PdfPTable table = new PdfPTable(5);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = new PdfPCell(new Phrase("Nomina de " + fecha));
        cell.setColspan(5);
        table.addCell(cell);
        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase(""));
        table.addCell(cell);
        // we add the four remaining cells with addCell()
        table.addCell("cant");
        table.addCell("Imp. Unit");
        table.addCell("Dev.");
        table.addCell("Deduc.");
        
        table.addCell("Salario Base");
        table.addCell("30 dias");
        table.addCell(String.valueOf(trabajador.getSalario()/30));
        table.addCell(String.valueOf(trabajador.getSalario()));
        table.addCell("");
        
        
        
        table.addCell("Prorrata");
        table.addCell("30 dias");
        if(trabajador.hasProrrateo()){
            table.addCell(String.valueOf(trabajador.getProrrateo()/30));
            table.addCell(String.valueOf(trabajador.getProrrateo()));
        }
        else{
            table.addCell(" ");
            table.addCell(" ");
        }
        table.addCell("");
        
        
        table.addCell("Complento");
        table.addCell("30 dias");
        table.addCell(String.valueOf(trabajador.getComplementos()/30));
        table.addCell(String.valueOf(trabajador.getComplementos()));
        table.addCell("");
        
        
        table.addCell("Antiguedad");
        table.addCell(String.valueOf(trabajador.getTrienios()) + " trienios.");
        table.addCell(String.valueOf(trabajador.getAntiguedad()/30));
        table.addCell(String.valueOf(trabajador.getAntiguedad()));
        table.addCell("");
        float devengoTotal;
        if(trabajador.hasProrrateo()){
            devengoTotal=trabajador.getAntiguedad()+trabajador.getComplementos()+trabajador.getProrrateo()+trabajador.getSalario();
        }
        else{
            devengoTotal=trabajador.getAntiguedad()+trabajador.getComplementos()+trabajador.getSalario();
        }
        
        cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(5);
        table.addCell(cell);
        
        table.addCell("Contingencias Generales");
        table.addCell("4.7%");
        table.addCell("de "+devengoTotal);
        table.addCell("");
        table.addCell(String.valueOf(trabajador.getContingencias()));
        
        table.addCell("Desempleo");
        table.addCell("1.6%");
        table.addCell("de "+devengoTotal);;
        table.addCell("");
        table.addCell(String.valueOf(trabajador.getDesempleo()));
        
        table.addCell("Cuota formación");
        table.addCell("0.1%");
        table.addCell("de "+devengoTotal);;
        table.addCell("");
        table.addCell(String.valueOf(trabajador.getFormacion()));
        
        table.addCell("IRPF");
        table.addCell(String.valueOf(trabajador.getPorcIRPF()));
        table.addCell("de "+devengoTotal);;
        table.addCell("");
        float descuentoTotal=(devengoTotal*(trabajador.getPorcIRPF()/100))+trabajador.getFormacion()+trabajador.getDesempleo()+trabajador.getContingencias();
        table.addCell(String.valueOf(devengoTotal*(trabajador.getPorcIRPF()/100)));
        
        cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(5);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Deducciones"));
        cell.setColspan(4);
        table.addCell(cell);
        table.addCell(String.valueOf(trabajador.getDescuentos()));
        
        cell = new PdfPCell(new Phrase("Total Dev"));
        cell.setColspan(3);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(String.valueOf(trabajador.getSalario() + trabajador.getProrrateo()+trabajador.getAntiguedad()+trabajador.getComplementos())));
        cell.setColspan(2);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Líquido a Percibir"));
        cell.setColspan(4);
        table.addCell(cell); 
        table.addCell(String.valueOf(devengoTotal-descuentoTotal));

        return table;
    }
    
    public static void createPdf(String filename, Trabajador employee)
        throws IOException, DocumentException {
        
        
        
        
    	// step 1
        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        Paragraph infoEmpresa = new Paragraph("info empresa\n");
        Paragraph infoEmpleado = new Paragraph(employee.toString());
        document.add(infoEmpresa);
        document.add(infoEmpleado);
        // step 410/
        document.add(createFirstTable(employee));
        document.add(createSecondTable(employee));
        // step 5
        document.close();
    }

    private static com.itextpdf.text.Element createSecondTable(Trabajador trabajador) {
        // a table with three columns
        PdfPTable table = new PdfPTable(5);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = new PdfPCell(new Phrase("Cálculo de coste para el empresario"));
        cell.setColspan(5);
        table.addCell(cell);
        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase(""));
        table.addCell("Calculo base");
        // we add the four remaining cells with addCell()
        table.addCell("");
        table.addCell("");
        table.addCell("");
        float devengos;
        if(trabajador.hasProrrateo()){
            devengos=trabajador.getAntiguedad()+trabajador.getComplementos()+trabajador.getProrrateo()+trabajador.getSalario();
        }
        else{
            devengos=trabajador.getAntiguedad()+trabajador.getComplementos()+trabajador.getSalario();
        }
        table.addCell(String.valueOf(devengos));
        
        cell = new PdfPCell(new Phrase("Contingencias comunes 23.60%"));
        cell.setColspan(4);
        table.addCell(cell);
        table.addCell(String.valueOf(devengos*23.60/100));
         
        cell = new PdfPCell(new Phrase("Desempleo 6.7%"));
        cell.setColspan(4);
        table.addCell(cell);
        table.addCell(String.valueOf(devengos*6.7/100)); 
        
        cell = new PdfPCell(new Phrase("Formación 0.6%"));
        cell.setColspan(4);
        table.addCell(cell);
        table.addCell(String.valueOf(devengos*0.6/100)); 
        
        cell = new PdfPCell(new Phrase("Accidentes de trabajo 1.0%"));
        cell.setColspan(4);
        table.addCell(cell);
        table.addCell(String.valueOf(devengos/100));
         
        cell = new PdfPCell(new Phrase("FOGASA 2%"));
        cell.setColspan(4);
        table.addCell(cell);
        table.addCell(String.valueOf(devengos*2/100));
         
        cell = new PdfPCell(new Phrase("TOTAL EMPRESARIO"));
        cell.setColspan(4);
        table.addCell(cell);
        table.addCell(String.valueOf(devengos*23.60/100+devengos*6.7/100+devengos*0.6/100+devengos/100+devengos*2/100));
        
        cell = new PdfPCell(new Phrase("Coste total del trabajador"));
        cell.setColspan(4);
        table.addCell(cell);
        table.addCell(String.valueOf(devengos*23.60/100+devengos*6.7/100+devengos*0.6/100+devengos/100+devengos*2/100+devengos));
        
        return table;
    }

    

    

    
   
}
