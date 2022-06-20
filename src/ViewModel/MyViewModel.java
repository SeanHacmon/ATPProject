package ViewModel;

import Model.IModel;
import Model.Movements;
import Model.MyModel;
import View.IView;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;


/* """connects model with view. */
public class MyViewModel extends Observable implements Observer
{
    private IModel model;
    private IView view;

    public MyViewModel(IModel m)
    {
        this.model = m;
        this.model.assignObserver(this);
    }


    @Override
    public void update(Observable o, Object arg)
    {
        setChanged();
        notifyObservers(arg);
    }

    public void transformMaze(int row, int col) {this.model.generateMaze(row, col);}

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

    public void getPlayerPosition(){}



    public void solveMaze() {this.model.solveMaze();}
    public Solution getSolution() {return this.model.getMazeSolution();}
    public IModel getModel() {return model;}
    public void setModel(IModel model) {this.model = model;}
    public IView getView() {return view;}
    public void setView(IView view) {this.view = view;}
}
