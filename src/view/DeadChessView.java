package view;

import controller.GameController;
import model.ChessPiece;
import model.Chessboard;
import model.ChessboardPoint;
import model.PlayerColor;
import view.chessView.*;

import javax.swing.*;
import java.awt.*;

public class DeadChessView extends JComponent {
    public CellView[][] deadGrid;
    private final int CHESS_SIZE;

    private GameController gameController;
    private GameFrame gameFrame;

    private PlayerColor color;


    public DeadChessView(int chessSize, PlayerColor color, GameController gameController, GameFrame gameFrame) {
        this.color = color;
        this.gameController = gameController;
        this.gameFrame = gameFrame;
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 2;
        int height = CHESS_SIZE * 4;
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        this.deadGrid = new CellView[4][2];
        initiateGrid();
    }

    public void addDeadChess() {
        if (this.color.equals(PlayerColor.RED)) {
            for (int i = 0; i < gameController.model.getRedDead().size(); i++) {
                ChessPiece chessPiece = gameController.model.getRedDead().get(i);
                int y = i > 3 ? 1 : 0;
                int x = i > 3 ? i - 4 : i;
                if (chessPiece.getName().equals("Elephant")) {
                    deadGrid[x][y].add(
                            new ElephantChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Lion")) {
                    deadGrid[x][y].add(
                            new LionChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Tiger")) {
                    deadGrid[x][y].add(
                            new TigerChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Leopard")) {
                    deadGrid[x][y].add(
                            new LeopardChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Wolf")) {
                    deadGrid[x][y].add(
                            new WolfChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Dog")) {
                    deadGrid[x][y].add(
                            new DogChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Cat")) {
                    deadGrid[x][y].add(
                            new CatChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else {
                    deadGrid[x][y].add(
                            new RatChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                }
            }
        }else {
            for (int i = 0; i < gameController.model.getBlueDead().size(); i++) {
                ChessPiece chessPiece = gameController.model.getBlueDead().get(i);
                int y = i > 3 ? 1 : 0;
                int x = i > 3 ? i - 4 : i;
                if (chessPiece.getName().equals("Elephant")) {
                    deadGrid[x][y].add(
                            new ElephantChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Lion")) {
                    deadGrid[x][y].add(
                            new LionChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Tiger")) {
                    deadGrid[x][y].add(
                            new TigerChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Leopard")) {
                    deadGrid[x][y].add(
                            new LeopardChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Wolf")) {
                    deadGrid[x][y].add(
                            new WolfChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Dog")) {
                    deadGrid[x][y].add(
                            new DogChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else if (chessPiece.getName().equals("Cat")) {
                    deadGrid[x][y].add(
                            new CatChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                } else {
                    deadGrid[x][y].add(
                            new RatChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE));
                }
            }
        }
    }
    public void initiateGrid(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                CellView cell;
                cell = new CellView(new Color(200,0,0,0), calculatePoint(i, j), CHESS_SIZE);
                deadGrid[i][j] = cell;
                this.add(cell);
                //deadGrid[i][j].repaint();
                //deadGrid[i][j].revalidate();
            }
        }
    }

    public void removeGird(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    deadGrid[i][j].removeAll();
                    //deadGrid[i][j].remove(0);
//                    deadGrid[i][j].repaint();
                    deadGrid[i][j].revalidate();
                }catch (Exception e){

                }
            }
        }
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }
    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }
}
