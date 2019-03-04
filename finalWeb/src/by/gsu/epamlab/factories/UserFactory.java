package by.gsu.epamlab.factories;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.impl.ImplementationException;

//import java.util.HashMap;
//import java.util.Map;

//import by.gsu.epamlab.impl.MemoryImpl;
//import by.gsu.epamlab.impl.MySQLImpl;
import by.gsu.epamlab.interfaces.IUserDAO;

public class UserFactory {
	private static final String CLASS_PATH = "by.gsu.epamlab.impl.";
	private UserFactory() {	
	}

	public static IUserDAO getClassFromFactory(String userImplName) {
		try {
			return  (IUserDAO) Class.forName(CLASS_PATH + userImplName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new ImplementationException(Constants.DAO_EXCEPTION, e);
		}
		
    }

}
