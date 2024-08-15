package com.lingg.hellospringboot.repositories;

import com.lingg.hellospringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
}