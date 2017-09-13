package application;

import java.util.Timer;

public class Adjuster {
	private Reminder reminder;
	private Timer timer;

	public Adjuster(Reminder r) {
		reminder = r;
		timer = new Timer();
	}

	public void start() {
		timer.scheduleAtFixedRate(new AdjustingTask(reminder), 0, 5);
	}

	public void stop() {
		timer.cancel();
	}

}
