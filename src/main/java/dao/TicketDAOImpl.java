package dao;

import modelo.EstadoTicket;
import modelo.Ticket;
import modelo.Usuario;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO {

    @Override
    public boolean insertar(Ticket ticket) {
        String sql = "INSERT INTO tickets (descripcion, fecha_creacion, fecha_cierre, estado_id, usuario_id, tecnico_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, ticket.getDescripcion());
            stmt.setDate(2, Date.valueOf(ticket.getFechaCreacion()));

            if (ticket.getFechaCierre() != null) {
                stmt.setDate(3, Date.valueOf(ticket.getFechaCierre()));
            } else {
                stmt.setNull(3, Types.DATE);
            }

            stmt.setInt(4, ticket.getEstado().getId());
            stmt.setInt(5, ticket.getCreador().getId());

            if (ticket.getTecnico() != null) {
                stmt.setInt(6, ticket.getTecnico().getId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ticket.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void actualizar(Ticket ticket) {
        String sql = "UPDATE tickets SET descripcion = ?, fecha_cierre = ?, estado_id = ?, tecnico_id = ? WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ticket.getDescripcion());

            if (ticket.getFechaCierre() != null) {
                stmt.setDate(2, Date.valueOf(ticket.getFechaCierre()));
            } else {
                stmt.setNull(2, Types.DATE);
            }

            stmt.setInt(3, ticket.getEstado().getId());

            if (ticket.getTecnico() != null) {
                stmt.setInt(4, ticket.getTecnico().getId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setInt(5, ticket.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ticket> obtenerTodos() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT t.*, " +
                "u1.nombre AS nombre_creador, " +
                "u2.nombre AS nombre_tecnico, " +
                "e.nombre_estado " +
                "FROM tickets t " +
                "JOIN usuario u1 ON t.usuario_id = u1.id " +
                "LEFT JOIN usuario u2 ON t.tecnico_id = u2.id " +
                "JOIN estado_ticket e ON t.estado_id = e.id";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ticket ticket = construirTicketDesdeResultSet(rs);
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    @Override
    public Ticket obtenerPorId(int id) {
        String sql = "SELECT t.*, " +
                "u1.nombre AS nombre_creador, " +
                "u2.nombre AS nombre_tecnico, " +
                "e.nombre_estado " +
                "FROM ticket t " +
                "JOIN usuario u1 ON t.usuario_id = u1.id " +
                "LEFT JOIN usuario u2 ON t.tecnico_id = u2.id " +
                "JOIN estado_ticket e ON t.estado_id = e.id " +
                "WHERE t.id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirTicketDesdeResultSet(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM tickets WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar
    private Ticket construirTicketDesdeResultSet(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.setDescripcion(rs.getString("descripcion"));
        ticket.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());

        Date fechaCierreSQL = rs.getDate("fecha_cierre");
        if (fechaCierreSQL != null) {
            ticket.setFechaCierre(fechaCierreSQL.toLocalDate());
        }

        EstadoTicket estado = new EstadoTicket();
        estado.setId(rs.getInt("estado_id"));
        estado.setNombreEstado(rs.getString("nombre_estado"));
        ticket.setEstado(estado);

        Usuario creador = new Usuario();
        creador.setId(rs.getInt("usuario_id"));
        creador.setNombre(rs.getString("nombre_creador"));
        ticket.setCreador(creador);

        if (rs.getObject("tecnico_id") != null) {
            Usuario tecnico = new Usuario();
            tecnico.setId(rs.getInt("tecnico_id"));
            tecnico.setNombre(rs.getString("nombre_tecnico"));
            ticket.setTecnico(tecnico);
        }

        return ticket;
    }
}
