package chess.extracreditcalculators;

import chess.*;

public class CastleCalculator {

    Boolean[] whiteCastling;
    Boolean[] blackCastling;

    public CastleCalculator() {
        //whiteCastling and blackCastling stores if the pieces for castling haven't moved
        whiteCastling = new Boolean[]{true, true, true};
        blackCastling = new Boolean[]{true, true, true};
        //castling[0] references the A rook, castling[1] references the King, and castling[2] references the H rook
    }

    public void setCastleBool(ChessGame.TeamColor color, int place, Boolean bool) {
        if (color == ChessGame.TeamColor.WHITE) {
            whiteCastling[place] = bool;
        } else {
            blackCastling[place] = bool;
        }
    }

    public Boolean[] getWhiteCastleBool() {
        return whiteCastling;
    }

    public Boolean[] getBlackCastleBool() {
        return blackCastling;
    }

    public void loadBoard(ChessBoard newBoard) {
        //Check to see if pieces are in places for castling
        ChessPiece whiteRook = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece blackRook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece whiteKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPiece blackKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);

        ChessPiece pieceAOne = newBoard.getPiece(new ChessPosition(1, 1));
        ChessPiece pieceEOne = newBoard.getPiece(new ChessPosition(1, 5));
        ChessPiece pieceHOne = newBoard.getPiece(new ChessPosition(1, 8));
        ChessPiece pieceAEight = newBoard.getPiece(new ChessPosition(8, 1));
        ChessPiece pieceEEight = newBoard.getPiece(new ChessPosition(8, 5));
        ChessPiece pieceHEight = newBoard.getPiece(new ChessPosition(8, 8));

        whiteCastling[0] = (pieceAOne != null && pieceAOne.equals(whiteRook));
        whiteCastling[1] = (pieceEOne != null && pieceEOne.equals(whiteKing));
        whiteCastling[2] = (pieceHOne != null && pieceHOne.equals(whiteRook));
        blackCastling[0] = (pieceAEight != null && pieceAEight.equals(blackRook));
        blackCastling[1] = (pieceEEight != null && pieceEEight.equals(blackKing));
        blackCastling[2] = (pieceHEight != null && pieceHEight.equals(blackRook));
    }

    public void checkRookCastling(ChessPosition movePos) {
        ChessPosition rookAOne = new ChessPosition(1,1);
        ChessPosition rookAEight = new ChessPosition(8,1);
        ChessPosition rookHOne = new ChessPosition(1, 8);
        ChessPosition rookHEight = new ChessPosition(8, 8);

        if (movePos.equals(rookAOne)) {
            whiteCastling[0] = false;
        } else if (movePos.equals(rookHOne)) {
            whiteCastling[2] = false;
        } else if (movePos.equals(rookAEight)) {
            blackCastling[0] = false;
        } else if (movePos.equals(rookHEight)) {
            blackCastling[2] = false;
        }
    }

    public boolean isCastlingMove(ChessMove move) {
        //If the king stays on the same row
        if(move.getStartPosition().getRow() == move.getEndPosition().getRow()) {
            //If the king tries to move 2 spaces to the right or left, it's a castling move
            return move.getStartPosition().getColumn() == move.getEndPosition().getColumn() - 2 ||
                    move.getStartPosition().getColumn() == move.getEndPosition().getColumn() + 2;
        }
        return false;
    }
}
