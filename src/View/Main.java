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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application
{
    private MyModel model;
    public static Media myMedia;
    public static MediaPlayer mediaPlayer;


    @Override
    public void start(Stage primaryStage) throws Exception {
        String path = "resources/Sounds/start.mp3";
        myMedia = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(myMedia);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);


        playStartMusic("Play");
        model = new MyModel();
        model.startServers();
        Parent root = FXMLLoader.load(getClass().getResource("BackGround.fxml"));
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

    public static void playStartMusic(String s)
    {
        if (s.equals("Stop"))
        {
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.setMute(true);
            mediaPlayer = null;
        }
        if (s.equals("Play"))
            mediaPlayer.play();
    }
}
