package by.gsu.epamlab.servlets;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.ConstantsJSP;
import by.gsu.epamlab.beans.User;

public class UserFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
    User user = (User) session.getAttribute(ConstantsJSP.KEY_USER);
    if (user == null) {
        session.invalidate();
        HttpServletResponse httpResponse = 
            (HttpServletResponse) response;
        httpResponse.sendRedirect(httpRequest.getContextPath() + Constants.START_PAGE); 
        return;
    }
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

}
