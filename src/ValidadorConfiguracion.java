import java.io.File;
import java.util.List;

public class ValidadorConfiguracion {
    
    public static String validar(ConfiguracionSistema config) {
        if (config.getNombreEmpresa() == null || config.getNombreEmpresa().trim().isEmpty()) {
            return "El nombre de la empresa no puede estar vacío.";
        }
        if (config.getNombreEmpresa().length() < 3 || config.getNombreEmpresa().length() > 100) {
            return "El nombre debe tener entre 3 y 100 caracteres.";
        }

        if (config.getRutaLogo() == null || config.getRutaLogo().trim().isEmpty()) {
            return "Debes especificar la ruta del logo.";
        } else {
            File logo = new File(config.getRutaLogo());
            if (!logo.exists()) return "El archivo de logo no existe.";
            String nombre = logo.getName().toLowerCase();
            if (!(nombre.endsWith(".jpg") || nombre.endsWith(".png"))) {
                return "El logo debe ser formato JPG o PNG.";
            }
            if (logo.length() > 2 * 1024 * 1024) {
                return "El logo no debe superar los 2MB.";
            }
        }

        if (config.getIdioma() == null || (!config.getIdioma().equals("Español") && !config.getIdioma().equals("Inglés"))) {
            return "Idioma no soportado.";
        }

        if (config.getZonaHoraria() == null || config.getZonaHoraria().isEmpty()) {
            return "Zona horaria inválida.";
        }

        int dias = config.getDiasVencimientoTickets();
        if (dias < 1 || dias > 365) {
            return "El tiempo de vencimiento debe ser entre 1 y 365 días.";
        }

        List<String> prioridades = config.getNivelesPrioridad();
        if (prioridades == null || prioridades.size() < 3) {
            return "Debes definir al menos tres niveles de prioridad.";
        }

        return null; // todo OK
    }
}
