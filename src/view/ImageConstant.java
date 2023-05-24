package view;

import javax.swing.*;
import java.awt.*;

public class ImageConstant {
    private static final int CHESS_SIZE = 72;
    private static final String picFormat = ".png";

    public static JLabel blueElephant;
    public static JLabel blueLion;
    public static JLabel blueTiger;
    public static JLabel blueLeopard;
    public static JLabel blueWolf;
    public static JLabel blueDog;
    public static JLabel blueCat;
    public static JLabel blueRat;
    public static JLabel redElephant;
    public static JLabel redLion;
    public static JLabel redTiger;
    public static JLabel redLeopard;
    public static JLabel redWolf;
    public static JLabel redDog;
    public static JLabel redCat;
    public static JLabel redRat;

    public static void setConstant(){
        Image image = new ImageIcon("chess/blue/Elephant" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        blueElephant = new JLabel(icon);

        image = new ImageIcon("chess/blue/Lion" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        blueLion = new JLabel(icon);

        image = new ImageIcon("chess/blue/Tiger" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        blueTiger = new JLabel(icon);

        image = new ImageIcon("chess/blue/Leopard" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        blueLeopard = new JLabel(icon);

        image = new ImageIcon("chess/blue/Wolf" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        blueWolf = new JLabel(icon);

        image = new ImageIcon("chess/blue/Dog" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        blueDog = new JLabel(icon);

        image = new ImageIcon("chess/blue/Cat" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        blueCat = new JLabel(icon);

        image = new ImageIcon("chess/blue/Rat" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        blueRat = new JLabel(icon);

        image = new ImageIcon("chess/red/Elephant" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        redElephant = new JLabel(icon);

        image = new ImageIcon("chess/red/Lion" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        redLion = new JLabel(icon);

        image = new ImageIcon("chess/red/Tiger" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        redTiger = new JLabel(icon);

        image = new ImageIcon("chess/red/Leopard" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        redLeopard = new JLabel(icon);

        image = new ImageIcon("chess/red/Wolf" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        redWolf = new JLabel(icon);

        image = new ImageIcon("chess/red/Dog" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        redDog = new JLabel(icon);

        image = new ImageIcon("chess/red/Cat" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        redCat = new JLabel(icon);

        image = new ImageIcon("chess/red/Rat" + picFormat).getImage();
        image = image.getScaledInstance(CHESS_SIZE, CHESS_SIZE, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);
        redRat = new JLabel(icon);
    }

}
