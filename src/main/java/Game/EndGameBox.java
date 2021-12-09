package Game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EndGameBox {
    public static void endGame(String message){
        Stage stage = new Stage();
        stage.setMinWidth(300);
        stage.setMinHeight(300);

        Button exitButton = new Button("exit");
        exitButton.setFont(Font.font(20));
        Text text = new Text(message);
        text.setFont(Font.font(20));
        exitButton.setOnMouseClicked(e->stage.close());

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.add(text, 4, 5);
        gridPane.add(exitButton, 4, 8);
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane);
        stage.setTitle("Game");
        stage.setScene(scene);
        stage.show();
    }
}

