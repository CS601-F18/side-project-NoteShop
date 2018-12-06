package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoteBase extends BasicBase{

	public NoteBase(Connection con, String table) {
		super(con, table);
		// TODO Auto-generated constructor stub
	}
	
	public boolean newNotes(String noteId, String name, String body, String tags, String creator) {	
		//open the link insert the parameter into the table
		String state = "INSERT INTO " + table + " (noteId, name, body, tags, creator) VALUES (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement insertStmt = con.prepareStatement(state);
			insertStmt.setString(1, noteId);
			insertStmt.setString(2, name);
			insertStmt.setString(3, body);
			insertStmt.setString(4, tags);
			insertStmt.setString(5, creator);
			insertStmt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * the search function which allow the user to search the events;
	 * if the type is num, then call available search function
	 * if not, use the like syntax in the mysql to partial search the term
	 * of the specific column
	 * @param type
	 * @param query
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public ResultSet partialSearch(String type, String query) throws NumberFormatException, SQLException {
		String sm = "select * from " + table + " where " + type + " like ?";
		PreparedStatement ps = con.prepareStatement(sm);
		ps.setString(1, "%" + query + "%");
		ResultSet res = ps.executeQuery();
		return res;
	}
	
	/**
	 * verify the creator of the event
	 * if the event creator is equal to the user id
	 * return true
	 * if not, return false;
	 * @param userId
	 * @param eventId
	 * @return
	 * @throws SQLException
	 */
	public boolean verifyCreator(String userId, String noteId){
		try {
			ResultSet res = super.getResult("noteId", noteId);
			while(res.next()) {
				String creator = res.getString("creator");
				if(creator.equals(userId)) {
					return true;
				}
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * update event data function,
	 * first call verify creator function to see if the user has the 
	 * right to modify it,
	 * then update the data in the data base
	 * @param userId
	 * @param noteId
	 * @param eventName
	 * @param date
	 * @param num
	 * @param detail
	 * @return
	 * @throws SQLException
	 */
	public boolean updateNote(String userId, String noteId, String body){
		if(!verifyCreator(userId, noteId)) {
			return false;
		}
		try {
			super.updateData("noteId", noteId, "body", body);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
