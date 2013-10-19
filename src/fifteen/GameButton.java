package fifteen;

import javax.swing.JButton;

/**
 * This class specifies a GameButton, which is one of the pieces on the game
 * board and is labeled with a number (or is blank).
 * 
 * @author luddenig. Created Sep 28, 2013.
 */
public class GameButton extends JButton {

	private int gridX, gridY;

	/**
	 * Constructs a new GameButton with the given label and (x, y) coordinate.
	 * Note: the (x, y) coordinate is relative to the grid of GameButtons and is
	 * not the same as the pixel (x, y) coordinate of the GameButton.
	 * 
	 * @param label
	 * @param x
	 * @param y
	 */
	public GameButton(int label, int x, int y) {
		this.gridX = x;
		this.gridY = y;
		if (label != 0) {
			this.setText(label + "");
		} else {
			this.setText("");
		}
	}

	/**
	 * Returns the value of the field called 'gridX'.
	 * 
	 * @return Returns the gridX.
	 */
	public int getGridX() {
		return this.gridX;
	}

	/**
	 * Returns the value of the field called 'gridY'.
	 * 
	 * @return Returns the gridY.
	 */
	public int getGridY() {
		return this.gridY;
	}
}
