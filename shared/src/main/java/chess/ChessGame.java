package chess;

import chess.movecalculators.PawnCalculator;
import com.sun.source.tree.WhileLoopTree;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor teamTurn;
    ChessBoard board;
    ChessMove lastMove;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
        lastMove = null;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //check if position is on the board
        if (startPosition.getRow() < 1 || startPosition.getRow() > 8 ||
                startPosition.getColumn() < 1 || startPosition.getColumn() > 8) {
            return null;
        //Check if there is no piece at position
        }

        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        }
        Collection<ChessMove> possibleMoves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();

        //Loop through possibleMoves and check to see if the king is in check
        for (ChessMove move : possibleMoves) {
            ChessGame testGame = new ChessGame();
            ChessBoard oldBoard = board.clone();
            if (oldBoard == null) {
                System.out.println("FAIL to clone, returning null");
                return null;
            }
            testGame.setBoard(oldBoard);
            TeamColor color = board.getPiece(startPosition).getTeamColor();
            testGame.executeMove(move);
            if (!testGame.isInCheck(color)) {
                validMoves.add(move);
            }
        }

        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            checkEnPassant(startPosition, validMoves);
        } else if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            checkCastling(startPosition, validMoves);
        }
        //Check EnPassant
        //Check Castling

        return validMoves;
    }

    public void checkEnPassant(ChessPosition startPosition, Collection<ChessMove> validMoves) {
        if (lastMove == null) {
            return;
        }

        //check if lastMove was a pawn that moved from 2->4 or 7->5
        ChessPiece lastMovePiece = board.getPiece(lastMove.getEndPosition());
        if (lastMovePiece.getPieceType() != ChessPiece.PieceType.PAWN) {
            return;
        }

        if (lastMovePiece.getTeamColor() == ChessGame.TeamColor.WHITE)  {
            if (lastMove.getStartPosition().getRow() == 2 && lastMove.getEndPosition().getRow() == 4) {
                if (startPosition.getRow() == 4) {
                    if (enPassantOneColumnOver(startPosition, lastMove.getEndPosition())) {
                        ChessPosition enPassant = new ChessPosition(3, lastMove.getEndPosition().getColumn());
                        validMoves.add(new ChessMove(startPosition, enPassant, null));
                    }
                }
            }
        } else {
            if (lastMove.getStartPosition().getRow() == 7 && lastMove.getEndPosition().getRow() == 5) {
                if (startPosition.getRow() == 5) {
                    if (enPassantOneColumnOver(startPosition, lastMove.getEndPosition())) {
                        ChessPosition enPassant = new ChessPosition(6, lastMove.getEndPosition().getColumn());
                        validMoves.add(new ChessMove(startPosition, enPassant, null));
                    }
                }
            }
        }
    }

    public boolean enPassantOneColumnOver(ChessPosition startPosition, ChessPosition lastMoveEnd) {
        int lastMoveCol = lastMoveEnd.getColumn();
        return startPosition.getColumn() == lastMoveCol + 1 || startPosition.getColumn() == lastMoveCol - 1;
    }

    public void checkCastling(ChessPosition startPosition, Collection<ChessMove> validMoves) {

    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (validMoves(move.getStartPosition()) == null || !validMoves(move.getStartPosition()).contains(move)) {
            throw new InvalidMoveException();
        }

        if(board.getPiece(move.getStartPosition()).getTeamColor() != teamTurn) {
            throw new InvalidMoveException();
        }

        //If EnPassant
        if (board.getPiece(move.getStartPosition()).getPieceType() == ChessPiece.PieceType.PAWN) {
            if (isEnPassantMove(move)) {
                ChessPosition capturedPiece = new ChessPosition(move.getStartPosition().getRow(),
                        move.getEndPosition().getColumn());
                board.addPiece(capturedPiece, null);
            }
        }

        executeMove(move);
        //If EnPassant or Castling, also need to remove other pawn or move rook
        lastMove = move;

        if (teamTurn == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        } else {
            teamTurn = TeamColor.WHITE;
        }
    }

    public boolean isEnPassantMove(ChessMove move) {
        //check to see if moving diagonally
        if (move.getStartPosition().getColumn() != move.getEndPosition().getColumn()) {
            //If there is a piece there, it is a normal move, otherwise it is enPassant
            return board.getPiece(move.getEndPosition()) == null;
        }
        return false;
    }

    public void executeMove(ChessMove move) {
        ChessPiece oldPiece = board.getPiece(move.getStartPosition());
        //Remove the piece from the start position
        board.addPiece(move.getStartPosition(), null);
        //If the piece doesn't promote, put the same piece at the end position
        if (move.getPromotionPiece() == null) {
            board.addPiece(move.getEndPosition(), oldPiece);
            //Otherwise, but the new promotion type at the end position
        } else {
            board.addPiece(move.getEndPosition(), new ChessPiece(oldPiece.getTeamColor(), move.getPromotionPiece()));
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = findKing(teamColor);
        if (kingPosition == null) {
            return false;
        }

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPiece piece = board.getPiece(new ChessPosition(row, col));
                if (piece != null && piece.getTeamColor() != teamColor) {
                    Collection<ChessMove> pieceMoves = piece.pieceMoves(board, new ChessPosition(row, col));
                    for (ChessMove attack : pieceMoves) {
                        if (attack.getEndPosition().equals(kingPosition)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && noTeamMoves(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && noTeamMoves(teamColor);
    }

    public boolean noTeamMoves(TeamColor teamColor) {
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPiece piece = board.getPiece(new ChessPosition(row, col));
                if (piece != null && piece.getTeamColor() == teamColor) {
                    Collection<ChessMove> moves = validMoves(new ChessPosition(row, col));
                    if (moves != null && !moves.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    public ChessPosition findKing(TeamColor color) {
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPiece piece = board.getPiece(new ChessPosition(row, col));
                if (piece != null &&
                        piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == color) {
                    return new ChessPosition(row, col);
                }
            }
        }
        return null;
    }
}
