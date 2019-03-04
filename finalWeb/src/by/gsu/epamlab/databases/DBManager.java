package by.gsu.epamlab.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.gsu.epamlab.Constants;

public class DBManager {
	private static final Logger LOGGER = Logger.getLogger( DBManager.class.getName() );
	private final static String URL = "jdbc:mysql://localhost:3306/results";
	private final static String DRIVER_PATH = "org.gjt.mm.mysql.Driver";
	private final static String USER = "web";
	private final static String PASS = "web";
	private static List<Connection> connections = new ArrayList<>();
	private static int connectionsToGenerateCount = 20;
	private static int freeConnectionsCount = 0; 
	private static DBManager manager;
	
	public static DBManager getInstance() {
		if(manager == null) {
			manager = new DBManager();
		}return manager;
	}
	
	private DBManager() {
		try {
			Class.forName(DRIVER_PATH);
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.FINE, e.toString(), e);
			throw new DataBaseException(Constants.DB_EXCEPTION, e);
		}
	}
	
	public Connection getConnection() {
		try {
			synchronized (connections) {
				if(freeConnectionsCount <=0) {
					if(connectionsToGenerateCount <=0) {
						while(freeConnectionsCount <= 0) {
							wait();
						}
					}else {
						connections.add(DriverManager.getConnection(URL, USER, PASS));
						freeConnectionsCount++;
						connectionsToGenerateCount--;
					}
				}
				freeConnectionsCount--;
			}
			return connections.get(freeConnectionsCount);
		} catch (InterruptedException | SQLException e) {
			LOGGER.log(Level.FINE, e.toString(), e);
			throw new DataBaseException(Constants.CONNECTION_EXCEPTION, e);
		}
	}
	
	public void putConnection(Connection connection) {
		synchronized (connections) {
		connections.add(connection);
		freeConnectionsCount++;
		connections.notify();
		}
	}
	
	public void closePreparedStatement(PreparedStatement ps) {
		try {
			if(ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.FINE, e.getMessage(), e);
		}
	}
	public void closeResultSet(ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.FINE, e.getMessage(), e);
		}
	}
}