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
import java.util.Map;
import java.util.TreeMap;

public class Figure extends ImageView {
    public static int totalMoves = 0;
    public int moves = 0;
    public static boolean castleFlag = false;
    public static StringBuilder PGN = new StringBuilder("");
    private final FigureColor color;
    private FigureType type;
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

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == this.getClass()) {
            Figure ob = (Figure) obj;
            return  this.getColor() == ob.getColor()&&this.getType()==ob.getType()&&this.getCurrentTile()==ob.getCurrentTile();
        }
        return false;
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

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }
    public void incrementMoves(){
        this.moves++;
    }
    public void setType(FigureType type) throws FileNotFoundException {
        this.type = type;
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

    public void addToNotation(int xc, int yc, Figure f2){
        if(!castleFlag){
            PGN.append(convertToPGN(this,xc,yc,f2));
        }
        castleFlag = false;
    }
    private static String convertToPGN(Figure f,int xc,int yc,Figure f2) {
        Map<FigureType, String> conv = new TreeMap<>();
        conv.put(FigureType.PAWN, "");
        conv.put(FigureType.BISHOP, "B");
        conv.put(FigureType.ROOK, "R");
        conv.put(FigureType.KING, "K");
        conv.put(FigureType.KNIGHT, "N");
        conv.put(FigureType.QUEEN, "Q");
        Map<Integer, String> x = new TreeMap<>();
        x.put(0, "a");
        x.put(1, "b");
        x.put(2, "c");
        x.put(3, "d");
        x.put(4, "e");
        x.put(5, "f");
        x.put(6, "g");
        x.put(7, "h");
        StringBuilder s = new StringBuilder("");
        if (f.color == FigureColor.BLACK) {
            s.append("B");
        } else {
            s.append("W");
        }
        s.append((int) Math.ceil((double) (++totalMoves) / 2));
        s.append(".");
        if (f2 == null) {
            s.append(conv.get(f.type));
            s.append(x.get(xc));
            s.append(Math.abs(8 - yc));
            s.append(" ");
            return s.toString();
        } else {
            if(f.type == FigureType.PAWN) {
                s.append(conv.get(f.type));
                s.append(x.get(f.getCurrentTile().getPositionX()));
                s.append("x");
                s.append(x.get(xc));
                s.append(Math.abs(8 - yc));
                s.append(" ");
                return s.toString();
            }
            else {
                s.append(conv.get(f.type));
                s.append("x");
                s.append(x.get(xc));
                s.append(Math.abs(8 - yc));
                s.append(" ");
                return s.toString();
            }
        }
    }
}
