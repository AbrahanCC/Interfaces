package dao;

import modelo.Departamento;
import util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class DepartamentoDAOImpl implements DepartamentoDAO {

        @Override
        public List<Departamento> obtenerTodos() {
            List<Departamento> lista = new ArrayList<>();
            String sql = "SELECT * FROM departamentos";

            try (Connection conn = Conexion.getConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Departamento d = new Departamento();
                    d.setId(rs.getInt("id"));
                    d.setNombre(rs.getString("nombre"));
                    d.setDescripcion(rs.getString("descripcion"));
                    lista.add(d);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return lista;
        }

        @Override
        public Departamento obtenerPorId(int id) {
            String sql = "SELECT * FROM departamentos WHERE id = ?";
            Departamento d = null;

            try (Connection conn = Conexion.getConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    d = new Departamento();
                    d.setId(rs.getInt("id"));
                    d.setNombre(rs.getString("nombre"));
                    d.setDescripcion(rs.getString("descripcion"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return d;
        }

        @Override
        public boolean insertar(Departamento departamento) {
            String sql = "INSERT INTO departamentos (nombre, descripcion) VALUES (?, ?)";

            try (Connection conn = Conexion.getConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, departamento.getNombre());
                stmt.setString(2, departamento.getDescripcion());

                return stmt.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public boolean actualizar(Departamento departamento) {
            String sql = "UPDATE departamentos SET nombre = ?, descripcion = ? WHERE id = ?";

            try (Connection conn = Conexion.getConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, departamento.getNombre());
                stmt.setString(2, departamento.getDescripcion());
                stmt.setInt(3, departamento.getId());

                return stmt.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public boolean eliminar(int id) {
            String sql = "DELETE FROM departamentos WHERE id = ?";

            try (Connection conn = Conexion.getConexion();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }