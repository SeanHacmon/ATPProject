package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.Configurations;
import Server.IServerStrategy;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import View.PlayerConfig;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;

public class MyModel extends Observable implements IModel
{
    private static Server mazeGeneratorServer = null;
    private static Server mazeSolverServer = null;
    private Solution mazeSolution;
    private MyMazeGenerator mg = new MyMazeGenerator();
    private Maze maze;
    private Position playerPosition = new Position(0,0);
    private Stage stage;


    public MyModel() {}

    public void startServers()
    {
        if (mazeGeneratorServer == null)
        {
            IServerStrategy serverStrategy = new ServerStrategyGenerateMaze();
            mazeGeneratorServer = new Server(5400, 1000, serverStrategy);
            mazeGeneratorServer.start();
        }
        if (mazeSolverServer == null)
        {
            mazeSolverServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
            mazeSolverServer.start();
        }
    }


    public void generateMaze(int row, int col)
    {
        String s = PlayerConfig.getInstance().getGenerateAlgorithm().name();
        try {
            try {
                if (s.equals("MyMazeGenerator"))
                    Configurations.createInstance().readConfig().getProperty("My");
                if (s.equals("SimpleMazeGenerator"))
                    Configurations.createInstance().readConfig().getProperty("Simple");
                if (s.equals("EmptyMazeGenerator"))
                    Configurations.createInstance().readConfig().getProperty("Empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        toServer.flush();

                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[row*col+12];
                        is.read(decompressedMaze);
                        Maze m = new Maze(decompressedMaze);
                        saveMaze(m);
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
//            var1.printStackTrace();
        }
        setChanged();
        notifyObservers("Maze generated");
    }

    public void solveMaze()
    {
        Maze m1 =this.maze;
        try {
             Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

                        Maze m = m1;
                        toServer.writeObject(maze);
                        toServer.flush();
                        Solution mazeSolution = (Solution)fromServer.readObject();
                        setMazeSolution(mazeSolution);
                        System.out.println(String.format("Solution steps:   %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();

                        for(int i = 0; i < mazeSolutionSteps.size(); ++i) {
                            System.out.println(String.format("%s. %s", i, ((AState)mazeSolutionSteps.get(i)).toString()));
                        }
                    } catch (Exception var10) {
//                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
        setChanged();
        notifyObservers("Solved Maze");

    }

    public void stopServers() {
        if (mazeGeneratorServer != null)
            mazeGeneratorServer.stop();
        if (mazeSolverServer != null)
            mazeSolverServer.stop();
    }

    @Override
    public void setStage(Stage s) {
        // todo
    }

    //    public void setStage(Stage s){this.stage = s;}
    public void updatePlayerPosition(Movements direction)
    {
        switch (direction) {
            case UP:
                if (inBounds(this.playerPosition.getRowIndex() - 1, this.playerPosition.getColumnIndex()))
                    if (maze.maze[this.playerPosition.getRowIndex() - 1][this.playerPosition.getColumnIndex()] != 1)
                        this.playerPosition.setRowIndex(this.playerPosition.getRowIndex() - 1);
                break;
            case DOWN:
                if (inBounds(this.playerPosition.getRowIndex() + 1, this.playerPosition.getColumnIndex()))
                    if (maze.maze[this.playerPosition.getRowIndex() + 1][this.playerPosition.getColumnIndex()] != 1)
                        this.playerPosition.setRowIndex(this.playerPosition.getRowIndex() + 1);
                break;
            case LEFT:
                if (inBounds(this.playerPosition.getRowIndex(), this.playerPosition.getColumnIndex() - 1))
                    if (maze.maze[this.playerPosition.getRowIndex()][this.playerPosition.getColumnIndex() - 1] != 1)
                        this.playerPosition.setColumnIndex(this.playerPosition.getColumnIndex() - 1);
                break;
            case RIGHT:
                if (inBounds(this.playerPosition.getRowIndex(), this.playerPosition.getColumnIndex() + 1))
                    if (maze.maze[this.playerPosition.getRowIndex()][this.playerPosition.getColumnIndex() + 1] != 1)
                        this.playerPosition.setColumnIndex(this.playerPosition.getColumnIndex() + 1);

                break;
            case RIGHT_UP_CROSS:
                if (inBounds(this.playerPosition.getRowIndex() - 1, this.playerPosition.getColumnIndex() + 1))
                {
                    if (maze.maze[this.playerPosition.getRowIndex() - 1][this.playerPosition.getColumnIndex() + 1] != 1) {
                        this.playerPosition.setColumnIndex(this.playerPosition.getColumnIndex() + 1);
                        this.playerPosition.setRowIndex(this.playerPosition.getRowIndex() - 1);
                    }
                }
                break;
            case RIGHT_DOWN_CROSS:
                if (inBounds(this.playerPosition.getRowIndex()+1, this.playerPosition.getColumnIndex()+1))
                {
                    if (maze.maze[this.playerPosition.getRowIndex() + 1][this.playerPosition.getColumnIndex() + 1] != 1) {
                        this.playerPosition.setColumnIndex(this.playerPosition.getColumnIndex() + 1);
                        this.playerPosition.setRowIndex(this.playerPosition.getRowIndex() + 1);
                    }
                }
                break;
            case LEFT_UP_CROSS:
                if (inBounds(this.playerPosition.getRowIndex()-1, this.playerPosition.getColumnIndex()-1))
                {
                    if (maze.maze[this.playerPosition.getRowIndex() - 1][this.playerPosition.getColumnIndex() - 1] != 1) {
                        this.playerPosition.setColumnIndex(this.playerPosition.getColumnIndex() - 1);
                        this.playerPosition.setRowIndex(this.playerPosition.getRowIndex() - 1);
                    }
                }
                break;
            case LEFT_DOWN_CROSS:
                if (inBounds(this.playerPosition.getRowIndex() +1, this.playerPosition.getColumnIndex() -1))
                {
                    if (maze.maze[this.playerPosition.getRowIndex() + 1][this.playerPosition.getColumnIndex() - 1] != 1) {
                        this.playerPosition.setColumnIndex(this.playerPosition.getColumnIndex() - 1);
                        this.playerPosition.setRowIndex(this.playerPosition.getRowIndex() + 1);
                    }
                }
                break;
        }
        setChanged();
        notifyObservers("Player was moved");
        if (this.playerPosition.equals(this.maze.goalPosition))
        {
            setChanged();
            notifyObservers("Won game");
        }
    }

    @Override
    public Solution getMazeSolution() {return this.mazeSolution;}

    @Override
    public void finishGame() {
        setChanged();
        notifyObservers(ModelReactions.Finish);
        stopServers();
    }


    public void saveMazeFile(File chosen)
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chosen))) {
            out.writeObject(this.maze);
        } catch (IOException e) {
            System.out.println("Maze haven't saved");
        }
    }

    @Override
    public void loadMaze(File chosen)
    {
        Maze tempMaze = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(chosen))) {
            tempMaze = (Maze) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Maze not allowed");
            return;
        }
        this.setMaze(tempMaze);
        setChanged();
        notifyObservers("Maze Loaded");
    }

    public void saveMaze(Maze m)
    {
        this.maze = m;
        this.playerPosition = this.maze.getStartPosition();
    }

    @Override
    public void assignObserver(MyViewModel myViewModel) {this.addObserver(myViewModel);}

    public boolean inBounds(int row, int col)
    {
        return (0 <= row && row < maze.row) && (0 <= col && col < maze.col);
    }


    // getters & setters.

    public Stage getStage() {return stage;}
    public MyMazeGenerator getMg() {return mg;}
    public void setMg(MyMazeGenerator mg) {this.mg = mg;}
    public void setMaze(Maze maze) {this.maze = maze;}
    public void setMazeSolution(Solution mazeSolution) {this.mazeSolution = mazeSolution;}
    public Position getPlayerPosition() {return playerPosition;}
    public void setPlayerPosition(Position playerPosition) {this.playerPosition = playerPosition;}
    public Maze getMaze() {return this.maze;}



}
