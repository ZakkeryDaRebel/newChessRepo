package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] board;

    public ChessBoard() {
        board = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;
        ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;

        ChessPiece.PieceType king = ChessPiece.PieceType.KING;
        ChessPiece.PieceType queen = ChessPiece.PieceType.QUEEN;
        ChessPiece.PieceType rook = ChessPiece.PieceType.ROOK;
        ChessPiece.PieceType bishop = ChessPiece.PieceType.BISHOP;
        ChessPiece.PieceType knight = ChessPiece.PieceType.KNIGHT;
        ChessPiece.PieceType pawn = ChessPiece.PieceType.PAWN;

        board[0][0] = new ChessPiece(white, rook);
        board[0][1] = new ChessPiece(white, knight);
        board[0][2] = new ChessPiece(white, bishop);
        board[0][3] = new ChessPiece(white, queen);
        board[0][4] = new ChessPiece(white, king);
        board[0][5] = new ChessPiece(white, bishop);
        board[0][6] = new ChessPiece(white, knight);
        board[0][7] = new ChessPiece(white, rook);

        for(int i = 0; i < 8; i++) {
            board[1][i] = new ChessPiece(white, pawn);
        }

        board[7][0] = new ChessPiece(black, rook);
        board[7][1] = new ChessPiece(black, knight);
        board[7][2] = new ChessPiece(black, bishop);
        board[7][3] = new ChessPiece(black, queen);
        board[7][4] = new ChessPiece(black, king);
        board[7][5] = new ChessPiece(black, bishop);
        board[7][6] = new ChessPiece(black, knight);
        board[7][7] = new ChessPiece(black, rook);

        for(int i = 0; i < 8; i++) {
            board[6][i] = new ChessPiece(black, pawn);
        }
    }
}
