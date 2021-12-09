package Game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Path;
import java.util.*;


public class TicTacToe extends Application {

    private final Image imgX;
    private final Image img0;
    private final int[][] boardState;
    private final GridPane gridPane;
    private final List<Field> takenByX;
    private final List<Field> takenBy0;
    private final int fieldWidth = 150;
    private final int filedHeight = 150;
    private final Random rnd = new Random();

    public TicTacToe(){
        gridPane = new GridPane();
        takenByX = new LinkedList<>();
        takenBy0 = new LinkedList<>();
        boardState = new int[3][3];
        for(int i = 0; i != 3; i++){
            for(int j = 0; j != 3; j++)
                boardState[i][j] = 0;
        }
        ClassLoader classLoader = getClass().getClassLoader();
        File fileX = new File(classLoader.getResource("x.jpg").getFile());
        imgX = new Image(Path.of(fileX.getPath()).toString(), fieldWidth, filedHeight, false, false);
        File file0 = new File(classLoader.getResource("o.jpg").getFile());
        img0 = new Image(Path.of(file0.getPath()).toString(), fieldWidth, filedHeight, false, false);
    }

    private boolean checkWinConditionForPlayer(Field field){
        boardState[field.getRow()][field.getColumn()] = 1;

        int firstDiagonalCounter = 0;
        int secondDiagonalCounter = 0;

        for (int i = 0; i != 3; i++) {
            int rowCounter = 0;
            int columnCounter = 0;
            if(boardState[i][i] == 1){
                firstDiagonalCounter++;
                if(firstDiagonalCounter == 3)
                    return true;
            }
            if(boardState[i][2-i] == 1){
                secondDiagonalCounter++;
                if(secondDiagonalCounter == 3)
                    return true;
            }
            for (int j = 0; j != 3; j++) {
                if(boardState[i][j] == 1){
                    rowCounter++;
                    if(rowCounter == 3)
                        return true;
                }
                if(boardState[j][i] == 1){
                    columnCounter++;
                    if(columnCounter == 3)
                        return true;
                }
            }
        }
        return false;
    }

    private boolean checkWinConditionForComputer(Field field){
        boardState[field.getRow()][field.getColumn()] = -1;

        int firstDiagonalCounter = 0;
        int secondDiagonalCounter = 0;

        for (int i = 0; i != 3; i++) {
            int rowCounter = 0;
            int columnCounter = 0;
            if(boardState[i][i] == -1){
                firstDiagonalCounter++;
                if(firstDiagonalCounter == 3)
                    return true;
            }
            if(boardState[i][2-i] == -1){
                secondDiagonalCounter++;
                if(secondDiagonalCounter == 3)
                    return true;
            }
            for (int j = 0; j != 3; j++) {
                if(boardState[i][j] == -1){
                    rowCounter++;
                    if(rowCounter == 3)
                        return true;
                }
                if(boardState[j][i] == -1){
                    columnCounter++;
                    if(columnCounter == 3)
                        return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfTaken(Field chosen){
        boolean flag_X = !takenByX.contains(chosen);
        boolean flag_0 = !takenBy0.contains(chosen);
        return flag_X && flag_0;
    }

    private void computersMove(){
        while(true){
            int i = rnd.nextInt(3);
            int j = rnd.nextInt(3);
            Field field = new Field(i, j);
            if(checkIfTaken(field)){
                takenBy0.add(field);
                gridPane.add(new ImageView(img0), j, i);
                if(checkWinConditionForComputer(field))
                    EndGameBox.endGame("Computer has won");
                break;
            }
        }
    }

    private void begin() {
        for(int i = 0; i != 3 ; i++) {
            for (int j = 0; j != 3; j++) {
                final int I = i;
                final int J = j;
                Rectangle temp = new Rectangle(fieldWidth, filedHeight, Color.CORAL);
                gridPane.add(temp, i, j);
                temp.setOnMouseClicked(r -> {
                    gridPane.add(new ImageView(imgX), I, J);
                    Field field = new Field(J, I);
                    boolean check = checkWinConditionForPlayer(field);
                    takenByX.add(field);
                    if(check)
                        EndGameBox.endGame("You have won");
                    if(takenByX.size() + takenBy0.size() < 9 && !check)
                        computersMove();
                    else if(takenByX.size() + takenBy0.size() == 9 && !check)
                        EndGameBox.endGame("Draw");
                });
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        begin();

        Scene scene = new Scene(gridPane, 4*fieldWidth, 4*filedHeight);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Game");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
