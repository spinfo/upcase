package de.uni_koeln.spinfo.upcase.util;

import java.util.Comparator;

import de.uni_koeln.spinfo.upcase.model.SearchResult;

public class ChapterComparator implements Comparator<SearchResult> {

	private String asc;

	public ChapterComparator(String asc) {
		this.asc = asc;
	}

	@Override
	public int compare(SearchResult o1, SearchResult o2) {
		return -1;
//		if(asc.equals("asc"))
//			return o1.getChapter().compareTo(o2.getChapter());
//		else 
//			return o2.getChapter().compareTo(o1.getChapter());
	}

}
