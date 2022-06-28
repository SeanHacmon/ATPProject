package ViewModel;

import Model.IModel;
import Model.Movements;
import Model.MyModel;
import View.IView;
import View.MyViewController;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.Observable;
import java.util.Observer;


/* """connects model with view. */
public class MyViewModel extends Observable implements Observer
{
    private IModel model;
    private IView view;
//    private Maze maze;
    private Solution solution;
    private Stage stage;

    public MyViewModel()
    {
        this.model = new MyModel();
        this.model.assignObserver(this);
        setControllerStage();
    }


    @Override
    public void update(Observable o, Object arg)
    {
        setChanged();
        notifyObservers(arg);
    }

    public void transformMaze(int row, int col) {this.model.generateMaze(row, col) ;}

    public void movePlayer(KeyEvent event)
    {
        Movements movement= null;
        switch (event.getCode())
        {
            case NUMPAD8 -> movement = Movements.UP;
            case NUMPAD6 ->  movement = Movements.RIGHT;
            case NUMPAD4 -> movement = Movements.LEFT;
            case NUMPAD2 -> movement = Movements.DOWN;
            case NUMPAD9 -> movement = Movements.RIGHT_UP_CROSS;
            case NUMPAD3 -> movement = Movements.RIGHT_DOWN_CROSS;
            case NUMPAD7 -> movement = Movements.LEFT_UP_CROSS;
            case NUMPAD1 -> movement = Movements.LEFT_DOWN_CROSS;
            default -> {
                return;
            }
        }
        this.model.updatePlayerPosition(movement);
    }

    public Position getPlayerPosition(){return this.model.getPlayerPosition();}

    public void setControllerStage(){this.setStage(this.model.getStage());}

    public void exit()
    {
        this.model.stopServers();
    }
    public void safeExit(Stage mainStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText("Are you sure you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            this.model.stopServers();
            mainStage.close();
            Platform.exit();
        }
    }



    public void setSolution(Solution solution) {this.solution = solution;}
    public Stage getStage() {return stage;}
    public void setStage(Stage stage) {this.stage = stage;}
    public void solveMaze() {this.model.solveMaze();}
    public Solution getSolution() {return this.model.getMazeSolution();}
    public IModel getModel() {return model;}
    public void setModel(IModel model) {this.model = model;}
    public IView getView() {return view;}
    public void setView(IView view) {this.view = view;}
    public Maze getMaze() {return model.getMaze();}
    public void setMaze(Maze m){model.setMaze(m);}
    public Position getPosition(){return model.getPlayerPosition();}

    public void saveMazeFile(File chosen)
    {
        model.saveMazeFile(chosen);
    }

    public void loadMaze(File chosen)
    {
        this.model.loadMaze(chosen);
    }
}
