package ChessApp.Util;

import ChessApp.Enums.FigureColor;
import ChessApp.Enums.FigureType;
import ChessApp.Node.Figure;
import ChessApp.Parent.Tile;

public class AvailableMovements {
    public static boolean checkAvailableMovement(Tile t,int xPosition, int yPosition, Figure f){
        if(f.getType()== FigureType.PAWN) {
            if (f.getColor() == FigureColor.WHITE) {
                if (xPosition == f.getCurrentTile().getPositionX()) {
                    if (yPosition == f.getCurrentTile().getPositionY() - 1 || yPosition == f.getCurrentTile().getPositionY() - 2) {
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
                return false;
            } else {
                if (xPosition == f.getCurrentTile().getPositionX()) {
                    if (yPosition == f.getCurrentTile().getPositionY() + 1 || yPosition == f.getCurrentTile().getPositionY() + 2) {
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
                return false;
            }
        }
        return true;
    }
}
