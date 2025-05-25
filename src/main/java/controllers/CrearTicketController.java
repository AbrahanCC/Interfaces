package controllers;

import dao.EstadoTicketDAOImpl;
import dao.TicketDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import modelo.EstadoTicket;
import modelo.Ticket;
import modelo.Usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CrearTicketController {

    @FXML
    private TextArea descripcionArea;

    private final TicketDAOImpl ticketDAO = new TicketDAOImpl();
    private final EstadoTicketDAOImpl estadoDAO = new EstadoTicketDAOImpl();

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @FXML
    private void guardarTicket() {
        String descripcion = descripcionArea.getText().trim();

        if (descripcion.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor complete la descripción.");
            return;
        }

        // Buscar estado "Abierto"
        List<EstadoTicket> estados = estadoDAO.obtenerTodos();
        EstadoTicket estadoAbierto = estados.stream()
                .filter(est -> "Abierto".equalsIgnoreCase(est.getNombreEstado()))
                .findFirst()
                .orElse(null);

        if (estadoAbierto == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró el estado 'Abierto' en la base de datos.");
            return;
        }

        // Crear ticket con los datos
        Ticket ticket = new Ticket();
        ticket.setDescripcion(descripcion);
        ticket.setFechaCreacion(LocalDate.now());
        ticket.setEstado(estadoAbierto);
        ticket.setCreador(usuario);
        ticket.setTecnico(null); // El técnico se asigna más adelante

        boolean exito = ticketDAO.insertar(ticket);

        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El ticket ha sido guardado correctamente.");
            limpiarFormulario();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar el ticket.");
        }
    }

    @FXML
    private void abrirCrearTicket() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistas/crear_ticket.fxml"));
            Parent root = loader.load();

            CrearTicketController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = new Stage();
            stage.setTitle("Crear Ticket");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar", "No se pudo cargar la vista de creación de ticket.");
        }
    }

    private void limpiarFormulario() {
        descripcionArea.clear();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    public void volverAlMenu(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
