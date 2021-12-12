package Game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EndGameBox {

    public static boolean nextStep;

    public static void endGame(String message){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(300);
        stage.setMinHeight(300);

        Button exitButton = new Button("Exit");
        exitButton.setFont(Font.font(20));
        exitButton.setOnMouseClicked(e->{
            stage.close();
            nextStep = false;
            javafx.application.Platform.exit();
        });

        Button resetButton = new Button("Play again");
        resetButton.setFont(Font.font(20));
        resetButton.setOnMouseClicked(e->{
            stage.close();
            nextStep = true;
        });

        Text text = new Text(message);
        text.setFont(Font.font(20));

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.add(text, 4, 5);
        gridPane.add(exitButton, 4, 8);
        gridPane.add(resetButton, 8, 8);
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane);
        stage.setTitle("Tic tac toe");
        stage.setScene(scene);
        stage.showAndWait();
    }
}
