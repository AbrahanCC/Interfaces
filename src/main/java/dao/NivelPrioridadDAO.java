package dao;

import modelo.NivelPrioridad;
import util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class NivelPrioridadDAO {

    public static void guardarNivelesPrioridad(List<NivelPrioridad> prioridades, int configuracionId) {
        String sql = "INSERT INTO niveles_prioridad (nombre, configuracion_id) VALUES (?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (NivelPrioridad prioridad : prioridades) {
                stmt.setString(1, prioridad.getNombre());
                stmt.setInt(2, configuracionId);
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar los niveles de prioridad.");
        }
    }
}