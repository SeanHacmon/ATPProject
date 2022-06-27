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
//    public void generateMaze(int row, int col);
    public Maze getMaze();
    public void solveMaze();
    public Solution getMazeSolution();
    public void finishGame();
    public void updatePlayerPosition(Movements direction);
    void assignObserver(MyViewModel myViewModel);
    Position getPlayerPosition();
    boolean inBounds(int row, int col);
    void setMaze(Maze m);
    void generateMaze(int row, int col);
    void startServers();
    void stopServers();
    void setStage(Stage s);
    Stage getStage();


    // TODO Relevant methods.
}
