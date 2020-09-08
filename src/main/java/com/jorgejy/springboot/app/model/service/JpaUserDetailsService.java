package com.jorgejy.springboot.app.model.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jorgejy.springboot.app.model.dao.UserDao;
import com.jorgejy.springboot.app.model.entity.Role;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.jorgejy.springboot.app.model.entity.User user = userDao.findByUsername(username);

		if(user == null) {
			logger.error("Error login user no exist, user name:  "+ username);
			throw new UsernameNotFoundException("userName "+ username+ " no exist. ");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			logger.info("Role: "+role.getAuthority());
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		if(authorities.isEmpty()) {
			logger.error("Error login user no have roles, user name:  "+ username);
			throw new UsernameNotFoundException("userName "+ username+ " no have roles.");
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getEnabled(), true, true, true, authorities);
	}

}
