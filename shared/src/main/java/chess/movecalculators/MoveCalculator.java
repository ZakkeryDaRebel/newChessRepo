package chess.movecalculators;

import chess.*;

import java.util.Collection;

public interface MoveCalculator {

    Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos);

    default boolean onBoard(ChessPosition pos) {
        return pos.getRow() >= 1 && pos.getRow() <= 8 && pos.getColumn() >= 1 && pos.getColumn() <= 8;
    }

    default boolean isEmpty(ChessBoard board, ChessPosition pos) {
        return board.getPiece(pos) == null;
    }

    default boolean isEnemy(ChessPiece piece, ChessGame.TeamColor color) {
        return piece.getTeamColor() != color;
    }
}
