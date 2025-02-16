package com.example.umsystem.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.umsystem.dto.UserRegistrationDto;
import com.example.umsystem.model.Role;
import com.example.umsystem.model.User;
import com.example.umsystem.repository.RoleRepository;
import com.example.umsystem.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		
		Role role = roleRepository.findByName(registrationDto.getRole());
		if(role == null) {
			role = new Role(registrationDto.getRole());
		}
		
		User user = new User(registrationDto.getFirstName(),
				registrationDto.getLastName(), registrationDto.getAge(),
				registrationDto.getEmail(), passwordEncoder.encode(registrationDto.getPassword()),
				Arrays.asList(role));
		
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public void deleteStudentById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User updateUser(User user, String role) {
		
		User existingUser = userRepository.findById(user.getId()).get();
		existingUser.setId(user.getId());
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setAge(user.getAge());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role roles = roleRepository.findByName(role);
		List<Role> list = new ArrayList<>();
		list.add(roles);
		existingUser.setRoles(list);
		
		return userRepository.save(existingUser);

	}

	@Override
	public List<User> getOnlyUsers() {
		
		Role role = roleRepository.findByName("USER");
		return userRepository.findAllByRoles(role);
	}

}




 



















