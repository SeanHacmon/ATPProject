package Model;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.Solution;

import java.util.Observable;

public class MyModel extends Observable implements IModel
{
    private Solution mazeSolution;
    private MyMazeGenerator mg = new MyMazeGenerator();
    private Maze maze;
    private int playerRow;
    private int playerCol;

    public MyModel()
    {
        maze = null;
        playerRow = 0;
        playerCol = 0;
    }

    public void updatePlayerPosition(Movements direction)
    {
        switch (direction)
        {
            case UP:
                if (inBounds(playerRow-1, playerCol))
                    if (maze.maze[playerRow-1][playerCol] != 1)
                        this.playerRow--;
                break;
            case DOWN:
                if (inBounds(playerRow+1, playerCol))
                    if (maze.maze[playerRow+1][playerCol] != 1)
                        this.playerRow++;
                break;
            case LEFT:
                if (inBounds(playerRow, playerCol-1) )
                    if (maze.maze[playerRow][playerCol-1] != 1)
                        this.playerCol--;
                break;
            case RIGHT:
                if (inBounds(playerRow, playerCol+1))
                    if (maze.maze[playerRow][playerCol+1] != 1)
                        this.playerCol++;
                break;
            case RIGHT_UP_CROSS:
                if (inBounds(playerRow, playerCol+1))
                    if (maze.maze[playerRow][playerCol+1] != 1)
                        this.playerCol++;
                break;
            case RIGHT_DOWN_CROSS:
                if (inBounds(playerRow+1, playerCol+1))
                    if (maze.maze[playerRow+1][playerCol+1] != 1)
                        this.playerCol++;
                break;
            case LEFT_UP_CROSS:
                if (inBounds(playerRow-1, playerCol-1))
                    if (maze.maze[playerRow-1][playerCol-1] != 1)
                        this.playerCol++;
                break;
            case LEFT_DOWN_CROSS:
                if (inBounds(playerRow-1, playerCol+1))
                    if (maze.maze[playerRow-1][playerCol+1] != 1)
                        this.playerCol++;
                break;

        }
    }


    @Override
    public void generateMaze(int row, int col)
    {
        maze = mg.generate(row, col);
        setChanged();
        notifyObservers();

    }

    @Override
    public void solveMaze()
    {
        // TODO
    }
    @Override
    public Solution getMazeSolution() {return this.mazeSolution;}

    @Override
    public void finishGame()
    {
        // TODO
    }

    @Override
    public void assignObserver(MyViewModel myViewModel) {this.addObserver(myViewModel);}

    public boolean inBounds(int row, int col)
    {
        return (0 <= row && row < maze.row) && (0 <= col && col < maze.col);
    }


    // getters & setters.
    public MyMazeGenerator getMg() {return mg;}
    public void setMg(MyMazeGenerator mg) {this.mg = mg;}
    public void setMaze(Maze maze) {this.maze = maze;}
    public int getPlayerRow() {return playerRow;}
    public void setPlayerRow(int playerRow) {this.playerRow = playerRow;}
    public int getPlayerCol() {return playerCol;}
    public void setPlayerCol(int playerCol) {this.playerCol = playerCol;}
    public Maze getMaze() {return this.maze;}
}
