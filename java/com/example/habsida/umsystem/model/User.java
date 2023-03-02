package com.example.habsida.umsystem.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
	
@SuppressWarnings("serial")
@Entity
@Table(name="user")
public class User implements UserDetails {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		private String firstName;
		
		private String lastName;
		
		private int age;
		
		private String email;
		
		private String password;
		
		@Column(name = "roles")
		@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH ,
							CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
		@JoinTable(name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id",
				referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name = "role_id",
				referencedColumnName = "id"))
		private List<Role> roles;
		
		public User() {
			
		}

		public User(String firstName, String lastName, int age, String email, String password, List<Role> list) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
			this.email = email;
			this.password = password;
			this.roles = list;
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public List<Role> getRoles() {
			return roles;
		}

		public void setRoles(List<Role> roles) {
			this.roles = roles;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return roles;
		}

		@Override
		public String getUsername() {
			return email;
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
	
}
