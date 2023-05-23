import controller.GameController;
import model.Chessboard;
import view.GameFrame;
import view.MainFrame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame(1300, 810);
            MainFrame mainFrame = new MainFrame(810,810,gameFrame);
            //TODO:调整界面大小，按钮位置，可以放得下棋子
            gameFrame.mainFrame = mainFrame;
            GameController gameController = new GameController(gameFrame.getChessboardView(),new Chessboard());
            mainFrame.setVisible(true);
            playMusic("bgm\\bgm5.wav");
        });
    }
    public static void playMusic(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
