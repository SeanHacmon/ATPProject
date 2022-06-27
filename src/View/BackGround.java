package View;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import Model.MyModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;


import java.io.IOException;
import java.nio.file.Paths;

public class BackGround
{
    private Stage stage = new Stage();
    public MyViewController viewController;
    public MyModel model;
    private AudioClip backgroundAudio;

    public BackGround() {}//this.playBackground();

    public void StartButton(ActionEvent actionEvent)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            viewController = fxmlLoader.getController();
            stage.setTitle("Pokemon LeafGreen");
            stage.setScene(new Scene(root, 1000, 900));
            stage.show();
            stage.setOnCloseRequest(e -> {
                e.consume();
                safeExit(stage);
            });
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
    }
    public void safeExit(Stage mainStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText("Are you sure you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            model = new MyModel();
            model.stopServers();
            mainStage.close();
            Platform.exit();
        }
    }

    private void playBackground()
    {
//        String path = "resources/Sounds/background.mp3";
        String path;
        try {
            path = getClass().getResource("/Sounds/background.mp3").toString();
            Media myMedia = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(myMedia);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Start sound not found");
        }
    }


    public void ExitButton(ActionEvent actionEvent)
    {
        model = new MyModel();
        stage.close();
        Platform.exit();
        model.stopServers();
    }

    public void setStage(Stage stage) {this.stage = stage;}
}
