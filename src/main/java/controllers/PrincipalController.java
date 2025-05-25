package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import modelo.Usuario;

import java.io.IOException;

public class PrincipalController {

    @FXML private Button btnConfiguracion;
    @FXML private Button btnUsuarios;
    @FXML private Button btnEstados;
    @FXML private Button btnTickets;
    @FXML private Button btnDepartamentos;

    private Usuario usuario;
    private Usuario usuarioActual;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.usuarioActual = usuario;
        System.out.println("Bienvenido, " + usuario.getNombre());

        String tipo = usuario.getTipoUsuario().toLowerCase();

        switch (tipo) {
            case "administrador":
                // Acceso completo: no ocultamos nada
                break;

            case "tecnico":
                if (btnConfiguracion != null) btnConfiguracion.setVisible(false);
                if (btnUsuarios != null) btnUsuarios.setVisible(false);
                if (btnDepartamentos != null) btnDepartamentos.setVisible(false);
                break;

            case "usuario":
                if (btnConfiguracion != null) btnConfiguracion.setVisible(false);
                if (btnUsuarios != null) btnUsuarios.setVisible(false);
                if (btnEstados != null) btnEstados.setVisible(false);
                if (btnDepartamentos != null) btnDepartamentos.setVisible(false);
                break;

            default:
                System.out.println("Tipo de usuario no reconocido.");
                break;
        }
    }

    public void abrirUsuarios(ActionEvent event) {
        cargarVentana("/view/usuario.fxml", "Gestión de Usuarios");
    }

    public void abrirEstados(ActionEvent event) {
        cargarVentana("/view/estadoTicket.fxml", "Gestión de Estados");
    }

    public void abrirTickets(ActionEvent event) {
        cargarVentana("/view/tickets.fxml", "Gestión de Tickets");
    }

    public void abrirConfiguracion(ActionEvent event) {
        cargarVentana("/view/configuracion.fxml", "Configuración del Sistema");
    }

    public void abrirDepartamentos(ActionEvent event) {
        cargarVentana("/view/departamento.fxml", "Gestión de Departamentos");
    }

    @FXML
    private void abrirCrearTicket() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/crearTicket.fxml"));
            Parent root = loader.load();

            CrearTicketController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = new Stage();
            stage.setTitle("Crear Ticket");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inicio de Sesión");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarVentana(String ruta, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
