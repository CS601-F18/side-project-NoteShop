package frontEnd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.GreatDataBase;

public class UserInfoServlet extends BaseServlet{
	
	/**
	 * display the user information page
	 * and the list of tickets
	 * also with the button to add new tags
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.checkLogin(request, response);
		
		GreatDataBase gdb = (GreatDataBase) getServletConfig().getServletContext().getAttribute(DATABASE);
		String user = (String) request.getSession().getAttribute(ACCOUNT);
		ResultSet res;
		
		PrintWriter out = prepareResponse(response);
		out.println(header("User Info", request));
		
		try {
			res = gdb.getUserData("userId", user);
			while(res.next()) {
				String firstName = res.getString("firstName");
				String lastName = res.getString("lastName");
				String detail = res.getString("detail");
				String tags = res.getString("tagBox");
				out.println("<p>User Id: " + user + "</p>");
				out.println("<p>First Name: " + firstName + "</p>");
				out.println("<p>Last Name: " + lastName + "</p>");
				out.println("<p>Detail: " + detail +"</p>");
				out.println("<p>Your tags: " + tags +"</p>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.println(formHeader("/userInfo"));
		out.println(formBody("New tag: (one tag)", "text", "tag"));
		out.println(formFooter());
		out.println(footer());
		
	}
	
	/**
	 * check if the add tag activity succeed
	 * display the correspond message and send back to the user page
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.checkLogin(request, response);
		GreatDataBase gdb = (GreatDataBase) getServletConfig().getServletContext().getAttribute(DATABASE);
		String userId = (String) request.getSession().getAttribute(ACCOUNT);
		String tag = request.getParameter("tag");
		
		PrintWriter out = prepareResponse(response);
		out.println(header("New Tag", request));
		if(gdb.addTag(userId, tag)) {
			out.println("<p>Succeed!<p>");
		}else{
			out.println("<p>failed!<p>");
		}
		out.println("<a href=\"userInfo\" style=" + ButtonStyle + ">Back</a>");
		out.println(footer());
	}

}
