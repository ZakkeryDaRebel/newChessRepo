package chess.movecalculators;

import chess.*;

import java.util.ArrayList;
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

    default Collection<ChessMove> listCheck(int[][] directions, ChessBoard board, ChessPosition startPos) {
        Collection<ChessMove> moves = new ArrayList<>();

        ChessGame.TeamColor color = board.getPiece(startPos).getTeamColor();

        //Loop through each possible option, and see if it is a space the piece can move to
        for (int[] oneMove : directions) {
            int testRow = startPos.getRow() + oneMove[0];
            int testCol = startPos.getColumn() + oneMove[1];
            ChessPosition testPos = new ChessPosition(testRow, testCol);
            if(onBoard(testPos) && (isEmpty(board, testPos) || isEnemy(board.getPiece(testPos), color))) {
                moves.add(new ChessMove(startPos, testPos, null));
            }
        }

        return moves;
    }
}
