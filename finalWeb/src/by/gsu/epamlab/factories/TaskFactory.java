package by.gsu.epamlab.factories;


import by.gsu.epamlab.Constants;
import by.gsu.epamlab.impl.ImplementationException;
import by.gsu.epamlab.interfaces.ITaskDAO;


public class TaskFactory {
	private static final String CLASS_PATH = "by.gsu.epamlab.impl.";
	private TaskFactory() {
		super();
	}

	public static ITaskDAO getTaskImpl(String taskImplName) {
		try {
			return  (ITaskDAO) Class.forName(CLASS_PATH + taskImplName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ImplementationException(Constants.DAO_EXCEPTION, e);
		}
	}
}
