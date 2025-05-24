package controllers;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.*;

import java.util.List;
import java.util.Optional;

public class TicketController {

    @FXML private Label labelUsuario;
    @FXML private TableView<Ticket> tablaTickets;
    @FXML private TableColumn<Ticket, Integer> colId;
    @FXML private TableColumn<Ticket, String> colTitulo;
    @FXML private TableColumn<Ticket, String> colDescripcion;
    @FXML private TableColumn<Ticket, String> colFecha;
    @FXML private TableColumn<Ticket, String> colEstado;
    @FXML private TableColumn<Ticket, String> colCreador;
    @FXML private TableColumn<Ticket, String> colTecnico;

    private final TicketDAO ticketDAO = new TicketDAOImpl();
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    private final EstadoTicketDAO estadoDAO = new EstadoTicketDAOImpl();

    private ObservableList<Ticket> listaTickets;
    private Usuario usuario;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getId()).asObject());
        colTitulo.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTitulo()));
        colDescripcion.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDescripcion()));
        colFecha.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getFechaCreacion().toString()));
        colEstado.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getEstado()));
        colCreador.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNombreCreador()));
        colTecnico.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getNombreTecnico()));

        cargarTickets();
    }

    private void cargarTickets() {
        List<Ticket> tickets = ticketDAO.obtenerTodos();
        listaTickets = FXCollections.observableArrayList(tickets);
        tablaTickets.setItems(listaTickets);
    }

    @FXML
    public void actualizarEstado() {
        if (!puedeEditar()) {
            mostrarAlerta("Permiso denegado. Solo técnicos y administradores pueden actualizar estados.");
            return;
        }

        Ticket ticket = tablaTickets.getSelectionModel().getSelectedItem();
        if (ticket == null) {
            mostrarAlerta("Debe seleccionar un ticket.");
            return;
        }

        EstadoTicket estadoActual = estadoDAO.obtenerTodos().stream()
                .filter(est -> est.getNombreEstado().equals(ticket.getEstado()))
                .findFirst()
                .orElse(null);

        if (estadoActual == null) {
            mostrarAlerta("Estado actual no encontrado.");
            return;
        }

        List<Integer> idsSiguientes = estadoDAO.obtenerEstadosSiguientes(estadoActual.getId());
        if (idsSiguientes.isEmpty()) {
            mostrarAlerta("No hay transiciones posibles desde este estado.");
            return;
        }

        List<EstadoTicket> opciones = estadoDAO.obtenerTodos().stream()
                .filter(est -> idsSiguientes.contains(est.getId()))
                .toList();

        ChoiceDialog<EstadoTicket> dialog = new ChoiceDialog<>(opciones.get(0), opciones);
        dialog.setTitle("Actualizar Estado");
        dialog.setHeaderText("Seleccione el nuevo estado:");
        Optional<EstadoTicket> resultado = dialog.showAndWait();

        resultado.ifPresent(nuevoEstado -> {
            ticket.setEstado(nuevoEstado.getNombreEstado());
            ticketDAO.actualizar(ticket);
            cargarTickets();
        });
    }

    @FXML
    public void asignarTecnico() {
        if (!puedeEditar()) {
            mostrarAlerta("Permiso denegado. Solo técnicos y administradores pueden asignar técnicos.");
            return;
        }

        Ticket ticket = tablaTickets.getSelectionModel().getSelectedItem();
        if (ticket == null) {
            mostrarAlerta("Debe seleccionar un ticket.");
            return;
        }

        List<Usuario> tecnicos = usuarioDAO.obtenerPorRol("Técnico");
        if (tecnicos.isEmpty()) {
            mostrarAlerta("No hay técnicos disponibles.");
            return;
        }

        ChoiceDialog<Usuario> dialog = new ChoiceDialog<>(tecnicos.get(0), tecnicos);
        dialog.setTitle("Asignar Técnico");
        dialog.setHeaderText("Seleccione un técnico:");
        Optional<Usuario> resultado = dialog.showAndWait();

        resultado.ifPresent(tecnico -> {
            ticket.setTecnico(tecnico);
            ticketDAO.actualizar(ticket);
            cargarTickets();
        });
    }

    private boolean puedeEditar() {
        return usuario.getTipoUsuario().equalsIgnoreCase("administrador") ||
                usuario.getTipoUsuario().equalsIgnoreCase("técnico");
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void inicializarUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelUsuario.setText("Bienvenido, " + usuario.getNombre() + " [" + usuario.getTipoUsuario() + "]");
    }
}
