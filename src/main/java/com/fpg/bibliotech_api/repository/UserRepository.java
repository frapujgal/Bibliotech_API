package com.fpg.bibliotech_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpg.bibliotech_api.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    List<User> findByNameContaining(String name);
    User findByEmail(String email);
    User findByLogin(String login);

    boolean existsByEmail(String email);
}
