package by.gsu.epamlab.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.ConstantsJSP;

public class MenuController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String menuOption = request.getParameter(Constants.PARAM_OPERATION_TYPE);
		request.getSession().setAttribute(ConstantsJSP.KEY_MENU_OPERATION, menuOption);
		response.sendRedirect(request.getContextPath() + Constants.JUMP_TO_EVENT);
	}

}
