package ChessApp.Util;

import ChessApp.Enums.FigureColor;
import ChessApp.Enums.FigureType;
import ChessApp.Node.Figure;
import ChessApp.Parent.Tile;
import com.sun.javafx.tk.Toolkit;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static ChessApp.Util.MovementUtil.*;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AvailableMovements {
    public static boolean checkAvailableMovement(Tile t,int xPosition, int yPosition, Figure f){
        if(f.getType()== FigureType.PAWN) {
            try {
                return pawnAvailableMovement(t, xPosition, yPosition, f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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






    private static boolean pawnAvailableMovement(Tile t,int xPosition, int yPosition, Figure f) throws FileNotFoundException {
        if (f.getColor() == FigureColor.WHITE) {
            if (xPosition == f.getCurrentTile().getPositionX()) {
                if (yPosition == f.getCurrentTile().getPositionY() - 1 || (yPosition == f.getCurrentTile().getPositionY() - 2&f.moves==0)) {
                    if (t.getCenter() != null) {
                        if(yPosition==0){
                            f.setType(ChangeFigureDialog.getChangeDialog().showAndWait().get());
                            Tile.figures.set(f.getPosition(),f);
                            return true;
                        }
                        return true;
                    }
                    return true;
                }
            } else {
                for (Figure figure : Tile.figures) {
                    if (figure.getCurrentTile().getPositionY() == yPosition && (figure.getCurrentTile().getPositionX() == xPosition)) {
                        if(f.getCurrentTile().getPositionY()-1==figure.getCurrentTile().getPositionY()&&(f.getCurrentTile().getPositionX()-1==figure.getCurrentTile().getPositionX()||f.getCurrentTile().getPositionX()+1==figure.getCurrentTile().getPositionX())) {
                            if(yPosition==0){
                                f.setType(ChangeFigureDialog.getChangeDialog().showAndWait().get());
                                Tile.figures.set(f.getPosition(),f);
                                return true;
                            }
                            return true;
                        }

                    }
                }
            }
        } else {
            if (xPosition == f.getCurrentTile().getPositionX()) {
                if (yPosition == f.getCurrentTile().getPositionY() + 1 || (yPosition == f.getCurrentTile().getPositionY() + 2&&f.moves==0)) {
                    if (t.getCenter() != null) {
                        if(yPosition==7){
                            f.setType(ChangeFigureDialog.getChangeDialog().showAndWait().get());
                            Tile.figures.set(f.getPosition(),f);
                            return true;
                        }
                        return true;
                    }
                    return true;
                }
            } else {
                for (Figure figure : Tile.figures) {
                    if (figure.getCurrentTile().getPositionY() == yPosition && (figure.getCurrentTile().getPositionX() == xPosition)) {
                        if(f.getCurrentTile().getPositionY()+1==figure.getCurrentTile().getPositionY()&&(f.getCurrentTile().getPositionX()-1==figure.getCurrentTile().getPositionX()||f.getCurrentTile().getPositionX()+1==figure.getCurrentTile().getPositionX())) {
                            if(yPosition==7){
                                f.setType(ChangeFigureDialog.getChangeDialog().showAndWait().get());
                                Tile.figures.set(f.getPosition(),f);
                                return true;
                            }
                            return true;
                        }
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
                    Figure.rochadeFlag = true;
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(7)));
                    Tile.figures.set(left.getPosition(),left);
                    Figure.PGN.append("O-O-O ");
                    return true;
                }
                if(xPosition == 6 && right.getMoves() == 0 && f.getMoves() == 0&&rightRookfieldsClear(f.getColor(),board)){
                    right.incrementMoves();
                    ((Tile)((VBox)board.getChildren().get(5)).getChildren().get(7)).setCenter(right);
                    Figure.rochadeFlag = true;
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(7)));
                    Tile.figures.set(left.getPosition(),right);
                    Figure.PGN.append("O-O ");
                    return true;
                }

            }
            else if(f.getColor()==FigureColor.BLACK) {
                HBox board = (HBox) t.getParent().getParent();

                if (xPosition == 2 && left.getMoves() == 0 && f.getMoves() == 0&&leftRookfieldsClear(f.getColor(),board)) {
                    left.incrementMoves();
                    Figure.rochadeFlag = true;
                    ((Tile)((VBox)board.getChildren().get(3)).getChildren().get(0)).setCenter(left);
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(0)));
                    Tile.figures.set(left.getPosition(),left);
                    Figure.PGN.append("O-O-O ");
                    return true;
                }
                if(xPosition == 6 && right.getMoves() == 0 && f.getMoves() == 0&&rightRookfieldsClear(f.getColor(),board)){
                    right.incrementMoves();
                    Figure.rochadeFlag = true;
                    ((Tile)((VBox)board.getChildren().get(5)).getChildren().get(0)).setCenter(right);
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(0)));
                    Tile.figures.set(left.getPosition(),right);
                    Figure.PGN.append("O-O ");
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

    public static List<Figure> findRooks(FigureColor color){
        List<Figure> rook = Tile.getFigures().stream().filter(figure -> figure.getType()==FigureType.ROOK).filter(figure -> figure.getColor()==color).collect(Collectors.toList());
        return rook;
    }

    public static Figure findKing(FigureColor color){
        Optional<Figure> queen = Tile.getFigures().stream().filter(figure -> figure.getType()==FigureType.KING).filter(figure -> figure.getColor()==color).findFirst();
        return queen.orElse(null);
    }

    }