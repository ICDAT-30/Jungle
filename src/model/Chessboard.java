package model;

import view.chessView.ElephantChessComponent;

import javax.swing.*;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private HashSet<ChessboardPoint> river;

    public List<Step> steps;
    private ArrayList<ChessPiece> redDead;
    private ArrayList<ChessPiece> blueDead;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
        this.steps = new ArrayList<>();
        this.redDead = new ArrayList<>();
        this.blueDead = new ArrayList<>();
        initGrid();
        initPieces();
        HashSet<ChessboardPoint> river2 = new HashSet<>();
        river2.add(new ChessboardPoint(3, 1));
        river2.add(new ChessboardPoint(3, 2));
        river2.add(new ChessboardPoint(4, 1));
        river2.add(new ChessboardPoint(4, 2));
        river2.add(new ChessboardPoint(5, 1));
        river2.add(new ChessboardPoint(5, 2));

        river2.add(new ChessboardPoint(3, 4));
        river2.add(new ChessboardPoint(3, 5));
        river2.add(new ChessboardPoint(4, 4));
        river2.add(new ChessboardPoint(4, 5));
        river2.add(new ChessboardPoint(5, 4));
        river2.add(new ChessboardPoint(5, 5));
        this.river = river2;
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
        grid[0][2].setType(CellType.RED_TRAP);
        grid[1][3].setType(CellType.RED_TRAP);
        grid[0][4].setType(CellType.RED_TRAP);
        grid[0][2].setType(CellType.BLUE_TRAP);
        grid[7][3].setType(CellType.BLUE_TRAP);
        grid[8][4].setType(CellType.BLUE_TRAP);
        grid[0][3].setType(CellType.RED_DEN);
        grid[8][3].setType(CellType.BLUE_DEN);
    }

    private void initPieces() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].removePiece();
            }
        }
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
    }

    //记得改private re：没啥可改的，写着写着就会发现还是public好
    public ChessPiece getChessPieceAt(ChessboardPoint point) {
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

    public void initBoard() {
        initGrid();
        initPieces();
        this.steps = new ArrayList<>();
        this.redDead = new ArrayList<>();
        this.blueDead = new ArrayList<>();
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        int a = 0;//test
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        //进入兽穴rank = 0 出现问题  re：已解决但是不知道为什么一直没有删掉todo
        if (getGridAt(src).getType() != null) {
            if (getGridAt(src).getType().equals(CellType.RED_TRAP)
                    | getGridAt(src).getType().equals(CellType.BLUE_TRAP)) {
                this.outOfTrap(src, dest);
            }
        }
        if (getGridAt(dest).getType() != null) {
            if (getGridAt(dest).getType().equals(CellType.RED_TRAP)
                    | getGridAt(dest).getType().equals(CellType.BLUE_TRAP)) {
                //a =
                this.intoTrap(src, dest); //test
            }
        }
        steps.add(new Step(src, dest, getChessPieceAt(src)));
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        //有可能出问题  re：看起来没问题
        steps.add(new Step(src, dest, getChessPieceAt(src), getChessPieceAt(dest)));
        if (getChessPieceAt(dest).getOwner().equals(PlayerColor.RED)) {
            redDead.add(getChessPieceAt(dest));
        } else {
            blueDead.add(getChessPieceAt(dest));
        }
        removeChessPiece(dest);
        setChessPiece(dest, removeChessPiece(src));
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {

        if (getChessPieceAt(src) == null) {
            return false;
        }
        if (getGridAt(dest).getType() != null) {
            if (getChessPieceAt(src).getOwner().equals(PlayerColor.RED)) {
                if (getGridAt(dest).getType().equals(CellType.RED_DEN)) return false;
            } else {
                if (getGridAt(dest).getType().equals(CellType.BLUE_DEN)) return false;
            }
        }

//象，豹，狼，狗，猫的行棋逻辑
        if (getChessPieceAt(src).getName().equals("Elephant")
                | getChessPieceAt(src).getName().equals("Leopard")
                | getChessPieceAt(src).getName().equals("Dog")
                | getChessPieceAt(src).getName().equals("Wolf")
                | getChessPieceAt(src).getName().equals("Cat")) {
            if (river.contains(dest)) {
                return false;
            } else {
                return calculateDistance(src, dest) == 1;
            }
//狮，虎的行棋逻辑
        } else if (getChessPieceAt(src).getName().equals("Lion")
                | getChessPieceAt(src).getName().equals("Tiger")) {
            if (river.contains(dest)) {    //判断终点是不是河
                return false;
            } else if (calculateDistance(src, dest) == 1) {
                return true;   //如果只走一格直接走
            } else {
                int y = Math.abs(src.getCol() - dest.getCol());
                int x = Math.abs(src.getRow() - dest.getRow());
                if (x != 0 & y != 0) {
                    return false;   //判断是不是在一行
                } else if (x == 0) {
                    boolean allRiver = true;
                    for (int i = Math.min(src.getCol(), dest.getCol()) + 1; i < Math.max(src.getCol(), dest.getCol()); i++) {
                        ChessboardPoint point = new ChessboardPoint(src.getRow(), i);
                        if (getChessPieceAt(point) != null) {
                            return false;
                        }
                        if (!river.contains(point)) {
                            allRiver = false;
                            break;
                        }
                    }
                    return allRiver;
                } else {
                    boolean allRiver = true;
                    for (int i = Math.min(src.getRow(), dest.getRow()) + 1; i < Math.max(src.getRow(), dest.getRow()); i++) {
                        ChessboardPoint point = new ChessboardPoint(i, src.getCol());
                        if (getChessPieceAt(point) != null) {
                            return false;
                        }
                        if (!river.contains(point)) {
                            allRiver = false;
                            break;
                        }
                    }
                    return allRiver;
                }
            }
//鼠鼠的行棋逻辑
        } else {
            return calculateDistance(src, dest) == 1;
        }
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) != null & getChessPieceAt(dest) != null) {
            if (getChessPieceOwner(src) != getChessPieceOwner(dest))
                return getChessPieceAt(src).canCapture(getChessPieceAt(dest)) && isValidMove(src, dest);
        }
        return false;
    }

    public void intoTrap(ChessboardPoint src, ChessboardPoint dest) {
        if (getGridAt(dest).getType().equals(CellType.RED_TRAP)
                & getChessPieceAt(src).getOwner().equals(PlayerColor.BLUE)) {
            getChessPieceAt(src).setRank(0);
//            return 1;
        } else if (getGridAt(dest).getType().equals(CellType.BLUE_TRAP)
                & getChessPieceAt(src).getOwner().equals(PlayerColor.RED)) {
            getChessPieceAt(src).setRank(0);
//            return 2;
        }
//        return  0;
    }

    public void outOfTrap(ChessboardPoint src, ChessboardPoint dest) {
        getChessPieceAt(src).setRank(getChessPieceAt(src).getName());
    }

    public PlayerColor checkWin() {
        if (grid[0][3].getPiece() != null) {
            if (grid[0][3].getPiece().getOwner() == PlayerColor.BLUE) {
                return PlayerColor.BLUE;
            }
        }
        if (grid[8][3].getPiece() != null) {
            if (grid[8][3].getPiece().getOwner() == PlayerColor.RED) {
                return PlayerColor.RED;
            }
        }
        if (blueDead.size() == 8) {
            return PlayerColor.RED;
        }
        if (redDead.size() == 8) {
            return PlayerColor.BLUE;
        }
        return null;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public ArrayList<ChessPiece> getRedDead() {
        return redDead;
    }

    public ArrayList<ChessPiece> getBlueDead() {
        return blueDead;
    }
}
