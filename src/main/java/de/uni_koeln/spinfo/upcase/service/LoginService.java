package de.uni_koeln.spinfo.upcase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.util.DataBase;

public class LoginService implements UserDetailsService {
	
	@Autowired
	private DataBase db;

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		UpcaseUser user = db.getUpcaseUserRepository().findByEmail(arg0);
		return new UpcaseUserDetails(user);
	}

}
