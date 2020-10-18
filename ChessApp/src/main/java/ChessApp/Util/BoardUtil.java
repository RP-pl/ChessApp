package ChessApp.Util;

import ChessApp.Enums.FieldColor;
import ChessApp.Enums.FigureColor;
import ChessApp.Enums.FigureType;
import ChessApp.Node.Figure;
import ChessApp.Parent.Tile;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class BoardUtil {
    public static HBox getChessBoard() throws FileNotFoundException {
        HBox board = new HBox();
        for(int i=0;i<8;i++){
            VBox row = new VBox();
            for(int j=0;j<8;j++){
                if(i%2==0){
                    if(j%2==0){
                        Tile t = new Tile(i,j, FieldColor.WHITE);
                        row.getChildren().add(t);
                    }
                    else {
                        row.getChildren().add(new Tile(i,j, FieldColor.BLACK));
                    }
                }
                else{
                    if(j%2==0){
                        row.getChildren().add(new Tile(i,j, FieldColor.BLACK));
                    }
                    else {
                        row.getChildren().add(new Tile(i,j, FieldColor.WHITE));
                    }
                }
            }
        board.getChildren().add(row);
        }
        Tile t= (Tile) ((VBox)board.getChildren().get(0)).getChildren().get(0);
        Figure f = new Figure(FigureColor.BLACK,FigureType.PAWN);
        f.setCurrentTile(t);
        t.addFigure(f);
        t.setCenter(f);
        Tile t2= (Tile) ((VBox)board.getChildren().get(1)).getChildren().get(7);
        Figure f2 = new Figure(FigureColor.WHITE,FigureType.PAWN);
        f2.setCurrentTile(t2);
        t2.addFigure(f2);
        t2.setCenter(f2);
        Tile t3= (Tile) ((VBox)board.getChildren().get(2)).getChildren().get(0);
        Figure f3 = new Figure(FigureColor.BLACK,FigureType.BISHOP);
        f3.setCurrentTile(t3);
        t3.addFigure(f3);
        t3.setCenter(f3);
        Tile t4= (Tile) ((VBox)board.getChildren().get(3)).getChildren().get(0);
        Figure f4 = new Figure(FigureColor.WHITE,FigureType.KNIGHT);
        f4.setCurrentTile(t4);
        t4.addFigure(f4);
        t4.setCenter(f4);
        return board;
    }
}
