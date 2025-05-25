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
                EstadoTicket estado = construirDesdeResultSet(rs);
                estado.setEstadosSiguientes(obtenerEstadosSiguientes(estado.getId()));
                lista.add(estado);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los estados: " + e.getMessage());
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
            System.err.println("Error al obtener estado por ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean guardarEstado(EstadoTicket estado) {
        boolean esActualizar = estado.getId() > 0;
        String sql = esActualizar
                ? "UPDATE estado_ticket SET nombre_estado=?, descripcion=?, es_final=? WHERE id=?"
                : "INSERT INTO estado_ticket(nombre_estado, descripcion, es_final) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, estado.getNombreEstado());
            stmt.setString(2, estado.getDescripcion());
            stmt.setBoolean(3, estado.isEsFinal());

            if (esActualizar) {
                stmt.setInt(4, estado.getId());
            }

            int filasAfectadas = stmt.executeUpdate();

            if (!esActualizar) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        estado.setId(rs.getInt(1));
                    }
                }
            }

            return filasAfectadas > 0 && guardarTransiciones(estado.getId(), estado.getEstadosSiguientes());

        } catch (SQLException e) {
            System.err.println("Error al guardar o actualizar estado: " + e.getMessage());
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
            System.err.println("Error al eliminar estado: " + e.getMessage());
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
            System.err.println("Error al obtener estados siguientes: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public boolean guardarTransiciones(int estadoOrigenId, List<Integer> estadosDestino) {
        String deleteSQL = "DELETE FROM transiciones_estado WHERE estado_origen_id = ?";
        String insertSQL = "INSERT INTO transiciones_estado (estado_origen_id, estado_destino_id) VALUES (?, ?)";

        try (Connection conn = Conexion.getConexion()) {
            conn.setAutoCommit(false);  // Inicia transacción

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
                deleteStmt.setInt(1, estadoOrigenId);
                deleteStmt.executeUpdate();
            }

            if (!estadosDestino.isEmpty()) {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                    for (int destinoId : estadosDestino) {
                        insertStmt.setInt(1, estadoOrigenId);
                        insertStmt.setInt(2, destinoId);
                        insertStmt.addBatch();
                    }
                    insertStmt.executeBatch();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al guardar transiciones: " + e.getMessage());
            try (Connection conn = Conexion.getConexion()) {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
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