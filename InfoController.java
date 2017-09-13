package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class InfoController implements Initializable {

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

	public void getStrings() {
		infoTask = infoTaskField.getText();
		infoTime = infoTimeField.getText();
		infoInfo = infoInfoField.getText();
	}

	public void onSubmitButtonPressed() throws IOException {
		getStrings();
		Model.getHomeController().createReminder(infoTask, infoInfo);
		Model.getInfoStage().hide();
		clearTextFields();
	}

	public void clearTextFields() {
		infoTaskField.clear();
		infoTimeField.clear();
		infoInfoField.clear();
	}

	// Issue because its creating a new instance of the home controller rather
	// than the original so it cant do stuff to the original home screen?
	// I think I resolved this by changing stuff in the main method.

	/*
	 * Getters and setters
	 */

}
