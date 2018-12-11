package frontEnd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.GreatDataBase;

public class NoteBoxServlet extends BaseServlet{
	
	/**
	 * display all the notes written by the user
	 * and the notes received from the other authors
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.checkLogin(request, response);
		
		GreatDataBase gdb = (GreatDataBase) getServletConfig().getServletContext().getAttribute(DATABASE);
		String userId = (String) request.getSession().getAttribute(ACCOUNT);
		ResultSet res;
		
		PrintWriter out = prepareResponse(response);
		out.println(header("Note Box", request));
		out.println("<a href=\"newNote\" style=" + ButtonStyle + ">Publish New Note</a><hr>");
		
		out.println("<p>Your Note:</p>");
		try {
			res = gdb.getNoteData("creator", userId);
			displayTable(out, res);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		out.println("<p>Received Note:</p>");
		
		try {
			res = gdb.getUserIncomeNote(userId);
			displayTable(out, res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(footer());
		
	}
	
	/**
	 * display the table of the result set;
	 * @param out
	 * @param res
	 */
	public void displayTable(PrintWriter out, ResultSet res) {
		out.println(TableStyle);
		out.println("<tr><th>Note Id</th>"
				+ "<th>Title</th>"
				+ "<th>Tags</th>"
				+ "<th>Author</th>"
				+ "</tr>");
		try {
			while(res.next()) {
				int id = res.getInt("noteId");
				String title = res.getString("name");
				String tags = res.getString("tags");
				String author = res.getString("creator");
				out.println("<tr><td><a href=\"note?noteId=" + id + "\">" + id + "</a></td>"
						      + "<td>" + title + "</td>"
						      + "<td>" + tags + "</td>"
						      + "<td>" + author + "</td>"
						      + "</tr>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("</table>");
	}
	

}
