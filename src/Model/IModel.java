package Model;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.Observable;

public interface IModel
{
    public void generateMaze(int row, int col);
    public Maze getMaze();
    public void solveMaze();
    public Solution getMazeSolution();
    public void finishGame();
    public void updatePlayerPosition(Movements direction);
    void assignObserver(MyViewModel myViewModel);
    public int getPlayerRow();
    public int getPlayerCol();


    // TODO Relevant methods.
}
