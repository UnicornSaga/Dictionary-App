import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dictionary");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource("/view/hello-view.fxml"));
        Scene scene = new Scene(loader.load(),805,612);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        System.err.close();
        System.setErr(System.out);
        launch(args);
    }
}
