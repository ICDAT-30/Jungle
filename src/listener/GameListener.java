package listener;

import model.ChessboardPoint;
import view.CellView;
import view.chessView.AnimalChessComponent;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellView component);


    void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component);

}
