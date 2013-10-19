package fifteen;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * This class specifies the TimerLabel that keeps track of how long the game is
 * taking and displays the current time.
 * 
 * @author thaimp. Created Sep 30, 2013.
 */
public class TimerLabel extends JLabel implements ActionListener {

	private Timer timer;
	private double startTime;

	/**
	 * Constructs a TimerLabel and sets it to 00:00
	 * 
	 */
	public TimerLabel() {
		super("00:00", SwingConstants.CENTER);
		this.setFont(new Font("Serif", Font.PLAIN, 24));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int time = (int) (System.currentTimeMillis() / 1000 - this.startTime);
		int mins = time / 60;
		String minsString = "";
		if (mins < 10) {
			minsString = "0" + mins;
		} else {
			minsString += mins;
		}
		int secs = time % 60;
		String secsString = "";
		if (secs < 10) {
			secsString = "0" + secs;
		} else {
			secsString += secs;
		}
		this.setText(minsString + ":" + secsString);
	}

	/**
	 * Starts the timer.
	 * 
	 */
	public void startTimer() {
		this.startTime = (int) (System.currentTimeMillis() / 1000.0);
		this.timer = new Timer(100, this);
		this.timer.start();
	}
}
