package fifteen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class is for the frame for the Fifteen game.
 * 
 * @author Melissa Thai. Created Sep 27, 2013.
 */
public class FifteenFrame extends JFrame implements ActionListener {

	private int rows = 4, cols = 4;
	private Puzzle puzzle;
	private List<GameButton> boardDisplay;
	private GamePanel gamePanel;
	private TimerLabel gameClock;
	private JPanel timerPanel;
	private int[][] board;

	/**
	 * Sets the size, title, and layout of the frame.
	 * 
	 */
	FifteenFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(600, 800));
		this.setTitle("Fifteen Game");
		this.setLayout(new BorderLayout());

		this.gamePanel = new GamePanel(this.rows, this.cols);

		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);

		ControlMenu gameOptions = new ControlMenu("Game Options");

		JMenuItem newPuzzle = new JMenuItem("Create New Puzzle");
		JMenuItem resetPuzzle = new JMenuItem("Reset Puzzle");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem redo = new JMenuItem("Redo");
		JMenuItem puzzleSize = new JMenuItem("Change size of puzzle");

		newPuzzle.addActionListener(this);
		resetPuzzle.addActionListener(this);
		undo.addActionListener(this);
		redo.addActionListener(this);
		puzzleSize.addActionListener(this);

		gameOptions.add(newPuzzle);
		gameOptions.add(resetPuzzle);
		gameOptions.add(undo);
		gameOptions.add(redo);
		gameOptions.add(puzzleSize);

		JMenu help = new JMenu("Help");
		JMenuItem bios = new JMenuItem("About the authors");
		bios.addActionListener(this);
		help.add(bios);

		bar.add(gameOptions);
		bar.add(help);

		this.timerPanel = new JPanel();
		this.gameClock = new TimerLabel();
		this.timerPanel.add(this.gameClock);
		this.gameClock.startTimer();
		this.add(this.timerPanel, BorderLayout.NORTH);

		this.puzzle = new Puzzle(this.rows, this.cols);
		this.puzzle.generatePuzzle();
		this.board = this.puzzle.returnPuzzle();
		this.boardDisplay = new ArrayList<GameButton>();
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				GameButton b = new GameButton(this.board[i][j], i, j);
				b.setFont(new Font("Serif", Font.PLAIN, 24));
				b.addActionListener(this);
				this.boardDisplay.add(b);
			}
		}
		for (GameButton button : this.boardDisplay) {
			this.gamePanel.add(button);
		}

		this.add(this.gamePanel, BorderLayout.CENTER);
	}

	/**
	 * Returns the number of rows in the puzzle in this frame.
	 * 
	 * @return rows Number of rows.
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * Returns the number of columns in the puzzle in this frame.
	 * 
	 * @return cols Number of columns.
	 */
	public int getCols() {
		return this.cols;
	}

	/**
	 * Changes the current puzzle to the given newPuzzle.
	 * 
	 * @param newPuzzle
	 *            The puzzle to set to.
	 */
	public void setPuzzle(Puzzle newPuzzle) {
		this.puzzle = newPuzzle;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean stoppedTimer = false;
		String command = e.getActionCommand();
		if (command.equals("About the authors")) {
			JOptionPane
					.showMessageDialog(
							null,
							"The authors are Melissa Thai, Daniel Schnipke, and Ian Ludden."
									+ " \n\n Melissa was born on May 18, 1995 in a thriving little town of Centreville, Virginia.  "
									+ "She attended Sinclair Elementary School, Stonewall Middle School, Stonewall Jackson High School, and the Governor's School @ Innovation Park."
									+ " \n\n Daniel was born on May 18, 1995 in the medium city of Kettering, Ohio. "
									+ "He went to Fairmont High School, which is larger than Rose-Hulman. He eats a lot."
									+ "\n\n Ian was not born on May 18, 1995 and grew up in Niceville, FL. He loved math so much that his mom would have math problems waiting for him when he got home from kindergarten.");
		} else if (command.equals("Create New Puzzle")) {
			this.setPuzzle(new Puzzle(this.rows, this.cols));
			this.puzzle.generatePuzzle();
			this.gameClock.startTimer();
			this.update();
		} else if (command.equals("Undo")) {
			this.puzzle.undo();
			this.update();
		} else if (command.equals("Redo")) {
			this.puzzle.redo();
			this.update();
		} else if (command.equals("Reset Puzzle")) {
			this.puzzle.setToSolution();
			this.remove(this.timerPanel);
			this.timerPanel = new JPanel();
			this.gameClock = new TimerLabel();
			this.timerPanel.add(this.gameClock);
			this.add(this.timerPanel, BorderLayout.NORTH);
			this.update();
		} else if (command.equals("Change size of puzzle")) {
			try {
				int newRows = Integer
						.parseInt(JOptionPane
								.showInputDialog("Please enter the desired number of rows (2-10):"));
				int newCols = Integer
						.parseInt(JOptionPane
								.showInputDialog("Please enter the desired number of columns (2-10):"));
				if (newRows < 2 || newRows > 10) {
					throw new Exception();
				}
				if (newCols < 2 || newCols > 10) {
					throw new Exception();
				}
				this.rows = newRows;
				this.cols = newCols;

			} catch (Exception exc) {
				JOptionPane.showMessageDialog(null, "Please try again.");
			}

			this.puzzle = new Puzzle(this.rows, this.cols);
			this.puzzle.generatePuzzle();
			this.gameClock.startTimer();
			this.update();
		} else {
			if (e.getSource() instanceof GameButton) {
				GameButton b = (GameButton) e.getSource();
				if (!b.getText().equals("")) {
					int gridX = b.getGridX();
					int gridY = b.getGridY();
					if (this.puzzle.makeMove(gridX, gridY)) {
						this.update();
					}
				}
			}
			if (this.puzzle.isSolved()) {
				stoppedTimer = true;
				this.remove(this.timerPanel);
				JOptionPane.showMessageDialog(null,
						"Congratulations, you win!!! \nIt took you "
								+ this.gameClock.getText()
								+ " to solve the puzzle.");
			}
			if (stoppedTimer) {
				stoppedTimer = false;
				this.timerPanel = new JPanel();
				this.gameClock = new TimerLabel();
				this.timerPanel.add(this.gameClock);
				this.add(this.timerPanel, BorderLayout.NORTH);
			}
		}
	}

	/**
	 * Updates the frame and its game panel to reflect any changes that have
	 * been made.
	 * 
	 */
	private void update() {
		this.remove(this.gamePanel);
		this.gamePanel = new GamePanel(this.rows, this.cols);
		this.board = this.puzzle.returnPuzzle();
		this.boardDisplay = new ArrayList<GameButton>();
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				GameButton b = new GameButton(this.board[i][j], i, j);
				b.setFont(new Font("Serif", Font.PLAIN, 24));
				b.addActionListener(this);
				this.boardDisplay.add(b);
			}
		}
		for (GameButton button : this.boardDisplay) {
			this.gamePanel.add(button);
		}

		this.add(this.gamePanel, BorderLayout.CENTER);
		this.revalidate();
	}
}