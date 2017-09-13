package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Model;
import application.Reminder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class EditController implements Initializable {

	private String infoTask = "";
	private String infoTime = "";
	private String infoInfo = "";

	@FXML
	private TextField infoTaskField;
	@FXML
	private TextField infoTimeField;
	@FXML
	private TextField infoInfoField;

	public void initialize(URL location, ResourceBundle resources) {

	}

	public void onEditButtonPressed() throws IOException {
		getStrings();
		Model.getHomeController().editCurrentReminder(infoTask, infoInfo);
		Model.getEditStage().hide();
		clearTextFields();
	}

	public void onDeleteButtonPressed() {
		Model.getHomeController().getCurrentReminder().fade();
		Model.getHomeController().getCurrentReminder().adjust();
		Model.getEditStage().hide();
		clearTextFields();
	}

	public void getContent(Reminder r) {
		infoTask = r.getTask();
		infoTaskField.setText(infoTask);

		infoInfo = r.getInfoString();
		infoInfoField.setText(infoInfo);
	}

	public void clearTextFields() {
		infoTaskField.clear();
		infoTimeField.clear();
		infoInfoField.clear();
	}

	public void getStrings() {
		infoTask = infoTaskField.getText();
		infoTime = infoTimeField.getText();
		infoInfo = infoInfoField.getText();
	}

	/*
	 * Getters and setters
	 */

}
