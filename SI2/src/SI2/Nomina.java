package SI2;

public class Nomina {

    
    
    int mes ; 
    int anio ; 
    int numeroTrienios ;
    double importeTrienios; 
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
    double brutoNomina;  
    double liquidoNomina;
    double costeTotalEmpresario;
    int idTrabajador;
    int idNomina;

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
    

    

  
    
    
    
}
