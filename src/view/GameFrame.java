package view;

import model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class GameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;

    private final int ONE_CHESS_SIZE;

    JLabel statusLabel;
    JLabel timeLabel;
    JLabel background;
    JLabel chessboard;
    JLabel[][] captured;

    public ChessboardView chessboardView;
    public MainFrame mainFrame;

    public final JLabel bg1;
    public final JLabel bg2;
    public final JLabel cb1;
    public final JLabel cb2;

    public GameFrame(int width, int height) {
        setTitle("Judge"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.ONE_CHESS_SIZE = (HEIGHT * 4 / 5) / 9;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        Image image = new ImageIcon("bg/1.jpg").getImage();
        image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        bg1 = new JLabel(icon);
        bg1.setSize(width, height);
        bg1.setLocation(0, 0);

        image = new ImageIcon("bg/cb1.jpg").getImage();
        image = image.getScaledInstance(ONE_CHESS_SIZE * 7, ONE_CHESS_SIZE * 9, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        cb1 = new JLabel(icon);
        cb1.setSize(ONE_CHESS_SIZE * 7, ONE_CHESS_SIZE * 9);
        cb1.setLocation(HEIGHT / 5, HEIGHT / 10);

        image = new ImageIcon("bg/2.png").getImage();
        image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        bg2 = new JLabel(icon);
        bg2.setSize(width, height);
        bg2.setLocation(0, 0);

        image = new ImageIcon("bg/cb2.jpg").getImage();
        image = image.getScaledInstance(ONE_CHESS_SIZE * 7, ONE_CHESS_SIZE * 9, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        cb2 = new JLabel(icon);
        cb2.setSize(ONE_CHESS_SIZE * 7, ONE_CHESS_SIZE * 9);
        cb2.setLocation(HEIGHT / 5, HEIGHT / 10);

        background = bg1;
        chessboard = cb1;
        captured = new JLabel[2][8]; // 第1行red 第2行blue

        addStatusLabel();
        addTimeLabel();
        addViewingAreaLabel();
        addChessboard();
        addResetButton();
        addSaveButton();
        addLoadButton();
        addRegretButton();
        addPBButton();
        addReturnButton();
        addBGButton();
        add(chessboard);
        add(background);

    }

    public ChessboardView getChessboardView() {
        return chessboardView;
    }

    public void setChessboardView(ChessboardView chessboardView) {
        this.chessboardView = chessboardView;
    }

    private void addChessboard() {
        chessboardView = new ChessboardView(ONE_CHESS_SIZE, this.statusLabel,this.timeLabel);
        chessboardView.gameFrame = this;
        chessboardView.setLocation(HEIGHT / 5, HEIGHT / 10);
        add(chessboardView);
    }

    private void addStatusLabel() {
        statusLabel = new JLabel("Turn 1 : BLUE");
        statusLabel.setLocation(HEIGHT - 500, HEIGHT / 10 - 60);
        statusLabel.setSize(280, 60);
        statusLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 35));
        add(statusLabel);
    }

    private void addTimeLabel() {
        timeLabel = new JLabel("0s");
        timeLabel.setLocation(HEIGHT + 200, HEIGHT / 10 - 60);
        timeLabel.setSize(280, 60);
        timeLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 35));
        add(timeLabel);
    }

    private void addViewingAreaLabel() {
        JLabel redVA = new JLabel("Red Viewing Area");
        JLabel blueVA = new JLabel("Blue Viewing Area");
        redVA.setLocation(HEIGHT-50 , HEIGHT / 10 - 60);
        redVA.setSize(280, 60);
        redVA.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
        redVA.setForeground(new Color(250,128,114,255));
        add(redVA);
        blueVA.setLocation(HEIGHT-55, HEIGHT / 10 +4*ONE_CHESS_SIZE+10);
        blueVA.setSize(280, 60);
        blueVA.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
        blueVA.setForeground(new Color(176,224,230,255));
        add(blueVA);
    }

    private void addBGButton() {
        JButton button = new JButton("Change Theme");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (background.equals(bg1)) {
                    remove(background);
                    background = bg2;
                    remove(chessboard);
                    chessboard = cb2;
                } else {
                    remove(background);
                    background = bg1;
                    remove(chessboard);
                    chessboard = cb1;
                }

                add(chessboard);
                add(background);
                repaint();
                revalidate();
            }
        });
        button.setLocation(HEIGHT + 200, HEIGHT / 10);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
        add(button);
    }

    private void addResetButton() {
        JButton button = new JButton("Reset");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //1TODO re:这为什么打了个todo？删了
                chessboardView.gameController.reset();
                chessboardView.gameController.time = 0;
                chessboardView.gameController.openTimer();
                chessboardView.repaint();
            }
        });
        button.setLocation(HEIGHT + 200, HEIGHT / 10 + 100);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
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
        button.setLocation(HEIGHT + 200, HEIGHT / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    chessboardView.gameController.load();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button.setLocation(HEIGHT + 200, HEIGHT / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
        add(button);
    }

    private void addRegretButton() {
        JButton button = new JButton("Regret");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessboardView.gameController.regret();
            }
        });
        button.setLocation(HEIGHT + 200, HEIGHT / 10 + 380);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
        add(button);
    }

    private void addPBButton() {
        JButton button = new JButton("Play Back");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessboardView.gameController.playBack();
            }
        });
        button.setLocation(HEIGHT + 200, HEIGHT / 10 + 460);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
        add(button);
    }

    private void addReturnButton() {
        JButton button = new JButton("Return");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                mainFrame.setVisible(true);
                chessboardView.gameController.reset();
                chessboardView.gameController.time = 0;
                chessboardView.gameController.closeTimer();
            }
        });
        button.setLocation(HEIGHT + 200, HEIGHT / 10 + 560);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
        add(button);
    }

    public void changeCapturedView(){
        List<ChessPiece> redCaptured = chessboardView.gameController.model.redCaptured;
        List<ChessPiece> blueCaptured = chessboardView.gameController.model.blueCaptured;

        for (int i = 0; i < redCaptured.size(); i++) {
            ChessPiece chess = redCaptured.get(i);
            if (chess.getName().equals("Elephant")) captured[0][i] = ImageConstant.redElephant;
            if (chess.getName().equals("Lion")) captured[0][i] = ImageConstant.redLion;
            if (chess.getName().equals("Tiger")) captured[0][i] = ImageConstant.redTiger;
            if (chess.getName().equals("Leopard")) captured[0][i] = ImageConstant.redLeopard;
            if (chess.getName().equals("Wolf")) captured[0][i] = ImageConstant.redWolf;
            if (chess.getName().equals("Dog")) captured[0][i] = ImageConstant.redDog;
            if (chess.getName().equals("Cat")) captured[0][i] = ImageConstant.redCat;
            if (chess.getName().equals("Rat")) captured[0][i] = ImageConstant.redRat;
            captured[0][i].setSize(ONE_CHESS_SIZE, ONE_CHESS_SIZE);
            captured[0][i].setLocation(HEIGHT - 40 + ONE_CHESS_SIZE * (i / 4),
                    HEIGHT / 10 + ONE_CHESS_SIZE * (i % 4));
            add(captured[0][i]);
            repaint();
            //System.out.println("***");
        }
        for (int i = 0; i < blueCaptured.size(); i++) {
            ChessPiece chess = blueCaptured.get(i);
            if (chess.getName().equals("Elephant")) captured[1][i] = ImageConstant.blueElephant;
            if (chess.getName().equals("Lion")) captured[1][i] = ImageConstant.blueLion;
            if (chess.getName().equals("Tiger")) captured[1][i] = ImageConstant.blueTiger;
            if (chess.getName().equals("Leopard")) captured[1][i] = ImageConstant.blueLeopard;
            if (chess.getName().equals("Wolf")) captured[1][i] = ImageConstant.blueWolf;
            if (chess.getName().equals("Dog")) captured[1][i] = ImageConstant.blueDog;
            if (chess.getName().equals("Cat")) captured[1][i] = ImageConstant.blueCat;
            if (chess.getName().equals("Rat")) captured[1][i] = ImageConstant.blueRat;
            captured[1][i].setSize(ONE_CHESS_SIZE, ONE_CHESS_SIZE);
            captured[1][i].setLocation(HEIGHT - 40 + ONE_CHESS_SIZE * (i / 4),
                    HEIGHT / 10 + ONE_CHESS_SIZE * (i % 4 + 5));
            add(captured[1][i]);
            repaint();
            //System.out.println("***");
        }
        remove(background);
        add(background);
    }

    public void removeCapturedView(){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if (captured[i][j] != null) {
                    remove(captured[i][j]);
                    captured[i][j] = null;
                }
            }
        }
        repaint();
    }

}
