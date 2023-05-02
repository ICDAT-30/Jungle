package model;

import view.chessView.ElephantChessComponent;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED,"Lion",7));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE,"Lion",7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED,"Tiger",6));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE,"Tiger",6));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED,"Leopard",5));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE,"Leopard",5));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED,"Wolf",4));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE,"Wolf",4));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED,"Dog",3));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE,"Dog",3));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED,"Cat",2));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE,"Cat",2));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED,"Rat",1));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE,"Rat",1));
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        // TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        HashSet<ChessboardPoint> river = new HashSet<>();
        river.add(new ChessboardPoint(3,1));
        river.add(new ChessboardPoint(3,2));
        river.add(new ChessboardPoint(4,1));
        river.add(new ChessboardPoint(4,2));
        river.add(new ChessboardPoint(5,1));
        river.add(new ChessboardPoint(5,2));

        river.add(new ChessboardPoint(3,4));
        river.add(new ChessboardPoint(3,5));
        river.add(new ChessboardPoint(4,4));
        river.add(new ChessboardPoint(4,5));
        river.add(new ChessboardPoint(5,4));
        river.add(new ChessboardPoint(5,5));
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        if (getChessPieceAt(src).getName().equals("Elephant")
                | getChessPieceAt(src).getName().equals("Leopard")
                | getChessPieceAt(src).getName().equals("Dog")
                | getChessPieceAt(src).getName().equals("Wolf")
                | getChessPieceAt(src).getName().equals("Cat")){
            if (river.contains(dest)){
                return false;
            }else {
                return calculateDistance(src, dest) == 1;
            }
//TODO: finish it.
        }
        return  false;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        return false;
    }
}
