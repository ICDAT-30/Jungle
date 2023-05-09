package model;


import java.awt.*;

public class ChessPiece {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;

    private Image image;
    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setRank(String name) {
        if (name.equals("Elephant")) {
            this.rank = 8;
        } else if (name.equals("Lion")) {
            this.rank = 7;
        } else if (name.equals("Tiger")) {
            this.rank = 6;
        } else if (name.equals("Leopard")) {
            this.rank = 5;
        } else if (name.equals("Wolf")) {
            this.rank = 4;
        } else if (name.equals("Dog")) {
            this.rank = 3;
        } else if (name.equals("Cat")) {
            this.rank = 2;
        } else if (name.equals("Rat")) {
            this.rank = 1;
        }
    }

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if (this.getName().equals("Rat")) {
            return target.getRank() == 8 | target.getRank() <= 1;
        } else if (this.getName().equals("Elephant")) {
            if (target.getRank() != 1) {
                return this.getRank() >= target.getRank();
            }
        } else {
            return this.getRank() >= target.getRank();
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }
}
