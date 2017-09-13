package application;

import java.util.Timer;

public class Fader {
	private Reminder reminder;
	private Timer timer;

	public Fader(Reminder r) {
		reminder = r;
		timer = new Timer();
	}

	public void start() {
		timer.scheduleAtFixedRate(new FadingTask(reminder), 0, 5);
	}

}
