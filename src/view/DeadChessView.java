package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;

public class DeadChessView extends JComponent {
    public CellView[][] deadGrid ;
    private final int CHESS_SIZE ;

    private GameController gameController;
    private Chessboard chessboard;
    private GameFrame gameFrame;

    private Color color;


    public DeadChessView(int chessSize,Color color,GameController gameController, GameFrame gameFrame) {
        this.color =color;
        this.gameController = gameController;
        this.gameFrame = gameFrame;
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 2;
        int height = CHESS_SIZE * 4;
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        this.deadGrid= new CellView[4][2];
    }

}
