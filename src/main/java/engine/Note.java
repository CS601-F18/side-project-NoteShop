package engine;

import java.util.HashSet;

/**
 * the note class used to pass the data
 * from publisher to broker and subscriber
 * @author yalei
 *
 */
public class Note {
	private int noteId;
	private HashSet<String> tags;
	
	/**
	 * pass in the note id and set of tags
	 * @param id
	 * @param tags
	 */
	public Note(int id, HashSet<String> tags) {
		this.noteId = id;
		this.tags = tags;
	}

	/**
	 * return the note id
	 * @return
	 */
	public int getNoteId() {
		return noteId;
	}

	/**
	 * return the set of tags
	 * @return
	 */
	public HashSet<String> getTags() {
		return tags;
	}
	
	
}
