package modelo;

import java.sql.Timestamp;

public class ConfiguracionSistema {
    private int id;
    private String empresa;
    private String rutaLogo;
    private String idiomaPredeterminado;
    private String zonaHoraria;
    private int diasVencimientoTicket;
    private Timestamp fechaConfiguracion;

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getRutaLogo() {
        return rutaLogo;
    }

    public void setRutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }

    public String getIdiomaPredeterminado() {
        return idiomaPredeterminado;
    }

    public void setIdiomaPredeterminado(String idiomaPredeterminado) {
        this.idiomaPredeterminado = idiomaPredeterminado;
    }

    public String getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(String zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }

    public int getDiasVencimientoTicket() {
        return diasVencimientoTicket;
    }

    public void setDiasVencimientoTicket(int diasVencimientoTicket) {
        this.diasVencimientoTicket = diasVencimientoTicket;
    }

    public Timestamp getFechaConfiguracion() {
        return fechaConfiguracion;
    }

    public void setFechaConfiguracion(Timestamp fechaConfiguracion) {
        this.fechaConfiguracion = fechaConfiguracion;
    }
}


