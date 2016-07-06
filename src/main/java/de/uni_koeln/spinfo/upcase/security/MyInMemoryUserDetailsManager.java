package de.uni_koeln.spinfo.upcase.security;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class MyInMemoryUserDetailsManager extends InMemoryUserDetailsManager {

	private Collection<UserDetails> users;

	public MyInMemoryUserDetailsManager(Collection<UserDetails> users) {
		super(users);
		this.users = users;
	}
	
	public Collection<UserDetails> getUsers() {
		return users;
	}
}
