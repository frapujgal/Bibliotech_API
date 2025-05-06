package com.fpg.bibliotech_api.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpg.bibliotech_api.model.User;
import com.fpg.bibliotech_api.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
	private UserRepository userRepository;
    
    public UserService() {
	}

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users == null || users.isEmpty()) {
            throw new NoSuchElementException("No users found");
        }

        return users;
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id " + id + " not found"));
    }

    public List<User> getUsersByName(String name) {
        List<User> users = userRepository.findByNameContaining(name);

        if (users == null || users.isEmpty()) {
            throw new NoSuchElementException("User with name " + name + " not found");
        }

        return users;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new NoSuchElementException("User with email " + email + " not found");
        }

        return user;
    }

    public User getUserByLogin(String login) {
        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new NoSuchElementException("User with login " + login + " not found");
        }

        return user;
    }

    public User addUser(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("ERROR: User with email " + user.getEmail() + " already exists");
        }
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("ERROR: User with id " + id + " not found");
        }
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public User login(String username, String password) {
        
        User user = userRepository.findByLogin(username);

        if (user == null) {
            throw new NoSuchElementException("ERROR: Incorrect user");
        }

        if (user.getPassword().equals(password)) {
            return user;
        } else {
            throw new IllegalArgumentException("ERROR: Incorrect password");
        }
    }

    public User modifyUser(Integer id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            throw new NoSuchElementException("ERROR: User with id " + id + " not found");
        }
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        existingUser.setAddress(user.getAddress());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        
        return userRepository.save(existingUser);
    }

}
