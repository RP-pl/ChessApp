package ChessApp.Util;

import ChessApp.Enums.FigureColor;
import ChessApp.Enums.FigureType;
import ChessApp.Node.Figure;
import ChessApp.Parent.Tile;
import com.sun.javafx.tk.Toolkit;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AvailableMovements {
    public static boolean checkAvailableMovement(Tile t,int xPosition, int yPosition, Figure f){
        if(f.getType()== FigureType.PAWN) {
         return pawnAvailableMovement(t,xPosition,yPosition,f);
        }
        else if(f.getType() == FigureType.ROOK){
            return rookAvailableMovement(t, xPosition, yPosition, f);
        }
        else if(f.getType() == FigureType.BISHOP){
            return bishopAvailableMovement(t, xPosition, yPosition, f);
        }
        else if(f.getType() == FigureType.QUEEN){
            return bishopAvailableMovement(t, xPosition, yPosition, f)||rookAvailableMovement(t, xPosition, yPosition, f);
        }
        else if(f.getType() == FigureType.KNIGHT){
            return knightAvailableMovement(t, xPosition, yPosition, f);
        }
        else if(f.getType() == FigureType.KING){
            return kingAvailableMovement(t, xPosition, yPosition, f);
        }
        return true;
    }






    private static boolean pawnAvailableMovement(Tile t,int xPosition, int yPosition, Figure f){
        if (f.getColor() == FigureColor.WHITE) {
            if (xPosition == f.getCurrentTile().getPositionX()) {
                if (yPosition == f.getCurrentTile().getPositionY() - 1 || (yPosition == f.getCurrentTile().getPositionY() - 2&f.moves==0)) {
                    if (t.getCenter() != null) {
                        return true;
                    }
                    return true;
                }
            } else {
                for (Figure figure : Tile.figures) {
                    if (figure.getCurrentTile().getPositionY() == yPosition && (figure.getCurrentTile().getPositionX() == xPosition)) {
                        return true;
                    }
                }
            }
        } else {
            if (xPosition == f.getCurrentTile().getPositionX()) {
                if (yPosition == f.getCurrentTile().getPositionY() + 1 || (yPosition == f.getCurrentTile().getPositionY() + 2&&f.moves==0)) {
                    if (t.getCenter() != null) {
                        return true;
                    }
                    return true;
                }
            } else {
                for (Figure figure : Tile.figures) {
                    if (figure.getCurrentTile().getPositionY() == yPosition && (figure.getCurrentTile().getPositionX() == xPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private static boolean rookAvailableMovement(Tile t,int xPosition, int yPosition, Figure f){
        if(xPosition!=f.getCurrentTile().getPositionX()&&yPosition==f.getCurrentTile().getPositionY()){
            return checkXAxisBlocked(xPosition,f);
        }
        else if(xPosition==f.getCurrentTile().getPositionX()&&yPosition!=f.getCurrentTile().getPositionY()){
            return checkYAxisBlocked(yPosition, f);
        }
        return false;
    }


    private static boolean bishopAvailableMovement(Tile t,int xPosition, int yPosition, Figure f){
        if(xPosition-yPosition==f.getCurrentTile().getPositionX()-f.getCurrentTile().getPositionY()){
            return checkDecrementingBlock(xPosition, yPosition, f);
        }
        if(xPosition+yPosition==f.getCurrentTile().getPositionX()+f.getCurrentTile().getPositionY()){
            return checkIncrementingBlock(xPosition, yPosition, f);
        }
        return false;
    }

    private static boolean knightAvailableMovement(Tile t,int xPosition, int yPosition, Figure f){
        int x = f.getCurrentTile().getPositionX();
        int y = f.getCurrentTile().getPositionY();
        if((xPosition==x+2||xPosition==x-2)&&(yPosition==y+1||yPosition==y-1)||(yPosition==y+2||yPosition==y-2)&&(xPosition==x+1||xPosition==x-1)){
            return true;
        }
        return false;
    }

    private static boolean kingAvailableMovement(Tile t,int xPosition, int yPosition, Figure f){
        List<Figure> rooks = findRooks(f.getColor());
        int x = f.getCurrentTile().getPositionX();
        int y = f.getCurrentTile().getPositionY();
        if((Math.abs(yPosition-y)==1&&Math.abs(xPosition-x)==1)||(Math.abs(yPosition-y)==0&&Math.abs(xPosition-x)==1)||(Math.abs(yPosition-y)==1&&Math.abs(xPosition-x)==0)){
            return true;
        }
        else if(rooks.size()==2){
            Figure left;
            Figure right;
            if(rooks.get(0).getCurrentTile().getPositionX()==0){
                 left = rooks.get(0);
                 right = rooks.get(1);
            }
            else{
                 left = rooks.get(1);
                 right = rooks.get(0);
            }
            if(f.getColor()==FigureColor.WHITE) {
                HBox board = (HBox) t.getParent().getParent();

                if (xPosition == 2 && left.getMoves() == 0 && f.getMoves() == 0&&leftRookfieldsClear(f.getColor(),board)) {
                    left.incrementMoves();
                    ((Tile)((VBox)board.getChildren().get(3)).getChildren().get(7)).setCenter(left);
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(7)));
                    Tile.figures.set(left.getPosition(),left);
                    ((Tile)((VBox)board.getChildren().get(3)).getChildren().get(7)).changeTurn();
                    return true;
                }
                if(xPosition == 6 && right.getMoves() == 0 && f.getMoves() == 0&&rightRookfieldsClear(f.getColor(),board)){
                    right.incrementMoves();
                    ((Tile)((VBox)board.getChildren().get(5)).getChildren().get(7)).setCenter(right);
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(7)));
                    Tile.figures.set(left.getPosition(),right);
                    ((Tile)((VBox)board.getChildren().get(5)).getChildren().get(7)).changeTurn();
                    return true;
                }

            }
            else if(f.getColor()==FigureColor.BLACK) {
                HBox board = (HBox) t.getParent().getParent();

                if (xPosition == 2 && left.getMoves() == 0 && f.getMoves() == 0&&leftRookfieldsClear(f.getColor(),board)) {
                    left.incrementMoves();
                    ((Tile)((VBox)board.getChildren().get(3)).getChildren().get(0)).setCenter(left);
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(0)));
                    Tile.figures.set(left.getPosition(),left);
                    ((Tile)((VBox)board.getChildren().get(3)).getChildren().get(0)).changeTurn();
                    return true;
                }
                if(xPosition == 6 && right.getMoves() == 0 && f.getMoves() == 0&&rightRookfieldsClear(f.getColor(),board)){
                    right.incrementMoves();
                    ((Tile)((VBox)board.getChildren().get(5)).getChildren().get(0)).setCenter(right);
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(0)));
                    Tile.figures.set(left.getPosition(),right);
                    ((Tile)((VBox)board.getChildren().get(5)).getChildren().get(0)).changeTurn();
                    return true;
                }

            }
        }
        return false;
    }


    private static boolean checkXAxisBlocked(int xPosition,Figure f){
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
    private static boolean checkYAxisBlocked(int yPosition,Figure f){
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


    private static boolean checkDecrementingBlock(int xPosition,int yPosition,Figure f){
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
    private static boolean checkIncrementingBlock(int xPosition,int yPosition,Figure f){
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


    private static boolean inline(Figure figure,int xPosition,int yPosition){
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

    private static boolean leftRookfieldsClear(FigureColor color, HBox board){
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

    private static boolean rightRookfieldsClear(FigureColor color, HBox board){
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


    public static List<Figure> findRooks(FigureColor color){
        List<Figure> rook = Tile.getFigures().stream().filter(figure -> figure.getType()==FigureType.ROOK).filter(figure -> figure.getColor()==color).collect(Collectors.toList());
        return rook;
    }

    public static Figure findKing(FigureColor color){
        Optional<Figure> queen = Tile.getFigures().stream().filter(figure -> figure.getType()==FigureType.KING).filter(figure -> figure.getColor()==color).findFirst();
        return queen.orElse(null);
    }

    }