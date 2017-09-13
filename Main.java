package application;

import application.Controllers.EditController;
import application.Controllers.HomeController;
import application.Controllers.InfoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Create home stage
			FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
			Parent homeRoot = homeLoader.load();
			Scene homeScene = new Scene(homeRoot, 400, 600);
			// homeScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(homeScene);
			primaryStage.setResizable(false);

			// Create info stage
			FXMLLoader infoLoader = new FXMLLoader(getClass().getResource("Info.fxml"));
			Parent infoRoot = infoLoader.load();
			Scene infoScene = new Scene(infoRoot, 320, 500);
			Stage infoStage = new Stage();
			infoStage.setScene(infoScene);
			infoStage.setResizable(false);

			// Create edit stage
			FXMLLoader editLoader = new FXMLLoader(getClass().getResource("Edit.fxml"));
			Parent editRoot = editLoader.load();
			Scene editScene = new Scene(editRoot, 320, 500);
			Stage editStage = new Stage();
			editStage.setScene(editScene);
			editStage.setResizable(false);

			// Create controllers
			InfoController infoController = infoLoader.getController();
			HomeController homeController = homeLoader.getController();
			EditController editController = editLoader.getController();

			// Create and set model
			//
			// All elements have access to the model, which has access to
			// everything, in order to have access to
			// each other

			Model.setHomeStage(primaryStage);
			Model.setInfoStage(infoStage);
			Model.setEditStage(editStage);
			Model.setHomeController(homeController);
			Model.setInfoController(infoController);
			Model.setEditController(editController);

			// Load
			homeController.load();
			// Show home stage
			primaryStage.show();

			// There is some kind of situation where we have to create the
			// controller here in the main and pass it to the other classes.
			// This allows us to have the controllers interact.

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
