/**
 *
 * @author abrah
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginUno extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        cambiarEscena("login.fxml");
    }

    public static void cambiarEscena(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(LoginUno.class.getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(fxml.equals("Tickets.fxml") ? "Gestión de Tickets" : "Login");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}



