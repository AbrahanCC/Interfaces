package controllers;

import dao.ConfiguracionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modelo.NivelPrioridad;
import modelo.Usuario;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ConfiguracionController {

    @FXML private Label labelBienvenida;
    @FXML private TextField txtEmpresa;
    @FXML private ComboBox<String> comboIdioma;
    @FXML private ComboBox<String> comboZonaHoraria;
    @FXML private TextField txtDiasVencimiento;
    @FXML private RadioButton radioAlta;
    @FXML private RadioButton radioMedia;
    @FXML private RadioButton radioBaja;
    @FXML private ToggleGroup grupoPrioridad;
    @FXML private Label labelLogoSeleccionado;

    private String usuarioNombre;
    private String rutaLogoSeleccionada = null;

    public void setUsuario(Usuario usuario) {
        setUsuarioNombre(usuario.getNombre());
    }

    public void setUsuarioNombre(String nombre) {
        this.usuarioNombre = nombre;
        labelBienvenida.setText("Bienvenido, " + nombre);
    }

    @FXML
    public void initialize() {
        comboIdioma.getItems().setAll("Español", "Inglés");
        comboIdioma.getSelectionModel().selectFirst();

        comboZonaHoraria.getItems().setAll(
                "America/Mexico_City",
                "America/Bogota",
                "America/Lima",
                "America/Guatemala",
                "America/Argentina/Buenos_Aires"
        );
        comboZonaHoraria.getSelectionModel().selectFirst();

        grupoPrioridad = new ToggleGroup();
        radioAlta.setToggleGroup(grupoPrioridad);
        radioMedia.setToggleGroup(grupoPrioridad);
        radioBaja.setToggleGroup(grupoPrioridad);
        radioMedia.setSelected(true);

        labelLogoSeleccionado.setText("Ningún archivo seleccionado");
    }

    @FXML
    private void seleccionarLogo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar logo de empresa");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File archivo = fileChooser.showOpenDialog(new Stage());
        if (archivo != null) {
            rutaLogoSeleccionada = archivo.getAbsolutePath();
            labelLogoSeleccionado.setText(archivo.getName());
        }
    }

    @FXML
    private void guardarConfiguracion(ActionEvent event) {
        try {
            String empresa = txtEmpresa.getText().trim();
            String idioma = comboIdioma.getValue();
            String zona = comboZonaHoraria.getValue();
            String prioridad = radioAlta.isSelected() ? "alta" : (radioBaja.isSelected() ? "baja" : "media");

            if (empresa.isEmpty() || idioma == null || zona == null || txtDiasVencimiento.getText().trim().isEmpty()) {
                mostrarAlerta("Advertencia", "⚠️ Todos los campos deben estar completos.");
                return;
            }

            int dias = Integer.parseInt(txtDiasVencimiento.getText().trim());
            if (dias <= 0) {
                mostrarAlerta("Advertencia", "⚠️ Los días deben ser mayores a cero.");
                return;
            }

            // Crear lista con un solo nivel por simplicidad
            List<NivelPrioridad> prioridades = List.of(new NivelPrioridad(prioridad));

            // Guardar en la BD usando el DAO
            try {
                ConfiguracionDAO.guardarConfiguracion(empresa, idioma, zona, dias, rutaLogoSeleccionada, prioridades);
                mostrarAlerta("Éxito", "✅ Configuración guardada correctamente.");
            } catch (RuntimeException e) {
                mostrarAlerta("Error", "❌ No se pudo guardar la configuración: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Advertencia", "⚠️ Ingrese un número válido para los días.");
        }
    }

    @FXML
    private void cancelarConfiguracion() {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtEmpresa.clear();
        comboIdioma.getSelectionModel().selectFirst();
        comboZonaHoraria.getSelectionModel().selectFirst();
        txtDiasVencimiento.clear();
        radioMedia.setSelected(true);
        labelLogoSeleccionado.setText("Ningún archivo seleccionado");
        rutaLogoSeleccionada = null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

