package application;

import java.util.TimerTask;

import javafx.application.Platform;

public class AdjustingTask extends TimerTask {
	private Reminder reminder;
	private double distance;
	private double originalLocation;
	private double endLocation;

	public AdjustingTask(Reminder r) {
		reminder = r;
		distance = r.getHeight();
		originalLocation = r.getLayoutY();
		endLocation = originalLocation - distance;
	}

	public void run() {
		if (reminder.getLayoutY() > endLocation) {
			Platform.runLater(new Runnable() {
				public void run() {
					reminder.setLayoutY(reminder.getLayoutY() - 1);
				}
			});
		} else {
			cancel();
			Platform.runLater(new Runnable() {
				public void run() {
					// reminder.getModel().getHomeController().reorganizeReminders();
				}
			});
		}
	}

}
