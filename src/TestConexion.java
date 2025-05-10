import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {
    public static void main(String[] args) {
        try {
            Connection conn = ConexionBD.conectar();
            System.out.println("✅ Conexión exitosa a la base de datos.");
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
        }
    }
}

