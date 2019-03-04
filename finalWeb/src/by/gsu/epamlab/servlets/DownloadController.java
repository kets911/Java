package by.gsu.epamlab.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.ConstantsJSP;
import by.gsu.epamlab.beans.Task;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.factories.TaskFactory;
import by.gsu.epamlab.impl.UserImplDB;
import by.gsu.epamlab.interfaces.ITaskDAO;


public class DownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UserImplDB.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user =(User) request.getSession().getAttribute(ConstantsJSP.KEY_USER);
		String defaultPathFile =(String) request.getServletContext().getAttribute(Constants.DEFAULT_FILE_PATH);
		String taskImplementation =(String) request.getServletContext().getAttribute(Constants.TASK_IMPLEMENTATION_KEY);
		String idTask = request.getParameter(ConstantsJSP.KEY_DOWNLOAD_TASK_ID);
		try {
			ITaskDAO taskImpl = TaskFactory.getTaskImpl(taskImplementation);
			Task task = taskImpl.getTask(Integer.parseInt(idTask));
			 File file = new File(defaultPathFile + user.getLogin() + Constants.PATH_DELIMENTR + task.getFileName());
		        response.setHeader("Content-Type", getServletContext().getMimeType(task.getFileName()));
		        response.setHeader("Content-Length", String.valueOf(file.length()));
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
		        Files.copy(file.toPath(), response.getOutputStream());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
	        forwardError(Constants.DOWNLOAD_EXCEPTION + e.getMessage(), request, response);
		}
	}
	protected void forwardError(String message, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(ConstantsJSP.KEY_ERROR_MESSAGE, message);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(Constants.MAIN_PAGE);
		rd.forward(request, response);
	}
}
