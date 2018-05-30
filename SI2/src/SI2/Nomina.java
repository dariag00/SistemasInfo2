package SI2;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;



public class Nomina {

    
    
    int mes ; 
    int anio ; 
    int numeroTrienios ;
    double importeTrienios ; 
    double importeSalarioMes ;
    double importeComplementoMes ;
    double valorProrrateo ;
    double brutoAnual ;
    double IRPF ;
    double importeIRPF ;
    double baseEmpresario ; 
    double seguridadSocialEmpresario ;
    double importeSeguridadSocialEmpresario ; 
    double desempleoEmpresario ;
    double importeDesempleoEmpresario ;
    double formacionEmpresario ;
    double importeFormacionEmpresario ;
    double accidentesTrabajoEmpresario ;
    double importeAccidentesTrabajo ;
    double FOGASAEmpresario ;  
    double importeFOGASAEmpresario ;
    double seguridadSocialTrabajador ;
    double importeSeguridadSocialTrabajador ;
    double desempleoTrabajador ;
    double importeDesempleoTrabajador ;
    double formacionTrabajador ;
    double importeFormacionTrabajador ;
    double brutoNomina ;  
    double liquidoNomina ;
    double costeTotalEmpresario ;
    int idTrabajador ;
    int idNomina ;

    public double getCosteTotalEmpresario() {
        return costeTotalEmpresario;
    }

    public void setCosteTotalEmpresario(double costeTotalEmpresario) {
        this.costeTotalEmpresario = costeTotalEmpresario;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public int getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(int idNomina) {
        this.idNomina = idNomina;
    }
   
    
    
    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getNumeroTrienios() {
        return numeroTrienios;
    }

    public void setNumeroTrienios(int numeroTrienios) {
        this.numeroTrienios = numeroTrienios;
    }

    public double getImporteTrienios() {
        return importeTrienios;
    }

    public void setImporteTrienios(double importeTrienios) {
        this.importeTrienios = importeTrienios;
    }

    public double getImporteSalarioMes() {
        return importeSalarioMes;
    }

    public void setImporteSalarioMes(double importeSalarioMes) {
        this.importeSalarioMes = importeSalarioMes;
    }

    public double getImporteComplementoMes() {
        return importeComplementoMes;
    }

    public void setImporteComplementoMes(double importeComplementoMes) {
        this.importeComplementoMes = importeComplementoMes;
    }

    public double getValorProrrateo() {
        return valorProrrateo;
    }

    public void setValorProrrateo(double valorProrrateo) {
        this.valorProrrateo = valorProrrateo;
    }

    public double getBrutoAnual() {
        return brutoAnual;
    }

    public void setBrutoAnual(double brutoAnual) {
        this.brutoAnual = brutoAnual;
    }

    public double getIRPF() {
        return IRPF;
    }

    public void setIRPF(double IRPF) {
        this.IRPF = IRPF;
    }

    public double getImporteIRPF() {
        return importeIRPF;
    }

    public void setImporteIRPF(double importeIPRF) {
        this.importeIRPF = importeIPRF;
    }

    public double getBaseEmpresario() {
        return baseEmpresario;
    }

    public void setBaseEmpresario(double baseEmpresario) {
        this.baseEmpresario = baseEmpresario;
    }

    public double getSeguridadSocialEmpresario() {
        return seguridadSocialEmpresario;
    }

    public void setSeguridadSocialEmpresario(double seguridadSocialEmpresario) {
        this.seguridadSocialEmpresario = seguridadSocialEmpresario;
    }

    public double getImporteSeguridadSocialEmpresario() {
        return importeSeguridadSocialEmpresario;
    }

    public void setImporteSeguridadSocialEmpresario(double importeSeguridadSocialEmpresario) {
        this.importeSeguridadSocialEmpresario = importeSeguridadSocialEmpresario;
    }

    public double getDesempleoEmpresario() {
        return desempleoEmpresario;
    }

    public void setDesempleoEmpresario(double desempleoEmpresario) {
        this.desempleoEmpresario = desempleoEmpresario;
    }

    public double getImporteDesempleoEmpresario() {
        return importeDesempleoEmpresario;
    }

    public void setImporteDesempleoEmpresario(double importeDesempleoEmpresario) {
        this.importeDesempleoEmpresario = importeDesempleoEmpresario;
    }

    public double getFormacionEmpresario() {
        return formacionEmpresario;
    }

    public void setFormacionEmpresario(double formacionEmpresario) {
        this.formacionEmpresario = formacionEmpresario;
    }

    public double getImporteFormacionEmpresario() {
        return importeFormacionEmpresario;
    }

    public void setImporteFormacionEmpresario(double importeFormacionEmpresario) {
        this.importeFormacionEmpresario = importeFormacionEmpresario;
    }

    public double getAccidentesTrabajoEmpresario() {
        return accidentesTrabajoEmpresario;
    }

    public void setAccidentesTrabajoEmpresario(double accidentesTrabajoEmpresario) {
        this.accidentesTrabajoEmpresario = accidentesTrabajoEmpresario;
    }

    public double getImporteAccidentesTrabajo() {
        return importeAccidentesTrabajo;
    }

    public void setImporteAccidentesTrabajo(double importeAccidentesTrabajo) {
        this.importeAccidentesTrabajo = importeAccidentesTrabajo;
    }

    public double getFOGASAEmpresario() {
        return FOGASAEmpresario;
    }

    public void setFOGASAEmpresario(double FOGASAEmpresario) {
        this.FOGASAEmpresario = FOGASAEmpresario;
    }

    public double getImporteFOGASAEmpresario() {
        return importeFOGASAEmpresario;
    }

    public void setImporteFOGASAEmpresario(double importeFOGASAEmpresario) {
        this.importeFOGASAEmpresario = importeFOGASAEmpresario;
    }

    public double getSeguridadSocialTrabajador() {
        return seguridadSocialTrabajador;
    }

    public void setSeguridadSocialTrabajador(double seguridadSocialTrabajador) {
        this.seguridadSocialTrabajador = seguridadSocialTrabajador;
    }

    public double getImporteSeguridadSocialTrabajador() {
        return importeSeguridadSocialTrabajador;
    }

    public void setImporteSeguridadSocialTrabajador(double importeSeguridadSocialTrabajador) {
        this.importeSeguridadSocialTrabajador = importeSeguridadSocialTrabajador;
    }

    public double getDesempleoTrabajador() {
        return desempleoTrabajador;
    }

    public void setDesempleoTrabajador(double desempleoTrabajador) {
        this.desempleoTrabajador = desempleoTrabajador;
    }

    public double getImporteDesempleoTrabajador() {
        return importeDesempleoTrabajador;
    }

    public void setImporteDesempleoTrabajador(double importeDesempleoTrabajador) {
        this.importeDesempleoTrabajador = importeDesempleoTrabajador;
    }

    public double getFormacionTrabajador() {
        return formacionTrabajador;
    }

    public void setFormacionTrabajador(double formacionTrabajador) {
        this.formacionTrabajador = formacionTrabajador;
    }

    public double getImporteFormacionTrabajador() {
        return importeFormacionTrabajador;
    }

    public void setImporteFormacionTrabajador(double importeFormacionTrabajador) {
        this.importeFormacionTrabajador = importeFormacionTrabajador;
    }

    public double getBrutoNomina() {
        return brutoNomina;
    }

    public void setBrutoNomina(double brutoNomina) {
        this.brutoNomina = brutoNomina;
    }

    public double getLiquidoNomina() {
        return liquidoNomina;
    }

    public void setLiquidoNomina(double liquidoNomina) {
        this.liquidoNomina = liquidoNomina;
    }
    
    
    public Nomina(){
        
    }
    /*
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
        
        
        table.addCell("Complemento");
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
    
     
     
     
    private static PdfPTable createSecondTable(Trabajador trabajador) {
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
    */

    

  
    
    
    
}
