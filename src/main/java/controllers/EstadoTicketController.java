package controllers;

import dao.EstadoTicketDAO;
import dao.EstadoTicketDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.EstadoTicket;

import java.util.ArrayList;
import java.util.List;

public class EstadoTicketController {

    @FXML private TableView<EstadoTicket> tablaEstados;
    @FXML private TableColumn<EstadoTicket, String> colNombre;
    @FXML private TableColumn<EstadoTicket, String> colDescripcion;
    @FXML private TableColumn<EstadoTicket, Boolean> colEsFinal;

    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private CheckBox chkEsFinal;

    @FXML private ListView<EstadoTicket> listaTransiciones;

    @FXML private Button btnGuardar;
    @FXML private Button btnEliminar;

    private final EstadoTicketDAO estadoDAO = new EstadoTicketDAOImpl();
    private ObservableList<EstadoTicket> listaObservable;
    private EstadoTicket estadoSeleccionado;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(c -> c.getValue().nombreEstadoProperty());
        colDescripcion.setCellValueFactory(c -> c.getValue().descripcionProperty());
        colEsFinal.setCellValueFactory(c -> c.getValue().esFinalProperty());

        listaTransiciones.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        cargarEstados();

        tablaEstados.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                estadoSeleccionado = newSel;
                mostrarDatosEstado(newSel);
            }
        });
    }

    private void cargarEstados() {
        List<EstadoTicket> estados = estadoDAO.obtenerTodos();
        listaObservable = FXCollections.observableArrayList(estados);
        tablaEstados.setItems(listaObservable);
        listaTransiciones.setItems(listaObservable);
    }

    private void mostrarDatosEstado(EstadoTicket estado) {
        txtNombre.setText(estado.getNombreEstado());
        txtDescripcion.setText(estado.getDescripcion());
        chkEsFinal.setSelected(estado.isEsFinal());

        List<Integer> transicionesIds = estadoDAO.obtenerEstadosSiguientes(estado.getId());

        listaTransiciones.getSelectionModel().clearSelection();
        for (EstadoTicket est : listaObservable) {
            if (transicionesIds.contains(est.getId())) {
                listaTransiciones.getSelectionModel().select(est);
            }
        }
    }

    @FXML
    private void guardarEstado() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        boolean esFinal = chkEsFinal.isSelected();

        if (nombre.isEmpty()) {
            mostrarAlerta("Validación", "El nombre no puede estar vacío.");
            return;
        }

        EstadoTicket estado = new EstadoTicket();
        if (estadoSeleccionado != null) {
            estado.setId(estadoSeleccionado.getId());
        }

        estado.setNombreEstado(nombre);
        estado.setDescripcion(descripcion);
        estado.setEsFinal(esFinal);

        List<Integer> idsSiguientes = new ArrayList<>();
        for (EstadoTicket e : listaTransiciones.getSelectionModel().getSelectedItems()) {
            idsSiguientes.add(e.getId());
        }
        estado.setEstadosSiguientes(idsSiguientes);

        boolean exito = estadoDAO.guardarEstado(estado);
        if (exito) {
            limpiarFormulario();
            cargarEstados();
        } else {
            mostrarAlerta("Error", "No se pudo guardar el estado.");
        }
    }

    @FXML
    private void eliminarEstado() {
        if (estadoSeleccionado == null) {
            mostrarAlerta("Seleccione un estado", "Debe seleccionar un estado para eliminar.");
            return;
        }

        boolean confirmado = mostrarConfirmacion("¿Está seguro?", "¿Desea eliminar este estado?");
        if (!confirmado) return;

        boolean exito = estadoDAO.eliminarEstado(estadoSeleccionado.getId());
        if (exito) {
            limpiarFormulario();
            cargarEstados();
        } else {
            mostrarAlerta("No se puede eliminar", "Este estado está en uso y no se puede eliminar.");
        }
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtDescripcion.clear();
        chkEsFinal.setSelected(false);
        listaTransiciones.getSelectionModel().clearSelection();
        estadoSeleccionado = null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private boolean mostrarConfirmacion(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        return alerta.showAndWait().filter(btn -> btn == ButtonType.OK).isPresent();
    }
}