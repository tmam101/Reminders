package application;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

public class Reminder extends AnchorPane {

	// Include functionality for time and information
	private Ellipse exampleCircle;
	private Label exampleLabel;
	private Line exampleTopLine;
	private Line exampleBottomLine;
	private Label exampleInfo;
	private AnchorPane exampleReminderPane;

	private Line newTopLine;
	private Line newBottomLine;
	private Label newInfo;
	private Label newLabel;
	private Ellipse newCircle;

	private String task = "";
	private String info = "";

	public Reminder() {
	}

	/*
	 * 
	 * 
	 * 
	 * Creating Reminder
	 * 
	 * 
	 * 
	 */

	public void createComponents() {
		// Create new components

		// AnchorPane newReminderPane = new AnchorPane();
		newTopLine = new Line();
		newBottomLine = new Line();
		newInfo = new Label();
		newLabel = new Label();
		newCircle = new Ellipse();

		newInfo.setVisible(false);

		// Set the user data of the objects in order to retrieve them easily
		// later by searching the children for the one with the tag

		// Should I set this reminder pane to have a unique tag?
		this.setUserData("newReminderPane");
		newTopLine.setUserData("newTopLine");
		newBottomLine.setUserData("newBottomLine");
		newInfo.setUserData("newInfo");
		newLabel.setUserData("newLabel");
		newCircle.setUserData("newCircle");

		// Add the children
		this.getChildren().add(newTopLine);
		this.getChildren().add(newBottomLine);
		this.getChildren().add(newInfo);
		this.getChildren().add(newLabel);
		this.getChildren().add(newCircle);

		// Add the methods
		// Info label method
		newInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				onInfoLabelClicked();
			}
		});
		// Circle method
		newCircle.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				onCircleClicked();
			}
		});
		// Label method
		newLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				onLabelClicked();
			}
		});

		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				onMouseEntered();
			}
		});

		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				onMouseExited();
			}
		});

		// Set the components equal to the original components
		setLinesEqual(newBottomLine, exampleBottomLine);
		if (Model.getHomeController().equals(null)) {
			System.out.println("home controller null");
		}
		if (Model.getHomeController().countRemindersExcludingOriginal() == 0) {
			setLinesEqual(newTopLine, exampleTopLine);
		}
		setAnchorPanesEqual(this, exampleReminderPane);
		setLabelsEqual(newInfo, exampleInfo);
		setLabelsEqual(newLabel, exampleLabel);
		setCirclesEqual(newCircle, exampleCircle);

		newInfo.setText("i");
	}

	public void onMouseEntered() {
		newInfo.setVisible(true);
	}

	public void onMouseExited() {
		newInfo.setVisible(false);
	}

	public void setLinesEqual(Line l, Line l2) {
		l.setLayoutX(l2.getLayoutX());
		l.setLayoutY(l2.getLayoutY());
		l.setStartX(l2.getStartX());
		l.setEndX(l2.getEndX());
		l.setStartY(l2.getStartY());
		l.setEndY(l2.getEndY());
		l.setStyle(l2.getStyle());
		l.setFill(l2.getFill());
		l.setStroke(l2.getStroke());
	}

	public void setAnchorPanesEqual(AnchorPane a, AnchorPane a2) {
		a.setLayoutX(a2.getLayoutX());
		a.setLayoutY(a2.getLayoutY());
		a.setPrefWidth(a2.getPrefWidth());
		a.setPrefHeight(a2.getPrefHeight());
	}

	public void setLabelsEqual(Label l, Label l2) {
		l.setLayoutX(l2.getLayoutX());
		l.setLayoutY(l2.getLayoutY());
		l.setPrefWidth(l2.getPrefWidth());
		l.setPrefHeight(l2.getPrefHeight());
		l.setAlignment(l2.getAlignment());
		l.setStyle(l2.getStyle());
	}

	public void setCirclesEqual(Ellipse e, Ellipse e2) {
		e.setCenterX(e2.getCenterX());
		e.setCenterY(e2.getCenterY());
		e.setRadiusX(e2.getRadiusX());
		e.setRadiusY(e2.getRadiusY());
		e.setFill(e2.getFill());
		e.setStroke(e2.getStroke());
		e.setLayoutX(e2.getLayoutX());
		e.setLayoutY(e2.getLayoutY());
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

	private int increment = 1;

	public void onLabelClicked() {
		for (int i = 1; i < 4; i++) {
			Model.getHomeController().createReminder("Example Task " + increment, "Example Info " + increment);
			increment++;
		}
	}

	public void onInfoLabelClicked() {
		Model.getEditStage().show();
		Model.getHomeController().setCurrentReminder((Reminder) newInfo.getParent());
		Model.getEditController().getContent((Reminder) newInfo.getParent());
	}

	public void onCircleClicked() {
		this.addCircleAccent();

		fade();
		adjust();
	}

	public void addCircleAccent() {
		Ellipse e = new Ellipse();
		setCirclesEqual(e, newCircle);
		e.setRadiusX(newCircle.getRadiusX() * .75);
		e.setRadiusY(newCircle.getRadiusY() * .75);
		e.setFill(Color.DEEPPINK);
		this.getChildren().add(e);
	}

	public ArrayList<Node> getRemindersBelowThisReminder() {
		ObservableList<Node> list = Model.getHomeController().getRemindersPane().getChildren();
		ArrayList<Node> newList = new ArrayList<Node>();

		int index = 99;
		// Get index of reminder
		for (Node n : list) {
			if (n.equals(this)) {
				index = list.indexOf(n);
			}
		}
		// Add reminders after this reminder to the new list
		for (Node n : list) {
			if (list.indexOf(n) > index) {
				newList.add(n);
			}
		}
		return newList;
	}

	public void fade() {
		Fader fader = new Fader(this);
		fader.start();
	}

	public void adjust() {
		for (Node n : getRemindersBelowThisReminder()) {
			Adjuster adjuster = new Adjuster((Reminder) n);
			adjuster.start();
		}
	}

	public void setTopLineVisible() {
		setLinesEqual(newTopLine, exampleTopLine);
		newTopLine.setVisible(true);
	}

	public void setTopLineInvisible() {
		newTopLine.setVisible(false);
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

	public void setExampleAnchorPane(AnchorPane a) {
		exampleReminderPane = a;
	}

	public void setExampleLabel(Label l) {
		exampleLabel = l;
	}

	public void setExampleTopLine(Line l) {
		exampleTopLine = l;
	}

	public void setExampleBottomLine(Line l) {
		exampleBottomLine = l;
	}

	public void setExampleInfo(Label l) {
		exampleInfo = l;
	}

	public void setExampleCircle(Ellipse e) {
		exampleCircle = e;
	}

	public Ellipse getCircle() {
		return newCircle;
	}

	public Label getLabel() {
		return newLabel;
	}

	public Line getTopLine() {
		return newTopLine;
	}

	public Line getBottomLine() {
		return newBottomLine;
	}

	public Label getInfoLabel() {
		return newInfo;
	}

	public AnchorPane getAnchorPane() {
		return this;
	}

	public void setTask(String s) {
		task = s;
		newLabel.setText(task);
	}

	public String getTask() {
		return task;
	}

	public void setInfoString(String s) {
		info = s;
	}

	public String getInfoString() {
		return info;
	}

}
