package ChessApp.Util;

import ChessApp.Enums.FigureColor;
import ChessApp.Enums.FigureType;
import ChessApp.Node.Figure;
import ChessApp.Parent.Tile;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ChessApp.Util.MovementUtil.*;
public class AvailableMovements {
    public static List<Move> moves = new LinkedList<>();
    public static List<Tile> kingMoves = new LinkedList<>();
    public static boolean checkAvailableMovement(Tile t,int xPosition, int yPosition, Figure f){
        Figure king = findKing(Tile.turn);
        if(checked(Tile.turn,Tile.figures)&&!checkIfMate(t, xPosition, yPosition, king)){
            if(f.getType()==FigureType.KING){
                boolean res = kingMoves.contains(t);
                res = res&&checkAvailable(t, xPosition, yPosition, f);
                res = res&&!blocked(t,f.getColor());
                kingMoves = new LinkedList<>();
                return res;
            }
            else{

                boolean res = moves.contains(new Move(t,f))&&checkAvailable(t, xPosition, yPosition, f);
                moves = new LinkedList<>();
                return res;
            }
        }
        else{
            return checkAvailable(t, xPosition, yPosition, f);
        }
    }


    public static boolean checkAvailable(Tile t,int xPosition, int yPosition, Figure f){
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
                if (yPosition == f.getCurrentTile().getPositionY() - 1 || (yPosition == f.getCurrentTile().getPositionY() - 2&&f.moves==0)) {
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
        if(((xPosition==x+2||xPosition==x-2)&&(yPosition==y+1||yPosition==y-1))||((yPosition==y+2||yPosition==y-2)&&(xPosition==x+1||xPosition==x-1))){
            return true;
        }
        return false;
    }

    private static boolean kingAvailableMovement(Tile t,int xPosition, int yPosition, Figure f){
        List<Figure> rooks = findRooks(Tile.turn);
        int x = f.getCurrentTile().getPositionX();
        int y = f.getCurrentTile().getPositionY();
        if((Math.abs(yPosition-y)==1&&Math.abs(xPosition-x)==1)||(Math.abs(yPosition-y)==0&&Math.abs(xPosition-x)==1)||(Math.abs(yPosition-y)==1&&Math.abs(xPosition-x)==0)&&!blocked(t,f.getColor())){
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

                if (xPosition == 2 && (left.getMoves() == 0||left.getMoves() == 1) && f.getMoves() == 0&&leftRookfieldsClear(f.getColor(),board)) {
                    left.incrementMoves();
                    ((Tile)((VBox)board.getChildren().get(3)).getChildren().get(7)).setCenter(left);
                    Figure.castleFlag = true;
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(7)));
                    Tile.figures.set(left.getPosition(),left);
                    Figure.PGN.append("W"+((int)Math.ceil((double)(++Figure.totalMoves)/2.0))+".O-O-O ");
                    return true;
                }
                if(xPosition == 6 && (right.getMoves() == 0||right.getMoves() == 1) && f.getMoves() == 0&&rightRookfieldsClear(f.getColor(),board)){
                    right.incrementMoves();
                    ((Tile)((VBox)board.getChildren().get(5)).getChildren().get(7)).setCenter(right);
                    Figure.castleFlag = true;
                    right.setCurrentTile(((Tile)((VBox)board.getChildren().get(5)).getChildren().get(7)));
                    Tile.figures.set(right.getPosition(),right);
                    Figure.PGN.append("W"+((int)Math.ceil((double)(++Figure.totalMoves)/2.0))+".O-O ");
                    return true;
                }

            }
            else if(f.getColor()==FigureColor.BLACK) {
                HBox board = (HBox) t.getParent().getParent();

                if (xPosition == 2 && (left.getMoves() == 0||left.getMoves() == 1) && f.getMoves() == 0&&leftRookfieldsClear(f.getColor(),board)) {
                    left.incrementMoves();
                    Figure.castleFlag = true;
                    ((Tile)((VBox)board.getChildren().get(3)).getChildren().get(0)).setCenter(left);
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(0)));
                    Tile.figures.set(left.getPosition(),left);
                    Figure.PGN.delete(Figure.PGN.length(),Figure.PGN.length());
                    Figure.PGN.append("B"+((int)Math.ceil((double)(++Figure.totalMoves)/2.0))+".O-O-O ");
                    return true;
                }
                if(xPosition == 6 && (right.getMoves() == 0||right.getMoves() == 1) && f.getMoves() == 0&&rightRookfieldsClear(f.getColor(),board)){
                    right.incrementMoves();
                    Figure.castleFlag = true;
                    ((Tile)((VBox)board.getChildren().get(5)).getChildren().get(0)).setCenter(right);
                    right.setCurrentTile(((Tile)((VBox)board.getChildren().get(5)).getChildren().get(0)));
                    Tile.figures.set(right.getPosition(),right);
                    Figure.PGN.append("B"+((int)Math.ceil((double)(++Figure.totalMoves)/2.0))+".O-O ");
                    return true;
                }
            }
        }
        else if(rooks.size()==1){
            Figure left = null;
            Figure right = null;
            if(rooks.get(0).getCurrentTile().getPositionX()==0){
                left = rooks.get(0);
            }
            else{
                right = rooks.get(0);
            }
            if(f.getColor()==FigureColor.WHITE) {
                HBox board = (HBox) t.getParent().getParent();

                if (left!=null&&xPosition == 2 && (left.getMoves() == 0||left.getMoves() == 1) && f.getMoves() == 0&&leftRookfieldsClear(f.getColor(),board)) {
                    left.incrementMoves();
                    ((Tile)((VBox)board.getChildren().get(3)).getChildren().get(7)).setCenter(left);
                    Figure.castleFlag = true;
                    left.setCurrentTile(((Tile)((VBox)board.getChildren().get(3)).getChildren().get(7)));
                    Tile.figures.set(left.getPosition(),left);
                    Figure.PGN.append("W"+((int)Math.ceil((double)(++Figure.totalMoves)/2.0))+".O-O-O ");
                    return true;
                }
                if(right!=null&&xPosition == 6 && (right.getMoves() == 0||right.getMoves() == 1) && f.getMoves() == 0&&rightRookfieldsClear(f.getColor(),board)){
                    right.incrementMoves();
                    ((Tile)((VBox)board.getChildren().get(5)).getChildren().get(7)).setCenter(right);
                    Figure.castleFlag = true;
                    right.setCurrentTile(((Tile)((VBox)board.getChildren().get(5)).getChildren().get(7)));
                    Tile.figures.set(right.getPosition(),right);
                    Figure.PGN.append("W"+((int)Math.ceil((double)(++Figure.totalMoves)/2.0))+".O-O ");
                    return true;
                }

            }
            else if(f.getColor()==FigureColor.BLACK) {
                HBox board = (HBox) t.getParent().getParent();

                if (left!=null&&xPosition == 2 && (left.getMoves() == 0 || left.getMoves() == 1) && f.getMoves() == 0 && leftRookfieldsClear(f.getColor(), board)) {
                    left.incrementMoves();
                    Figure.castleFlag = true;
                    ((Tile) ((VBox) board.getChildren().get(3)).getChildren().get(0)).setCenter(left);
                    left.setCurrentTile(((Tile) ((VBox) board.getChildren().get(3)).getChildren().get(0)));
                    Tile.figures.set(left.getPosition(), left);
                    Figure.PGN.delete(Figure.PGN.length(), Figure.PGN.length());
                    Figure.PGN.append("B" + ((int) Math.ceil((double) (++Figure.totalMoves) / 2.0)) + ".O-O-O ");
                    return true;
                }
                if (right!=null&&xPosition == 6 && (right.getMoves() == 0 || right.getMoves() == 1) && f.getMoves() == 0 && rightRookfieldsClear(f.getColor(), board)) {
                    right.incrementMoves();
                    Figure.castleFlag = true;
                    ((Tile) ((VBox) board.getChildren().get(5)).getChildren().get(0)).setCenter(right);
                    right.setCurrentTile(((Tile) ((VBox) board.getChildren().get(5)).getChildren().get(0)));
                    Tile.figures.set(right.getPosition(), right);
                    Figure.PGN.append("B" + ((int) Math.ceil((double) (++Figure.totalMoves) / 2.0)) + ".O-O ");
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean checkIfMate(Tile t,int xPosition,int yPosition,Figure f){
        List<Integer[]> movements = new LinkedList<>();
        movements.add(new Integer[]{-1,-1});
        movements.add(new Integer[]{0,-1});
        movements.add(new Integer[]{1,-1});
        movements.add(new Integer[]{1,0});
        movements.add(new Integer[]{1,1});
        movements.add(new Integer[]{0,1});
        movements.add(new Integer[]{-1,1});
        movements.add(new Integer[]{-1,0});
        int i=0;
        HBox board = (HBox) t.getParent().getParent();
        Figure king = findKing(f.getColor());
        for(Integer[] move : movements){
            if(king.getCurrentTile().getPositionX()+move[0]<0||king.getCurrentTile().getPositionX()+move[0]>7){
                i++;
            }
            else if(king.getCurrentTile().getPositionY()+move[1]<0||king.getCurrentTile().getPositionY()+move[1]>7){
                i++;
            }
            else if(blocked((Tile) ((VBox)board.getChildren().get(king.getCurrentTile().getPositionX()+move[0])).getChildren().get(king.getCurrentTile().getPositionY()+move[1]),f.getColor())){
                i++;
            }
            else{
                kingMoves.add((Tile) ((VBox)board.getChildren().get(king.getCurrentTile().getPositionX()+move[0])).getChildren().get(king.getCurrentTile().getPositionY()+move[1]));
            }
        }

        for(Figure figure : Tile.figures.stream().filter(figure -> figure.getColor()==king.getColor()).filter(figure -> figure.getType()!=FigureType.KING).collect(Collectors.toList())){
            for(int j=0;j<8;j++){
                for(int k=0;k<8;k++) {
                    List<Figure> figures = Tile.getFigures();
                        Tile befTile = figure.getCurrentTile();
                        Tile tile = (Tile) ((VBox) board.getChildren().get(j)).getChildren().get(k);
                        if(checkAvailable(tile, tile.getPositionX(), tile.getPositionY(), figure)) {
                            if(tile.getCenter() != null && ((Figure) tile.getCenter()).getColor() != figure.getColor()) {
                                Figure onTile = (Figure) tile.getCenter();
                                tile.setCenter(figure);
                                figure.setCurrentTile(tile);
                                figures.set(figure.getPosition(), figure);
                                figures.remove(onTile);
                                if (!checked(king.getColor(), figures)) {
                                    moves.add(new Move(tile, figure));
                                    i--;
                                }
                                if(!Figure.castleFlag) {
                                    figures.add(onTile);
                                }
                            figures.sort((f1,f2)->f1.getPosition()-f2.getPosition());
                            tile.setCenter(onTile);
                            befTile.setCenter(figure);
                            figure.setCurrentTile(befTile);
                            figures.set(figure.getPosition(),figure);
                            int z=0;
                        }
                        else if(tile.getCenter() != null && ((Figure) tile.getCenter()).getColor() == figure.getColor()){
                        }
                        else {
                            tile.setCenter(figure);
                            figure.setCurrentTile(tile);
                            figures.set(figure.getPosition(), figure);
                            if (!checked(king.getColor(), figures)) {
                                moves.add(new Move(tile, figure));
                                i--;
                            }
                            tile.setCenter(null);
                            figures.sort((f1,f2)->f1.getPosition()-f2.getPosition());
                            befTile.setCenter(figure);
                            figure.setCurrentTile(befTile);
                            figures.set(figure.getPosition(),figure);
                        }
                    }
                    }
                }
            }

        if(i==8){
            if(FigureColor.BLACK==f.getColor()){
                TextDialog.getTextDialog("BIALE WYGRALY").showAndWait();
                t.getScene().getWindow().hide();

            }
            else if(FigureColor.WHITE==f.getColor()) {
                TextDialog.getTextDialog("CZARNE WYGRALY").showAndWait();
                t.getScene().getWindow().hide();
            }
        }

        return false;
    }
}