package ChessApp.Util;

import ChessApp.Enums.FigureColor;
import ChessApp.Enums.FigureType;
import ChessApp.Node.Figure;
import ChessApp.Parent.Tile;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ChessApp.Util.AvailableMovements.checkAvailable;

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
    public static boolean checked(FigureColor color, List<Figure> figures){
        Figure king = findKing(color);
        List<Figure> oposite = figures.stream().filter(figure -> figure.getColor()!=color).collect(Collectors.toList());
        for(Figure figure : oposite){
            if(checkAvailable(king.getCurrentTile(),king.getCurrentTile().getPositionX(),king.getCurrentTile().getPositionY(),figure)){
                if(Figure.castleFlag == true) {
                    Figure.PGN.deleteCharAt(Figure.PGN.length() - 1);
                    int index = Figure.PGN.lastIndexOf(" ");
                    Figure.PGN = new StringBuilder(Figure.PGN.subSequence(0, index) + " ");
                    Figure.castleFlag = false;
                }
                return true;
            }
        }
        return false;
    }
    public static boolean blocked(Tile t,FigureColor color){
        List<Figure> opposite = Tile.figures.stream().filter(figure -> figure.getColor()!=color).collect(Collectors.toList());
        if(t.getCenter()!=null&&((Figure)t.getCenter()).getColor()==color){
            return true;
        }
        else if(t.getCenter()!=null&&((Figure)t.getCenter()).getColor()!=color){
            return false;
        }
        for(Figure figure : opposite){
            if(checkAvailable(t,t.getPositionX(),t.getPositionY(),figure)){
                return true;
            }
        }
        return false;
    }
    public static List<Figure> findRooks(FigureColor color){
        List<Figure> rook = Tile.getFigures().stream().filter(figure -> figure.getType()== FigureType.ROOK).filter(figure -> figure.getColor()==color).collect(Collectors.toList());
        return rook;
    }

    public static Figure findKing(FigureColor color){
        Optional<Figure> queen = Tile.getFigures().stream().filter(figure -> figure.getType()==FigureType.KING).filter(figure -> figure.getColor()==color).findFirst();
        return queen.orElse(null);
    }
    public static boolean checkXAxisBlocked(int xPosition, Figure f){
        if(xPosition>f.getCurrentTile().getPositionX()) {
            int min = xPosition;
            for (Figure figure : Tile.getFigures()) {
                if(figure.getCurrentTile().getPositionX()<=xPosition&&figure.getCurrentTile().getPositionX()>f.getCurrentTile().getPositionX()&&f.getCurrentTile().getPositionY()==figure.getCurrentTile().getPositionY()){
                    min = Math.min(min,figure.getCurrentTile().getPositionX());
                }
            }
            return min >= xPosition;
        }
        else if(xPosition<f.getCurrentTile().getPositionX()){
            int max = xPosition;
            for (Figure figure : Tile.getFigures()) {
                if(figure.getCurrentTile().getPositionX()>=xPosition&&figure.getCurrentTile().getPositionX()<f.getCurrentTile().getPositionX()&&f.getCurrentTile().getPositionY()==figure.getCurrentTile().getPositionY()){
                    max = Math.max(max,figure.getCurrentTile().getPositionX());
                }
            }
            return max <= xPosition;
        }
        return true;
    }
    public static boolean checkYAxisBlocked(int yPosition, Figure f){
        if(yPosition>f.getCurrentTile().getPositionY()) {
            int min = yPosition;
            for (Figure figure : Tile.getFigures()) {
                if(figure.getCurrentTile().getPositionY()<=yPosition&&figure.getCurrentTile().getPositionY()>f.getCurrentTile().getPositionY()&&f.getCurrentTile().getPositionX()==figure.getCurrentTile().getPositionX()){
                    min = Math.min(min,figure.getCurrentTile().getPositionY());
                }
            }
            return min >= yPosition;
        }
        else if(yPosition<f.getCurrentTile().getPositionY()){
            int max = yPosition;
            for (Figure figure : Tile.getFigures()) {
                if(figure.getCurrentTile().getPositionY()>=yPosition&&figure.getCurrentTile().getPositionY()<f.getCurrentTile().getPositionY()&&f.getCurrentTile().getPositionX()==figure.getCurrentTile().getPositionX()){
                    max = Math.max(max,figure.getCurrentTile().getPositionY());
                }
            }
            return max <= yPosition;
        }
        return true;
    }


    public static boolean checkDecrementingBlock(int xPosition, int yPosition, Figure f){
        if(yPosition>f.getCurrentTile().getPositionY()&&xPosition>f.getCurrentTile().getPositionX()){
            int minY=yPosition,minX=xPosition;
            for(Figure figure : Tile.getFigures()){
                if(inline(figure,xPosition,yPosition)&&figure.getCurrentTile()!=f.getCurrentTile()&&f.getCurrentTile().getPositionX()<figure.getCurrentTile().getPositionX()){
                    minX=Math.min(figure.getCurrentTile().getPositionX(),minX);
                    minY=Math.min(figure.getCurrentTile().getPositionY(),minY);
                }
            }
            return minX >= xPosition&&minY>= yPosition;
        }
        else{
            int maxY=yPosition,maxX=xPosition;
            for(Figure figure : Tile.getFigures()){
                if(inline(figure,xPosition,yPosition)&&figure.getCurrentTile()!=f.getCurrentTile()&&f.getCurrentTile().getPositionX()>figure.getCurrentTile().getPositionX()){
                    maxX=Math.max(figure.getCurrentTile().getPositionX(),maxX);
                    maxY=Math.max(figure.getCurrentTile().getPositionY(),maxY);
                }
            }
            return maxX <= xPosition&&maxY<= yPosition;
        }
    }
    public static void wierdCastleShit(Figure king) {
        HBox board = (HBox) king.getCurrentTile().getParent().getParent();
        if (((Tile) ((VBox) board.getChildren().get(5)).getChildren().get(0)).getCenter() != null || ((Tile) ((VBox) board.getChildren().get(5)).getChildren().get(7)).getCenter() != null) {
            if (Tile.turn == FigureColor.WHITE) {
                Tile t = (Tile) ((VBox) board.getChildren().get(5)).getChildren().get(7);
                Figure rook = (Figure) t.getCenter();
                Tile bef = (Tile) ((VBox) board.getChildren().get(7)).getChildren().get(7);
                bef.setCenter(rook);
                rook.setCurrentTile(bef);
                Tile.figures.set(rook.getPosition(), rook);
            } else if (Tile.turn == FigureColor.BLACK) {
                Tile t = (Tile) ((VBox) board.getChildren().get(5)).getChildren().get(0);
                Figure rook = (Figure) t.getCenter();
                Tile bef = (Tile) ((VBox) board.getChildren().get(7)).getChildren().get(0);
                bef.setCenter(rook);
                rook.setCurrentTile(bef);
                Tile.figures.set(rook.getPosition(), rook);
            }
        } else if (((Tile) ((VBox) board.getChildren().get(3)).getChildren().get(0)).getCenter() != null || ((Tile) ((VBox) board.getChildren().get(3)).getChildren().get(7)).getCenter() != null) {
            if (Tile.turn == FigureColor.WHITE) {
                Tile t = (Tile) ((VBox) board.getChildren().get(3)).getChildren().get(7);
                Figure rook = (Figure) t.getCenter();
                Tile bef = (Tile) ((VBox) board.getChildren().get(0)).getChildren().get(7);
                bef.setCenter(rook);
                rook.setCurrentTile(bef);
                Tile.figures.set(rook.getPosition(), rook);
            } else if (Tile.turn == FigureColor.BLACK) {
                Tile t = (Tile) ((VBox) board.getChildren().get(3)).getChildren().get(0);
                Figure rook = (Figure) t.getCenter();
                Tile bef = (Tile) ((VBox) board.getChildren().get(0)).getChildren().get(0);
                bef.setCenter(rook);
                rook.setCurrentTile(bef);
                Tile.figures.set(rook.getPosition(), rook);
            }
        }
    }
}
