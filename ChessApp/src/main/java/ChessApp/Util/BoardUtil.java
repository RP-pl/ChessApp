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
    public static void addFigure(int x,int y,HBox board,Figure f){
        Tile t= (Tile) ((VBox)board.getChildren().get(x)).getChildren().get(y);
        f.setCurrentTile(t);
        t.addFigure(f);
        t.setCenter(f);
    }


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
        Figure f = new Figure(FigureColor.BLACK,FigureType.ROOK);
        addFigure(0,0,board,f);
        Figure f2 = new Figure(FigureColor.WHITE,FigureType.PAWN);
        addFigure(1,7,board,f2);
        Figure f3 = new Figure(FigureColor.BLACK,FigureType.BISHOP);
        addFigure(2,0,board,f3);
        Figure f4 = new Figure(FigureColor.WHITE,FigureType.KING);
        addFigure(3,0,board,f4);
        Figure f5 = new Figure(FigureColor.WHITE,FigureType.KNIGHT);
        addFigure(3,3,board,f5);
        return board;
    }
}
