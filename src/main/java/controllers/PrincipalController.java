package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Usuario;

public class PrincipalController {

        public void abrirUsuarios(ActionEvent event) {
            cargarVentana("/view/usuario.fxml", "Gestión de Usuarios");
        }

        public void abrirEstados(ActionEvent event) {
            cargarVentana("/view/estadoTicket.fxml", "Gestión de Estados");
        }

        public void abrirTickets(ActionEvent event) {
            cargarVentana("/view/tickets.fxml", "Gestión de Tickets");
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

        private Usuario usuario;

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
            // Aquí podrías cargar datos personalizados, por ejemplo:
            System.out.println("Bienvenido, " + usuario.getNombre());
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
