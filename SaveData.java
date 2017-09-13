package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveData implements Serializable {
	public SaveData() {

	}

	private List<String> tasks = new ArrayList<String>();
	private List<String> info = new ArrayList<String>();
	private List<String> completedTasks = new ArrayList<String>();
	private List<String> completedInfo = new ArrayList<String>();
	private List<String> deletedTasks = new ArrayList<String>();
	private List<String> deletedInfo = new ArrayList<String>();

	public void addToTasks(String s) {
		tasks.add(s);
	}

	public List<String> getTasks() {
		return tasks;
	}

	public void addToInfo(String s) {
		info.add(s);
	}

	public List<String> getInfo() {
		return info;
	}

	public void addToCompletedTasks(String s) {
		completedTasks.add(s);
	}

	public List<String> getCompletedTasks() {
		return completedTasks;
	}

	public void addToCompletedInfo(String s) {
		completedInfo.add(s);
	}

	public List<String> getCompletedInfo() {
		return completedInfo;
	}
}
