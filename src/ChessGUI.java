import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGUI {
    private JFrame frame;
    private JButton[][] squares = new JButton[8][8];
    private Piece[][] board = new Piece[8][8];
    private int selectedRow = -1;
    private int selectedCol = -1;

    enum Piece {
        EMPTY(""),
        WHITE_PAWN("\u2659"),
        WHITE_ROOK("\u2656"),
        WHITE_KNIGHT("\u2658"),
        WHITE_BISHOP("\u2657"),
        WHITE_QUEEN("\u2655"),
        WHITE_KING("\u2654"),
        BLACK_PAWN("\u265F"),
        BLACK_ROOK("\u265C"),
        BLACK_KNIGHT("\u265E"),
        BLACK_BISHOP("\u265D"),
        BLACK_QUEEN("\u265B"),
        BLACK_KING("\u265A");

        final String symbol;
        Piece(String symbol) {
            this.symbol = symbol;
        }
    }

    public ChessGUI() {
        frame = new JFrame("Chess GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(8, 8));
        initBoard();
        frame.pack();
        frame.setVisible(true);
    }

    private void initBoard() {
        // Initialize pieces
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                board[r][c] = Piece.EMPTY;
                JButton button = new JButton();
                button.setFont(new Font("SansSerif", Font.PLAIN, 32));
                button.addActionListener(new SquareListener(r, c));
                if ((r + c) % 2 == 0) {
                    button.setBackground(new Color(240, 217, 181));
                } else {
                    button.setBackground(new Color(181, 136, 99));
                }
                squares[r][c] = button;
                frame.add(button);
            }
        }
        setupPieces();
        refreshBoard();
    }

    private void setupPieces() {
        // Set up white pieces
        board[7][0] = Piece.WHITE_ROOK;
        board[7][1] = Piece.WHITE_KNIGHT;
        board[7][2] = Piece.WHITE_BISHOP;
        board[7][3] = Piece.WHITE_QUEEN;
        board[7][4] = Piece.WHITE_KING;
        board[7][5] = Piece.WHITE_BISHOP;
        board[7][6] = Piece.WHITE_KNIGHT;
        board[7][7] = Piece.WHITE_ROOK;
        for (int c = 0; c < 8; c++) {
            board[6][c] = Piece.WHITE_PAWN;
        }
        // Set up black pieces
        board[0][0] = Piece.BLACK_ROOK;
        board[0][1] = Piece.BLACK_KNIGHT;
        board[0][2] = Piece.BLACK_BISHOP;
        board[0][3] = Piece.BLACK_QUEEN;
        board[0][4] = Piece.BLACK_KING;
        board[0][5] = Piece.BLACK_BISHOP;
        board[0][6] = Piece.BLACK_KNIGHT;
        board[0][7] = Piece.BLACK_ROOK;
        for (int c = 0; c < 8; c++) {
            board[1][c] = Piece.BLACK_PAWN;
        }
    }

    private void refreshBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c].setText(board[r][c].symbol);
            }
        }
    }

    private class SquareListener implements ActionListener {
        private int row;
        private int col;
        SquareListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedRow == -1) {
                if (board[row][col] != Piece.EMPTY) {
                    selectedRow = row;
                    selectedCol = col;
                    squares[row][col].setBackground(Color.YELLOW);
                }
            } else {
                movePiece(selectedRow, selectedCol, row, col);
                resetSquareColor(selectedRow, selectedCol);
                selectedRow = -1;
                selectedCol = -1;
            }
        }
    }

    private void resetSquareColor(int r, int c) {
        Color color = ((r + c) % 2 == 0) ? new Color(240, 217, 181) : new Color(181, 136, 99);
        squares[r][c].setBackground(color);
    }

    private void movePiece(int fromR, int fromC, int toR, int toC) {
        board[toR][toC] = board[fromR][fromC];
        board[fromR][fromC] = Piece.EMPTY;
        refreshBoard();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}
