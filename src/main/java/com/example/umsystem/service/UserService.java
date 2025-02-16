package com.example.umsystem.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.umsystem.dto.UserRegistrationDto;
import com.example.umsystem.model.User;

public interface UserService extends UserDetailsService{

	User save(UserRegistrationDto registrationDto);
	
	public List<User> getAllUsers();
	
	public List<User> getOnlyUsers();
	
	User getUserById(Long id);
	
	User updateUser(User user, String role);
	
	void deleteStudentById(Long id);

}
