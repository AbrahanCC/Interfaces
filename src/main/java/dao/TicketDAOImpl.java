package dao;

import modelo.Ticket;
import modelo.Usuario;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAOImpl implements TicketDAO {

    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    @Override
    public List<Ticket> obtenerTodos() {
        List<Ticket> lista = new ArrayList<>();
        String sql = "SELECT * FROM ticket";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ticket ticket = extraerTicket(rs);
                lista.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Ticket obtenerPorId(int id) {
        String sql = "SELECT * FROM ticket WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extraerTicket(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertar(Ticket ticket) {
        String sql = "INSERT INTO ticket(titulo, descripcion, fecha_creacion, estado, id_creador, id_tecnico) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, ticket.getTitulo());
            stmt.setString(2, ticket.getDescripcion());
            stmt.setDate(3, Date.valueOf(ticket.getFechaCreacion()));
            stmt.setString(4, ticket.getEstado());
            stmt.setInt(5, ticket.getCreador().getId());
            if (ticket.getTecnico() != null) {
                stmt.setInt(6, ticket.getTecnico().getId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        ticket.setId(rs.getInt(1));
                    }
                }
            }

            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Ticket ticket) {
        String sql = "UPDATE ticket SET titulo=?, descripcion=?, fecha_creacion=?, estado=?, id_creador=?, id_tecnico=? WHERE id=?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ticket.getTitulo());
            stmt.setString(2, ticket.getDescripcion());
            stmt.setDate(3, Date.valueOf(ticket.getFechaCreacion()));
            stmt.setString(4, ticket.getEstado());
            stmt.setInt(5, ticket.getCreador().getId());
            if (ticket.getTecnico() != null) {
                stmt.setInt(6, ticket.getTecnico().getId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }
            stmt.setInt(7, ticket.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM ticket WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Ticket extraerTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.setTitulo(rs.getString("titulo"));
        ticket.setDescripcion(rs.getString("descripcion"));
        ticket.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
        ticket.setEstado(rs.getString("estado"));

        int idCreador = rs.getInt("id_creador");
        int idTecnico = rs.getInt("id_tecnico");

        Usuario creador = usuarioDAO.obtenerPorId(idCreador);
        ticket.setCreador(creador);

        if (idTecnico > 0) {
            Usuario tecnico = usuarioDAO.obtenerPorId(idTecnico);
            ticket.setTecnico(tecnico);
        }

        return ticket;
    }
}
