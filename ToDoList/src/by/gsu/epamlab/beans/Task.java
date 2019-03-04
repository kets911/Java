package by.gsu.epamlab.beans;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.servlets.AuthorizationController;

public class Task {
	private static final Logger LOGGER = Logger.getLogger(AuthorizationController.class.getName());
	private static final String DATE_FORMATE_IN = "yyyy-MM-dd";
	private String name;
	private String fileName;
	private int id;
	private Date date;
	private boolean isDone;
	private boolean isIntoBin;
	
	public Task() {
		super();
	}
	
	public Task(String name, int id, Date date, boolean isDone, boolean isIntoBin, String fileName) {
		super();
		this.name = name;
		this.id = id;
		this.date = date;
		this.isDone = isDone;
		this.isIntoBin = isIntoBin;
		this.fileName = fileName;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setDate(long date) {
		this.date = new Date(date);
	}
	public void setDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_IN);
		try {
			setDate(sdf.parse(date).getTime());
		}catch (ParseException e) {
			LOGGER.log(Level.FINE, e.getMessage(), e);
			throw new IllegalArgumentException(Constants.DATE_EXCEPTION + date, e);
		}
	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public boolean isIntoBin() {
		return isIntoBin;
	}

	public void setIntoBin(boolean isIntoBin) {
		this.isIntoBin = isIntoBin;
	}
	
	
}
