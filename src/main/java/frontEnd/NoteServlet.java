package frontEnd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.GreatDataBase;

public class NoteServlet extends BaseServlet{
	
	/**
	 * display the note page
	 * contain all the note details
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.checkLogin(request, response);
		
		GreatDataBase gdb = (GreatDataBase) getServletConfig().getServletContext().getAttribute(DATABASE);
		int noteId = Integer.parseInt(request.getParameter("noteId"));
		ResultSet res;
		
		PrintWriter out = prepareResponse(response);
		out.println(header("Note Detail", request));
		
		try {
			res = gdb.getNoteData("noteId", noteId);
			while(res.next()) {
				String title = res.getString("name");
				String body = res.getString("body");
				String tags = res.getString("tags");
				String author = res.getString("creator");
				out.println("<p>Note Id: " + noteId + "</p>");
				out.println("<p>Title: " + title + "</p>");
				out.println("<p>Author: " + author + "</p>");
				out.println("<p>Tags: " + tags + "</p>");
				out.println("<p>" + body + "</p>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		out.println(footer());
		
	}

}
