package de.uni_koeln.spinfo.upcase.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;

@Service
public class UpcaseUserDetailsService implements UserDetailsService {

	// @formatter:off
	@Autowired private UpcaseUserRepository upcaseUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UpcaseUser user = upcaseUserRepository.findByEmail(username);
		
		if (user == null)
			throw new UsernameNotFoundException("User " + username + " not found.");
		
		return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
	}
	// @formatter:on

}
