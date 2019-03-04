package by.gsu.epamlab.impl;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.interfaces.IUserDAO;

public abstract class AbstractUserDAO implements IUserDAO {

	public void validation(String login, String password) {
		if(login == null || Constants.EMPTY.equals(login)) {
			throw new ImplementationException(Constants.LOGIN_EMPTY_EXCEPTION);
		}
		if(password == null || Constants.EMPTY.equals(password)) {
			throw new ImplementationException(Constants.PASSWORD_EMPTY_EXCEPTION);
		}
	}

}
