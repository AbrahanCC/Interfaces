import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TicketsController implements Initializable {

    @FXML
    private TableView<Ticket> tableTickets;

    @FXML
    private TableColumn<Ticket, Integer> colId;

    @FXML
    private TableColumn<Ticket, String> colDescripcion;

    @FXML
    private TableColumn<Ticket, String> colEstado;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private ComboBox<String> cmbEstado;

    @FXML
    private ComboBox<String> cmbDepartamento;

    @FXML
    private Button btnGuardar;

    private final ObservableList<Ticket> listaTickets = FXCollections.observableArrayList();
    private final Map<String, Departamento> mapaDepartamentos = new HashMap<>();
    private int contadorId = 1;
    private Ticket ticketSeleccionado = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        colEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        tableTickets.setItems(listaTickets);

        cmbEstado.setItems(FXCollections.observableArrayList("Abierto", "En Proceso", "Cerrado"));
        cmbDepartamento.setItems(FXCollections.observableArrayList("Soporte", "Ventas", "TI"));

        tableTickets.setOnMouseClicked(event -> {
            ticketSeleccionado = tableTickets.getSelectionModel().getSelectedItem();
            if (ticketSeleccionado != null) {
                txtDescripcion.setText(ticketSeleccionado.getDescripcion());
                cmbEstado.setValue(ticketSeleccionado.getEstado());
                cmbDepartamento.setValue(ticketSeleccionado.getDepartamento());
            }
        });
    }

    @FXML
    private void handleCrear(ActionEvent event) {
        limpiarFormulario();
        ticketSeleccionado = null;
    }

    @FXML
    private void handleEditar(ActionEvent event) {
        ticketSeleccionado = tableTickets.getSelectionModel().getSelectedItem();
        if (ticketSeleccionado != null) {
            txtDescripcion.setText(ticketSeleccionado.getDescripcion());
            cmbEstado.setValue(ticketSeleccionado.getEstado());
            cmbDepartamento.setValue(ticketSeleccionado.getDepartamento());
        } else {
            mostrarAlerta("Selecciona un ticket para editar.");
        }
    }

    @FXML
    private void handleEliminar(ActionEvent event) {
        Ticket seleccionado = tableTickets.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaTickets.remove(seleccionado);
            limpiarFormulario();
        } else {
            mostrarAlerta("Selecciona un ticket para eliminar.");
        }
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        String descripcion = txtDescripcion.getText();
        String estado = cmbEstado.getValue();
        String departamento = cmbDepartamento.getValue();

        if (descripcion.isEmpty() || estado == null || departamento == null) {
            mostrarAlerta("Por favor completa todos los campos.");
            return;
        }

        mapaDepartamentos.putIfAbsent(departamento, new Departamento(departamento));
        Departamento depto = mapaDepartamentos.get(departamento);

        if (ticketSeleccionado == null) {
            Ticket nuevo = new Ticket(contadorId++, descripcion, estado, departamento);
            listaTickets.add(nuevo);
            depto.agregarTicket(nuevo);
        } else {
            ticketSeleccionado.setDescripcion(descripcion);
            if (!ticketSeleccionado.getEstado().equals(estado)) {
                ticketSeleccionado.cambiarEstado(estado);
            }
            ticketSeleccionado.setDepartamento(departamento);
            tableTickets.refresh();
        }

        limpiarFormulario();
    }

    @FXML
    private void handleAtenderSiguiente(ActionEvent event) {
        String depto = cmbDepartamento.getValue();
        if (depto == null) {
            mostrarAlerta("Selecciona un departamento para atender el siguiente ticket.");
            return;
        }

        Departamento departamento = mapaDepartamentos.get(depto);
        if (departamento != null) {
            Ticket siguiente = departamento.obtenerSiguienteTicket();
            if (siguiente != null) {
                mostrarAlerta("Atendiendo ticket ID: " + siguiente.getId() + " - " + siguiente.getDescripcion());
            } else {
                mostrarAlerta("No hay tickets pendientes en este departamento.");
            }
        } else {
            mostrarAlerta("Departamento no encontrado.");
        }
    }

    @FXML
    private void handleDeshacer(ActionEvent event) {
        Ticket seleccionado = tableTickets.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (seleccionado.deshacerUltimoCambio()) {
                tableTickets.refresh();
                txtDescripcion.setText(seleccionado.getDescripcion());
                cmbEstado.setValue(seleccionado.getEstado());
                cmbDepartamento.setValue(seleccionado.getDepartamento());
            } else {
                mostrarAlerta("No hay cambios para deshacer.");
            }
        } else {
            mostrarAlerta("Selecciona un ticket para deshacer el cambio.");
        }
    }

    private void limpiarFormulario() {
        txtDescripcion.clear();
        cmbEstado.setValue(null);
        cmbDepartamento.setValue(null);
        ticketSeleccionado = null;
        tableTickets.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atención");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}


