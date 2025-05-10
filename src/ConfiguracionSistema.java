import java.util.List;

    
public class ConfiguracionSistema {
    //encapsulamiento de variables
    private String nombreEmpresa;
    private String rutaLogo; // ruta local del archivo
    private String idioma;
    private String zonaHoraria;
    private int diasVencimientoTickets;
    private List<String> nivelesPrioridad;

    public ConfiguracionSistema() {
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getRutaLogo() {
        return rutaLogo;
    }

    public void setRutaLogo(String rutaLogo) {
        this.rutaLogo = rutaLogo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getZonaHoraria() {
        return zonaHoraria;
    }

    public void setZonaHoraria(String zonaHoraria) {
        this.zonaHoraria = zonaHoraria;
    }

    public int getDiasVencimientoTickets() {
        return diasVencimientoTickets;
    }

    public void setDiasVencimientoTickets(int diasVencimientoTickets) {
        this.diasVencimientoTickets = diasVencimientoTickets;
    }

    public List<String> getNivelesPrioridad() {
        return nivelesPrioridad;
    }

    public void setNivelesPrioridad(List<String> nivelesPrioridad) {
        this.nivelesPrioridad = nivelesPrioridad;
    }

}
