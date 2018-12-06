package engine;

import java.util.HashSet;

public class Note {
	private String noteId;
	private HashSet<String> tags;
	
	public Note(String id, HashSet<String> tags) {
		this.noteId = id;
		this.tags = tags;
	}

	public String getNoteId() {
		return noteId;
	}

	public HashSet<String> getTags() {
		return tags;
	}
	
	
}
