package ChessApp.Main;

import ChessApp.Util.BoardUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene sc = new Scene(BoardUtil.getChessBoard());
        primaryStage.setScene(sc);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
