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
    public static void addAllFigures(HBox board) throws FileNotFoundException {
        Figure f = new Figure(FigureColor.BLACK,FigureType.ROOK);
        addFigure(0,0,board,f);
        Figure f2 = new Figure(FigureColor.BLACK,FigureType.KNIGHT);
        addFigure(1,0,board,f2);
        Figure f3 = new Figure(FigureColor.BLACK,FigureType.BISHOP);
        addFigure(2,0,board,f3);
        Figure f4 = new Figure(FigureColor.BLACK,FigureType.QUEEN);
        addFigure(3,0,board,f4);
        Figure f5 = new Figure(FigureColor.BLACK,FigureType.KING);
        addFigure(4,0,board,f5);
        Figure f6 = new Figure(FigureColor.BLACK,FigureType.BISHOP);
        addFigure(5,0,board,f6);
        Figure f7 = new Figure(FigureColor.BLACK,FigureType.KNIGHT);
        addFigure(6,0,board,f7);
        Figure f8 = new Figure(FigureColor.BLACK,FigureType.ROOK);
        addFigure(7,0,board,f8);
        Figure f9 = new Figure(FigureColor.BLACK,FigureType.PAWN);
        Figure f10 = new Figure(FigureColor.BLACK,FigureType.PAWN);
        Figure f11 = new Figure(FigureColor.BLACK,FigureType.PAWN);
        Figure f12 = new Figure(FigureColor.BLACK,FigureType.PAWN);
        Figure f13 = new Figure(FigureColor.BLACK,FigureType.PAWN);
        Figure f14 = new Figure(FigureColor.BLACK,FigureType.PAWN);
        Figure f15 = new Figure(FigureColor.BLACK,FigureType.PAWN);
        Figure f16 = new Figure(FigureColor.BLACK,FigureType.PAWN);
        addFigure(0,1,board,f9);
        addFigure(1,1,board,f10);
        addFigure(2,1,board,f11);
        addFigure(3,1,board,f12);
        addFigure(4,1,board,f13);
        addFigure(5,1,board,f14);
        addFigure(6,1,board,f15);
        addFigure(7,1,board,f16);
        Figure f17 = new Figure(FigureColor.WHITE,FigureType.ROOK);
        addFigure(0,7,board,f17);
        Figure f18 = new Figure(FigureColor.WHITE,FigureType.KNIGHT);
        addFigure(1,7,board,f18);
        Figure f19 = new Figure(FigureColor.WHITE,FigureType.BISHOP);
        addFigure(2,7,board,f19);
        Figure f20 = new Figure(FigureColor.WHITE,FigureType.QUEEN);
        addFigure(3,7,board,f20);
        Figure f21 = new Figure(FigureColor.WHITE,FigureType.KING);
        addFigure(4,7,board,f21);
        Figure f22 = new Figure(FigureColor.WHITE,FigureType.BISHOP);
        addFigure(5,7,board,f22);
        Figure f23 = new Figure(FigureColor.WHITE,FigureType.KNIGHT);
        addFigure(6,7,board,f23);
        Figure f24 = new Figure(FigureColor.WHITE,FigureType.ROOK);
        addFigure(7,7,board,f24);
        Figure f25 = new Figure(FigureColor.WHITE,FigureType.PAWN);
        Figure f26 = new Figure(FigureColor.WHITE,FigureType.PAWN);
        Figure f27 = new Figure(FigureColor.WHITE,FigureType.PAWN);
        Figure f28 = new Figure(FigureColor.WHITE,FigureType.PAWN);
        Figure f29 = new Figure(FigureColor.WHITE,FigureType.PAWN);
        Figure f30 = new Figure(FigureColor.WHITE,FigureType.PAWN);
        Figure f31 = new Figure(FigureColor.WHITE,FigureType.PAWN);
        Figure f32 = new Figure(FigureColor.WHITE,FigureType.PAWN);
        addFigure(0,6,board,f25);
        addFigure(1,6,board,f26);
        addFigure(2,6,board,f27);
        addFigure(3,6,board,f28);
        addFigure(4,6,board,f29);
        addFigure(5,6,board,f30);
        addFigure(6,6,board,f31);
        addFigure(7,6,board,f32);
    }
    public static void addFigure(int x,int y,HBox board,Figure f){
        Tile t= (Tile) ((VBox)board.getChildren().get(x)).getChildren().get(y);
        f.setCurrentTile(t);
        t.addFigure(f);
        t.setCenter(f);
    }
    public static void updatePositions(){
        for (Figure figure : Tile.figures) {
            int p = Tile.figures.indexOf(figure);
            figure.setPosition(p);
            Tile.figures.set(p, figure);
        }
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
        addAllFigures(board);
        return board;
    }
}
