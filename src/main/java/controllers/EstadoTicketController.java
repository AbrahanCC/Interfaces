package controllers;

import dao.EstadoTicketDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import modelo.EstadoTicket;

public class EstadoTicketController {

    @FXML
    private TextField nombreEstadoField;

    @FXML
    private TextArea descripcionField;

    @FXML
    private CheckBox esFinalCheck;

    @FXML
    private Button guardarBtn;

    @FXML
    private TableView<EstadoTicket> tablaEstados;

    @FXML
    private TableColumn<EstadoTicket, Integer> idColumna;

    @FXML
    private TableColumn<EstadoTicket, String> nombreColumna;

    private final EstadoTicketDAOImpl estadoDAO = new EstadoTicketDAOImpl();
    private final ObservableList<EstadoTicket> listaEstados = FXCollections.observableArrayList();
    private EstadoTicket estadoSeleccionado = null;

    @FXML
    public void initialize() {
        idColumna.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nombreColumna.setCellValueFactory(cellData -> cellData.getValue().nombreEstadoProperty());

        tablaEstados.setItems(listaEstados);
        cargarEstados();
    }

    private void cargarEstados() {
        listaEstados.clear();
        listaEstados.addAll(estadoDAO.obtenerTodos());
    }

    @FXML
    private void guardarEstado() {
        String nombre = nombreEstadoField.getText().trim();
        String descripcion = descripcionField.getText().trim();
        boolean esFinal = esFinalCheck.isSelected();

        if (nombre.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo vacío", "Por favor ingrese un nombre para el estado.");
            return;
        }

        if (estadoSeleccionado == null) {
            // Nuevo estado
            EstadoTicket nuevoEstado = new EstadoTicket();
            nuevoEstado.setNombreEstado(nombre);
            nuevoEstado.setDescripcion(descripcion);
            nuevoEstado.setEsFinal(esFinal);

            if (estadoDAO.guardarEstado(nuevoEstado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Estado agregado correctamente.");
                cargarEstados();
                limpiarFormulario();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo agregar el estado.");
            }
        } else {
            // Editar estado
            estadoSeleccionado.setNombreEstado(nombre);
            estadoSeleccionado.setDescripcion(descripcion);
            estadoSeleccionado.setEsFinal(esFinal);

            if (estadoDAO.guardarEstado(estadoSeleccionado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Estado actualizado correctamente.");
                cargarEstados();
                limpiarFormulario();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el estado.");
            }
        }
    }

    @FXML
    private void seleccionarEstado(MouseEvent event) {
        estadoSeleccionado = tablaEstados.getSelectionModel().getSelectedItem();

        if (estadoSeleccionado != null) {
            nombreEstadoField.setText(estadoSeleccionado.getNombreEstado());
            descripcionField.setText(estadoSeleccionado.getDescripcion());
            esFinalCheck.setSelected(estadoSeleccionado.isEsFinal());
        }
    }

    @FXML
    private void eliminarEstado() {
        if (estadoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Seleccione un estado para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea eliminar el estado seleccionado?");
        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                if (estadoDAO.eliminarEstado(estadoSeleccionado.getId())) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Estado eliminado correctamente.");
                    cargarEstados();
                    limpiarFormulario();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el estado.");
                }
            }
        });
    }

    @FXML
    private void limpiarFormulario() {
        nombreEstadoField.clear();
        descripcionField.clear();
        esFinalCheck.setSelected(false);
        estadoSeleccionado = null;
        tablaEstados.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
