package controllers;

import dao.DepartamentoDAO;
import dao.DepartamentoDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Departamento;
import java.util.List;
import java.util.Optional;

public class DepartamentoController {
    @FXML private TableView<Departamento> tablaDepartamentos;
    @FXML private TableColumn<Departamento, Integer> colId;
    @FXML private TableColumn<Departamento, String> colNombre;
    @FXML private TableColumn<Departamento, String> colDescripcion;

    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;

    private DepartamentoDAO departamentoDAO = new DepartamentoDAOImpl();

    private ObservableList<Departamento> listaDepartamentos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colDescripcion.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());

        cargarDepartamentos();
    }

    private void cargarDepartamentos() {
        List<Departamento> departamentos = departamentoDAO.obtenerTodos();
        listaDepartamentos.setAll(departamentos);
        tablaDepartamentos.setItems(listaDepartamentos);
    }

    @FXML
    private void agregarDepartamento() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (nombre.isEmpty()) {
            mostrarAlerta("Error", "El nombre no puede estar vacío.");
            return;
        }

        Departamento nuevo = new Departamento(nombre, descripcion);
        departamentoDAO.insertar(nuevo);
        cargarDepartamentos();

        txtNombre.clear();
        txtDescripcion.clear();
    }

    @FXML
    private void editarDepartamento() {
        Departamento seleccionado = tablaDepartamentos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un departamento para editar.");
            return;
        }

        String nuevoNombre = txtNombre.getText().trim();
        String nuevaDescripcion = txtDescripcion.getText().trim();

        if (nuevoNombre.isEmpty()) {
            mostrarAlerta("Error", "El nombre no puede estar vacío.");
            return;
        }

        seleccionado.setNombre(nuevoNombre);
        seleccionado.setDescripcion(nuevaDescripcion);

        departamentoDAO.actualizar(seleccionado);
        cargarDepartamentos();
    }

    @FXML
    private void eliminarDepartamento() {
        Departamento seleccionado = tablaDepartamentos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un departamento para eliminar.");
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Estás seguro de eliminar el departamento seleccionado?",
                ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.YES) {
            departamentoDAO.eliminar(seleccionado.getId());
            cargarDepartamentos();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}