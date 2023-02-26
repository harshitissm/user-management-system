package com.example.habsida.umsystem.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.habsida.umsystem.dto.UserRegistrationDto;
import com.example.habsida.umsystem.model.User;

public interface UserService extends UserDetailsService{

	User save(UserRegistrationDto registrationDto);
	
	public List<User> getAllUsers();
	
	User getUserById(Long id);
	
	User updateUser(User user);
	
	void deleteStudentById(Long id);

	UserRegistrationDto userToDto(User user);

//	void updateUser(UserRegistrationDto userRegistrationDto, Long id);

}
