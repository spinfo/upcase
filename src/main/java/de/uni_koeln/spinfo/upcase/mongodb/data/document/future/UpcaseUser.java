package de.uni_koeln.spinfo.upcase.mongodb.data.document.future;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import de.uni_koeln.spinfo.upcase.model.form.RegistrationForm;

@Document(collection = "ref_users")
public class UpcaseUser implements UserDetails {

	private static final long serialVersionUID = 7421580832939472903L;

	@Transient
	public static final String COLLECTION = "ref_users";

	@Id
	private String id;
	private Date creationDate;
	private Date lastLogin;
	private String email; // username
	private String institution;
	private String firstName;
	private String lastName;
	private String hash; // password
	private boolean enabled;

	// @DBRef private List<Collection> owns;
	private Set<String> owns;

	// @DBRef private List<Collection> contributes;
	private Set<String> contributesTo;

	@Transient
	private Set<GrantedAuthority> authorities;
	@Transient
	private boolean accountNonExpired;
	@Transient
	private boolean credentialsNonExpired;
	@Transient
	private boolean accountNonLocked;

	private Set<String> roles;

	public UpcaseUser() {
	}

	public UpcaseUser(final RegistrationForm registrationForm) {
		super();
		this.email = registrationForm.getEmail();
		this.creationDate = new Date();
		this.lastLogin = new Date();
		this.firstName = registrationForm.getFirstName();
		this.lastName = registrationForm.getLastName();
		this.institution = registrationForm.getInstitution();
		// this.hash = BCrypt.hashpw(registrationForm.getPassword(),
		// BCrypt.gensalt());
		this.hash = registrationForm.getPassword();
		this.owns = new HashSet<>();
		this.contributesTo = new HashSet<>();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		this.enabled = true;
		this.accountNonExpired = true;
		this.credentialsNonExpired = true;
		this.accountNonLocked = true;
		authorities.add(new GrantedAuthority() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return "ROLE_USER";
			}
		});
		
		roles = new HashSet<>();
		for (GrantedAuthority role : authorities) {
			roles.add(role.getAuthority());
		}
		
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	public UpcaseUser(final RegistrationForm registrationForm, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.email = registrationForm.getEmail();
		this.creationDate = new Date();
		this.lastLogin = new Date();
		this.firstName = registrationForm.getFirstName();
		this.lastName = registrationForm.getLastName();
		this.institution = registrationForm.getInstitution();
		// this.hash = BCrypt.hashpw(registrationForm.getPassword(),
		// BCrypt.gensalt());
		this.hash = registrationForm.getPassword();
		this.owns = new HashSet<>();
		this.contributesTo = new HashSet<>();
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		
		roles = new HashSet<>();
		for (GrantedAuthority role : authorities) {
			roles.add(role.getAuthority());
		}
		
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}

	public void addCollection(final String collectionId) {
		this.owns.add(collectionId);
	}

	public void removeCollection(final String collectionId) {
		this.owns.remove(collectionId);
	}

	public Set<String> getCollections() {
		return owns;
	}

	public Set<String> getContributions() {
		return contributesTo;
	}

	public void addContribution(final String collectionId) {
		this.contributesTo.add(collectionId);
	}

	public void removeContribution(final String collectionId) {
		this.contributesTo.remove(collectionId);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public int hashCode() {
		return email.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UpcaseUser) {
			return email.equals(((UpcaseUser) obj).email);
		}
		return false;
	}

	@Override
	public String toString() {
		return "UpcaseUser [role=" + roles + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return hash;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());

		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			if (g2.getAuthority() == null) {
				return -1;
			}

			if (g1.getAuthority() == null) {
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

}
