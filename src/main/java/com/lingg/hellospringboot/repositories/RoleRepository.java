package com.lingg.hellospringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lingg.hellospringboot.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
