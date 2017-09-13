package application;

import application.Controllers.EditController;
import application.Controllers.HomeController;
import application.Controllers.InfoController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class Model {
	private static Reminder currentReminder;

	private static Stage editStage;
	private static Stage infoStage;
	private static Stage homeStage;

	@FXML
	private static HomeController homeController;
	@FXML
	private static InfoController infoController;
	@FXML
	private static EditController editController;

	// public Model() {
	//
	// }

	public static Reminder getCurrentReminder() {
		return currentReminder;
	}

	public static void setCurrentReminder(Reminder r) {
		currentReminder = r;
	}

	public static Stage getEditStage() {
		return editStage;
	}

	public static void setEditStage(Stage s) {
		editStage = s;
	}

	public static Stage getInfoStage() {
		return infoStage;
	}

	public static void setInfoStage(Stage s) {
		infoStage = s;
	}

	public static Stage getHomeStage() {
		return homeStage;
	}

	public static void setHomeStage(Stage s) {
		homeStage = s;
	}

	public static HomeController getHomeController() {
		return homeController;
	}

	public static void setHomeController(HomeController c) {
		homeController = c;
	}

	public static InfoController getInfoController() {
		return infoController;
	}

	public static void setInfoController(InfoController c) {
		infoController = c;
	}

	public static EditController getEditController() {
		return editController;
	}

	public static void setEditController(EditController c) {
		editController = c;
	}

}
