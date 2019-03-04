package by.gsu.epamlab.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.beans.Task;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.databases.DBManager;
import by.gsu.epamlab.interfaces.ITaskDAO;

public class TaskDBImpl implements ITaskDAO  {
	private static final Logger LOGGER = Logger.getLogger(UserImplDB.class.getName());
	private static DBManager maneger;
	
	private final static String SELECT_TASK = "select idEvent, name, dat, isDone, isIntoBin, fileName from events where idEvent = (?)";
	private final static String DELET_TASK = "Delete From events where idEvent = (?)";
	private final static String insertTask = "INSERT INTO events (userId, name, dat, isDone, isIntoBin, fileName) VALUES (?, ?, ?, ?, ?, ?)";
	private final static int ID_LOGIN = 1;
	private final static int ID_NAME = 2;
	private final static int ID_DATE = 3;
	private final static int ID_DONE = 4;
	private final static int ID_BIN = 5;
	private final static int ID_FILE = 6;
	private final static int ID_TASK = 1;
	
	public TaskDBImpl() {
		super();
		maneger = DBManager.getInstance();
	}
	
	private enum Menu{
		TODAY {
			final String selectTasks = "select idEvent, name, dat, isDone, isIntoBin, fileName from events where userId= (?) and dat <= CURDATE()  and isDone = false and isIntoBin = false";
			@Override
			PreparedStatement getSelectTasksStatement(User user, Connection connection) throws SQLException {
				PreparedStatement psSelectTasks =  connection.prepareStatement(selectTasks);
				psSelectTasks.setInt(ID_LOGIN, user.getId());
				return psSelectTasks;
			}

			@Override
			PreparedStatement getInsertTaskStatement(User user, Task task, Connection connection) throws SQLException {
				Date date = new Date();
				task.setDate(date.getTime());
				PreparedStatement psInsetTasks = connection.prepareStatement(insertTask);
				psInsetTasks.setInt(ID_LOGIN, user.getId());
				psInsetTasks.setString(ID_NAME, task.getName());
				psInsetTasks.setDate(ID_DATE, task.getDate());
				psInsetTasks.setBoolean(ID_DONE, task.isDone());
				psInsetTasks.setBoolean(ID_BIN, task.isIntoBin());
				psInsetTasks.setString(ID_FILE, task.getFileName());
				return psInsetTasks;
			}
		}, TOMORROW {
			final String selectTasks = "select idEvent, name, dat, isDone, isIntoBin, fileName from events where userId= (?) and dat = DATE_ADD(CURDATE(), INTERVAL 1 DAY) and isDone = false and isIntoBin = false";
			@Override
			PreparedStatement getSelectTasksStatement(User user, Connection connection) throws SQLException {
				PreparedStatement psSelectTasks =  connection.prepareStatement(selectTasks);
				psSelectTasks.setInt(ID_LOGIN, user.getId());
				return psSelectTasks;
			}

			@Override
			PreparedStatement getInsertTaskStatement(User user, Task task, Connection connection) throws SQLException {
				Date date= new Date(new Date().getTime() + (24 * 60 * 60 * 1000));
				task.setDate(date.getTime());
				PreparedStatement psInsetTasks = connection.prepareStatement(insertTask);
				psInsetTasks.setInt(ID_LOGIN, user.getId());
				psInsetTasks.setString(ID_NAME, task.getName());
				psInsetTasks.setDate(ID_DATE, task.getDate());
				psInsetTasks.setBoolean(ID_DONE, task.isDone());
				psInsetTasks.setBoolean(ID_BIN, task.isIntoBin());
				psInsetTasks.setString(ID_FILE, task.getFileName());
				return psInsetTasks;
			}
		}, SOMEDAY {
			final String selectTasks = "select idEvent, name, dat, isDone, isIntoBin, fileName from events where userId= (?) and dat > DATE_ADD(CURDATE(), INTERVAL 1 DAY) and isDone = false and isIntoBin = false";
			@Override
			PreparedStatement getSelectTasksStatement(User user, Connection connection) throws SQLException {
				PreparedStatement psSelectTasks =  connection.prepareStatement(selectTasks);
				psSelectTasks.setInt(ID_LOGIN, user.getId());
				return psSelectTasks;
			}

			@Override
			PreparedStatement getInsertTaskStatement(User user, Task task, Connection connection) throws SQLException {
				PreparedStatement psInsetTasks = connection.prepareStatement(insertTask);
				psInsetTasks.setInt(ID_LOGIN, user.getId());
				psInsetTasks.setString(ID_NAME, task.getName());
				psInsetTasks.setDate(ID_DATE, task.getDate());
				psInsetTasks.setBoolean(ID_DONE, task.isDone());
				psInsetTasks.setBoolean(ID_BIN, task.isIntoBin());
				psInsetTasks.setString(ID_FILE, task.getFileName());
				return psInsetTasks;	
			}
		}, FIXED {
			final String selectTasks = "select idEvent, name, dat, isDone, isIntoBin, fileName from events where userId= (?) and isDone = true and isIntoBin = false";
			@Override
			PreparedStatement getSelectTasksStatement(User user, Connection connection) throws SQLException {
				PreparedStatement psSelectTasks =  connection.prepareStatement(selectTasks);
				psSelectTasks.setInt(ID_LOGIN, user.getId());
				return psSelectTasks;
			}

			@Override
			PreparedStatement getInsertTaskStatement(User user, Task task, Connection connection) throws SQLException {
				task.setDone(true);
				PreparedStatement psInsetTasks = connection.prepareStatement(insertTask);
				psInsetTasks.setInt(ID_LOGIN, user.getId());
				psInsetTasks.setString(ID_NAME, task.getName());
				psInsetTasks.setDate(ID_DATE, task.getDate());
				psInsetTasks.setBoolean(ID_DONE, task.isDone());
				psInsetTasks.setBoolean(ID_BIN, task.isIntoBin());
				psInsetTasks.setString(ID_FILE, task.getFileName());
				return psInsetTasks;
				
			}
		}, RECYCLE_BIN {
			final String selectTasks = "select idEvent, name, dat, isDone, isIntoBin, fileName from events where userId= (?) and isIntoBin = true";
			@Override
			PreparedStatement getSelectTasksStatement(User user, Connection connection) throws SQLException {
				PreparedStatement psSelectTasks =  connection.prepareStatement(selectTasks);
				psSelectTasks.setInt(ID_LOGIN, user.getId());
				return psSelectTasks;
			}

			@Override
			PreparedStatement getInsertTaskStatement(User user, Task task, Connection connection) throws SQLException {
				task.setIntoBin(true);
				PreparedStatement psInsetTasks = connection.prepareStatement(insertTask);
				psInsetTasks.setInt(ID_LOGIN, user.getId());
				psInsetTasks.setString(ID_NAME, task.getName());
				psInsetTasks.setDate(ID_DATE, task.getDate());
				psInsetTasks.setBoolean(ID_DONE, task.isDone());
				psInsetTasks.setBoolean(ID_BIN, task.isIntoBin());
				psInsetTasks.setString(ID_FILE, task.getFileName());
				return psInsetTasks;
			}
		};
		abstract PreparedStatement getSelectTasksStatement(User user, Connection connection)  throws SQLException;
		abstract PreparedStatement getInsertTaskStatement(User user, Task task, Connection connection)  throws SQLException;
	}
	
	@Override
	public void setTask(User user, Task task, String menuOption) {
		Connection connection = maneger.getConnection();
		PreparedStatement psInsetTasks = null;
		try {
			psInsetTasks = Menu.valueOf(menuOption.toUpperCase()).getInsertTaskStatement(user, task, connection);
			if(psInsetTasks.executeUpdate() < 1) {
				throw new ImplementationException(Constants.ADD_TASK_EXCEPTION);
			}
			
		} catch (SQLException e) {
			LOGGER.log(Level.FINE, e.getMessage(), e);
			throw new ImplementationException(Constants.ADD_TASK_EXCEPTION, e);
		}finally {
			maneger.closePreparedStatement(psInsetTasks);
			maneger.putConnection(connection);
		}
	}

	@Override
	public List<Task> getTasks(User user, String menuOption) {
		List<Task> tasks = new ArrayList<>();
		Connection connection = maneger.getConnection();
		PreparedStatement psSelectTasks = null;
		ResultSet rs = null;
		try {
			psSelectTasks = Menu.valueOf(menuOption.toUpperCase()).getSelectTasksStatement(user, connection);
			rs = psSelectTasks.executeQuery();
			while (rs.next()) {
				tasks.add(new Task(rs.getString("name"), rs.getInt("idEvent"), rs.getDate("dat"),
						rs.getBoolean("isDone"), rs.getBoolean("IsIntoBin"), rs.getString("fileName")));
			}
			return tasks;
		} catch (SQLException e) {
			LOGGER.log(Level.FINE, e.getMessage(), e);
			throw new ImplementationException(Constants.GET_TASKS_EXCEPTION, e);
		}finally {
			maneger.closeResultSet(rs);
			maneger.closePreparedStatement(psSelectTasks);
			maneger.putConnection(connection);
		}
	}

	@Override
	public Task remove(int idTask) {
		Connection connection = maneger.getConnection();
		PreparedStatement psDeletTask = null;
		try {
			Task task = getTask(idTask);
			psDeletTask = connection.prepareStatement(DELET_TASK);
			psDeletTask.setInt(ID_TASK, task.getId());
			psDeletTask.executeUpdate();
			return task;
		} catch (SQLException e) {
			LOGGER.log(Level.FINE, e.getMessage(), e);
			throw new ImplementationException(Constants.REMOVE_TASK_EXCEPTION + idTask, e);
		}finally {
			maneger.closePreparedStatement(psDeletTask);
			maneger.putConnection(connection);
		}
	}

	@Override
	public Task getTask(int idTask) {
		Connection connection = maneger.getConnection();
		PreparedStatement psSelectTask = null;
		ResultSet rs = null;
		try {
			psSelectTask = connection.prepareStatement(SELECT_TASK);
			psSelectTask.setInt(ID_LOGIN, idTask);
			rs = psSelectTask.executeQuery();
			if(rs.next()) {
				return new Task(rs.getString("name"), rs.getInt("idEvent"), rs.getDate("dat"),
						rs.getBoolean("isDone"), rs.getBoolean("isIntoBin"), rs.getString("fileName"));
			}else {
				throw new ImplementationException(Constants.REMOVE_TASK_EXCEPTION);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.FINE, e.getMessage(), e);
			throw new ImplementationException(Constants.REMOVE_TASK_EXCEPTION, e);
		}finally {
			maneger.closeResultSet(rs);
			maneger.closePreparedStatement(psSelectTask);
			maneger.putConnection(connection);
		}
	}

		
	
}
