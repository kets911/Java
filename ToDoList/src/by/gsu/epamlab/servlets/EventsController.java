package by.gsu.epamlab.servlets;

import java.io.IOException;
import java.util.List;
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
import by.gsu.epamlab.impl.ImplementationException;
import by.gsu.epamlab.impl.UserImplDB;
import by.gsu.epamlab.interfaces.ITaskDAO;

public class EventsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UserImplDB.class.getName());
	private static final String defaultMenuOption = "Today";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user =(User) request.getSession().getAttribute(ConstantsJSP.KEY_USER);
		String taskImplementation =(String) request.getServletContext().getAttribute(Constants.TASK_IMPLEMENTATION_KEY);
			try {
				ITaskDAO taskImpl = TaskFactory.getTaskImpl(taskImplementation);

				String menuOption = (String) request.getSession().getAttribute(ConstantsJSP.KEY_MENU_OPERATION);
				if(menuOption == null) {
					menuOption = defaultMenuOption;
				}
				request.getSession().setAttribute(ConstantsJSP.KEY_MENU_OPERATION, menuOption);
				List<Task> tasks = taskImpl.getTasks(user, menuOption);
				request.setAttribute(ConstantsJSP.KEY_TASKS, tasks);
				RequestDispatcher rd = getServletContext().getRequestDispatcher(Constants.MAIN_PAGE);
		        rd.forward(request, response);
			}catch (ImplementationException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				forwardError(e.getMessage(), request, response);
			}
	}
	
	protected void forwardError(String message, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(ConstantsJSP.KEY_ERROR_MESSAGE, message);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(Constants.START_PAGE);
		rd.forward(request, response);
	}
}
