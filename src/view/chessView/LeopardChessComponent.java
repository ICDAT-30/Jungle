package view.chessView;


import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class LeopardChessComponent extends AnimalChessComponent {
    private PlayerColor owner;

    private boolean selected;

    public LeopardChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIcon pic = new ImageIcon("chess\\red\\Leopard.jpg");
        if (owner == PlayerColor.BLUE){
            pic = new ImageIcon("chess\\blue\\Leopard.jpg");
        }
        Image image = pic.getImage();
        pic = new ImageIcon(image.getScaledInstance(getWidth(), getHeight(),Image.SCALE_SMOOTH));
        JLabel label = new JLabel(pic);
        g2.setColor(owner.getColor());
        add(label);
        g2.drawImage(image,0,0,getWidth(),getHeight(),null);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
