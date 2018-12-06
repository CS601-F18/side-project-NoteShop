package database;

import java.util.HashMap;
import java.util.HashSet;

public class NoteFactory {

	public static HashSet<String> getFreqTags(String text) {
		HashMap<String, Integer> freqMap = new HashMap<String, Integer>();
		HashSet<String> res = new HashSet<String>();
		
		String[] args = text.split("\\s+");
		for(String s: args) {
			s = s.replaceAll("\\W", "");
			s = s.toLowerCase();
			if(!freqMap.containsKey(s)) {
				freqMap.put(s, 1);
			}else {
				int freq = freqMap.get(s);
				freq++;
				freqMap.put(s, freq);
			}
		}
		
		for(String s: freqMap.keySet()) {
			if(!StopWords.isStopword(s) && freqMap.get(s) >= 3) {
				res.add(s);
			}
		}
		return res;
	}
	
}
