package de.uni_koeln.spinfo.upcase.mongodb.data.document.future.jtree;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

//Alternative format of the node (id & parent are required)

//	{
//	  id          : "string" // required
//	  parent      : "string" // required
//	  text        : "string" // node text
//	  icon        : "string" // string for custom
//	  state       : {
//	    opened    : boolean  // is the node open
//	    disabled  : boolean  // is the node disabled
//	    selected  : boolean  // is the node selected
//	  },
//	  li_attr     : {}  // attributes for the generated LI node
//	  a_attr      : {}  // attributes for the generated A node
//	}

@Document
public class Node {
	
	private String id;
	private String parent;
	private String text;
	private String icon;
	private State state;
	@JsonProperty(value="li_attr") private List<String> listAttributes;
	@JsonProperty(value="a_attr") private List<String> anchorAttributes;
	
	public Node(String id, String text, String parent) {
		this.id = id;
		this.text = text;
		this.parent = parent;
		this.state = new State();
		this.listAttributes = new ArrayList<>();
		this.anchorAttributes = new ArrayList<>();
	}
	
	class State {
		
		boolean opened;
		boolean disabled;
		boolean selected;
		
		public boolean isOpened() {
			return opened;
		}
		public void setOpened(boolean opened) {
			this.opened = opened;
		}
		public boolean isDisabled() {
			return disabled;
		}
		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}
		public boolean isSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<String> getListAttributes() {
		return listAttributes;
	}

	public void setListAttributes(List<String> listAttributes) {
		this.listAttributes = listAttributes;
	}

	public List<String> getAnchorAttributes() {
		return anchorAttributes;
	}

	public void setAnchorAttributes(List<String> anchorAttributes) {
		this.anchorAttributes = anchorAttributes;
	}

}
