package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class GameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;

    private final int ONE_CHESS_SIZE;

    JLabel statusLabel;
    private ChessboardView chessboardView;
    public GameFrame(int width, int height) {
        setTitle("Judge"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addTurnLabel();
        addResetButton();
        addSaveButton();
        addLoadButton();

    }

    public ChessboardView getChessboardView() {
        return chessboardView;
    }

    public void setChessboardView(ChessboardView chessboardView) {
        this.chessboardView = chessboardView;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardView = new ChessboardView(ONE_CHESS_SIZE,statusLabel);
        chessboardView.setLocation(HEIGHT / 5, HEIGHT / 10);
        add(chessboardView);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addTurnLabel() {
        statusLabel = new JLabel("Turn 1 : BLUE");
        statusLabel.setLocation(HEIGHT, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addResetButton() {
        JButton button = new JButton("Reset");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
                chessboardView.gameController.reSet();
                chessboardView.repaint();
            }
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog("存档名");
            while (path.equals("")) {
                JOptionPane.showMessageDialog(null, "存档名不能为空！");
                path = JOptionPane.showInputDialog("存档名");
            }
            try {
                chessboardView.gameController.save(path);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chessboardView.gameController.Load();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button.setLocation(HEIGHT, HEIGHT / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
//    }


}
