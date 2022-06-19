package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;

public class MyViewController implements IView, Initializable
{
    private Maze maze;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public Label label_Prow;
    public Label label_Pcol;
    public MazeCanvasDisplay mazeDisplay = new MazeCanvasDisplay();

    protected MyMazeGenerator mg = new MyMazeGenerator();
    public KeyEvent key = null;

    private StringProperty updatePlayerRow = new SimpleStringProperty();
    private StringProperty updatePlayerCol = new SimpleStringProperty();

    public void generateMazeButton(ActionEvent actionEvent)
    {
        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeColumns.getText());
        Maze maze = this.mg.generate(rows, cols);
        mazeDisplay.drawMaze(maze);
    }

    public void keyPress(KeyEvent keyevent)
    {
        int row = mazeDisplay.getPlayerRow();
        int col = mazeDisplay.getPlayerCol();
        switch (keyevent.getCode()) {
            case UP -> {
                if (inBounds(row - 1, col))
                    if (mazeDisplay.maze.maze[row - 1][col] != 1) {
                        row -= 1;
                    }
                key = keyevent;
            }
            case DOWN -> {
                if (inBounds(row + 1, col))
                    if (mazeDisplay.maze.maze[row + 1][col] != 1) {
                        row += 1;
                    }
                key = keyevent;
            }
            case LEFT -> {
                if (inBounds(row, col - 1))
                    if (mazeDisplay.maze.maze[row][col - 1] != 1) {
                        col -= 1;
                    }
                key = keyevent;
            }
            case RIGHT -> {
                if (inBounds(row, col + 1))
                    if (mazeDisplay.maze.maze[row][col + 1] != 1) {
                        col += 1;
                    }
                key = keyevent;
            }
        }
        mazeDisplay.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
//        mazeDisplay.
        keyevent.consume();
    }

    public boolean inBounds(int row, int col)
    {
        return (0 <= row && row < mazeDisplay.maze.row) && (0 <= col && col < mazeDisplay.maze.col);
    }

    public void ClickedMouse(MouseEvent actionEvent)
    {
        mazeDisplay.requestFocus();
    }


    public void solveMaze(ActionEvent actionEvent)
    {
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
}
