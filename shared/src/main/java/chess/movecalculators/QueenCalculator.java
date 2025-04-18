package chess.movecalculators;

import chess.*;

import java.util.Collection;

public class QueenCalculator implements MoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        int[][] directions = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
        return loopCheck(directions, board, pos);
    }
}
