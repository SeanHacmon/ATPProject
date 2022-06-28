package View;

//import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;


public class MazeCanvasDisplay extends Canvas
{
    // init
    protected Maze maze;
    protected MazeGallery mazeGallery;
    protected Solution solution;
    protected GraphicsContext graphicsContext = getGraphicsContext2D();

    private StringProperty imageFileWall = new SimpleStringProperty();
    private StringProperty imageFilePlayer = new SimpleStringProperty();
    private int playerRow;
    private int playerCol;
    protected double canvasHeight;
    protected double canvasWidth;
    private MazeCanvasDisplay camera;
//    private MazeAudioPlayer mazeAudioPlayer;



//    public void LoadRes()
//    {
//        this.LoadRes(MazeGallery.Theme.Dora);
//    }
//    public void LoadRes(MazeGallery.Theme theme)
//    {
//        LoadImages(theme);
//        LoadSound();
//    }

    private void LoadImages(MazeGallery.Theme theme) { this.mazeGallery = MazeGallery.getInstance(theme);}

//    private void LoadSound() {this.mazeAudioPlayer = MazeAudioPlayer.getInstance();}



    public void drawMaze(Maze m)
    {
        this.maze = m;
        this.playerRow = maze.startPosition.getRowIndex();
        this.playerCol = maze.startPosition.getColumnIndex();
        canvasHeight = getHeight();
        canvasWidth = getWidth();

        draw();
    }

    // Display the maze.
    private void draw()
    {
        if (this.maze != null)
        {

            int rows = maze.maze.length;
            int cols = maze.maze[0].length;
            double cellHeight = canvasHeight/rows;
            double cellWidth = canvasWidth/cols;
            drawMazeWalls(cellWidth, cellHeight);
            if(solution != null)
                drawSolution(cellWidth, cellHeight);
            drawMazePlayer(cellWidth, cellHeight);
            drawGoal(cellWidth, cellHeight);
        }
    }

    // Draw all the walls.
    private void drawMazeWalls(double cellWidth, double cellHeight)
    {
        int rows = maze.maze.length;
        int cols = maze.maze[0].length;
        Image wallImage = null;
        try {
            wallImage = new Image(new FileInputStream("./resources/Images/wall.png"));
        }
        catch (FileNotFoundException e){System.out.println("There is no wall image");}

        graphicsContext.clearRect(0, 0, cellWidth* cols, cellHeight*rows);
        graphicsContext.setFill(Color.WHEAT);

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (maze.maze[i][j] == 1)
                {
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if (wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
            }
        }
    }
//
//    public Alert getAlertImage()
//    {
//        ButtonType Yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
//        ButtonType No = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
//        Alert alert = new Alert(Alert.AlertType.WARNING,"Start a new game?",Yes,No);
//        alert.setHeaderText("You won the game!");
//        Image image = new Image("./resources/Images/EndGame.png");
//        ImageView imageView = new ImageView(image);
//        alert.setGraphic(imageView);
//        return alert;
//    }

    private void StartGame()
    {
        if (this.maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.maze.length;
            int cols = maze.maze[0].length;
            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;
            drawMazeWalls(cellWidth, cellHeight);
            drawGoal(cellWidth, cellHeight);
            if(solution != null)
                drawSolution(cellWidth, cellHeight);
        }
    }

    // Draws a goal Cell Color.
    private void drawGoal(double cellWidth, double cellHeight)
    {
        Image goalImage = null;
        try
        {
            goalImage = new Image(new FileInputStream("./resources/Images/Mewtoo.png"));
        }
        catch (FileNotFoundException e){System.out.println("There is no Goal image");}
        Color goalColor = Color.LAWNGREEN;

        Position goalPosition = this.maze.getGoalPosition();
        double x = goalPosition.getRowIndex() * cellWidth;
        double y = goalPosition.getColumnIndex() * cellHeight;

        if (goalImage == null)
        {
            graphicsContext.setFill(goalColor);
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        } else
            graphicsContext.drawImage(goalImage, x, y, cellWidth, cellHeight);
    }


    public void drawMazePlayer(double cellWidth, double cellHeight)
    {
        Image playerImageUp = null;
        Image playerImageDown = null;
        Image playerImageRight = null;
        Image playerImageLeft = null;

        try {
            playerImageUp = new Image(new FileInputStream("./resources/Images/Ash_Up.png"));
            playerImageDown = new Image(new FileInputStream("./resources/Images/Ash_Down.png"));
            playerImageRight = new Image(new FileInputStream("./resources/Images/Ash_Right.png"));
            playerImageLeft = new Image(new FileInputStream("./resources/Images/Ash_Left.png"));
        }
        catch (FileNotFoundException e){System.out.println("There is no player image");}
        double x = this.playerCol* cellWidth;
        double y = this.playerRow * cellHeight;
        graphicsContext.setFill(Color.FORESTGREEN);
        if (playerImageDown == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImageDown, x, y, cellWidth, cellHeight);
    }


    public void PlayerGoRight(double cellWidth, double cellHeight)
    {
        Image playerImageUp = null;
        Image playerImageDown = null;
        Image playerImageRight = null;
        Image playerImageLeft = null;

        try {
            playerImageUp = new Image(new FileInputStream("./resources/Images/Ash_Up.png"));
            playerImageDown = new Image(new FileInputStream("./resources/Images/Ash_Down.png"));
            playerImageRight = new Image(new FileInputStream("./resources/Images/Ash_Right.png"));
            playerImageLeft = new Image(new FileInputStream("./resources/Images/Ash_Left.png"));
        }
        catch (FileNotFoundException e){System.out.println("There is no player image");}
        double x = this.playerCol* cellWidth;
        double y = this.playerRow * cellHeight;
        graphicsContext.setFill(Color.FORESTGREEN);
        if (playerImageDown == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImageRight, x, y, cellWidth, cellHeight);
    }
    public void PlayerGoUp(double cellWidth, double cellHeight)
    {
        Image playerImageUp = null;
        Image playerImageDown = null;
        Image playerImageRight = null;
        Image playerImageLeft = null;

        try {
            playerImageUp = new Image(new FileInputStream("./resources/Images/Ash_Up.png"));
            playerImageDown = new Image(new FileInputStream("./resources/Images/Ash_Down.png"));
            playerImageRight = new Image(new FileInputStream("./resources/Images/Ash_Right.png"));
            playerImageLeft = new Image(new FileInputStream("./resources/Images/Ash_Left.png"));
        }
        catch (FileNotFoundException e){System.out.println("There is no player image");}
        double x = this.playerCol* cellWidth;
        double y = this.playerRow * cellHeight;
        graphicsContext.setFill(Color.FORESTGREEN);
        if (playerImageDown == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImageUp, x, y, cellWidth, cellHeight);
    }
    public void PlayerGoDown(double cellWidth, double cellHeight)
    {
        Image playerImageUp = null;
        Image playerImageDown = null;
        Image playerImageRight = null;
        Image playerImageLeft = null;

        try {
            playerImageUp = new Image(new FileInputStream("./resources/Images/Ash_Up.png"));
            playerImageDown = new Image(new FileInputStream("./resources/Images/Ash_Down.png"));
            playerImageRight = new Image(new FileInputStream("./resources/Images/Ash_Right.png"));
            playerImageLeft = new Image(new FileInputStream("./resources/Images/Ash_Left.png"));
        }
        catch (FileNotFoundException e){System.out.println("There is no player image");}
        double x = this.playerCol* cellWidth;
        double y = this.playerRow * cellHeight;
        graphicsContext.setFill(Color.FORESTGREEN);
        if (playerImageDown == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImageDown, x, y, cellWidth, cellHeight);
    }
    public void PlayerGoLeft(double cellWidth, double cellHeight)
    {
        Image playerImageUp = null;
        Image playerImageDown = null;
        Image playerImageRight = null;
        Image playerImageLeft = null;

        try {
            playerImageUp = new Image(new FileInputStream("./resources/Images/Ash_Up.png"));
            playerImageDown = new Image(new FileInputStream("./resources/Images/Ash_Down.png"));
            playerImageRight = new Image(new FileInputStream("./resources/Images/Ash_Right.png"));
            playerImageLeft = new Image(new FileInputStream("./resources/Images/Ash_Left.png"));
        }
        catch (FileNotFoundException e){System.out.println("There is no player image");}
        double x = this.playerCol* cellWidth;
        double y = this.playerRow * cellHeight;
        graphicsContext.setFill(Color.FORESTGREEN);
        if (playerImageDown == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImageLeft, x, y, cellWidth, cellHeight);
    }







    // Draws Solution path in the maze.
    private void drawSolution(double cellWidth, double cellHeight) {
        if (this.solution == null)
            return;
        Image solutionImage = null;
        HashSet<Position> pathHashMap = solutionToPositionsHashSet();
        try {
            solutionImage = new Image(new FileInputStream("./Resources/Images/solution.png"));
        }
        catch(FileNotFoundException e){System.out.println("There is no solution image");}
        Color solutionColor = Color.MEDIUMTURQUOISE;

        Position goalPosition = this.maze.getGoalPosition();
        Position tempPosition = null;
        for (int i = 0; i < this.maze.row; i++) {
            for (int j = 0; j < this.maze.col; j++) {
                tempPosition = new Position(i, j);
                if (pathHashMap.contains(tempPosition) && !tempPosition.equals(goalPosition)) {
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if (solutionImage == null) {
                        graphicsContext.setFill(solutionColor);
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    } else
                        graphicsContext.drawImage(solutionImage, x, y, cellWidth, cellHeight);

                }
            }
        }
    }
    private HashSet<Position> solutionToPositionsHashSet() {
        HashSet<Position> pathHashMap = new HashSet<>();
        ArrayList<AState> list = this.solution.getSolutionPath();
        for (int i = 0; i < list.size(); i++)
        {
            AState s = list.get(i);
            Position p = ((MazeState) s).getPosition();
            pathHashMap.add(p);
        }
        return pathHashMap;
    }

    public void finish() {
//        this.mazeAudioPlayer.stopAll();
        StageGenerator.getInstance(StageGenerator.StageName.Main).close();
        StageGenerator.startVideo();
    }

    public void toggleFreeCamera() {
        this.camera.toggleFreeCamera();
        this.draw();
    }

//    public void wallHit() {this.mazeAudioPlayer.play(MazeAudioPlayer.MazeSound.WallHit);}

    // getters & setters.


    public Maze getMaze() {return maze;}
    public void setMaze(Maze maze) {this.maze = maze;}
    public int getPlayerRow() {return playerRow;}
    public void setPlayerRow(int playerRow) {this.playerRow = playerRow;}
    public int getPlayerCol() {return playerCol;}
    public void setPlayerCol(int playerCol) {this.playerCol = playerCol;}

    public String getImageFileWall() {return imageFileWall.get();}
    public String getImageFilePlayer() {return imageFilePlayer.get();}
    public void setImageFileWall(String imageFileWall) {this.imageFileWall.set(imageFileWall);}
    public void setImageFilePlayer(String imageFilePlayer) {this.imageFilePlayer.set(imageFilePlayer);}
    public void setSolution(Solution sol){this.solution = sol; draw();}
    public void setPlayerPosition(int row, int col)
    {
        if (row == this.playerRow && col == this.playerCol)
        {
            draw();
        }
        if (this.playerCol < col)
        {
            this.playerRow = row;
            this.playerCol = col;
            StartGame();
            PlayerGoRight(canvasWidth / this.maze.maze[0].length, canvasHeight/this.maze.maze.length);
        }
        else if (this.playerCol > col)
        {
            this.playerRow = row;
            this.playerCol = col;
            StartGame();
            PlayerGoLeft(canvasWidth / this.maze.maze[0].length, canvasHeight/this.maze.maze.length);
        }
        else if (this.playerRow < row)
        {
            this.playerRow = row;
            this.playerCol = col;
            StartGame();
            PlayerGoDown(canvasWidth / this.maze.maze[0].length, canvasHeight/this.maze.maze.length);
        }
        else if (this.playerRow > row)
        {
            this.playerRow = row;
            this.playerCol = col;
            StartGame();
            PlayerGoUp(canvasWidth / this.maze.maze[0].length, canvasHeight/this.maze.maze.length);
        }
    }

    public void help() throws FileNotFoundException
    {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        AnchorPane anchorPane = new AnchorPane();
        InputStream stream = new FileInputStream("resources/Images/HelpMenu.png");
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(gridPane.widthProperty());
        imageView.setPreserveRatio(true);
        anchorPane.getChildren().add(imageView);
        gridPane.getChildren().add(anchorPane);
        Scene scene = new Scene(gridPane, 900, 760);
        InputStream Stream = new FileInputStream("resources/Images/HelpMenu.png");
        stage.getIcons().add(new Image(Stream));
        stage.setTitle("Help");
        stage.setScene(scene);
        stage.show();
    }

    public void About() throws FileNotFoundException
    {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        AnchorPane anchorPane = new AnchorPane();
        InputStream stream = new FileInputStream("resources/Images/AboutImage.png");
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(gridPane.widthProperty());
        imageView.setPreserveRatio(true);
        anchorPane.getChildren().add(imageView);
        gridPane.getChildren().add(anchorPane);
        Scene scene = new Scene(gridPane, 600, 220);
        InputStream Stream = new FileInputStream("resources/Images/AboutImage.png");
        stage.getIcons().add(new Image(Stream));
        stage.setTitle("About");
        stage.setScene(scene);
        stage.show();

    }



//
//    public void resizeHandle() {
//        this.updateCamera();
//        this.draw();
//    }
//
//    private void updateCamera() {
//        //todo
//    }

}
