package controllers;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Usuario;

import java.util.List;
import java.util.Optional;

    public class UsuarioController {

        @FXML private TableView<Usuario> tablaUsuarios;
        @FXML private TableColumn<Usuario, Integer> colId;
        @FXML private TableColumn<Usuario, String> colNombre;
        @FXML private TableColumn<Usuario, String> colCorreo;
        @FXML private TableColumn<Usuario, String> colTipo;

        @FXML private TextField txtNombre;
        @FXML private TextField txtCorreo;
        @FXML private ComboBox<String> comboTipoUsuario;
        @FXML private PasswordField txtClave;

        private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        private final ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();

        @FXML
        public void initialize() {
            colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
            colCorreo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCorreo()));
            colTipo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipoUsuario()));

            comboTipoUsuario.setItems(FXCollections.observableArrayList("administrador", "tecnico", "usuario"));

            cargarUsuarios();
        }

        private void cargarUsuarios() {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            listaUsuarios.setAll(usuarios);
            tablaUsuarios.setItems(listaUsuarios);
        }

        @FXML
        private void agregarUsuario() {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String clave = txtClave.getText();
            String tipoUsuario = comboTipoUsuario.getValue();

            if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty() || tipoUsuario == null) {
                mostrarAlerta("Error", "Todos los campos deben estar completos.");
                return;
            }

            Usuario usuario = new Usuario(0, nombre, correo, clave, tipoUsuario);
            usuarioDAO.guardar(usuario);

            cargarUsuarios();
            limpiarCampos();
        }

        @FXML
        private void editarUsuario() {
            Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarAlerta("Advertencia", "Seleccione un usuario para editar.");
                return;
            }

            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String tipo = comboTipoUsuario.getValue();

            if (nombre.isEmpty() || correo.isEmpty() || tipo == null) {
                mostrarAlerta("Error", "Todos los campos deben estar completos.");
                return;
            }

            seleccionado.setNombre(nombre);
            seleccionado.setCorreo(correo);
            seleccionado.setTipoUsuario(tipo);

            usuarioDAO.actualizar(seleccionado);
            cargarUsuarios();
            limpiarCampos();
        }

        @FXML
        private void eliminarUsuario() {
            Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarAlerta("Advertencia", "Seleccione un usuario para eliminar.");
                return;
            }

            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Estás seguro de eliminar el usuario seleccionado?",
                    ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.YES) {
                usuarioDAO.eliminar(seleccionado.getId());
                cargarUsuarios();
                limpiarCampos();
            }
        }

        private void mostrarAlerta(String titulo, String mensaje) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        }

        private void limpiarCampos() {
            txtNombre.clear();
            txtCorreo.clear();
            txtClave.clear();
            comboTipoUsuario.getSelectionModel().clearSelection();
        }
    }