package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import engine.AsyncUnorderedDispatchBroker;
import engine.Broker;
import engine.Note;
import engine.NoteUser;
import engine.Subscriber;

/**
 * the data manager class for the server,
 * manage all the function which connect the server and data base
 * @author yalei
 *
 */
public class GreatDataBase {
	private static GreatDataBase instance;
	private UserBase users;
	private NoteBase notes;
	private LinkBase links;
	private final String TEST = "com.mysql.cj.jdbc.Driver";
	private String username, password, db, url, timezone;
	private String userTable, noteTable, linkTable;
	private Connection con;
	private ReentrantReadWriteLock userLock, noteLock, linkLock;
	private Broker b;
	private HashMap<String, Subscriber> map;
	
	/**
	 * the constructor of the class,
	 * call prepare param to read the parameters from config file
	 * then start up the connection
	 * finally initiate the three data bases
	 * @throws SQLException
	 */
	private GreatDataBase() throws SQLException {
		prepareParam();
		prepareConnection();
		
		this.users = new UserBase(con, userTable);
		this.notes = new NoteBase(con, noteTable);
		this.links = new LinkBase(con, linkTable);
		
		this.userLock = new ReentrantReadWriteLock();
		this.noteLock = new ReentrantReadWriteLock();
		this.linkLock = new ReentrantReadWriteLock();
		
		this.b = new AsyncUnorderedDispatchBroker(10, 20000);
		readDataBase();
	}
	
	public void readDataBase() {
		this.map = new HashMap<String, Subscriber>();
		
		try {
			ResultSet set = this.users.getAllResult();
			while(set.next()) {
				String tagBox = set.getString("tagBox").toLowerCase();
				String userId = set.getString("userId");
				String[] tags = tagBox.split("\\s+");
				NoteUser user = new NoteUser(this.links, userId, b);
				this.map.put(userId, user);
				for(String tag: tags) {
					user.addTag(tag);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * read the config file to get the parameters
	 */
	public void prepareParam() {
		ConfigReader cr = new ConfigReader("config.json");
		this.username = cr.getConfig("username");
		this.password = cr.getConfig("password");
		this.db = cr.getConfig("database");
		this.url = cr.getConfig("url");
		this.timezone = cr.getConfig("timezone");
		this.userTable = cr.getConfig("userTable");
		this.noteTable = cr.getConfig("noteTable");
		this.linkTable = cr.getConfig("linkTable");
	}

	/**
	 * start up the connection of the jdbc to the mysql database
	 * @throws SQLException
	 */
	public void prepareConnection() throws SQLException {
		try {
			// load driver
			Class.forName(TEST).newInstance();
		}
		catch (Exception e) {
			System.err.println("Can't find driver");
			System.exit(1);
		}

		con = DriverManager.getConnection(url + db + timezone, username, password);
	}
	
	/**
	 * get the singleton instance of AmazonDataBase
	 * @return
	 * @throws SQLException 
	 */
	public static synchronized GreatDataBase getInstance() throws SQLException {
		if(instance == null) {
			instance = new GreatDataBase();
		}
		return instance;
	}
	
	/**
	 * check the if the password is right according to the user id
	 * @param userId
	 * @param password
	 * @return
	 */
	public boolean checkPass(String userId, String password) {
		try {
			userLock.readLock().lock();
			return users.checkPass(userId, password);
		}finally {
			userLock.readLock().unlock();
		}
		
	}
	
	/**
	 * return if the user is already exit
	 * @param userId
	 * @return
	 */
	public boolean ifUserExist(String userId) {
		try {
			userLock.readLock().lock();
			return users.ifUserExist(userId);
		}finally {
			userLock.readLock().unlock();
		}
	}
	
	/**
	 * call the partial search method in the event base
	 * get all the event which partially has the value
	 * return table to the server
	 * @param type
	 * @param value
	 * @return
	 */
	public ResultSet partialSearch(String type, String value) {
		try {
			noteLock.readLock().lock();
			return this.notes.partialSearch(type, value);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			noteLock.readLock().unlock();
		}
	}
	
	/**
	 * get the joined table which contains
	 * the events which user has its tickets
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getUserIncomeNote(String userId) throws SQLException {
		String stmt = "SELECT * FROM links join notes "
				+ "on links.noteId=notes.noteId "
				+ "where userId=?";
		PreparedStatement select = con.prepareStatement(stmt);
		select.setString(1, userId);
		linkLock.readLock().lock();
		noteLock.readLock().lock();
		ResultSet res = select.executeQuery();
		linkLock.readLock().unlock();
		noteLock.readLock().unlock();
		return res;
	}
	
	
	public boolean publish(String noteId, String title, String body, String tags, String creator) {
		HashSet<String> tagSet = NoteFactory.getFreqTags(body);
		String fTags = finalTags(tags, tagSet);
		
		Note note = new Note(noteId, tagSet);
		this.b.publish(note);
		
		return this.notes.newNotes(noteId, title, body, fTags, creator);
	}
	
	public String finalTags(String tags, HashSet<String> tagSet) {
		String res = " ";
		String[] args = tags.split("\\s+");
		for(String s: args) {
			tagSet.add(s.toLowerCase());
		}
		for(String s: tagSet) {
			res += " " + s;
		}
		return res;
	}
	
	/**
	 * update the event by the parameters
	 * return true if succeed
	 * @param userId
	 * @param eventId
	 * @param eventName
	 * @param date
	 * @param num
	 * @param detail
	 * @return
	 */
	public boolean updateNote(String userId, String noteId, String body) {
		try {
			this.noteLock.writeLock().lock();
			return this.notes.updateNote(userId, noteId, body);		
		}finally {
			this.noteLock.writeLock().unlock();
		}
	}
	
	/**
	 * save the new user data into the data base
	 * return true if succeed
	 * @param userId
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param detail
	 * @return
	 * @throws SQLException
	 */
	public boolean saveUserData(String userId, String password, 
			String firstName, String lastName, String detail,
			String tagBox, int newNote, String own, String others) throws SQLException {
		try {
			this.userLock.writeLock().lock();
			return users.saveUserData(userId, password, firstName, lastName, detail, tagBox);
		}finally {
			NoteUser newUser = new NoteUser(links, userId, b);
			this.map.put(userId, newUser);
			this.userLock.writeLock().unlock();
		}
		
	}
	
	public boolean addTag(String userId, String tag) {
		NoteUser user = (NoteUser) this.map.get(userId);
		user.addTag(tag);
		return this.users.addData(userId, tag, "tagBox");
	}

	/**
	 * return all the notes to the server
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getAllEvents() throws SQLException {
		this.noteLock.readLock().lock();
		ResultSet res = this.notes.getAllResult();
		this.noteLock.readLock().unlock();
		return res;
	}
	
	/**
	 * return the user data by entering the query type
	 * and the value of the query
	 * @param query
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getUserData(String query, String value) throws SQLException {
		this.userLock.readLock().lock();
		ResultSet res = this.users.getResult(query, value);
		this.userLock.readLock().unlock();
		return res;
	}
	
	/**
	 * get the note data by the query and the 
	 * value of the query
	 * @param query
	 * @param value
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getNoteData(String query, String value) throws SQLException {
		this.noteLock.readLock().lock();
		ResultSet res = this.notes.getResult(query, value);
		this.noteLock.readLock().unlock();
		return res;
	}
	
}
