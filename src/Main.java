import controller.GameController;
import model.Chessboard;
import view.GameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame mainFrame = new GameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardView(), new Chessboard());
            mainFrame.setVisible(true);
        });
    }
}
