package chess.movecalculators;

import chess.*;

import java.util.Collection;

public class KingCalculator implements MoveCalculator{

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        int[][] directions = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
        return listCheck(directions, board, pos);
    }
}
