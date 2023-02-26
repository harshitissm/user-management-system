package com.example.habsida.umsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.habsida.umsystem.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private UserService userService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
	 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		
		 httpSecurity.authorizeHttpRequests().requestMatchers(
				 "/registration**",
				 "/js/**",
				 "/css/**",
				 "img/**").permitAll()
		 .anyRequest().authenticated()
		 .and()		
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/")
//			.successHandler(new CustomAuthenticationSuccessHandler())
			.permitAll()
			.and()
			.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout")
			.permitAll();
			
		httpSecurity.authenticationProvider(authenticationProvider());
		 
		return httpSecurity.build();
	}
	
}
 