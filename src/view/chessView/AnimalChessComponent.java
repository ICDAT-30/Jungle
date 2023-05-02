package view.chessView;

import model.PlayerColor;

import javax.swing.*;

public class AnimalChessComponent extends JComponent {
    private PlayerColor owner;

    private boolean selected;
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
