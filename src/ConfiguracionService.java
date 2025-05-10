import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ConfiguracionService {

    public void guardar(ConfiguracionSistema config) {
        String sql = "INSERT INTO configuracion (nombre_empresa, ruta_logo, idioma, zona_horaria, vencimiento_tickets, " +
                     "nivel_prioridad_1, nivel_prioridad_2, nivel_prioridad_3) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, config.getNombreEmpresa());
            stmt.setString(2, config.getRutaLogo());
            stmt.setString(3, config.getIdioma());
            stmt.setString(4, config.getZonaHoraria());
            stmt.setInt(5, config.getDiasVencimientoTickets());

            List<String> prioridades = config.getNivelesPrioridad();
            stmt.setString(6, prioridades.size() > 0 ? prioridades.get(0) : null);
            stmt.setString(7, prioridades.size() > 1 ? prioridades.get(1) : null);
            stmt.setString(8, prioridades.size() > 2 ? prioridades.get(2) : null);

            stmt.executeUpdate();
            System.out.println("✅ Configuración guardada en la base de datos.");

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar en la base de datos: " + e.getMessage());
        }
    }
}


