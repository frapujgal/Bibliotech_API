package com.fpg.bibliotech_api.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpg.bibliotech_api.model.User;
import com.fpg.bibliotech_api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Get all users", description = "Get all users or filter by name or login")
    @GetMapping("")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String login) {
        try {
            List<User> users;
            
            if (login != null) {
                System.out.println("REQUEST: Getting user with login '" + login + "'");
                User user = userService.getUserByLogin(login);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            else if (name != null) {
                System.out.println("REQUEST: Getting users with name '" + name + "'");
                users = userService.getUsersByName(name);
            } else {
                System.out.println("REQUEST: Getting all users");
                users = userService.getAllUsers();
            }
            
            System.out.println("\t- " + users.size() + " users found");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        System.out.println("REQUEST: Getting user with id " + id);

        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add new user")
    @PostMapping("")
	public ResponseEntity<User> add(@RequestBody User user) {
		System.out.println("REQUEST: Adding new user...");

        try {
            User newUser = userService.addUser(user);
            System.out.println("\t- User added with id: " + newUser.getId());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            System.out.println("\t- ERROR: User with email " + user.getEmail() + " already exists");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        
	}

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
        System.out.println("REQUEST: Deleting user...");

		try {
			userService.deleteUser(id);
            System.out.println("\t- User deleted with id: " + id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NoSuchElementException e) {
            System.out.println("\t- ERROR: User with id " + id + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (EmptyResultDataAccessException e) {
            System.out.println("\t- ERROR: User with id " + id + " not found");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

    @Operation(summary = "Login user")
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        System.out.println("REQUEST: Logging in user...");

        try {
            User user = userService.login(username, password);
            System.out.println("\t- User logged in with id: " + user.getId());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println("\t- ERROR: User with login " + username + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.out.println("\t- ERROR: Incorrect password for user with login " + username);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
}
