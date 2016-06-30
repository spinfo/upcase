package de.uni_koeln.spinfo.upcase.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.uni_koeln.spinfo.upcase.mongodb.data.document.UpcaseUser;

public class UpcaseUserDetails implements UserDetails {

	private static final long serialVersionUID = -7841813932397898590L;
	private UpcaseUser user;

	public UpcaseUserDetails(final UpcaseUser user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		final Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		List<String> roles = null;
		if (user != null) {
			roles = user.getRoles();
		}
		if (roles != null) {
			for (String role : roles) {
				grantedAuthorities.add(new GrantedAuthority() {
					
					private static final long serialVersionUID = 1L;

					@Override
					public String getAuthority() {
						return role;
					}
				});
			}
		}

		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return user.getHash();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

}
