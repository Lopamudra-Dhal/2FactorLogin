package com.app.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.model.User;
import com.app.repo.RoleRepo;
import com.app.repo.UserRepo;

@Service

public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public static String QR_PREFIX = 
			"https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
	public static String APP_NAME = "GoogleAppRegistration";

	@Override
	public void save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>(roleRepo.findAll()));
		userRepo.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepo.findByUserName(username);
	}


	 @Override
	    public String generateQRUrl(User user) throws UnsupportedEncodingException {
	        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");
	    }
}