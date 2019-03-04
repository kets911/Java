package by.gsu.epamlab.servlets;

import java.io.File;
import java.io.IOException;
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

public class OperationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(UserImplDB.class.getName());
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user =(User) request.getSession().getAttribute(ConstantsJSP.KEY_USER);
		String taskImplementation =(String) request.getServletContext().getAttribute(Constants.TASK_IMPLEMENTATION_KEY);
		String buttonName = request.getParameter(ConstantsJSP.KEY_BUTTON);
		String menuOption =(String) request.getSession().getAttribute(ConstantsJSP.KEY_MENU_OPERATION);
		String[] TaskIds = request.getParameterValues(ConstantsJSP.KEY_IDS_FROM_CHECKBOXES);
		Task task = null;
		try {
			if(TaskIds == null) {
				forwardError(Constants.IDS_NULL_EXCEPTION, request, response, Constants.MAIN_PAGE);
			}else {
				ITaskDAO taskImpl = TaskFactory.getTaskImpl(taskImplementation);
				if(buttonName.equals(ConstantsJSP.KEY_DELET_ALL_BTN) || buttonName.equals(ConstantsJSP.KEY_DELET_BTN)) {
					if(ConstantsJSP.KEY_RECYCLE_BIN_MENU.equalsIgnoreCase(menuOption)) {
						String defaultPathFile =(String) request.getServletContext().getAttribute(Constants.DEFAULT_FILE_PATH);
						for(String id : TaskIds) {
							task = taskImpl.remove(Integer.parseInt(id));
							if(task.getFileName()!=null) {
								new File(defaultPathFile + user.getLogin() + Constants.PATH_DELIMENTR + task.getFileName()).delete();
							}
						}	
					}else {
						for(String id : TaskIds) {
							task = taskImpl.remove(Integer.parseInt(id));
							taskImpl.setTask(user, task, ConstantsJSP.KEY_RECYCLE_BIN_MENU);
						}	
					}
				}
				if(buttonName.equals(ConstantsJSP.KEY_DONE_BTN)) {
					for(String id : TaskIds) {
						task = taskImpl.remove(Integer.parseInt(id));
						task.setDone(true);
						taskImpl.setTask(user, task, menuOption);
					}
				}
				if(buttonName.equals(ConstantsJSP.KEY_RETURN_TASK_BTN)) {
					for(String id : TaskIds) {
						task = taskImpl.remove(Integer.parseInt(id));
						task.setIntoBin(false);
						taskImpl.setTask(user, task, ConstantsJSP.KEY_SOMEDAY_MENU);
					}
				}
				response.sendRedirect(request.getContextPath() + Constants.JUMP_TO_EVENT);
			}
		}catch (ImplementationException | IllegalArgumentException e) {
			LOGGER.log(Level.FINE, e.getMessage(), e);
			forwardError(e.getMessage(), request, response, Constants.MAIN_PAGE);
		}
	}

	protected void forwardError(String message, HttpServletRequest request,
			HttpServletResponse response, String page) throws ServletException, IOException {
		request.setAttribute(ConstantsJSP.KEY_ERROR_MESSAGE, message);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(page);
		rd.forward(request, response);
	}
}
