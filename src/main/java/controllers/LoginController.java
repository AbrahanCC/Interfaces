package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import modelo.Usuario;
import util.Conexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML private TextField userField;
    @FXML private PasswordField passwordField;

    @FXML
    private void cerrarVentana(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String correo = userField.getText();
        String clave = passwordField.getText();

        if (correo.isEmpty() || clave.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, ingresa correo y clave.");
            return;
        }

        try (Connection conn = Conexion.getConexion()) {
            String sql = "SELECT id, nombre, tipo_usuario FROM usuarios WHERE correo = ? AND clave = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, clave);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String tipoUsuario = rs.getString("tipo_usuario");

                Usuario usuario = new Usuario(id, nombre, correo, tipoUsuario, clave);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader;
                Parent root;
                Scene nuevaEscena;

                if (tipoUsuario.equalsIgnoreCase("administrador")) {
                    loader = new FXMLLoader(getClass().getResource("/view/principal.fxml"));
                    root = loader.load();
                    PrincipalController controller = loader.getController();
                    controller.setUsuario(usuario);
                    stage.setTitle("Panel Principal");
                } else {
                    loader = new FXMLLoader(getClass().getResource("/view/tickets.fxml"));
                    root = loader.load();
                    TicketController controller = loader.getController();
                    controller.inicializarUsuario(usuario);
                    stage.setTitle("Gestión de Tickets");
                }

                nuevaEscena = new Scene(root);
                stage.setScene(nuevaEscena);
                stage.show();

            } else {
                mostrarAlerta("Credenciales inválidas", "Correo o clave incorrectos.");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al iniciar sesión.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}