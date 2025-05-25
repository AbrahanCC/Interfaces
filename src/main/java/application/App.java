package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import util.Conexion;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        // Probar conexión antes de cargar la interfaz
        Conexion.getConexion();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Inicio de Sesión");
        stage.setScene(scene);
        stage.show();
    }
}

