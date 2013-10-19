package fifteen;

import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * This class specifies the GamePanel that will be placed in the center of the
 * frame.
 * 
 * @author Melissa Thai. Created Sep 27, 2013.
 */
public class GamePanel extends JPanel {

	/**
	 * Constructs a GamePanel to contain all the buttons.
	 * 
	 * @param m
	 *            Rows of puzzle
	 * @param n
	 *            Columns of puzzle
	 * 
	 */
	public GamePanel(int m, int n) {
		this.setLayout(new GridLayout(m, n));
	}

}
