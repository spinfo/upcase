package de.uni_koeln.spinfo.upcase.service.security;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.future.UpcaseUser;
import de.uni_koeln.spinfo.upcase.mongodb.repository.future.UpcaseUserRepository;

@Component
public class UpcaseAuthProvider implements AuthenticationProvider {

	// @formatter:off
	@Autowired private UpcaseUserRepository upcaseUserRepository;
	private UpcaseUserDetailsService userDetailsService;

	@Inject
	public UpcaseAuthProvider(UpcaseUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		logout();

		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		UserDetails principal = userDetailsService.loadUserByUsername(username);

		if (principal != null) {
			if (BCrypt.checkpw(password, principal.getPassword())) {
				Authentication authenticate = new UsernamePasswordAuthenticationToken(principal, "ignored", principal.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticate);
				upcaseUserRepository.updateLastLogin(authentication.getName());
				
				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			    HttpSession session = attr.getRequest().getSession(true);
			    session.setAttribute("userName", username);
				
				return authenticate;
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	public boolean loggedIn() {
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}

	public String getCurrentUserEmail() {
		if (loggedIn()) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			return authentication.getName();
		}
		return null;
	}

	public UpcaseUser getCurrentUser() {
		if (loggedIn()) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UpcaseUser user = upcaseUserRepository.findByEmail(authentication.getName());
			if (user != null) {
				return user;
			}
		}
		return null;
	}
	// @formatter:on

}
