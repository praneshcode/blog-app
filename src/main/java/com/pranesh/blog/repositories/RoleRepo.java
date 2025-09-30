package com.pranesh.blog.repositories;

import com.pranesh.blog.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pranesh.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

    Role findByName(String name) throws ResourceNotFoundException;
}
