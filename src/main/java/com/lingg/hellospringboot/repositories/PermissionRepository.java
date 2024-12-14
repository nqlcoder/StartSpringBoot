package com.lingg.hellospringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lingg.hellospringboot.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
