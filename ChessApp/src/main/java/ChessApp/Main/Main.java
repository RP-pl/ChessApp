package ChessApp.Main;

import ChessApp.Util.BoardUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chess App");
        primaryStage.getIcons().add(new Image(new FileInputStream("src\\main\\resources\\White_queen.png")));
        Scene sc = new Scene(BoardUtil.getChessBoard());
        primaryStage.setScene(sc);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
