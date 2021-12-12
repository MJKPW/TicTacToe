package Game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Path;
import java.util.*;


public class TicTacToe extends Application {

    private int playerScore;
    private int computerScore;
    private final Text player;
    private final Text computer;
    private final Image imgX;
    private final Image img0;
    private final int[][] boardState;
    private final GridPane gridPane;
    private final List<Field> takenByX;
    private final List<Field> takenBy0;
    private final int fieldWidth = 150;
    private final int filedHeight = 150;
    private final Random rnd;

    public TicTacToe(){
        rnd = new Random();
        gridPane = new GridPane();
        player = new Text();
        computer = new Text();
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

    private boolean checkWinCondition(int choice){

        int firstDiagonalCounter = 0;
        int secondDiagonalCounter = 0;

        for (int i = 0; i != 3; i++) {
            int rowCounter = 0;
            int columnCounter = 0;
            if(boardState[i][i] == choice){
                firstDiagonalCounter++;
                if(firstDiagonalCounter == 3)
                    return true;
            }
            if(boardState[i][2-i] == choice){
                secondDiagonalCounter++;
                if(secondDiagonalCounter == 3)
                    return true;
            }
            for (int j = 0; j != 3; j++) {
                if(boardState[i][j] == choice){
                    rowCounter++;
                    if(rowCounter == 3)
                        return true;
                }
                if(boardState[j][i] == choice){
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

    private void randomComputerMove(){
        while(true){
            int i = rnd.nextInt(3);
            int j = rnd.nextInt(3);
            Field field = new Field(i, j);
            if(checkIfTaken(field)){
                takenBy0.add(field);
                gridPane.add(new ImageView(img0), j, i);
                boardState[i][j] = -1;
                if(checkWinCondition(-1)) {
                    EndGameBox.endGame("You have lost");
                    computerScore++;
                    if(EndGameBox.nextStep)
                        newGame();
                }
                break;
            }
        }
    }

    private void createBoard() {

        player.setText("player: " + playerScore);
        player.setFont(Font.font(16));
        computer.setText("computer: " + computerScore);
        computer.setFont(Font.font(16));

        gridPane.add(player, 5, 0);
        gridPane.add(computer, 5, 1);

        for(int i = 0; i != 3 ; i++) {
            for (int j = 0; j != 3; j++) {
                final int I = i;
                final int J = j;
                Rectangle temp = new Rectangle(fieldWidth, filedHeight, Color.CORAL);
                gridPane.add(temp, i, j);
                temp.setOnMouseClicked(r -> {
                    gridPane.add(new ImageView(imgX), I, J);
                    boardState[J][I] = 1;
                    Field filed = new Field(J, I);
                    takenByX.add(filed);
                    boolean outcome = checkWinCondition(1);
                    if(outcome){
                        EndGameBox.endGame("You have won");
                        playerScore++;
                        if(EndGameBox.nextStep)
                            newGame();
                    }else if(takenByX.size() + takenBy0.size() < 9)
                        randomComputerMove();
                    else{
                        EndGameBox.endGame("Draw");
                        playerScore++;
                        computerScore++;
                        if(EndGameBox.nextStep)
                            newGame();
                    }
                });
            }
        }
    }

    private void newGame(){
        for(int i = 0; i != 3; ++i){
            for(int j = 0; j != 3; ++j)
                boardState[i][j] = 0;
        }
        takenBy0.clear();
        takenByX.clear();
        gridPane.getChildren().removeIf(node -> node.getClass() == ImageView.class);
        player.setText("player: " + playerScore);
        computer.setText("computer: " + computerScore);
    }

    @Override
    public void start(Stage primaryStage)  {

        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        createBoard();

        Scene scene = new Scene(gridPane, 4*fieldWidth, 4*filedHeight);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic tac toe");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
