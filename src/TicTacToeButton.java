import javax.swing.*;

public class TicTacToeButton extends JButton {
    private int row;
    private int col;
    private char state = ' '; // Empty by default

    public TicTacToeButton(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setState(char state) {
        this.state = state;
    }
}
