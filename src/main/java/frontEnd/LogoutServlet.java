package frontEnd;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends BaseServlet{
	
	/**
	 * set the login value in the session to false
	 * then the user can not access the page without login
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		session.setAttribute(LOGIN, "false");
		session.setAttribute(ACCOUNT, null);
		response.sendRedirect(response.encodeRedirectURL("/login"));
	}

}
