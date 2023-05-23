package view.chessView;


import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class RatChessComponent extends AnimalChessComponent {
    private PlayerColor owner;

    private boolean selected;

    public RatChessComponent(PlayerColor owner, int size) {
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
        ImageIcon pic = new ImageIcon("chess/red/Rat.png");
        if (owner == PlayerColor.BLUE){
            pic = new ImageIcon("chess/blue/Rat.png");
        }
        Image image = pic.getImage();
        pic = new ImageIcon(image.getScaledInstance(getWidth(), getHeight(),Image.SCALE_SMOOTH));
        JLabel label = new JLabel(pic);
        g2.setColor(owner.equals(PlayerColor.BLUE)? new Color(173,216,230,100) : new Color(240,128,128,100));
        add(label);
        g2.drawImage(image,0,0,getWidth(),getHeight(),null);
        if (isSelected()) { // Highlights the model if selected.
            Rectangle2D rectangle2D = new Rectangle2D.Double(1, 1,
                    this.getWidth() - 1, this.getHeight() - 1);
            g2.fill(rectangle2D);
        }
    }
}
