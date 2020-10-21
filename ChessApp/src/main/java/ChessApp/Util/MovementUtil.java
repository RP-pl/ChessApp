package ChessApp.Util;

import ChessApp.Enums.FigureColor;
import ChessApp.Node.Figure;
import ChessApp.Parent.Tile;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MovementUtil {
    static boolean checkIncrementingBlock(int xPosition, int yPosition, Figure f){
        if(yPosition<f.getCurrentTile().getPositionY()&&xPosition>f.getCurrentTile().getPositionX()){
            int minY=yPosition,minX=xPosition;
            for(Figure figure : Tile.getFigures()){
                if(inlinei(figure,xPosition,yPosition)&&figure.getCurrentTile()!=f.getCurrentTile()&&f.getCurrentTile().getPositionX()<figure.getCurrentTile().getPositionX()){
                    minX=Math.min(figure.getCurrentTile().getPositionX(),minX);
                    minY=Math.max(figure.getCurrentTile().getPositionY(),minY);
                }
            }
            return minX >= xPosition&&minY<= yPosition;
        }
        else{
            int maxY=yPosition,maxX=xPosition;
            for(Figure figure : Tile.getFigures()){
                if(inlinei(figure,xPosition,yPosition)&&figure.getCurrentTile()!=f.getCurrentTile()&&f.getCurrentTile().getPositionX()>figure.getCurrentTile().getPositionX()){
                    maxX=Math.max(figure.getCurrentTile().getPositionX(),maxX);
                    maxY=Math.min(figure.getCurrentTile().getPositionY(),maxY);
                }
            }
            return maxX <= xPosition&&maxY>= yPosition;
        }
    }


    static boolean inline(Figure figure, int xPosition, int yPosition){
        int x = figure.getCurrentTile().getPositionX();
        int y = figure.getCurrentTile().getPositionY();
        for(int i=-8;i<=8;i++) {
            if ((x + i == xPosition && y + i == yPosition)){
                return true;
            }
        }
        return false;
    }
    private static boolean inlinei(Figure figure,int xPosition,int yPosition){
        int x = figure.getCurrentTile().getPositionX();
        int y = figure.getCurrentTile().getPositionY();
        for(int i=-8;i<=8;i++) {
            if ((x + i == xPosition && y - i == yPosition)){
                return true;
            }
        }
        return false;
    }

    static boolean leftRookfieldsClear(FigureColor color, HBox board){
        VBox first = (VBox) board.getChildren().get(1);
        VBox second = (VBox) board.getChildren().get(2);
        VBox third = (VBox) board.getChildren().get(3);
        if(color == FigureColor.WHITE){
            Tile f = (Tile) first.getChildren().get(7);
            Tile s = (Tile) second.getChildren().get(7);
            Tile t = (Tile) third.getChildren().get(7);
            if(f.getCenter()==null&&s.getCenter()==null&&t.getCenter()==null){
                return true;
            }
            return false;
        }
        else if(color == FigureColor.BLACK){
            Tile f = (Tile) first.getChildren().get(0);
            Tile s = (Tile) second.getChildren().get(0);
            Tile t = (Tile) third.getChildren().get(0);
            if(f.getCenter()==null&&s.getCenter()==null&&t.getCenter()==null){
                return true;
            }
            return false;
        }
        return false;
    }

    static boolean rightRookfieldsClear(FigureColor color, HBox board){
        VBox first = (VBox) board.getChildren().get(6);
        VBox second = (VBox) board.getChildren().get(5);
        if(color == FigureColor.WHITE){
            Tile f = (Tile) first.getChildren().get(7);
            Tile s = (Tile) second.getChildren().get(7);
            if(f.getCenter()==null&&s.getCenter()==null){
                return true;
            }
            return false;
        }
        else if(color == FigureColor.BLACK){
            Tile f = (Tile) first.getChildren().get(0);
            Tile s = (Tile) second.getChildren().get(0);
            if(f.getCenter()==null&&s.getCenter()==null){
                return true;
            }
            return false;
        }
        return false;
    }


}
