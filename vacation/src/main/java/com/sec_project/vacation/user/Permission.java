package com.sec_project.vacation.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
	
	
	ADMIN_READ("admin:read"),
	ADMIN_UPDATE("admin:update"),
	ADMIN_CREATE("admin:create"),
	ADMIN_DELETE("admin:delete"),
	
	User_READ("user:read"),
	USER_CREATE("user:create"),
	
	MANAGER_READ("anagement:read"),
	MANAGER_UPDATE("anagement:update"),
	MANAGER_CREATE("anagement:create"),
	MANAGER_DELETE("anagement:delete")
	
	;

	@Getter
	private final String premission;
}
