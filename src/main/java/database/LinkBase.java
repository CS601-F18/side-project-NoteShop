package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The ticket base used to maintain the functions
 * and connection to the mysql server
 * @author yalei
 *
 */
public class LinkBase extends BasicBase{
	
	/**
	 * pass the connection and table name to build the data base
	 * @param con
	 * @param table
	 */
	public LinkBase(Connection con, String table) {
		super(con, table);
	}
	
	/**
	 * create a new ticket by entering the event id and user id
	 * loop for num times
	 * @param eventId
	 * @param noteId
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public boolean newLink(String userId, String noteId){
		//open the link insert the parameter into the table
		try {
			String state = "INSERT INTO " + table + " (userId, noteId) VALUES (?, ?)";
			PreparedStatement insertStmt = con.prepareStatement(state);
			insertStmt.setString(1, userId);
			insertStmt.setString(2, noteId);
			insertStmt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
