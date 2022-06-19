package Model;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface IModel
{
    void generateMaze(int row, int col);
    Maze getMaze();
    void solveMaze();
    Solution getMazeSolution();
    void finishGame();
    
    // TODO Relevant methods.
}
