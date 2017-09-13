package application.Controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import application.Model;
import application.Reminder;
import application.SaveData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class HomeController implements Initializable {

	// Controls
	@FXML
	private Label todayLabel;
	@FXML
	private Button addButton;
	@FXML
	private Button toggleShowCompletedButton;
	@FXML
	private Ellipse originalCircle;
	@FXML
	private Label originalLabel;
	@FXML
	private Label numberOfRemindersLabel;
	@FXML
	private Line originalTopLine;
	@FXML
	private Line originalBottomLine;
	@FXML
	private Label originalInfo;
	@FXML
	private AnchorPane originalReminder;
	@FXML
	private AnchorPane remindersPane;
	@FXML
	private AnchorPane mainPane;
	@FXML
	private ScrollPane scrollPane;

	private Reminder currentReminder;
	private boolean showingCompletedReminders = false;
	private List<Reminder> reminderList = new ArrayList<Reminder>();
	private List<Reminder> completedReminderList = new ArrayList<Reminder>();
	private List<Reminder> deletedReminderList = new ArrayList<Reminder>();

	// Save objects
	private FileOutputStream fileOut;
	private ObjectOutputStream out;
	private FileInputStream fileIn;
	private ObjectInputStream in;
	private String file = new String("/Users/tpgoss/Desktop/My Apps/Reminders/Resources/SaveFile.ser");
	private SaveData importedData;
	private SaveData data;

	public void initialize(URL location, ResourceBundle resources) {
		originalReminder.setUserData("original reminder");
		numberOfRemindersLabel.setText("0 Reminders");
	}

	/*
	 * 
	 * 
	 * 
	 * Creating Reminders
	 * 
	 * 
	 * 
	 */

	public void createReminder(String task, String info) {
		// If the user is on the completed reminder screen, switch screens
		// before proceeding.
		if (showingCompletedReminders) {
			this.toggleCompletedTasks();
		}
		// Create the reminder
		Reminder reminder = new Reminder();
		prepareReminder(reminder);

		// Finishing touches
		// Remove original reminder
		int indexToRemove = 99;
		for (Node n : remindersPane.getChildren()) {
			if (n.getUserData().equals("original reminder")) {
				indexToRemove = remindersPane.getChildren().indexOf(n);
			}
		}
		if (indexToRemove != 99) {
			remindersPane.getChildren().remove(indexToRemove);
		}

		// Count reminders and use the count to determine where to place the
		// newest reminder
		// A small offset is necessary because otherwise the top line gets cut
		// off a bit
		reminder.setLayoutY((countRemindersOnScreen() * originalReminder.getPrefHeight()) + 1);
		// reminder.setLayoutY((countRemindersOnScreen() * 50) + 1);

		remindersPane.getChildren().add(reminder);
		reminder.setTask(task);
		reminder.setInfoString(info);
		reminderList.add(reminder);
		this.updateNumberOfRemindersLabel();
		save();
	}

	public void prepareReminder(Reminder r) {
		setExamples(r);
		r.createComponents();
		int[] a = new int[2];
		String s = new String();
	}

	public void setExamples(Reminder r) {
		r.setExampleAnchorPane(originalReminder);
		r.setExampleBottomLine(originalBottomLine);
		r.setExampleCircle(originalCircle);
		r.setExampleInfo(originalInfo);
		r.setExampleLabel(originalLabel);
		r.setExampleTopLine(originalTopLine);
	}

	/*
	 * 
	 * 
	 * 
	 * Other Tasks
	 * 
	 * 
	 * 
	 */

	public int countRemindersExcludingOriginal() {
		int count = 0;
		for (Node n : remindersPane.getChildren()) {
			if (!n.getUserData().equals("original reminder")) {
				count++;
			}
		}
		return count;
	}

	public void deleteReminder(Reminder r) {
		remindersPane.getChildren().remove(r);

		if (reminderList.contains(r)) {
			reminderList.remove(r);
			completedReminderList.add(r);
		} else if (completedReminderList.contains(r)) {
			completedReminderList.remove(r);
			addToDeletedReminderList(r);
		}

		r.addCircleAccent();
		this.updateNumberOfRemindersLabel();
		save();
	}

	public void reorganizeReminders(List<Reminder> l) {
		remindersPane.getChildren().clear();
		for (Reminder r : l) {
			r.setOpacity(1);
			r.setLayoutY((countRemindersOnScreen() * 50) + 1);
			remindersPane.getChildren().add(r);
			r.setTopLineInvisible();
			// Ensure that the top line is visible on the top reminder
			if (remindersPane.getChildren().get(0).equals(r)) {
				r.setTopLineVisible();
			}
		}
		this.updateNumberOfRemindersLabel();
		save();
	}

	public int countRemindersOnScreen() {
		int count = 0;
		for (Node n : remindersPane.getChildren()) {
			count++;
		}
		return count;
	}

	public void editCurrentReminder(String task, String info) {
		for (Node n : remindersPane.getChildren()) {
			if (n.equals(currentReminder)) {
				Reminder r = (Reminder) n;
				r.setTask(task);
				r.setInfoString(info);
			}
		}
		save();
	}

	public void toggleCompletedTasks() {
		if (showingCompletedReminders == false) {
			this.reorganizeReminders(completedReminderList);
			this.toggleShowCompletedButton.setText("Hide Completed");
			this.showingCompletedReminders = true;
		} else {
			this.reorganizeReminders(reminderList);
			this.toggleShowCompletedButton.setText("Show Completed");
			this.showingCompletedReminders = false;
		}
		this.updateNumberOfRemindersLabel();
	}

	public void onAddButtonPressed() {
		Model.getInfoStage().show();
	}

	public void updateNumberOfRemindersLabel() {
		String text = String.valueOf(this.countRemindersOnScreen());
		if (showingCompletedReminders) {
			text += " Completed Reminders";
		} else {
			text += " Reminders";
		}
		this.numberOfRemindersLabel.setText(text);
	}

	// I should have a pop up "Are you sure you want to clear?"
	public void clearReminders() {
		if (showingCompletedReminders) {
			// Send these reminders to the deleted reminders list
			for (Node n : completedReminderList) {
				addToDeletedReminderList((Reminder) n);
			}
			// Clear and reorganize
			this.completedReminderList.clear();
			this.reorganizeReminders(this.completedReminderList);
		} else {
			for (Node n : reminderList) {
				addToDeletedReminderList((Reminder) n);
			}
			this.reminderList.clear();
			this.reorganizeReminders(this.reminderList);
		}
		this.updateNumberOfRemindersLabel();
	}

	public void addToDeletedReminderList(Reminder r) {
		this.deletedReminderList.add(r);
		if (deletedReminderList.size() > 50) {
			deletedReminderList.remove(0);
		}
	}

	public void save() {
		data = new SaveData();

		// Save Reminders
		for (Node n : reminderList) {
			Reminder r = (Reminder) n;
			data.addToTasks(r.getTask());
			data.addToInfo(r.getInfoString());
		}

		// Save Completed Reminders
		for (Node n : completedReminderList) {
			Reminder r = (Reminder) n;
			data.addToCompletedTasks(r.getTask());
			data.addToCompletedInfo(r.getInfoString());
		}

		try {
			fileOut = new FileOutputStream(file);
			out = new ObjectOutputStream(fileOut);
			out.writeObject(data);
			out.close();
			fileOut.close();

			System.out.println("Serialized data is saved in " + file);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void load() {
		// Load the data
		try {
			fileIn = new FileInputStream(file);
			in = new ObjectInputStream(fileIn);
			importedData = (SaveData) in.readObject();
			in.close();
			fileIn.close();

			this.reminderList.clear();
			this.completedReminderList.clear();

			if (importedData.getTasks().size() != 0) {
				for (int i = 0; i < importedData.getTasks().size(); i++) {
					Reminder r = new Reminder();
					prepareReminder(r);
					r.setTask(importedData.getTasks().get(i));
					r.setInfoString(importedData.getInfo().get(i));
					reminderList.add(r);
				}
			}

			if (importedData.getCompletedTasks().size() != 0) {
				for (int i = 0; i < importedData.getCompletedTasks().size(); i++) {
					Reminder r = new Reminder();
					prepareReminder(r);
					r.setTask(importedData.getCompletedTasks().get(i));
					r.setInfoString(importedData.getCompletedInfo().get(i));
					r.addCircleAccent();
					completedReminderList.add(r);
				}
			}
			this.reorganizeReminders(this.reminderList);
			// TODO

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * 
	 * 
	 * Getters and Setters
	 * 
	 * 
	 * 
	 */

	public void setCurrentReminder(Reminder r) {
		currentReminder = r;
	}

	public Reminder getCurrentReminder() {
		return currentReminder;
	}

	public void changeTodayLabel(String s) {
		todayLabel.setText(s);
	}

	public AnchorPane getRemindersPane() {
		return remindersPane;
	}

	public String getDateTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY:MM:dd HH:mm");
		String x = sdf.format(cal.getTime());
		return x;
	}

	public List<Reminder> getReminderList() {
		return reminderList;
	}

	public List<Reminder> getCompletedReminderList() {
		return completedReminderList;
	}

}
