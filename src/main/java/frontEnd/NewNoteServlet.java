package frontEnd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.GreatDataBase;

public class NewNoteServlet extends BaseServlet{
	
	/**
	 * display the page to allow user write a new note
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.checkLogin(request, response);
		
		PrintWriter out = prepareResponse(response);
		out.println(header("New Note Page", request));
		
		out.println(formHeader("/newNote"));
		out.println(formBody("Title: ", "text", "name"));
		out.println(formBody("Tags (format: tag1 tag2 tag3): ", "text", "tags"));
		out.println("<br><br><textarea name=\"body\" style=\"width:800px;height:600px;\">body</textarea>");
		out.println(formFooter());
		out.println(footer());
	}
	
	/**
	 * call the data manager to publish the note
	 * send to the users who have register the tags of it
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.checkLogin(request, response);
		GreatDataBase gdb = (GreatDataBase) getServletConfig().getServletContext().getAttribute(DATABASE);
		String userId = (String) request.getSession().getAttribute(ACCOUNT);
		String title = request.getParameter("name");
		String body = request.getParameter("body");
		String tags = request.getParameter("tags");
		
		PrintWriter out = prepareResponse(response);
		out.println(header("New Note Page", request));
		if(gdb.publish(title, body, tags, userId)) {
			out.println("<p>Succeed!<p>");
		}else{
			out.println("<p>failed!<p>");
		}
		out.println("<a href=\"newNote\" style=" + ButtonStyle + ">Back</a>");
		out.println(footer());
	}

}
