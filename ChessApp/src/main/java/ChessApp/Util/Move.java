package ChessApp.Util;

import ChessApp.Node.Figure;
import ChessApp.Parent.Tile;

public class Move {
    public final Figure figure;
    public final Tile tile;
    public Move(Tile tile,Figure figure) {
        this.figure = figure;
        this.tile = tile;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() == obj.getClass()) {
            return this.tile == ((Move) obj).tile && this.figure == ((Move) obj).figure;
        } else {
            return false;
        }
    }
}
