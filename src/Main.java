
import java.util.Arrays;


public class Main {

    public static void main(String[] args) {
        
        ConfiguracionSistema config = new ConfiguracionSistema();
        config.setNombreEmpresa("Mi Empresa S.A.");
        config.setRutaLogo("C:/logos/logo.png"); // ruta válida en tu PC
        config.setIdioma("Español");
        config.setZonaHoraria("America/Mexico_City");
        config.setDiasVencimientoTickets(30);
        config.setNivelesPrioridad(Arrays.asList("Alta", "Media", "Baja"));

        String error = ValidadorConfiguracion.validar(config);
        if (error != null) {
            System.out.println("❌ Error: " + error);
        } else {
            new ConfiguracionService().guardar(config);
            System.out.println("✅ Configuración guardada con éxito.");
        }
    }
}
