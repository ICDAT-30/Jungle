package controller;


import listener.GameListener;
import model.*;
import view.CellView;
import view.chessView.AnimalChessComponent;
import view.ChessboardView;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardView view;
    private PlayerColor currentPlayer;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    private List<Step> steps;

    private PlayerColor winner;

    public GameController(ChessboardView view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.steps = model.getSteps();
        this.winner = null;
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
//TODO:这是在干嘛？先打个todo
            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    private Boolean win() {
        return winner != null;
    }

    private PlayerColor checkWin() {
        return model.checkWin();
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellView component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            System.out.println(model.getChessPieceAt(selectedPoint).getRank());
            model.moveChessPiece(selectedPoint, point);
            //为什么有返回值之后就可以了？？？？草 太难绷了 re:好了，虽然不知道怎么好的
            //System.out.println(a);
            System.out.println(model.getChessPieceAt(point).getRank());
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            //TODO:更新label
            view.repaint();
            winner = checkWin();
            if (win()) {
                System.out.println("win!");//test
            }
            // TODO: if the chess enter Dens or Traps and so on 好像全写到move里了？不确定，再看看
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidCapture(selectedPoint, point)) {
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                view.repaint();
                winner = checkWin();
                if (win()) {
                    System.out.println("win!");//test
                }
            }
        }

        // TODO: Implement capture function
    }

    public void reSet() {
        //TODO:烦死了烦死了烦死了这个破架构怎么看不懂
        this.currentPlayer = PlayerColor.BLUE;
        model.initBoard();
        view.initiateChessComponent(model);
        view.initiateGridComponents();
        selectedPoint = null;
    }

    public void save(String fileName) throws IOException {
        String location = "save\\" + fileName + ".txt";
        File file = new File(location);
        if (file.exists()) {
            int n = JOptionPane.showConfirmDialog(view, "存档已存在，是否覆盖?", "", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                file.delete();
            }
        }

        FileWriter fileWriter = new FileWriter(location, true);
        //储存步数
        String step = String.valueOf(steps.size());
        fileWriter.write(step);
        fileWriter.write("\n");
        //当前行棋方
        if (currentPlayer == PlayerColor.RED) {
            fileWriter.write("RED");
        } else {
            fileWriter.write("BLUE");
        }
        fileWriter.write("\n");
        //储存步骤
        for (int i = 0; i < steps.size(); i++) {
            fileWriter.write(steps.get(i).toString());
            fileWriter.write("\n");
        }
        fileWriter.write("\n");
        //储存棋盘
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                String name = model.getGrid()[i][j].getPiece() != null ? model.getGrid()[i][j].getPiece().getName() : "null";
                fileWriter.write(name + " ");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
        System.out.println("Save Done");
    }

    public void Load() throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("save"));
        chooser.showOpenDialog(view);
        File file = chooser.getSelectedFile();

        if (!file.getName().endsWith(".txt")) {
            JOptionPane.showMessageDialog(null, "文件扩展名错误\n请重新选择",
                    "文件扩展名错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ArrayList<String> readList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            readList.add(line);
        }
        System.out.println(readList.size());
        for (int i = 0; i < readList.size(); i++) {
            System.out.println(readList.get(i));
        }
        //读取步数
        ArrayList<Step> stepArrayList = new ArrayList<>();
        int step = Integer.parseInt(readList.get(0));
        String player = readList.get(1);
        String[][] steps = new String[step][];
        for (int i = 2; i < step + 2; i++) {
            String[] str = readList.get(i).split(" ");
            steps[i - 2] = str;
//            ChessboardPoint src = new ChessboardPoint(Integer.parseInt(steps[i-2][2]),Integer.parseInt(steps[i-2][3]));
//            ChessboardPoint dest = new ChessboardPoint(Integer.parseInt(steps[i-2][4]),Integer.parseInt(steps[i-2][5]));
//            stepArrayList.get(i-2) = new Step(src,dest,new ChessPiece((steps[i-2][0] == "BLUE" ? PlayerColor.BLUE: PlayerColor.RED),
//                    steps[i-2][1],);
        }
        //判断行棋方是否正确
        boolean isPlayer = true;
        for (int i = 0; i < steps.length - 1; i++) {
            if (steps[i][0].equals(steps[i + 1][0])) isPlayer = false;
            break;
        }
        if (steps[step - 1][0].equals(player) | !isPlayer) {
            JOptionPane.showMessageDialog(null, "行棋方错误\n请重新选择",
                    "行棋方错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //读取棋盘
        String[][] board = new String[9][];
        for (int i = readList.size() - 9; i < readList.size(); i++) {
            //System.out.println("i");
            String str = readList.get(i);
            //System.out.println(str);
            board[i + 9 - readList.size()] = str.split(" ");
            //System.out.println(Arrays.toString(board[i + 9 - readList.size()]));
        }
        //检测棋盘大小错误
        for (int i = 0; i < 9; i++) {
            if (board[i].length!= 7){
                JOptionPane.showMessageDialog(null, "棋盘大小错误\n请重新选择",
                        "棋盘大小错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //行棋，并检测棋子步骤错误
        Chessboard chessTest = new Chessboard();
        for (int i = 0; i < steps.length; i++) {
            if (steps[i][6].equals("null")){
                ChessboardPoint src = new ChessboardPoint(Integer.parseInt(steps[i][2]),Integer.parseInt(steps[i][3]));
                ChessboardPoint dest = new ChessboardPoint(Integer.parseInt(steps[i][4]),Integer.parseInt(steps[i][5]));
                if (chessTest.getChessPieceAt(src) == null){
                    JOptionPane.showMessageDialog(null, "棋子位置错误\n请重新选择",
                            "棋子位置错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!chessTest.isValidMove(src,dest)){
                    JOptionPane.showMessageDialog(null, "棋子移动错误\n请重新选择",
                            "棋子移动错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                chessTest.moveChessPiece(src,dest);
            }else {
                ChessboardPoint src = new ChessboardPoint(Integer.parseInt(steps[i][2]),Integer.parseInt(steps[i][3]));
                ChessboardPoint dest = new ChessboardPoint(Integer.parseInt(steps[i][4]),Integer.parseInt(steps[i][5]));
                if (chessTest.getChessPieceAt(src) == null | chessTest.getChessPieceAt(dest) == null){
                    JOptionPane.showMessageDialog(null, "棋子位置错误\n请重新选择",
                            "棋子位置错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(!chessTest.isValidCapture(src,dest)){
                    JOptionPane.showMessageDialog(null, "棋子移动错误\n请重新选择",
                            "棋子移动错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                chessTest.captureChessPiece(src,dest);
            }
        }
        //检测棋子移动完位置错误
        boolean isPosition = true;
        label:
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (chessTest.getChessPieceAt(new ChessboardPoint(i,j)) == null){
                    isPosition = board[i][j].equals("null");
                }else {
                    isPosition = board[i][j].equals(chessTest.getChessPieceAt(new ChessboardPoint(i,j)).getName());
                }
                if (!isPosition) break label;
            }
        }
        if (!isPosition){
            JOptionPane.showMessageDialog(null, "棋子位置错误\n请重新选择",
                    "棋子位置错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //没有错误开始还原棋盘
        //TODO:先写好reset，在重新行棋一遍
    }
}
