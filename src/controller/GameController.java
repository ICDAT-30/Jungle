package controller;


import listener.GameListener;
import model.*;
import view.CellView;
import view.chessView.*;
import view.ChessboardView;

import javax.swing.*;
import java.awt.*;
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
//这是在干嘛？先打个todo re：到要结束了也没明白，不管了
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
            //System.out.println(model.getChessPieceAt(selectedPoint).getRank());
            model.moveChessPiece(selectedPoint, point);
            //为什么有返回值之后就可以了？？？？草 太难绷了 re:好了，虽然不知道怎么好的
            //System.out.println(a);
            //System.out.println(model.getChessPieceAt(point).getRank());
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            removeCanMove();
            //更新label
            String text = String.format("Turn %d : %s", (steps.size()) / 2 + 1, currentPlayer.toString());
            view.statusLabel.setText(text);
            view.repaint();
            winner = checkWin();
            if (win()) {
                JOptionPane.showMessageDialog(null, winner.toString() + " is win!");
                reSet();
                return;
            }
            // if the chess enter Dens or Traps and so on 好像全写到move里了？不确定，再看看
            //System.out.println(steps.size());
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                setCanMove(selectedPoint);
                component.setSelected(true);
                component.repaint();
                view.repaint();
                view.revalidate();
                //选中棋子画圈在哪，找不到了  re:找到了，在每个动物的view里
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            removeCanMove();
            component.setSelected(false);
            component.repaint();
            view.repaint();
            view.revalidate();
        } else {
            removeCanMove();
            if (model.isValidCapture(selectedPoint, point)) {
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                selectedPoint = null;
                swapColor();
                String text = String.format("Turn %d : %s", (steps.size()) / 2 + 1, currentPlayer.toString());
                view.statusLabel.setText(text);
                deadPic();
                view.repaint();
                winner = checkWin();
                if (win()) {
                    JOptionPane.showMessageDialog(null, winner.toString() + " is win!");
                    reSet();
                    return;
                }
            }
            view.repaint();
            view.revalidate();
        }
    }

    public void reSet() {
        //烦死了烦死了烦死了这个破架构怎么看不懂 解决了，但是还是没看懂，算了能用就行
        this.currentPlayer = PlayerColor.BLUE;
        model.initBoard();
        view.removeChessComponent();
        //view.gridComponents = new CellView[9][7];
        view.initiateChessComponent(model);
        removeCanMove();
        //view.initiateGridComponents();
        view.repaint();
        view.revalidate();
        selectedPoint = null;
        this.steps = model.steps;
        view.statusLabel.setText("Turn 1 : BLUE");
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
        if ((!player.equals("RED")) & !(player.equals("BLUE"))) {
            JOptionPane.showMessageDialog(null, "缺少行棋方\n请重新选择",
                    "缺少行棋方", JOptionPane.ERROR_MESSAGE);
            return;
        }
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
            if (board[i].length != 7) {
                JOptionPane.showMessageDialog(null, "棋盘大小错误\n请重新选择",
                        "棋盘大小错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        //行棋，并检测棋子步骤错误
        Chessboard chessTest = new Chessboard();
        for (int i = 0; i < steps.length; i++) {
            if ((!steps[i][1].equals("Elephant")) &
                    (!steps[i][1].equals("Cat")) &
                    (!steps[i][1].equals("Dog")) &
                    (!steps[i][1].equals("Leopard")) &
                    (!steps[i][1].equals("Lion")) &
                    (!steps[i][1].equals("Rat")) &
                    (!steps[i][1].equals("Tiger")) &
                    (!steps[i][1].equals("Wolf"))){
                JOptionPane.showMessageDialog(null, "棋子类型错误\n请重新选择",
                        "棋子类型错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
                if (steps[i][6].equals("null")) {
                    ChessboardPoint src = new ChessboardPoint(Integer.parseInt(steps[i][2]), Integer.parseInt(steps[i][3]));
                    ChessboardPoint dest = new ChessboardPoint(Integer.parseInt(steps[i][4]), Integer.parseInt(steps[i][5]));
                    if (chessTest.getChessPieceAt(src) == null) {
                        JOptionPane.showMessageDialog(null, "棋子位置错误\n请重新选择",
                                "棋子位置错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!chessTest.isValidMove(src, dest)) {
                        JOptionPane.showMessageDialog(null, "棋子移动错误\n请重新选择",
                                "棋子移动错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    chessTest.moveChessPiece(src, dest);
                } else {
                    ChessboardPoint src = new ChessboardPoint(Integer.parseInt(steps[i][2]), Integer.parseInt(steps[i][3]));
                    ChessboardPoint dest = new ChessboardPoint(Integer.parseInt(steps[i][4]), Integer.parseInt(steps[i][5]));
                    if (chessTest.getChessPieceAt(src) == null | chessTest.getChessPieceAt(dest) == null) {
                        JOptionPane.showMessageDialog(null, "棋子位置错误\n请重新选择",
                                "棋子位置错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!chessTest.isValidCapture(src, dest)) {
                        JOptionPane.showMessageDialog(null, "棋子移动错误\n请重新选择",
                                "棋子移动错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    chessTest.captureChessPiece(src, dest);
                }
        }
        //检测棋子移动完位置错误
        boolean isPosition = true;
        label:
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (chessTest.getChessPieceAt(new ChessboardPoint(i, j)) == null) {
                    isPosition = board[i][j].equals("null");
                } else {
                    isPosition = board[i][j].equals(chessTest.getChessPieceAt(new ChessboardPoint(i, j)).getName());
                }
                if (!isPosition) break label;
            }
        }
        if (!isPosition) {
            JOptionPane.showMessageDialog(null, "棋子位置错误\n请重新选择",
                    "棋子位置错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //没有错误开始还原棋盘

        reSet();
        currentPlayer = player.equals("BLUE") ? PlayerColor.BLUE : PlayerColor.RED;
        view.statusLabel.setText(String.format("Turn %d : %s", (steps.length) / 2 + 1, currentPlayer.toString()));
        //statusLabel没有变化 好像不太对 re:controller的steps和board的steps在初始化之后解耦了
        for (int i = 0; i < steps.length; i++) {
            ChessboardPoint src = new ChessboardPoint(Integer.parseInt(steps[i][2]), Integer.parseInt(steps[i][3]));
            ChessboardPoint dest = new ChessboardPoint(Integer.parseInt(steps[i][4]), Integer.parseInt(steps[i][5]));
            if (steps[i][6].equals("null")) {
                model.moveChessPiece(src, dest);
                //System.out.println(1);
                view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
            } else {
                model.captureChessPiece(src, dest);
                //System.out.println(2);
                view.removeChessComponentAtGrid(dest);
                view.setChessComponentAtGrid(dest, view.removeChessComponentAtGrid(src));
            }
        }
        view.repaint();
        view.revalidate();
    }

    public void regret() {
        ArrayList<Step> newSteps = new ArrayList<>();
        if (steps.size() == 0) {
            JOptionPane.showMessageDialog(null, "已到达棋局开始，无法悔棋！",
                    "无法悔棋", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < steps.size() - 1; i++) {
            if (steps.get(i).getCapturedPiece() == null) {
                newSteps.add(new Step(steps.get(i).getSrc(), steps.get(i).getDest(), steps.get(i).getPiece()));
            } else {
                newSteps.add(new Step(steps.get(i).getSrc(), steps.get(i).getDest(),
                        steps.get(i).getPiece(), steps.get(i).getCapturedPiece()));
            }
        }
        reSet();
        for (int i = 0; i < newSteps.size(); i++) {
            if (newSteps.get(i).getCapturedPiece() == null) {
                model.moveChessPiece(newSteps.get(i).getSrc(), newSteps.get(i).getDest());
                view.setChessComponentAtGrid(newSteps.get(i).getDest(),
                        view.removeChessComponentAtGrid(newSteps.get(i).getSrc()));
                swapColor();
                view.statusLabel.setText(String.format("Turn %d : %s", (steps.size()) / 2 + 1, currentPlayer.toString()));
            } else {
                model.captureChessPiece(newSteps.get(i).getSrc(), newSteps.get(i).getDest());
                view.removeChessComponentAtGrid(newSteps.get(i).getDest());
                view.setChessComponentAtGrid(newSteps.get(i).getDest(),
                        view.removeChessComponentAtGrid(newSteps.get(i).getSrc()));
                swapColor();
                view.statusLabel.setText(String.format("Turn %d : %s", (steps.size()) / 2 + 1, currentPlayer.toString()));
            }
        }
        view.repaint();
        view.revalidate();
    }

    //TODO:更美观的方式显示被吃的棋子，但是想不明白怎么写 re：javaswing是傻逼
    public void deadPic() {
        for (int i = 0; i < model.getRedDead().size(); i++) {
            ImageIcon icon;
            System.out.println("1");
            if (model.getRedDead().get(i).getName().equals("Elephant")) {
                Image image = new ImageIcon("chess/red/Elephant.jpg").getImage();
                image = image.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
                icon = new ImageIcon(image);
                System.out.println("e");
                //TODO:路径错了，记得改
            } else if (model.getRedDead().get(i).getName().equals("Lion")) {
                Image image = new ImageIcon("resource/chess/red/Lion.jpg").getImage();
                image = image.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
                icon = new ImageIcon(image);
                System.out.println("l");
            } else if (model.getRedDead().get(i).getName().equals("Tiger")) {
                Image image = new ImageIcon("resource/chess/red/Tiger.jpg").getImage();
                image = image.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
                icon = new ImageIcon(image);
            } else if (model.getRedDead().get(i).getName().equals("Leopard")) {
                Image image = new ImageIcon("resource/chess/red/Leopard.jpg").getImage();
                image = image.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
                icon = new ImageIcon(image);
            } else if (model.getRedDead().get(i).getName().equals("Wolf")) {
                Image image = new ImageIcon("resource/chess/red/Wolf.jpg").getImage();
                image = image.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
                icon = new ImageIcon(image);
            } else if (model.getRedDead().get(i).getName().equals("Dog")) {
                Image image = new ImageIcon("resource/chess/red/Dog.jpg").getImage();
                image = image.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
                icon = new ImageIcon(image);
            } else if (model.getRedDead().get(i).getName().equals("Cat")) {
                Image image = new ImageIcon("resource/chess/red/Cat.jpg").getImage();
                image = image.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
                icon = new ImageIcon(image);
            } else {
                Image image = new ImageIcon("resource/chess/red/Rat.jpg").getImage();
                image = image.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
                icon = new ImageIcon(image);
            }
            JLabel label = new JLabel();
            label.setIcon(icon);
            label.setLocation(600, 400);
            view.gameFrame.mainFrame.add(label);
        }
    }
    public void playBack(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Step> newSteps = new ArrayList<>();
                if (steps.size() == 0) {
                    JOptionPane.showMessageDialog(null, "还未开始棋局，无法回放棋局！",
                            "无法回放", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for (int i = 0; i < steps.size(); i++) {
                    if (steps.get(i).getCapturedPiece() == null) {
                        newSteps.add(new Step(steps.get(i).getSrc(), steps.get(i).getDest(), steps.get(i).getPiece()));
                    } else {
                        newSteps.add(new Step(steps.get(i).getSrc(), steps.get(i).getDest(),
                                steps.get(i).getPiece(), steps.get(i).getCapturedPiece()));
                    }
                }
                reSet();
                view.repaint();
                view.revalidate();
                for (int i = 0; i < newSteps.size(); i++) {
                    try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    if (newSteps.get(i).getCapturedPiece() == null) {
                        model.moveChessPiece(newSteps.get(i).getSrc(), newSteps.get(i).getDest());
                        view.setChessComponentAtGrid(newSteps.get(i).getDest(),
                                view.removeChessComponentAtGrid(newSteps.get(i).getSrc()));
                        swapColor();
                        view.statusLabel.setText(String.format("Turn %d : %s", (steps.size()) / 2 + 1, currentPlayer.toString()));
                        view.repaint();
                        view.revalidate();
                    } else {
                        model.captureChessPiece(newSteps.get(i).getSrc(), newSteps.get(i).getDest());
                        view.removeChessComponentAtGrid(newSteps.get(i).getDest());
                        view.setChessComponentAtGrid(newSteps.get(i).getDest(),
                                view.removeChessComponentAtGrid(newSteps.get(i).getSrc()));
                        swapColor();
                        view.statusLabel.setText(String.format("Turn %d : %s", (steps.size()) / 2 + 1, currentPlayer.toString()));
                        view.repaint();
                        view.revalidate();
                    }
                }
            }
        });
        thread.start();




    }
    private void setCanMove(ChessboardPoint point) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint dest = new ChessboardPoint(i, j);
                if (model.isValidMove(point, dest) || model.isValidCapture(point, dest)) {
                    view.gridComponents[i][j].setCanMove(true);
                }
            }
        }
    }

    private void removeCanMove() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                view.gridComponents[i][j].setCanMove(false);
            }
        }
    }
}
