
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.itextpdf.text.DocumentException;
import java.io.File;
import hibernate.ConexionBD;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static ArrayList<Trabajador> listaTrabajadores;
    private static int nCu;
    private static int nCuMal;
    private static String fecha;
    private static ArrayList<Empresa> listaEmpresas;
    private static ArrayList<Categoria> listaCategorias;
    private static ArrayList<model.Nomina> listaNominas;

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



        listaNominas=new ArrayList<>();
        calcularNominas(fecha, sheetData2);
        
        ConexionBD conexion = new ConexionBD();
        listaCategorias.forEach((cat) -> {
            conexion.insertCategoria(cat);
        });
        
        listaEmpresas.forEach((empr) -> {
            conexion.insertEmpresa(empr);
        });
        
        for(Trabajador trab : listaTrabajadores){
            conexion.insertTrabajador(trab);
        }
        
        for(Nomina nomina : listaNominas){
            conexion.insertNomina(nomina);
            
        }
        
        ConexionBD.closeSession();
         
        
    }
    
    private static void showData(List sheetData, List sheetData2) throws IOException, ParseException{
        ArrayList<String> listaNifs = new ArrayList<String>();
        listaCategorias = new ArrayList<>();
        listaEmpresas = new ArrayList<>();
        //Creamos la lista de categorias
        Categoria cat;
        for(int i=1;i<15;i++){
            List list = (List) sheetData2.get(i);
            Cell categCell= (Cell) list.get(0);
            Cell salarCell = (Cell) list.get(1);
            Cell compCell = (Cell) list.get(2);
            cat=new Categoria(i, categCell.toString(), Double.valueOf(salarCell.toString()), Double.valueOf(compCell.toString()));
            listaCategorias.add(cat);
        }
        
        
        // lo demás
        for(int i = 1; i< sheetData.size(); i++){
            List list = (List) sheetData.get(i);
            Cell cell = (Cell) list.get(0);      
            //Creamos el objeto trabajador. Lo seteamos en cada if
            Trabajador trab=new Trabajador();
            trab.setIdTrabajador(i);
            
            if(cell.getCellTypeEnum() == CellType.STRING){ //El DNI tiene una letra al final
                String analizedNif = analizamosNif(cell.toString());
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
            Cell cellEmpr= (Cell) list.get(7);
            Cell cuenta = (Cell) list.get(14);
            Cell pais = (Cell) list.get(15);
            Cell cellCategoria = (Cell) list.get(5);
            Cell prorrata = (Cell) list.get(13);
            Cell fechaAlta = (Cell) list.get(8);
            Cell cellCifEmpr = (Cell) list.get(6);
            
            
            if(cuenta.getCellTypeEnum()==CellType.STRING && pais.getCellTypeEnum()==CellType.STRING){
                //Comprobacion de cuenta
                String cuentaRecalculada=compruebaCuenta(cuenta.toString(),list,i, pais.toString(), trab);
                trab.setCuenta(cuentaRecalculada);
                write(i,14,cuentaRecalculada);
                //Calculo de iban
                String ibanCalculado=trab.calculateIBAN(cuentaRecalculada, pais.toString());
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
                //Creamos la empresa y la añadimos si no está              
                nombreEmpresa=cellEmpr.toString();
                cifEmpresa = cellCifEmpr.toString();
                Empresa empr = new Empresa(nombreEmpresa, cifEmpresa);
                trab.setEmpresa(empr);
                //TODO añadir direccion
                if(listaEmpresas.isEmpty()){
                    listaEmpresas.add(empr);
                }else{
                    boolean contenido = false;
                    for(Empresa empresa:listaEmpresas){
                        if(empresa.getNombre().equals(nombreEmpresa)){
                            contenido = true;
                        }
                    }
                    if(!contenido)
                        listaEmpresas.add(empr);
                }
                //trab.setEmpresa(empr.getIdEmpresa());
                
                for(Categoria cate : listaCategorias){
                    if(cate.getNombreCategoria().equals(cellCategoria.toString())){
                        trab.setCategoria(cate);
                    
                    }
                }
                
                apellido1=cellApe1.toString();
                trab.setApellido1(apellido1);
                trab.setApellido2(apellido2);
                nombre=cellNom.toString();
                trab.setNombre(nombre);
                
                String correo=trab.calculaCorreo(nombre.toLowerCase(), apellido1.toLowerCase(), apellido2.toLowerCase(), nombreEmpresa.toLowerCase());
                trab.setCorreo(correo);
                String prort = prorrata.toString();
                if(prort.equals("NO")){
                    trab.setProrrateo(false);
                }else{
                    trab.setProrrateo(true);
                }
                trab.setFechaAltaEmpresa(fechaAlta.getDateCellValue());
                trab.calculateTrienios(fecha);
                
                
                listaTrabajadores.add(trab);

                write(i, 4, correo);
                
            }
            
        }
    }
    //FunciÃ³n que comprueba que el nif estÃ© bien
   
    private static String analizamosNif(String nif){
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
  
    private static String compruebaCuenta(String numeroCuenta, List list, int fila, String pais, Trabajador trab){
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
            addToXMLCuentas(doc2, list, fila, trab.calculateIBAN(cuentaCorregida, pais), cuentaOriginal);
            return cuentaCorregida;
        }
        else{ //Si estÃ¡ bien, devuelve la original
            
            return cuentaOriginal;
        }
        
       
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

    
    

    
   

    private static void calcularNominas(String fecha, List sheetData2) throws IOException, DocumentException {
        
        model.Nomina nomi;
        int count=0;
        for(Trabajador trab : listaTrabajadores){
            
            if(trab.getDNI() != null){
                count++;;
                nomi= new Nomina(); 
                nomi.setTrabajador(trab);
                nomi.setIdNomina(count);
                nomi.setMes(Integer.valueOf(fecha.substring(0,2)));
                nomi.setAnio(Integer.valueOf(fecha.substring(3,7)));
                nomi.setNumeroTrienios(trab.getnTrienios());

                //Calculamos Trabajador 
                for(int i = 1; i< sheetData2.size(); i++){
                    List list = (List) sheetData2.get(i);
                    Cell complementCell= (Cell) list.get(2);
                    Cell salarCell = (Cell) list.get(1);
                    Cell categCell = (Cell) list.get(0);
                    if((listaCategorias.get((trab.getCategoria().getIdCategoria())-1).nombreCategoria).equals(categCell.toString())){
                        nomi.setBrutoAnual(Double.parseDouble(salarCell.toString()));
                        nomi.setImporteComplementoMes(Double.parseDouble(complementCell.toString())/14);
                        nomi.setImporteSalarioMes(Double.parseDouble(salarCell.toString())/14);
                        break;
                    }


                }
                //Calculamos la cantidad de los trienios
                for(int i=18;i<36;i++){
                    List list= (List) sheetData2.get(i);
                    Cell nTriCell=(Cell) list.get(3);
                    Cell cantTriCell=(Cell) list.get(4);
                    if(nomi.getNumeroTrienios()==Double.valueOf(nTriCell.toString())){
                        nomi.setImporteTrienios(Double.valueOf(cantTriCell.toString()));
                    }
                }
                for(int i=49;i>0;i--){
                    List list = (List) sheetData2.get(i);
                    Cell brutoCell = (Cell) list.get(5);
                    Cell IRPFCell = (Cell) list.get(6);
                    boolean encontrado=false;
                    if(nomi.getBrutoAnual()>Double.parseDouble(brutoCell.toString())){
                    }
                    else{
                        if(!encontrado){
                            nomi.setIRPF(Double.valueOf(IRPFCell.toString()));
                            encontrado=true;
                        }

                    }
                }
                //Calculamos si lo prorratea o no. 

                //SI el trabajador tiene prorrateo...
                if(trab.isProrrateo()){
                    nomi.setValorProrrateo(nomi.importeSalarioMes/6);
                }
                else{
                    nomi.setValorProrrateo(0);
                }
                nomi.setImporteIRPF(nomi.brutoAnual*nomi.getIRPF()/100/14);

                //TODO COMPROBAR ESTOS

                List listCuotaO=(List) sheetData2.get(17);
                List listDesempleoT=(List) sheetData2.get(18);
                List listFormacionT=(List) sheetData2.get(19);
                nomi.setSeguridadSocialTrabajador(Double.parseDouble(listCuotaO.get(1).toString()));
                nomi.setImporteSeguridadSocialTrabajador(nomi.getImporteSalarioMes()*nomi.getSeguridadSocialTrabajador()/100);
                nomi.setDesempleoTrabajador(Double.parseDouble(listDesempleoT.get(1).toString()));
                nomi.setImporteDesempleoTrabajador(nomi.getImporteSalarioMes()*nomi.getDesempleoTrabajador()/100);
                nomi.setFormacionTrabajador(Double.parseDouble(listFormacionT.get(1).toString()));
                nomi.setImporteFormacionTrabajador(nomi.getImporteSalarioMes()*nomi.getImporteFormacionTrabajador()/100);




                //EMPRESARIO
                List listSS=(List) sheetData2.get(20);
                List listFOGASA=(List) sheetData2.get(21);
                List listDesempleo=(List) sheetData2.get(22);
                List listFormacion=(List) sheetData2.get(23);
                List listAccidentes=(List) sheetData2.get(24);
                nomi.setBaseEmpresario(nomi.getBrutoAnual()/12);
                nomi.setSeguridadSocialEmpresario(Double.parseDouble(listSS.get(1).toString()));
                nomi.setImporteSeguridadSocialEmpresario(nomi.getBaseEmpresario()*nomi.getSeguridadSocialEmpresario()/100);
                nomi.setDesempleoEmpresario(Double.parseDouble(listDesempleo.get(1).toString()));
                nomi.setImporteDesempleoEmpresario(nomi.getBaseEmpresario()*nomi.getDesempleoEmpresario()/100);
                nomi.setFormacionEmpresario(Double.parseDouble(listFormacion.get(1).toString()));
                nomi.setImporteFormacionEmpresario(nomi.getBaseEmpresario()*nomi.getFormacionEmpresario()/100);
                nomi.setAccidentesTrabajoEmpresario(Double.parseDouble(listAccidentes.get(1).toString()));
                nomi.setImporteAccidentesTrabajo(nomi.getBaseEmpresario()*nomi.getAccidentesTrabajoEmpresario()/100);
                nomi.setFOGASAEmpresario(Double.parseDouble(listFOGASA.get(1).toString()));
                nomi.setImporteFOGASAEmpresario(nomi.getBaseEmpresario()*nomi.getFOGASAEmpresario()/100);

                //TODO Aqui me quede

                nomi.setBrutoNomina(nomi.getImporteSalarioMes()+nomi.getImporteTrienios()+nomi.getImporteComplementoMes()+nomi.getValorProrrateo());
                nomi.setLiquidoNomina(nomi.getBrutoNomina()-(nomi.getImporteDesempleoTrabajador()+nomi.getImporteFormacionTrabajador()+nomi.getImporteSeguridadSocialTrabajador()+nomi.getImporteIRPF()));
                System.out.println("Bruto de la nómina: "+nomi.getBrutoNomina());
                System.out.println("Desempleo: "+nomi.getBrutoNomina());
                System.out.println("Bruto de la nómina: "+nomi.getBrutoNomina());
                
                System.err.println(nomi.getLiquidoNomina());
                nomi.setCosteTotalEmpresario(nomi.getImporteSalarioMes()+nomi.getImporteDesempleoEmpresario()+nomi.getImporteFOGASAEmpresario()+nomi.getImporteFormacionEmpresario()+nomi.getImporteSeguridadSocialEmpresario());

                
                String filename = "nominas/"+trab.getDNI()+"_"+trab.getNombre()+trab.getApellido1()+trab.getApellido2()+"_"+nomi.getMes()+"-"+nomi.getAnio();
                if(nomi.getMes() == 6 || nomi.getAnio() == 12){
                    if(trab.isProrrateo() == false){
                        //Crear objeto nomina nuevo y añadirlo
                        Nomina nominaExtra = new Nomina();
                        nominaExtra.createPdf(filename + "_EXTRA"+".pdf");
                        listaNominas.add(nominaExtra);
                    }
                }
                nomi.createPdf(filename+".pdf");
                
                //TODO be happy

                listaNominas.add(nomi);
            }
               
        }

    }
    
        
        
        
}

