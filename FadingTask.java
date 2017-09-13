package application;

import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class FadingTask extends TimerTask {

	private Reminder thisReminder;
	private Reminder reminderBelowThisReminder;
	private AnchorPane pane;
	private boolean done = false;
	private int yChange = 1;
	private double opacityChange = .02;

	public FadingTask(Reminder r) {
		this.thisReminder = r;
		pane = Model.getHomeController().getRemindersPane();
	}

	public void run() {
		if (thisReminder.getOpacity() <= 0.01) {
			this.cancel();
			done = true;
		}
		if (done == true) {
			Platform.runLater(new Runnable() {
				public void run() {
					Model.getHomeController().deleteReminder(thisReminder);
					// WE DONT REORGANIZE REMINDERS HERE ANYMORE IN ORDER FOR IT
					// TO SEEM SMOOTH. MIGHT CAUSE AN ISSUE LATER ON
					// model.getHomeController().reorganizeReminders(model.getHomeController().getReminderList());
					if (reminderBelowThisReminder != null) {
						// if
						// (pane.getChildren().indexOf(reminderBelowThisReminder)
						// != 0) {
						// reminderBelowThisReminder.setTopLineInvisible();
						// }
						if (pane.getChildren().indexOf(reminderBelowThisReminder) != 0) {
							reminderBelowThisReminder.setTopLineInvisible();
						}
					}
				}
			});
		}

		Platform.runLater(new Runnable() {
			public void run() {
				thisReminder.setTopLineVisible();
				// Code for correcting the visibility of the lines

				for (Node n : pane.getChildren()) {
					if (n.equals(thisReminder)) {
						int index = pane.getChildren().indexOf(n);
						if (pane.getChildren().size() >= index + 2) {
							reminderBelowThisReminder = (Reminder) pane.getChildren().get(index + 1);
							reminderBelowThisReminder.setTopLineVisible();
						}
					}
				}
				thisReminder.setLayoutY(thisReminder.getLayoutY() - yChange);
				thisReminder.setOpacity(thisReminder.getOpacity() - opacityChange);
			}
		});
	}
}
