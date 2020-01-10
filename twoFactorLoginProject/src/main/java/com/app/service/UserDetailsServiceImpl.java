package com.app.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.model.Role;
import com.app.model.User;
import com.app.repo.UserRepo;
@Service

public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
		Set<GrantedAuthority> grantedRole = new HashSet<>();
		User user = userRepo.findByUserName(username);

		 if (user == null) throw new UsernameNotFoundException(username);

		for(Role role: user.getRoles()) {
			grantedRole.add(new SimpleGrantedAuthority(role.getName()));
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), 
				user.getPassword(), grantedRole);
	}

}
