package by.gsu.epamlab.interfaces;

import by.gsu.epamlab.beans.User;

public interface IUserDAO {
	User authorizationUser(String login, String password);
	User registrationUser(String login, String password);
	void close();
}
