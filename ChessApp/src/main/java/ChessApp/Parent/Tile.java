package ChessApp.Parent;

import ChessApp.Enums.FieldColor;
import ChessApp.Enums.FigureColor;
import ChessApp.Node.Figure;
import ChessApp.Util.AvailableMovements;
import ChessApp.Util.BoardUtil;
import ChessApp.Util.TextDialog;
import javafx.collections.ObservableArray;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Tile  extends BorderPane {
    public static ArrayList<Figure> figures = new ArrayList<>();
    public static FigureColor turn = FigureColor.WHITE;
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
                if (turn==figures.get(Integer.parseInt(event.getDragboard().getString())).getColor()&&AvailableMovements.checkAvailableMovement(tile, tile.getPositionX(), tile.getPositionY(), figures.get(Integer.parseInt(event.getDragboard().getString())))) {
                    if (tile.getCenter() != null && figures.indexOf((Figure) tile.getCenter()) != Integer.parseInt(event.getDragboard().getString())) {
                        changeTurn();
                        Figure f = (Figure) tile.getCenter();
                        if(f.getColor()!=figures.get(Integer.parseInt(event.getDragboard().getString())).getColor()) {
                            figures.get(Integer.parseInt(event.getDragboard().getString())).addToNotation(tile.getPositionX(),tile.getPositionY(),f);
                            tile.setCenter(figures.get(Integer.parseInt(event.getDragboard().getString())));
                            figures.remove(f);
                            BoardUtil.updatePositions();
                        }
                        else {
                            TextDialog.getTextDialog("NIEDOZWOLONE").showAndWait();
                        }
                    } else {
                        changeTurn();
                        tile.setCenter(figures.get(Integer.parseInt(event.getDragboard().getString())));
                        figures.get(Integer.parseInt(event.getDragboard().getString())).addToNotation(tile.getPositionX(),tile.getPositionY(),null);
                    }
                    Figure f = (Figure) tile.getCenter();
                    f.incrementMoves();
                    f.setCurrentTile(tile);
                    figures.set(f.getPosition(), f);
                    System.out.println(Figure.PGN);
                }
                else{
                    TextDialog.getTextDialog("NIEDOZWOLONE").showAndWait();
                }
                if(AvailableMovements.findKing(FigureColor.BLACK)==null){
                    TextDialog.getTextDialog("BIALE WYGRALY").showAndWait();
                    tile.getScene().getWindow().hide();
                    //figures = new ArrayList<>();
                    //tile.getScene().setRoot(BoardUtil.getChessBoard());

                }
                else if(AvailableMovements.findKing(FigureColor.WHITE)==null) {
                    TextDialog.getTextDialog("CZARNE WYGRALY").showAndWait();
                    tile.getScene().getWindow().hide();
                    //figures = new ArrayList<>();
                    //tile.getScene().setRoot(BoardUtil.getChessBoard());
                }
            }
        });
    }
    public void changeTurn(){
        if(turn == FigureColor.BLACK){
            turn = FigureColor.WHITE;
        }
        else if(turn == FigureColor.WHITE){
            turn= FigureColor.BLACK;
        }
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

    @Override
    public boolean equals(Object obj) {
        Tile t = (Tile) obj;
        return this.posx == t.posx && this.posy == t.posy;
    }
}
