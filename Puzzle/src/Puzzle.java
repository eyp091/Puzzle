import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Puzzle extends JFrame implements ActionListener {

    //ImageIcon Icon1 = new ImageIcon("row-1-column-1.png");
    ImageIcon Icon = new ImageIcon("puzzle.jpg");
    Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 4);

    private final JButton[][] buttons = new JButton[4][4];
    private final JPanel puzzlePanel = new JPanel(new GridLayout(4, 4));

    private final int[][] puzzle = new int[4][4];
    private int emptyRow;
    private int emptyCol;


    public Puzzle() {
        this.setIconImage(Icon.getImage());
        this.setTitle("Slide Puzzle");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 300);
        //buttons[0][0].setIcon(Icon1);

        JPanel controlPanel = new JPanel();

        add(controlPanel, BorderLayout.NORTH);
        add(puzzlePanel, BorderLayout.CENTER);
        puzzlePanel.setBorder(border);
        resetPuzzle();
        displayPuzzle();

    }

    private void resetPuzzle() {
        int count = 1;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                puzzle[row][col] = count;
                count++;
            }
        }
        emptyRow = 3;
        emptyCol = 3;
        puzzle[emptyRow][emptyCol] = 0;

        shufflePuzzle();
    }

    private void shufflePuzzle() {
        for (int i = 0; i < 1000; i++) {
            int direction = (int) (Math.random() * 4);
            switch (direction) {
                case 0: // Up
                    if (emptyRow > 0) {
                        puzzle[emptyRow][emptyCol] = puzzle[emptyRow - 1][emptyCol];
                        puzzle[emptyRow - 1][emptyCol] = 0;
                        emptyRow--;
                    }
                    break;
                case 1: // Down
                    if (emptyRow < 3) {
                        puzzle[emptyRow][emptyCol] = puzzle[emptyRow + 1][emptyCol];
                        puzzle[emptyRow + 1][emptyCol] = 0;
                        emptyRow++;
                    }
                    break;
                case 2: // Left
                    if (emptyCol > 0) {
                        puzzle[emptyRow][emptyCol] = puzzle[emptyRow][emptyCol - 1];
                        puzzle[emptyRow][emptyCol - 1] = 0;
                        emptyCol--;
                    }
                    break;
                case 3: // Right
                    if (emptyCol < 3) {
                        puzzle[emptyRow][emptyCol] = puzzle[emptyRow][emptyCol + 1];
                        puzzle[emptyRow][emptyCol + 1] = 0;
                        emptyCol++;
                    }
                    break;
            }
        }
    }

    private void displayPuzzle() {
        puzzlePanel.removeAll();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                int value = puzzle[row][col];
                if (value == 0) {
                    buttons[row][col] = new JButton("");
                } else {
                    buttons[row][col] = new JButton("" + value);
                    buttons[row][col].addActionListener(this);
                }
                puzzlePanel.add(buttons[row][col]);
            }
        }
        validate();
    }

    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) event.getSource();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (button == buttons[row][col]) {
                    if (row == emptyRow && col == emptyCol) {
                        return;
                    }
                    if (row == emptyRow) {
                        int distance = Math.abs(col - emptyCol);
                        if (col < emptyCol) {
                            for (int i = 0; i < distance; i++) {
                                puzzle[row][emptyCol] = puzzle[row][emptyCol - 1];
                                puzzle[row][emptyCol - 1] = 0;
                                emptyCol--;
                            }
                        } else {
                            for (int i = 0; i < distance; i++) {
                                puzzle[row][emptyCol] = puzzle[row][emptyCol + 1];
                                puzzle[row][emptyCol + 1] = 0;
                                emptyCol++;
                            }
                        }
                    } else if (col == emptyCol) {
                        int distance = Math.abs(row - emptyRow);
                        if (row < emptyRow) {
                            for (int i = 0; i < distance; i++) {
                                puzzle[emptyRow][col] = puzzle[emptyRow - 1][col];
                                puzzle[emptyRow - 1][col] = 0;
                                emptyRow--;
                            }
                        } else {
                            for (int i = 0; i < distance; i++) {
                                puzzle[emptyRow][col] = puzzle[emptyRow + 1][col];
                                puzzle[emptyRow + 1][col] = 0;
                                emptyRow++;
                            }
                        }
                    } else {
                        return;
                    }
                    displayPuzzle();
                    return;
                }
            }
        }
        boolean isWin = true;
        int count = 1;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (puzzle[row][col] != count) {
                    isWin = false;
                    break;
                }
                count++;
            }
            if (!isWin) {
                break;
            }
        }
        if (isWin) {
            JOptionPane.showMessageDialog(this, "Congrats YOU WİN THE GAME !!!!");
        }
    }
}
