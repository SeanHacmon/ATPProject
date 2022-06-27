package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Main extends Application
{
    private MyModel model;
    @Override
    public void start(Stage primaryStage) throws Exception {
        model = new MyModel();
        model.startServers();
        Parent root = FXMLLoader.load(getClass().getResource("MyView.fxml"));
        primaryStage.setTitle("Pokemon LeafGreen");
        primaryStage.setScene(new Scene(root, 1000, 900));
        this.model.setStage(primaryStage);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            safeExit(primaryStage);
        });
    }


    public void safeExit(Stage mainStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText("Are you sure you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            model.stopServers();
            mainStage.close();
            Platform.exit();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
