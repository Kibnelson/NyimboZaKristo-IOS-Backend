package com.nyimbozakristo.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.nyimbozakristo.users.Users;

/**
 * @author nelson
 * 
 *         userDetailsService used by Spring security to authenticate on login
 */
@Service("userDetailsAdapter")
public class UserDetailsAdapter {

	org.springframework.security.core.userdetails.User buildUserFromUserEntity(
			Users userEntity) {
		String username = userEntity.getUSERNAME();
		String password = userEntity.getPASSWORD();
		boolean enabled = userEntity.getENABLED();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
				username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
		return user;
	}

}