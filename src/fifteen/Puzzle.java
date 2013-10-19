package fifteen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * This class contains the value storage and algorithms for a fifteen puzzle.
 * 
 * @author Melissa Thai. Created Sep 27, 2013.
 */
public class Puzzle {

	private int[][] gameBoard;
	private Stack<Move> movesMade;
	private Stack<Move> redos;
	private static final int NUMBER_OF_SCRAMBLE_MOVES = 100;

	/**
	 * Constructs the puzzles gameBoard with "rows" rows and "cols" columns.
	 * 
	 * @param rows
	 * @param cols
	 */
	public Puzzle(int rows, int cols) {
		this.gameBoard = new int[rows][cols];
		this.movesMade = new Stack<Move>();
		this.redos = new Stack<Move>();
		this.setToSolution();
	}

	/**
	 * Generates a random solvable puzzle.
	 * 
	 */
	public void generatePuzzle() {
		for (int i = 0; i < Puzzle.NUMBER_OF_SCRAMBLE_MOVES
				* this.gameBoard.length * this.gameBoard[0].length; i++) {
			this.makeRandomMove();
		}

	}

	/**
	 * Resets the gameBoard to the solved state.
	 * 
	 */
	public void setToSolution() {
		this.gameBoard = this.generateSolution();
		this.gameBoard[this.gameBoard.length - 1][this.gameBoard[0].length - 1] = 0;
	}

	/**
	 * Randomly swaps the blank space with an adjacent space.
	 * 
	 */
	public void makeRandomMove() {
		int blankX = -1;
		int blankY = -1;
		for (int i = 0; i < this.gameBoard.length; i++) {
			for (int j = 0; j < this.gameBoard[i].length; j++) {
				if (this.gameBoard[i][j] == 0) {
					blankX = i;
					blankY = j;
				}
			}
		}
		List<Move> availableMoves = new ArrayList<Move>();
		if (blankX + 1 < this.gameBoard.length) {
			availableMoves.add(new Move(blankX + 1, blankY, blankX, blankY));
		}
		if (blankY + 1 < this.gameBoard[0].length) {
			availableMoves.add(new Move(blankX, blankY + 1, blankX, blankY));
		}

		if (blankX - 1 > -1) {
			availableMoves.add(new Move(blankX - 1, blankY, blankX, blankY));
		}
		if (blankY - 1 > -1) {
			availableMoves.add(new Move(blankX, blankY - 1, blankX, blankY));
		}
		Random generator = new Random();
		int index = -1;
		while (index == -1) {
			index = generator.nextInt(availableMoves.size());
		}
		this.makeMove(availableMoves.get(index));

	}

	/**
	 * Generates and returns a solution based off of the size of this gameboard
	 * 
	 * @return solution
	 */
	public int[][] generateSolution() {
		int[][] soln = new int[this.gameBoard.length][this.gameBoard[0].length];
		int k = 1;
		for (int i = 0; i < this.gameBoard.length; i++) {
			for (int j = 0; j < this.gameBoard[i].length; j++) {
				soln[i][j] = k;
				k++;
			}
		}

		soln[this.gameBoard.length - 1][this.gameBoard[0].length - 1] = 0;
		return soln;
	}

	/**
	 * Makes a move from the given (x,y) coordinate to the blank space, if
	 * possible.
	 * 
	 * @param fromX Previous x position.
	 * @param fromY Previous y position.
	 * @return true If the move is valid, false otherwise.
	 */
	public boolean makeMove(int fromX, int fromY) {
		int blankX = -1;
		int blankY = -1;
		fromX = Math.abs(fromX);
		fromY = Math.abs(fromY);
		for (int i = 0; i < this.gameBoard.length; i++) {
			for (int j = 0; j < this.gameBoard[i].length; j++) {
				if (this.gameBoard[i][j] == 0) {
					blankX = i;
					blankY = j;
				}
			}
		}
		if (Math.abs(fromX - blankX) + Math.abs(fromY - blankY) == 1) {
			Move m = new Move(fromX, fromY, blankX, blankY);
			this.makeMove(m);
			this.movesMade.push(m);
			this.redos.clear();
			return true;
		}
		return false;

	}

	private void makeMove(Move m) {
		this.gameBoard[m.getPostX()][m.getPostY()] = this.gameBoard[m
				.getPreviousX()][m.getPreviousY()];
		this.gameBoard[m.getPreviousX()][m.getPreviousY()] = 0;
	}

	/**
	 * Returns this Puzzle's gameBoard.
	 * 
	 * @return gameBoard
	 */
	public int[][] returnPuzzle() {
		return this.gameBoard;
	}

	/**
	 * Reverts the gameboard to the state before the previous move and adds the
	 * "undone" move to the stack of moves that can be "redone."
	 * 
	 */
	public void undo() {
		if (this.movesMade.size() > 0) {
			Move m = (this.movesMade.pop());
			this.makeMove(m.invert());
			this.redos.push(m);
		}
	}

	/**
	 * Makes the last "undone" move.
	 * 
	 */
	public void redo() {
		if (!redoable()) {
			return;
		}
		Move m = this.redos.pop();
		this.makeMove(m);
		this.movesMade.push(m);

	}

	/**
	 * Checks to see if there are redos to be made.
	 * 
	 * @return true If there are redos to be made, false otherwise.
	 */
	public boolean redoable() {
		return this.redos.size() > 0;
	}

	/**
	 * This checks if the puzzle is solved
	 * 
	 * @return if it is solved
	 */
	public boolean isSolved() {
		int[][] soln = this.generateSolution();
		for (int i = 0; i < this.gameBoard.length; i++) {
			for (int j = 0; j < this.gameBoard[i].length; j++) {
				if (this.gameBoard[i][j] != soln[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

}
