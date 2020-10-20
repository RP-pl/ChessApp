package ChessApp.Parent;

import ChessApp.Enums.FieldColor;
import ChessApp.Node.Figure;
import ChessApp.Util.AvailableMovements;
import javafx.collections.ObservableArray;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import java.util.ArrayList;
import java.util.List;

public class Tile  extends BorderPane {
    public static ArrayList<Figure> figures = new ArrayList<>();
    private Figure onField;
    private final int posy;
    private final int posx;
    private final FieldColor fieldColor;
    private final Tile tile = this;

    public Tile(int posx, int posy, FieldColor color){
        this.posx = posx;
        this.posy = posy;
        this.fieldColor = color;
        this.setMinSize(60,60);
        this.setMaxSize(60,60);
        Background b;
        if(color == FieldColor.BLACK) {
            b = new Background(new BackgroundFill(Paint.valueOf("#8FBC8F"), null, null));
        }
        else{
            b = new Background(new BackgroundFill(Paint.valueOf("#FFFFFF"), null, null));
        }
        this.setBackground(b);
        this.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (AvailableMovements.checkAvailableMovement(tile, tile.getPositionX(), tile.getPositionY(), figures.get(Integer.parseInt(event.getDragboard().getString())))) {
                    if (tile.getCenter() != null && figures.indexOf((Figure) tile.getCenter()) != Integer.parseInt(event.getDragboard().getString())) {
                        Figure f = (Figure) tile.getCenter();
                        //TODO
                        tile.setCenter(figures.get(Integer.parseInt(event.getDragboard().getString())));
                        figures.remove(f);
                        for (Figure figure : figures) {
                            int p = figures.indexOf(figure);
                            figure.setPosition(p);
                            figures.set(p, figure);
                        }
                    } else {
                        tile.setCenter(figures.get(Integer.parseInt(event.getDragboard().getString())));
                    }
                    tile.onField = (Figure) tile.getCenter();
                    Figure f = (Figure) tile.getCenter();
                    f.incrementMoves();
                    f.setCurrentTile(tile);
                    figures.set(f.getPosition(), f);
                }
                else{
                    System.out.println("NIEDOZWOLONE");
                }
            }

        });
    }

    public int getPositionX() {
        return posx;
    }

    public int getPositionY() {
        return posy;
    }

    public FieldColor getFieldColor() {
        return fieldColor;
    }
    public void addFigure(Figure figure){
        figures.add(figure);
        figures.get(figures.indexOf(figure)).setPosition(figures.indexOf(figure));
    }

    public static List<Figure> getFigures() {
        return figures;
    }
}
