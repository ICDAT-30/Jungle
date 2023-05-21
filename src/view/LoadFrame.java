package view;

import javax.swing.*;

public class LoadFrame extends JFrame {
    public int width;
    public int height;
    public LoadFrame(int width,int height){
        setTitle("Load");
        this.width = width;
        this.height = height;
        setSize(width,height);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
    }
}
