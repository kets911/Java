package by.gsu.epamlab.interfaces;

import java.util.List;

import by.gsu.epamlab.beans.Task;
import by.gsu.epamlab.beans.User;

public interface ITaskDAO {
	void setTask(User user, Task task, String menuOption);
	List<Task> getTasks(User user, String menuOption);
	Task remove(int idTask);
	Task getTask(int idTask);
}
