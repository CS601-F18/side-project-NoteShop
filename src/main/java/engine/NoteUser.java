package engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.LinkBase;

/**
 * subclass of subscriber
 * used to stand for a user of the application
 * contain the method of adding new tags into the broker
 * and the method of receive the note
 * @author yalei
 *
 * @param <T>
 */
public class NoteUser<T> implements Subscriber<T>, Comparable<NoteUser<T>>{
	private final static String TableName = "users";
	private LinkBase links;
	String userId;
	private Broker<T> b;
	
	/**
	 * pass int the link base and the user id
	 * also with the broker which will subscribe
	 * @param links
	 * @param userId
	 * @param b
	 */
	public NoteUser(LinkBase links, String userId, Broker<T> b) {
		this.links = links;
		this.userId = userId;
		this.b = b;
	}
	
	/**
	 * add a new tag into the broker
	 * allow receive new kind of note
	 * @param tag
	 */
	public void addTag(String tag) {
		this.b.subscribe(this, tag);
	}
	
	/**
	 * save the new link into the link base
	 * to record which note the user has received
	 */
	@Override
	public void onEvent(T item) {
		// TODO Auto-generated method stub
		Note note = (Note) item;
		int id = note.getNoteId();		
		
		System.out.println("new link: " + userId + "/" + id);
		this.links.newLink(userId, id);
		
	}

	/**
	 * work for the hash set of subscriber
	 */
	@Override
	public int compareTo(NoteUser<T> o) {
		// TODO Auto-generated method stub
		return this.userId.compareTo(o.userId);
	}

}
