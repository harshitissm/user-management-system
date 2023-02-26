package com.example.habsida.umsystem.service;

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

import com.example.habsida.umsystem.dto.UserRegistrationDto;
import com.example.habsida.umsystem.model.Role;
import com.example.habsida.umsystem.model.User;
import com.example.habsida.umsystem.repository.RoleRepository;
import com.example.habsida.umsystem.repository.UserRepository;

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

//	@Override
//	public void updateUser(UserRegistrationDto userRegistrationDto, Long id) {
//		
//		User existingUser = userRepository.findById(id).get();;
//		existingUser.setId(id);
//		existingUser.setFirstName(userRegistrationDto.getFirstName());
//		existingUser.setLastName(userRegistrationDto.getLastName());
//		existingUser.setAge(userRegistrationDto.getAge());
//		existingUser.setEmail(userRegistrationDto.getEmail());
//		existingUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
//		
//		userRepository.save(existingUser);
//	}

	@Override
	public void deleteStudentById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserRegistrationDto userToDto(User user) {
		
		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
		userRegistrationDto.setFirstName(user.getFirstName());
		userRegistrationDto.setLastName(user.getLastName());
		userRegistrationDto.setAge(user.getAge());
		userRegistrationDto.setEmail(user.getEmail());
		userRegistrationDto.setRole(user.getRoles().get(0).getName());
		return userRegistrationDto;
	}

	@Override
	public User updateUser(User user) {
		
		User existingUser = userRepository.findById(user.getId()).get();
		existingUser.setId(user.getId());
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setAge(user.getAge());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepository.save(existingUser);

	}

}




 



















