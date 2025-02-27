package com.example.umsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.umsystem.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String role);
}
