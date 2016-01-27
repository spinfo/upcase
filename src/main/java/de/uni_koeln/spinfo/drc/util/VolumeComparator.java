package de.uni_koeln.spinfo.drc.util;

import java.util.Comparator;

import de.uni_koeln.spinfo.drc.lucene.SearchResult;

public class VolumeComparator implements Comparator<SearchResult> {

	private String asc;

	public VolumeComparator(String asc) {
		this.asc = asc;
	}

	@Override
	public int compare(SearchResult o1, SearchResult o2) {
		if(asc.equals("asc"))
			return o1.getVolumeTitle().compareTo(o2.getVolumeTitle());
		else 
			return o2.getVolumeTitle().compareTo(o1.getVolumeTitle());
	}

}
