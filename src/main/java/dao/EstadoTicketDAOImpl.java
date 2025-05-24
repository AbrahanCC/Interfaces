package dao;

import modelo.EstadoTicket;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoTicketDAOImpl implements EstadoTicketDAO {

    @Override
    public List<EstadoTicket> obtenerTodos() {
        List<EstadoTicket> lista = new ArrayList<>();
        String sql = "SELECT * FROM estado_ticket";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EstadoTicket est = construirDesdeResultSet(rs);
                est.setEstadosSiguientes(obtenerEstadosSiguientes(est.getId()));
                lista.add(est);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public EstadoTicket obtenerPorId(int id) {
        String sql = "SELECT * FROM estado_ticket WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EstadoTicket estado = construirDesdeResultSet(rs);
                    estado.setEstadosSiguientes(obtenerEstadosSiguientes(id));
                    return estado;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean guardarEstado(EstadoTicket estado) {
        boolean actualizar = (estado.getId() > 0);
        String sql = actualizar
                ? "UPDATE estado_ticket SET nombre_estado=?, descripcion=?, es_final=? WHERE id=?"
                : "INSERT INTO estado_ticket(nombre_estado, descripcion, es_final) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, estado.getNombreEstado());
            stmt.setString(2, estado.getDescripcion());
            stmt.setBoolean(3, estado.isEsFinal());

            if (actualizar) {
                stmt.setInt(4, estado.getId());
            }

            int filasAfectadas = stmt.executeUpdate();

            if (!actualizar) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        estado.setId(rs.getInt(1));
                    }
                }
            }

            return filasAfectadas > 0 && guardarTransiciones(estado.getId(), estado.getEstadosSiguientes());

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarEstado(int id) {
        String sql = "DELETE FROM estado_ticket WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Integer> obtenerEstadosSiguientes(int estadoId) {
        List<Integer> lista = new ArrayList<>();
        String sql = "SELECT estado_destino_id FROM transiciones_estado WHERE estado_origen_id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estadoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getInt("estado_destino_id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public boolean guardarTransiciones(int estadoOrigenId, List<Integer> estadosDestino) {
        String deleteSQL = "DELETE FROM transiciones_estado WHERE estado_origen_id = ?";
        String insertSQL = "INSERT INTO transiciones_estado (estado_origen, estado_destino_id) VALUES (?, ?)";

        try (Connection conn = Conexion.getConexion()) {
            conn.setAutoCommit(false);

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
                deleteStmt.setInt(1, estadoOrigenId);
                deleteStmt.executeUpdate();
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                for (int destinoId : estadosDestino) {
                    insertStmt.setInt(1, estadoOrigenId);
                    insertStmt.setInt(2, destinoId);
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private EstadoTicket construirDesdeResultSet(ResultSet rs) throws SQLException {
        return new EstadoTicket(
                rs.getInt("id"),
                rs.getString("nombre_estado"),
                rs.getString("descripcion"),
                rs.getBoolean("es_final")
        );
    }
}