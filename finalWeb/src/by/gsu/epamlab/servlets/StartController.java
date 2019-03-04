package by.gsu.epamlab.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.Constants;

public class StartController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String defaultFilePath = config.getInitParameter(Constants.DEFAULT_FILE_PATH);
		String taskImplementation = config.getInitParameter(Constants.TASK_IMPLEMENTATION_KEY);
		ServletContext context = config.getServletContext();
		context.setAttribute(Constants.TASK_IMPLEMENTATION_KEY, taskImplementation);
		context.setAttribute(Constants.DEFAULT_FILE_PATH, defaultFilePath);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(Constants.START_PAGE);
		dispatcher.forward(request, response);
	}

}
