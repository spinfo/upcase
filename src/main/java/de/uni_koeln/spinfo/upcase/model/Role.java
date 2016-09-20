package de.uni_koeln.spinfo.upcase.model;

public enum Role {

	ADMIN("ROLE_ADMIN", "Admin"), TRUSTED("ROLE_TRUSTED_USER", "Internal"), GUEST("ROLE_GUEST", "Guest");

	private String roleId;
	private String roleName;

	private Role(String roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

}
