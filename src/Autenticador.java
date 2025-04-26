import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

public class Autenticador {
    String bd = "";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;

    //Lista de usuarios
    private final List<Usuario> usuariosRegistrados;

    public Autenticador() {
        usuariosRegistrados = new ArrayList<>();
        // Usuarios válidos simulados
        usuariosRegistrados.add(new Usuario("admin", "1234"));
        usuariosRegistrados.add(new Usuario("juan", "abc123"));
        usuariosRegistrados.add(new Usuario("maria", "claveSegura"));
    }

    public Connection conectar() {
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url + bd, user, password);
            System.out.println("Se conectó a BD: " + bd);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Autenticador.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("NO SE CONECTÓ A BD: " + bd);
        }
        return cx;
    }

    public void desconectar() {
        try {
            if (cx != null && !cx.isClosed()) {
                cx.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Autenticador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean autenticar(String username, String password) {
        for (Usuario u : usuariosRegistrados) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Clase interna para representar un usuario
    private static class Usuario {
        private final String username;
        private final String password;

        public Usuario(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
    }

    public static void main(String[] args) {
        Autenticador auth = new Autenticador();

        //
        auth.conectar();
        auth.desconectar();

        //autentificacion
        String usuario = "Julio";
        String clave = "123456";

        if (auth.autenticar(usuario, clave)) {
            System.out.println("Inicio de sesión exitoso");
        } else {
            System.out.println("Usuario o contraseña incorrectos");
        }
    }
}
