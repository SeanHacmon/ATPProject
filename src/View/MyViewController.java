package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.layout.VBox;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import static View.Main.playStartMusic;
import static View.Main.mediaPlayer;
import static View.Main.myMedia;



public class MyViewController implements IView, Initializable, Observer
{
    public GridPane pane;
    FileChooser fileChooser = new FileChooser();
    VBox menu;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public Label label_Prow;
    public Label label_Pcol;
    public MazeCanvasDisplay mazeDisplay = new MazeCanvasDisplay();
    public MenuItem saveItem;
    protected MyMazeGenerator mg = new MyMazeGenerator();
    private MyViewModel viewModel;

    private StringProperty updatePlayerRow = new SimpleStringProperty();
    private StringProperty updatePlayerCol = new SimpleStringProperty();

    public MyViewController()
    {
        viewModel = new MyViewModel();
        viewModel.addObserver(this);
        // todo propetis window.
    }

    public void generateMazeButton(ActionEvent actionEvent)
    {
        mediaPlayer.stop();
        String path = "resources/Sounds/RunningMaze.mp3";
        myMedia = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(myMedia);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeColumns.getText());
        viewModel.transformMaze(rows, cols);
        viewModel.setSolution(null);
    }

    public void keyPress(KeyEvent keyevent)
    {
        viewModel.movePlayer(keyevent);
        keyevent.consume();
    }

    public boolean inBounds(int row, int col)
    {
        return (0 <= row && row < mazeDisplay.maze.row) && (0 <= col && col < mazeDisplay.maze.col);
    }

    public void ClickedMouse(MouseEvent actionEvent)
    {
        mazeDisplay.requestFocus();}


    public void solveMaze(ActionEvent actionEvent)
    {
        viewModel.solveMaze();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        label_Prow.textProperty().bind(updatePlayerRow);
        label_Pcol.textProperty().bind(updatePlayerCol);
    }

    // getters & setters.
    public String getUpdatePlayerRow() {return updatePlayerRow.get();}
    public void setUpdatePlayerRow(int updatePlayerRow) {this.updatePlayerRow.set("" + updatePlayerRow);}
    public String getUpdatePlayerCol() {return updatePlayerCol.get();}
    public void setUpdatePlayerCol(int updatePlayerCol) {this.updatePlayerCol.set("" + updatePlayerCol);}
    public void setViewController(MyViewModel m){this.viewModel = m;}

    public void SaveButton(ActionEvent actionEvent)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        File chosen = fc.showSaveDialog(null);
        if (chosen != null)
            this.viewModel.saveMazeFile(chosen);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        String str = (String)arg;
        switch (str)
        {
            case "Maze generated" -> {
                mazeDisplay.drawMaze(viewModel.getMaze());
                mazeDisplay.setSolution(null);
            }
            case "Player was moved" ->{
                mazeDisplay.setPlayerPosition
                    (viewModel.getPosition().getRowIndex(),viewModel.getPosition().getColumnIndex());
                setUpdatePlayerRow(viewModel.getPlayerPosition().getRowIndex());
                setUpdatePlayerCol(viewModel.getPlayerPosition().getColumnIndex());}
            case "Solved Maze" -> mazeDisplay.setSolution(viewModel.getSolution());

            case "Won game" -> {
                Stage stage = new Stage();
                EndGame(stage);
            }
            case "Maze Loaded" -> {
                Position p = viewModel.getPlayerPosition();
                mazeDisplay.drawMaze(viewModel.getMaze());
                setUpdatePlayerRow(p.getRowIndex());
                setUpdatePlayerCol(p.getColumnIndex());
            }
            default -> System.out.println("didnt do anything");
        }

    }

    public void ExitButton(ActionEvent actionEvent)
    {
        this.viewModel.exit();
        Platform.exit();
    }

    public void EndGame(Stage mainStage) {
        ButtonType Yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType No = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,"Start a new game?",Yes,No);
        alert.setHeaderText("You won the game!");


        if (alert.showAndWait().get() == Yes)
        {
            int rows = Integer.parseInt(textField_mazeRows.getText());
            int cols = Integer.parseInt(textField_mazeColumns.getText());
            viewModel.transformMaze(rows, cols);
            viewModel.setSolution(null);
            mazeDisplay.setSolution(null);
            mazeDisplay.drawMaze(viewModel.getMaze());
        }
        else
        {
            viewModel.getModel().stopServers();
            mainStage.close();
            Platform.exit();
        }
    }



    public GridPane getPane() {return pane;}
    public void setPane(GridPane pane) {this.pane = pane;}
    public MyViewModel getViewModel() {return viewModel;}
    public void setViewModel(MyViewModel viewModel) {this.viewModel = viewModel;}

    public void AboutClick(ActionEvent actionEvent) throws FileNotFoundException {
        mazeDisplay.About();
    }

    public void HelpClick(ActionEvent actionEvent) throws FileNotFoundException {
        mazeDisplay.help();
    }

    public void ExitClick(ActionEvent actionEvent)
    {
        ExitButton(actionEvent);
    }

    public void PropertiesClick(ActionEvent actionEvent) {
    }

    public void LoadButton(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        File chosen = fc.showOpenDialog(null);
        viewModel.loadMaze(chosen);
    }

    public void NewButton(ActionEvent actionEvent)
    {
        generateMazeButton(actionEvent);
    }
}