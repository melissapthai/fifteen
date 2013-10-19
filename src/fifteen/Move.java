package fifteen;

/**
 * This class specifies a move from a previous (x,y) to a post (x,y).
 * 
 * @author Melissa Thai. Created Sep 27, 2013.
 */
public class Move {

	private int previousX;
	private int previousY;
	private int postX;
	private int postY;

	/**
	 * Constructs a new Move given the parameters for the previous (x,y) and the
	 * post (x,y).
	 * 
	 * @param previousX
	 * @param previousY
	 * @param postX
	 * @param postY
	 */
	public Move(int previousX, int previousY, int postX, int postY) {
		this.previousX = previousX;
		this.previousY = previousY;
		this.postX = postX;
		this.postY = postY;
	}

	/**
	 * Getter method for previousX.
	 * 
	 * @return previousX
	 */
	public int getPreviousX() {
		return this.previousX;
	}

	/**
	 * Getter method for previousY.
	 * 
	 * @return previousY
	 */
	public int getPreviousY() {
		return this.previousY;
	}

	/**
	 * Getter method for postX.
	 * 
	 * @return postX
	 */
	public int getPostX() {
		return this.postX;
	}

	/**
	 * Getter method for postY.
	 * 
	 * @return postY
	 */
	public int getPostY() {
		return this.postY;
	}

	/**
	 * Returns an inverted version of this move, i.e., previous (x,y) and post
	 * (x,y) are flipped.
	 * 
	 * @return inverted move
	 */
	public Move invert() {
		return new Move(this.postX, this.postY, this.previousX, this.previousY);
	}
}
