package de.uni_koeln.spinfo.drc.mongodb.data.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UpcaseUser {

	@Id
	private String id;
	private Date creationDate;
	private Date lastLogin;
	private String email;
	private String userName;
	private String hash;

	public UpcaseUser(String id, Date creationDate, Date lastLogin,
			String userName, String hash) {
		super();
		this.id = id;
		this.creationDate = creationDate;
		this.lastLogin = lastLogin;
		this.userName = userName;
		this.hash = hash;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
