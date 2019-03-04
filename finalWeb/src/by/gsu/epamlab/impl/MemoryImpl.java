package by.gsu.epamlab.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.beans.User;

public class MemoryImpl extends AbstractUserDAO {
	private static final Logger LOGGER = Logger.getLogger(MemoryImpl.class.getName());
	private static Map<String, String> users = new HashMap<>();
	
	public MemoryImpl() {
		super();
	}

	@Override
	public User authorizationUser(String login, String password) {
		validation(login, password);
		if(!users.containsKey(login)) {
			LOGGER.log(Level.FINE, Constants.LOGIN_WRONG_EXCEPTION);
			throw new ImplementationException(Constants.LOGIN_WRONG_EXCEPTION);
		}
		if(!users.get(login).equals(password)) {
			LOGGER.log(Level.FINE, Constants.PASSWORD_WRONG_EXCEPTION);
			throw new ImplementationException(Constants.PASSWORD_WRONG_EXCEPTION);
		}
		return new User(login);
	}

	@Override
	public User registrationUser(String login, String password) {
		validation(login, password);
		synchronized (MemoryImpl.class) {
			if(users.containsKey(login)) {
				LOGGER.log(Level.FINE, Constants.REGISTRATION_EXCEPTION);
				throw new ImplementationException(Constants.REGISTRATION_EXCEPTION);
			}
			users.put(login, password);
		}
		try {
			return authorizationUser(login, password);
		}catch (ImplementationException e) {
			LOGGER.log(Level.FINE, Constants.USER_NOT_ADDED_EXCEPTION);
			throw new ImplementationException(Constants.USER_NOT_ADDED_EXCEPTION);
		}
	}

	@Override
	public void close() {
		users=null;
		
	}

	

}
