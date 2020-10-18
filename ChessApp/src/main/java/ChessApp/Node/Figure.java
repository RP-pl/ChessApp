package ChessApp.Node;

import ChessApp.Enums.FigureColor;
import ChessApp.Enums.FigureType;
import ChessApp.Parent.Tile;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Figure extends ImageView {
    private final FigureColor color;
    private final FigureType type;
    private final Figure figure = this;
    private int position;
    private Tile currentTile;

    public Figure(FigureColor color, FigureType type) throws FileNotFoundException {
        this.color = color;
        this.type = type;
        this.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard d = figure.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(String.valueOf(position));
                d.setContent(content);
            }
        });
        if(type == FigureType.PAWN){
            if(color == FigureColor.BLACK){
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\Black_pawn.png")));
            }
            else{
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\White_pawn.png")));
            }
        }
        else if(type == FigureType.BISHOP){
            if(color == FigureColor.BLACK){
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\Black_bishop.png")));
            }
            else{
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\White_bishop.png")));
            }
        }
        else if(type == FigureType.KING){
            if(color == FigureColor.BLACK){
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\Black_king.png")));
            }
            else{
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\White_king.png")));
            }
        }
        else if(type == FigureType.KNIGHT){
            if(color == FigureColor.BLACK){
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\Black_knight.png")));
            }
            else{
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\White_knight.png")));
            }
        }
        else if(type == FigureType.ROOK){
            if(color == FigureColor.BLACK){
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\Black_rook.png")));
            }
            else{
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\White_rook.png")));
            }
        }
        else if(type == FigureType.QUEEN){
            if(color == FigureColor.BLACK){
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\Black_queen.png")));
            }
            else{
                this.setImage(new Image(new FileInputStream("src\\main\\resources\\White_queen.png")));
            }
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public int getPosition() {
        return position;
    }

    public FigureType getType() {
        return type;
    }

    public FigureColor getColor() {
        return color;
    }
}
