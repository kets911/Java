package by.gsu.epamlab.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.databases.DBManager;

public class UserImplDB extends AbstractUserDAO {
	private static final Logger LOGGER = Logger.getLogger(UserImplDB.class.getName());
	DBManager maneger;
	private final static String SELECT_USER = "select login, passwd, idUser from users, passwords where login= (?) and passwords.userId = users.idUser";
	private final static String SELECT_USER_ID = "select idUser from users where login= (?)";
	private final static String INSERT_USER = "INSERT INTO users (login) VALUES (?)";
	private final static String INSERT_PASSWORD = "INSERT INTO passwords (userId, passwd) VALUES (?, ?)";
	private final static String USER_ID_COLOMN = "idUser";
	private final static String USER_LOGIN_COLOMN = "login";
	private final static String USER_PASSWORD_COLOMN = "passwd";
	private final static int ID_LOGIN = 1;
	private final static int ID_PASSWORD = 2;
	
	public UserImplDB() {
		super();
		maneger = DBManager.getInstance();
	}

	@Override
	public User authorizationUser(String login, String password) {
		validation(login, password);
		Connection connection = maneger.getConnection();
		PreparedStatement psSelectUser = null;
		ResultSet rs = null;
		try {
			psSelectUser = connection.prepareStatement(SELECT_USER);
			psSelectUser.setString(ID_LOGIN, login);
			rs = psSelectUser.executeQuery();
			if(!rs.next()) {
				throw new ImplementationException(Constants.LOGIN_WRONG_EXCEPTION);
			}
			if(rs.getString(USER_PASSWORD_COLOMN).equals(password)) {
				return new User(rs.getString(USER_LOGIN_COLOMN), rs.getInt(USER_ID_COLOMN));
			}else {
				throw new ImplementationException(Constants.PASSWORD_WRONG_EXCEPTION);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.FINE, e.toString(), e);
			throw new ImplementationException(Constants.DB_EXCEPTION, e);
		}finally {
			maneger.closeResultSet(rs);
			maneger.closePreparedStatement(psSelectUser);
			maneger.putConnection(connection);
		}
	}

	@Override
	public User registrationUser(String login, String password) {
		validation(login, password);
		Connection connection = maneger.getConnection();
		PreparedStatement psInsertUser = null;
		PreparedStatement psInsertPassword = null;
		PreparedStatement psSelectUserId = null;
		User user = new User();
		ResultSet rs = null;
		try {
			psInsertUser = connection.prepareStatement(INSERT_USER);
			psInsertPassword = connection.prepareStatement(INSERT_PASSWORD);
			psSelectUserId =connection.prepareStatement(SELECT_USER_ID);
			psSelectUserId.setString(ID_LOGIN, login);
			psInsertUser.setString(ID_LOGIN, login);
			synchronized (UserImplDB.class) {
				rs = psSelectUserId.executeQuery();
				if(rs.next()) {
					throw new ImplementationException(Constants.REGISTRATION_EXCEPTION);
				}
				psInsertUser.execute();
				}
				rs = psSelectUserId.executeQuery();
				if(rs.next()) {
					user.setLogin(login);
					int id = rs.getInt(USER_ID_COLOMN);
					user.setId(id);
					psInsertPassword.setInt(ID_LOGIN, id);
					psInsertPassword.setString(ID_PASSWORD, password);
					psInsertPassword.execute();
				}else {
					throw new ImplementationException(Constants.USER_NOT_ADDED_EXCEPTION);
				}
			return user;
		}catch (SQLException e) {
			LOGGER.log(Level.FINE, e.toString(), e);
			throw new ImplementationException(Constants.DB_EXCEPTION, e);
		}finally {
			maneger.closeResultSet(rs);
			maneger.closePreparedStatement(psSelectUserId);
			maneger.closePreparedStatement(psInsertPassword);
			maneger.closePreparedStatement(psInsertUser);
			maneger.putConnection(connection);
		}
	}

	@Override
	public void close() {
		
		
	}
}
