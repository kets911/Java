package by.gsu.epamlab.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.ConstantsJSP;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.factories.UserFactory;
import by.gsu.epamlab.impl.ImplementationException;
import by.gsu.epamlab.interfaces.IUserDAO;


public class AuthorizationController extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(AuthorizationController.class.getName());
	private static final long serialVersionUID = 1L;
	private static String userImplementation;
	
 
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userImplementation = config.getInitParameter(Constants.USER_IMPLEMENTATION_KEY);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String href = request.getParameter(Constants.PARAM_OPERATION_TYPE);	
			if(ConstantsJSP.KEY_LOGOUT.equals(href)) {
				request.getSession().removeAttribute(ConstantsJSP.KEY_USER);
				response.sendRedirect(request.getContextPath() + Constants.START_PAGE);
			}else {
				IUserDAO userImpl = UserFactory.getClassFromFactory(userImplementation);
				String login = request.getParameter(ConstantsJSP.KEY_LOGIN);
				String password = request.getParameter(ConstantsJSP.KEY_PASSWORD);
				String buttonName = request.getParameter(ConstantsJSP.KEY_BUTTON);
				User user = null;
				if(ConstantsJSP.KEY_ENTER_BTN.equals(buttonName)) {
				user = userImpl.authorizationUser(login, password);
				}
				if(ConstantsJSP.KEY_REG_BTN.equals(buttonName)) {
					user = userImpl.registrationUser(login, password);
				}
				request.getSession().setAttribute(ConstantsJSP.KEY_USER, user);
				
				response.sendRedirect(request.getContextPath() + Constants.JUMP_TO_EVENT);
			}
		}catch (Exception e) {
			LOGGER.log(Level.FINE, e.toString(), e);
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
