package com.example.habsida.umsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.habsida.umsystem.model.Role;
import com.example.habsida.umsystem.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	List<User> findAllByRoles (Role role);
}
