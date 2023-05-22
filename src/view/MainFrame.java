package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    GameFrame gameFrame;

    public MainFrame(int width,int height,GameFrame gameFrame){
        this.gameFrame = gameFrame;
        setTitle("Judge");
        this.WIDTH = width;
        this.HEIGHT = height;
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addBeginLabel();
        addExitLabel();

        Image image = new ImageIcon("bg/main.png").getImage();
        image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        JLabel label = new JLabel(icon);
        label.setSize(width, height);
        label.setLocation(0, 0);
        add(label);
    }
    private void addBeginLabel(){
        JButton button = new JButton("Begin");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.setVisible(true);
                setVisible(false);
            }
        });
        button.setLocation(WIDTH/2-100, HEIGHT / 10 + 300);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        add(button);

    }
    private void addExitLabel(){
        JButton button = new JButton("Exit");
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        button.setLocation(WIDTH/2-100, HEIGHT / 10 +440);
        button.setSize(200, 60);
        button.setFont(new Font("Showcard Gothic", Font.BOLD, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        add(button);

    }
}
