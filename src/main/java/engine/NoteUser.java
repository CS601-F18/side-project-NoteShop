package engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.LinkBase;

public class NoteUser<T> implements Subscriber<T>, Comparable<NoteUser<T>>{
	private final static String TableName = "users";
	private LinkBase links;
	String userId;
	private Broker<T> b;
	
	public NoteUser(LinkBase links, String userId, Broker<T> b) {
		this.links = links;
		this.userId = userId;
		this.b = b;
	}
	
	public void addTag(String tag) {
		this.b.subscribe(this, tag);
	}
	
	@Override
	public void onEvent(T item) {
		// TODO Auto-generated method stub
		Note note = (Note) item;
		String id = note.getNoteId();		
		
		this.links.newLink(userId, id);
		
	}

	@Override
	public int compareTo(NoteUser<T> o) {
		// TODO Auto-generated method stub
		return this.userId.compareTo(o.userId);
	}

}
