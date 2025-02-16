package com.example.umsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

	private String firstName;
	
	private String lastName;
		
	private int age;
	
	private String email;

	private String password;
	
	private String role;

}
