package dao;

import modelo.NivelPrioridad;
import util.Conexion;

import java.sql.*;
import java.util.List;

public class ConfiguracionDAO {

    public static void guardarConfiguracion(String empresa, String idioma, String zonaHoraria, int dias, String rutaLogo, List<NivelPrioridad> prioridades) {
        try (Connection conn = Conexion.getConexion()) {

            // Verificar si ya existe OTRA configuración con el mismo nombre de empresa
            String sqlEmpresaRepetida = "SELECT COUNT(*) FROM configuracion_sistema WHERE empresa = ? AND id <> 1";
            PreparedStatement psEmpresa = conn.prepareStatement(sqlEmpresaRepetida);
            psEmpresa.setString(1, empresa);
            ResultSet rsEmpresa = psEmpresa.executeQuery();
            rsEmpresa.next();
            int empresaDuplicada = rsEmpresa.getInt(1);
            rsEmpresa.close();
            psEmpresa.close();

            if (empresaDuplicada > 0) {
                throw new RuntimeException("Ya existe una configuración con el mismo nombre de empresa.");
            }

            // Verifica si ya hay configuración con id 1
            String sqlCheck = "SELECT COUNT(*) FROM configuracion_sistema WHERE id = 1";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            psCheck.close();

            if (count > 0) {
                // UPDATE
                String sqlUpdate = "UPDATE configuracion_sistema SET empresa=?, idioma_predeterminado=?, zona_horaria=?, dias_vencimiento_ticket=?, ruta_logo=?, fecha_configuracion=NOW() WHERE id=1";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setString(1, empresa);
                psUpdate.setString(2, idioma);
                psUpdate.setString(3, zonaHoraria);
                psUpdate.setInt(4, dias);
                psUpdate.setString(5, rutaLogo);
                psUpdate.executeUpdate();
                psUpdate.close();
            } else {
                // INSERT
                String sqlInsert = "INSERT INTO configuracion_sistema (id, empresa, idioma_predeterminado, zona_horaria, dias_vencimiento_ticket, ruta_logo, fecha_configuracion) VALUES (1, ?, ?, ?, ?, ?, NOW())";
                PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
                psInsert.setString(1, empresa);
                psInsert.setString(2, idioma);
                psInsert.setString(3, zonaHoraria);
                psInsert.setInt(4, dias);
                psInsert.setString(5, rutaLogo);
                psInsert.executeUpdate();
                psInsert.close();
            }

            // Eliminar niveles anteriores
            String sqlDelete = "DELETE FROM niveles_prioridad WHERE configuracion_id = 1";
            PreparedStatement psDelete = conn.prepareStatement(sqlDelete);
            psDelete.executeUpdate();
            psDelete.close();

            // Insertar nuevos niveles
            String sqlInsertPrioridad = "INSERT INTO niveles_prioridad (nombre, configuracion_id) VALUES (?, 1)";
            PreparedStatement psInsertPrioridad = conn.prepareStatement(sqlInsertPrioridad);

            for (NivelPrioridad prioridad : prioridades) {
                psInsertPrioridad.setString(1, prioridad.getNombre());
                psInsertPrioridad.executeUpdate();
            }
            psInsertPrioridad.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar la configuración y prioridades: " + e.getMessage());
        }
    }

}




