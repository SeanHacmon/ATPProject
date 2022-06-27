package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.scene.layout.VBox;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

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
        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeColumns.getText());
        viewModel.transformMaze(rows, cols);
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
//        mazeDisplay.drawSolution(mazeDisplay.canvasWidth* mazeDisplay.canvasHeight);
        //TODO
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
        Window stage = menu.getScene().getWindow();
        fileChooser.setTitle("Save Maze");
        fileChooser.setInitialFileName("MySave");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze file","*.png"));    }

    public void OpenButton(ActionEvent actionEvent)
    {
        Window stage = menu.getScene().getWindow();
        fileChooser.setTitle("load Maze");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze txt file","*.txt","*.doc"),
                new FileChooser.ExtensionFilter("Maze pdf", "*.pdf"),
                new FileChooser.ExtensionFilter("Maze Image","*jpg","*gif"));

        try {
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());//save the chosen directory
            //loading file
        }
        catch(Exception e) {

        }
    }
    @Override
    public void update(Observable o, Object arg)
    {
        String str = (String)arg;
        switch (str)
        {
            case "Maze generated" -> mazeDisplay.drawMaze(viewModel.getMaze());
            case "Player was moved" ->{
                mazeDisplay.setPlayerPosition
                    (viewModel.getPosition().getRowIndex(),viewModel.getPosition().getColumnIndex());
                setUpdatePlayerRow(viewModel.getPlayerPosition().getRowIndex());
                setUpdatePlayerCol(viewModel.getPlayerPosition().getColumnIndex());}
            case "Maze Solved" -> mazeDisplay.setSolution(viewModel.getSolution());
            default -> System.out.println("didnt do anything");
        }

    }

    public void ExitButton(ActionEvent actionEvent)
    {
        this.viewModel.exit();
        Platform.exit();
    }
}