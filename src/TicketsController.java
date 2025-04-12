/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
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
    private Button btnGuardar;

    private ObservableList<Ticket> listaTickets = FXCollections.observableArrayList();
    private int contadorId = 1;

    private Ticket ticketSeleccionado = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        colEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());

        tableTickets.setItems(listaTickets);
        cmbEstado.setItems(FXCollections.observableArrayList("Abierto", "En Proceso", "Cerrado"));

        // Si el usuario hace clic en una fila, se carga en el formulario
        tableTickets.setOnMouseClicked(event -> {
            ticketSeleccionado = tableTickets.getSelectionModel().getSelectedItem();
            if (ticketSeleccionado != null) {
                txtDescripcion.setText(ticketSeleccionado.getDescripcion());
                cmbEstado.setValue(ticketSeleccionado.getEstado());
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

        if (descripcion.isEmpty() || estado == null) {
            mostrarAlerta("Por favor completa todos los campos.");
            return;
        }

        if (ticketSeleccionado == null) {
            Ticket nuevo = new Ticket(contadorId++, descripcion, estado);
            listaTickets.add(nuevo);
        } else {
            ticketSeleccionado.setDescripcion(descripcion);
            ticketSeleccionado.setEstado(estado);
            tableTickets.refresh();
        }

        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtDescripcion.clear();
        cmbEstado.setValue(null);
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


