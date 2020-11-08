package ChessApp.Util;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextDialog {
    public static Dialog<String> getTextDialog(String text){
        Dialog<String> d = new Dialog<>();
        d.setTitle("Info");
        VBox root = new VBox();
        root.getChildren().add(new Label(text));
        ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.APPLY);
        d.getDialogPane().getButtonTypes().add(ok);
        d.getDialogPane().setContent(root);
        d.setResultConverter((bt)->{
            if(bt == ok){
                return "";
            }
            else{
                return "";
            }
        });
        return d;
    }
}
