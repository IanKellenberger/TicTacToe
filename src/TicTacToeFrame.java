import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private static final int ROW = 3;
    private static final int COL = 3;
    private TicTacToeButton[][] buttons; // Use TicTacToeButton
    private String[][] board;
    private String currentPlayer;
    private int moveCnt;

    public TicTacToeFrame() {
        super("Tic Tac Toe");
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(ROW, COL));
        buttons = new TicTacToeButton[ROW][COL];
        board = new String[ROW][COL];

        // Initialize buttons (Tic Tac Toe grid)
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                buttons[row][col] = new TicTacToeButton(row, col);
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[row][col].addActionListener(new ButtonListener());
                boardPanel.add(buttons[row][col]);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton, BorderLayout.SOUTH);

        clearBoard();
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void clearBoard() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = " ";
                if (buttons[row][col] != null) {
                    buttons[row][col].setText("");
                }
            }
        }
        currentPlayer = "X";
        moveCnt = 0;
    }

    private boolean isWin(String player) {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private boolean isRowWin(String player) {
        for (int row = 0; row < ROW; row++) {
            if (board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isColWin(String player) {
        for (int col = 0; col < COL; col++) {
            if (board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonalWin(String player) {
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
                (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    private boolean noWinningMovesLeft() {
        // Only check for winning moves if there have been at least 5 moves
        if (moveCnt < 5) {
            return false; // Not enough moves made for a tie check
        }

        // Check for all possible winning positions for both players
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (board[row][col].equals(" ")) {
                    // Check if placing an X or O here would create a win
                    board[row][col] = "X"; // Check for X
                    if (isWin("X")) {
                        board[row][col] = " "; // Reset the board
                        return false; // Winning move is possible
                    }
                    board[row][col] = "O"; // Check for O
                    if (isWin("O")) {
                        board[row][col] = " "; // Reset the board
                        return false; // Winning move is possible
                    }
                    board[row][col] = " "; // Reset the board
                }
            }
        }
        return true; // No winning moves left for either player
    }

    private void promptPlayAgain() {
        int result = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play Again?",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            clearBoard(); // Reset the game
        } else {
            System.exit(0); // Exit the game if No
        }
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton button = (TicTacToeButton) e.getSource();
            int row = button.getRow();
            int col = button.getCol();

            if (!board[row][col].equals(" ")) {
                JOptionPane.showMessageDialog(null, "Invalid Move! Try again.");
                return;
            }

            board[row][col] = currentPlayer;
            button.setText(currentPlayer);
            moveCnt++;

            // Check for win condition
            if (moveCnt >= 5 && isWin(currentPlayer)) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                promptPlayAgain();
                return;
            }

            // Check for tie condition
            if (noWinningMovesLeft()) {
                JOptionPane.showMessageDialog(null, "It's a Tie!");
                promptPlayAgain();
                return;
            }

            // Switch player
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }
    }

    public static void main(String[] args) {
        new TicTacToeFrame();
    }
}
