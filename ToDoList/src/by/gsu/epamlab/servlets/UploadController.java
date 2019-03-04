package by.gsu.epamlab.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.sun.jmx.snmp.Timestamp;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.ConstantsJSP;
import by.gsu.epamlab.beans.Task;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.factories.TaskFactory;
import by.gsu.epamlab.impl.UserImplDB;
import by.gsu.epamlab.interfaces.ITaskDAO;


public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UserImplDB.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user =(User) request.getSession().getAttribute(ConstantsJSP.KEY_USER);
		String menuOption =(String) request.getSession().getAttribute(ConstantsJSP.KEY_MENU_OPERATION);
		String defaultPathFile =(String) request.getServletContext().getAttribute(Constants.DEFAULT_FILE_PATH);
		String taskImplementation =(String) request.getServletContext().getAttribute(Constants.TASK_IMPLEMENTATION_KEY);
		String userFilePath = defaultPathFile + user.getLogin() + Constants.PATH_DELIMENTR;
		File dirFile = new File(defaultPathFile + user.getLogin());
		if(!dirFile.isDirectory()) {
			dirFile.mkdirs();
		}
		 try {
		        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		        Task task = new Task();
		        for (FileItem item : items) {
		            if (item.isFormField()) {
		                String fieldName = item.getFieldName();
		                String fieldValue = item.getString();
		                if(ConstantsJSP.KEY_DESCRIPTION_FIELD.equals(fieldName)) {
		                	if(fieldValue == null || fieldValue.isEmpty()) {
		                		throw new IllegalArgumentException(Constants.EMPTY_DESCRIPTION_EXCEPTION);
		                	}
		                	task.setName(fieldValue);
		                }
		                if(ConstantsJSP.KEY_DATE_FIELD.equals(fieldName)) {
		                	task.setDate(fieldValue);
		                }
		            } else {
		            	String fieldName = item.getFieldName();
		                String fileName = FilenameUtils.getName(item.getName());
		                if(ConstantsJSP.KEY_FILE_NAME_FIELD.equals(fieldName) && !fileName.isEmpty()) {
		                	InputStream fileContent = item.getInputStream();
			                File file = new File(userFilePath + new Timestamp().getDateTime()+fileName);
			                Files.copy(fileContent, file.toPath());
			                task.setFileName(file.getName());
		                }
		            }
		        }
		        ITaskDAO taskImpl = TaskFactory.getTaskImpl(taskImplementation);
		        taskImpl.setTask(user, task, menuOption);
		        response.sendRedirect(request.getContextPath() + Constants.JUMP_TO_EVENT);
		    } catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, e.getMessage(), e);
		        forwardError(e.getMessage(), request, response);
		        }
	}
	protected void forwardError(String message, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(ConstantsJSP.KEY_ERROR_MESSAGE, message);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(Constants.MAIN_PAGE);
		rd.forward(request, response);
	}

}
