package com.nyimbozakristo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nyimbozakristo.users.Users;
import com.nyimbozakristo.users.UsersDAO;

/**
 * @author nelson
 *  
 * userDetailsService used by Spring security to authenticate on
 *         login
 */

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersDAO userDao;

	@Autowired
	private UserDetailsAdapter userDetailsAdapter;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		UserDetails userDetails = null;
		Users userEntity = userDao.getUser(username);

		if (userEntity == null) {
			throw new UsernameNotFoundException("user not found");
		}
		userDetails = userDetailsAdapter.buildUserFromUserEntity(userEntity);

		return userDetails;
	}
}