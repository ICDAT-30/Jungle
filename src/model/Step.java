package model;

public class Step {
    private ChessboardPoint src;
    private ChessboardPoint dest;

    private ChessPiece piece;

    private ChessPiece capturedPiece;
    private PlayerColor color;
    public Step(ChessboardPoint src,ChessboardPoint dest , ChessPiece piece ,ChessPiece capturedPiece){
        this.src = src;
        this.dest = dest;
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.color = piece.getOwner();
    }

    public Step(ChessboardPoint src,ChessboardPoint dest , ChessPiece piece ){
        this.src = src;
        this.dest = dest;
        this.piece = piece;
        this.capturedPiece = null;
        this.color = piece.getOwner();
    }
    @Override
    public String toString(){
        if (capturedPiece == null){
            return String.format("%s %s %d %d %d %d null",
                    piece.getColor(),piece.getName(),src.getRow(),src.getCol(),
                    dest.getRow(),dest.getCol());
        }else return String.format("%s %s %d %d %d %d %s %s",
                piece.getColor(),piece.getName(),src.getRow(),src.getCol(),
                dest.getRow(),dest.getCol(),capturedPiece.getColor(),capturedPiece.getName());
    }

    public ChessboardPoint getSrc() {
        return src;
    }

    public void setSrc(ChessboardPoint src) {
        this.src = src;
    }

    public ChessboardPoint getDest() {
        return dest;
    }

    public void setDest(ChessboardPoint dest) {
        this.dest = dest;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public ChessPiece getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(ChessPiece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }
}