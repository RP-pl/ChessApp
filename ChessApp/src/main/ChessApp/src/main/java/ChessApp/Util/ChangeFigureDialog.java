package ChessApp.Util;

import ChessApp.Enums.FigureType;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ChangeFigureDialog {
    public static Dialog<FigureType> getChangeDialog(){
        Dialog<FigureType> d = new Dialog<>();
        d.setTitle("Info");
        VBox root = new VBox();
        root.getChildren().add(new Label("Choose figure"));
        ButtonType queen = new ButtonType("Queen", ButtonBar.ButtonData.APPLY);
        ButtonType rook = new ButtonType("Rook", ButtonBar.ButtonData.APPLY);
        ButtonType knight = new ButtonType("Knight", ButtonBar.ButtonData.APPLY);
        ButtonType bishop = new ButtonType("Bishop", ButtonBar.ButtonData.APPLY);
        d.getDialogPane().getButtonTypes().addAll(queen,rook,knight,bishop);
        d.getDialogPane().setContent(root);
        d.setResultConverter((bt)->{
            if(bt == queen){
                return FigureType.QUEEN;
            }
            if(bt == rook){
                return FigureType.ROOK;
            }
            if(bt == bishop){
                return FigureType.BISHOP;
            }
            if(bt == knight){
                return FigureType.KNIGHT;
            }
            return FigureType.PAWN;
        });
        return d;
    }
}
