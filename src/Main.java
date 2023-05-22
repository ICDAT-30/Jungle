import controller.GameController;
import model.Chessboard;
import view.GameFrame;
import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame(1300, 810);
            MainFrame mainFrame = new MainFrame(810,810,gameFrame);
            //TODO:调整界面大小，按钮位置，可以放得下棋子
            gameFrame.mainFrame = mainFrame;
            GameController gameController = new GameController(gameFrame.getChessboardView(), new Chessboard());
            mainFrame.setVisible(true);
        });
    }
}
