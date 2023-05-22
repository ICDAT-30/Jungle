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
    JLabel background;
    JLabel chessboard;
    private ChessboardView chessboardView;
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

        addStatusLabel();
        addChessboard();
        addResetButton();
        addSaveButton();
        addLoadButton();
        addRegretButton();
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

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardView = new ChessboardView(ONE_CHESS_SIZE, this.statusLabel);
        chessboardView.gameFrame = this;
        chessboardView.setLocation(HEIGHT / 5, HEIGHT / 10);
        add(chessboardView);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addStatusLabel() {
        statusLabel = new JLabel("Turn 1 : BLUE");
        statusLabel.setLocation(HEIGHT - 100, HEIGHT / 10 - 40);
        statusLabel.setSize(280, 60);
        statusLabel.setFont(new Font("Showcard Gothic", Font.PLAIN, 35));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */
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
        button.setLocation(HEIGHT + 200, HEIGHT / 10 - 40);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
        add(button);
    }

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
        button.setLocation(HEIGHT + 200, HEIGHT / 10 + 80);
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
                    chessboardView.gameController.Load();

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
        button.setLocation(HEIGHT + 200, HEIGHT / 10 + 400);
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
            }
        });
        button.setLocation(HEIGHT + 200, HEIGHT / 10 + 520);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.PLAIN, 20));
        add(button);
    }

}
