package frontEnd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.GreatDataBase;

public class MainPageServlet extends BaseServlet{
	
	/**
	 * display the page which contain all the notes and the 
	 * link to other services
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.checkLogin(request, response);
		
		GreatDataBase gdb = (GreatDataBase) getServletConfig().getServletContext().getAttribute(DATABASE);
		ResultSet res;
		
		PrintWriter out = prepareResponse(response);
		out.println(header("Main Page", request));
		out.println(TableStyle);
		out.println("<tr><th>Note Id</th><th>Title</th><th>Tags</th><th>Author</th></tr>");
		try {
			res = gdb.getAllNotes();
			while(res.next()) {
				int noteId = res.getInt("noteId");
				String title = res.getString("name");
				String tags = res.getString("tags");
				String author = res.getString("creator");
				out.println("<tr><td><a href=\"note?noteId=" + noteId + "\">" + noteId + "</a></td>"
						      + "<td>" + title + "</td>"
						      + "<td>" + tags + "</td>"
						      + "<td>" + author + "</td></tr>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("</table>");
		out.println(footer());
		
	}
}
