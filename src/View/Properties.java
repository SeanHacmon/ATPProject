package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import View.Properties;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static View.MyViewController.propertyStage;
import static View.Properties.*;


public class Properties implements Initializable {
    public Slider backSlider;
    public Slider fxSlider;
    public ToggleButton muteToggle;
    public ChoiceBox<PlayerConfig.GenerateAlgorithm> choiceBox;
    public Stage stage ;
    public MyViewController viewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyView.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            viewController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayerConfig config = PlayerConfig.getInstance();
        this.backSlider.setValue(config.getBackVolume() * 100);
        this.fxSlider.setValue(config.getFxVolume() * 100);
        this.muteToggle.setSelected(config.isMute());
        this.muteToggle.setText(config.isMute() ? "On" : "Off");
        this.muteToggle.setOnMouseClicked((e) ->
                this.muteToggle.setText(this.muteToggle.getText().equals("On") ? "Off" : "On")
        );
        this.choiceBox.setValue(config.getGenerateAlgorithm());
        this.choiceBox.getItems().add(0, PlayerConfig.GenerateAlgorithm.MyMazeGenerator);
        this.choiceBox.getItems().add(1, PlayerConfig.GenerateAlgorithm.SimpleMazeGenerator);
        this.choiceBox.getItems().add(2, PlayerConfig.GenerateAlgorithm.EmptyMazeGenerator);
//        config.setGenerateAlgorithm(this.choiceBox.getValue());
        this.choiceBox.setValue(config.getGenerateAlgorithm());
    }

    public void ApplyButton(ActionEvent actionEvent) {
        PlayerConfig config = PlayerConfig.getInstance();
        config.setBackVolume(this.backSlider.getValue() / 100);
        config.setFxVolume(this.fxSlider.getValue() / 100);
        config.setMute(this.muteToggle.isSelected());
//        PlayerConfig.GenerateAlgorithm g = config.getGenerateAlgorithm();
//        switch (PlayerConfig.toAlgorithm(this.choiceBox.getValue().toString())) {
//            case EmptyMazeGenerator:
//                if (this.choiceBox.getValue() != null)
//                    config.setGenerateAlgorithm(PlayerConfig.toAlgorithm("Empty"));
//                break;
//            case SimpleMazeGenerator:
//                if (this.choiceBox.getValue() != null)
//                    config.setGenerateAlgorithm(PlayerConfig.toAlgorithm("Simple"));
//                break;
//            case MyMazeGenerator:
//                if (this.choiceBox.getValue() != null)
//                    config.setGenerateAlgorithm(PlayerConfig.toAlgorithm("My"));
//                break;
//        }
//        if (this.choiceBox.getValue() != null)
//            config.setGenerateAlgorithm(PlayerConfig.toAlgorithm(this.choiceBox.getValue().toString()));
        if (this.choiceBox.getValue() != null)
            config.setGenerateAlgorithm(PlayerConfig.toAlgorithm(this.choiceBox.getValue().toString()));
        propertyStage.close();
//        StageGenerator.getInstance(StageGenerator.StageName.Properties).close();
    }
}