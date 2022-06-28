package Model;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.io.File;

public interface IModel
{
    Maze getMaze();
    void solveMaze();
    Solution getMazeSolution();
    void finishGame();
    void updatePlayerPosition(Movements direction);
    void assignObserver(MyViewModel myViewModel);
    Position getPlayerPosition();
    boolean inBounds(int row, int col);
    void setMaze(Maze m);
    void generateMaze(int row, int col);
    void startServers();
    void stopServers();
    void setStage(Stage s);
    Stage getStage();
    void saveMazeFile(File chosen);
    void loadMaze(File chosen);


    // TODO Relevant methods.
}
